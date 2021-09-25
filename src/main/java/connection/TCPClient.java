package connection;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.Socket;

@Slf4j
@AllArgsConstructor
public class TCPClient extends Thread {
    String host;
    int port;

    @SneakyThrows
    @Override
    public void run(){
        log.info("Try to connect to tcp Server {}:{}",host,port);
        EventLoopGroup workerGroup= new NioEventLoopGroup();
        TCPClientHandle clientHandle= new TCPClientHandle();
        ChannelFuture future= null;
//        while(true){
            try{
                Bootstrap bootstrap= new Bootstrap();
                bootstrap.group(workerGroup);
                bootstrap.channel(NioSocketChannel.class);
                bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
                bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel){

                        socketChannel.pipeline().addLast(new ByteArrayEncoder());
                        socketChannel.pipeline().addLast(clientHandle);
                    }
                });

                future= null;
                while(true){
                    try{
                        log.info("Trying to connect to server");
                        future= bootstrap.connect(host,port).sync();
                        log.info("Server {}:{} opened",host,port);
                        future.channel().closeFuture().sync();

//                        break;
                    }catch (Exception e){
                        Thread.sleep(3000);
//                    e.printStackTrace();
                    }
                }


            }finally {

            }

//            Thread.sleep(1000);
//        }
    }
}
