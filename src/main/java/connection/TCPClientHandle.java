package connection;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.NMEAMessage;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static model.DataQueue.receiveMessageQueue;

@NoArgsConstructor
@Slf4j
@ChannelHandler.Sharable
public class TCPClientHandle extends ChannelInboundHandlerAdapter {
    ChannelHandlerContext ctx;
    List<String> buffer= new ArrayList<>();
    final int messageLenMax=100;
    List<Byte> header= new ArrayList<Byte>();
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        header.add((byte)'$');
        header.add((byte)'!');
        this.ctx=ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf= (ByteBuf) msg;
        byte[] bytes= ByteBufUtil.getBytes(byteBuf);
        String str= new String(bytes, StandardCharsets.UTF_8);
        log.trace("Receive new tcp message {}",str);
//        System.out.print(str);
        Process(bytes);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.warn("Exception caught in handle client");
        cause.printStackTrace();
        ctx.close();
    }

    public void Process(byte[] newData){
        String newDataStr= new String(newData,StandardCharsets.UTF_8);
        if(buffer.size()==0){
            buffer.add(newDataStr);
        }else if(header.contains(newData[0])){
            String message="";
            for(String piece:buffer){
                message+=piece;
            }
            message = message.replace("\n", "").replace("\r", "");
            buffer.clear();
            if(message.length()<messageLenMax&&message.length()>0){
                NMEAMessage nmeaMessage= new NMEAMessage(message);
                boolean state=receiveMessageQueue.add(nmeaMessage);
                if(state){
                    log.info("Add new to receive queue. Queue size{}",receiveMessageQueue.size());
                }else{
                    log.info("Error add new to receive queue. Queue size{}",receiveMessageQueue.size());
                }
            }else{
                log.warn("Message length is not proper");
            }
            buffer.add(newDataStr);
        }else{
            buffer.add(newDataStr);
        }
    }


}
