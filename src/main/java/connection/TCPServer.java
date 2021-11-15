package connection;

import Config.ReadConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TCPServer extends Thread{
    TCPServerHandle handle= new TCPServerHandle();
    @Override
    public void run(){
        EventLoopGroup workerGroup= new NioEventLoopGroup();
        EventLoopGroup bossGroup= new NioEventLoopGroup();

        try{
            while(true){
                ServerBootstrap server= new ServerBootstrap();
                server.group(workerGroup,bossGroup).channel(NioServerSocketChannel.class);
//                        .option(ChannelOption.SO_KEEPALIVE,true);
                server.childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel){
                        socketChannel.pipeline().addLast(new ByteArrayDecoder());
                        socketChannel.pipeline().addLast(handle);
                    }
                }
                );

                //start server
                ChannelFuture channelFuture= null;
                try{
                    channelFuture= server.bind(ReadConfig.projectConfig.getTCPServerPort()).sync();
                    log.info("server bind successfull port: {}",ReadConfig.projectConfig.getTCPServerPort());
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

                try{
                    channelFuture.channel().closeFuture().sync();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }finally{
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }


    public void sendMessage(ByteBuf message){
        handle.sendMessage(message);
    }

}

