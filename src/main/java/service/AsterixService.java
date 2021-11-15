package service;

import Asterix.Cat10.AsterixCat10LightPSRTrackBuilder;
import Asterix.Cat10.AsterixCat10Type1Builder;
import connection.TCPServer;
import io.netty.buffer.Unpooled;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import model.GlobalPoint;

import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.FileOutputStream;

import static model.DataQueue.outTargetQueue;

@Slf4j
public class AsterixService extends Thread{
    private TCPServer server;
    FileOutputStream fos;

    public AsterixService(TCPServer server){
        this.server= new TCPServer();
        this.server=server;
    }
    @SneakyThrows
    @Override
    public void run(){
//        File file= new File("cat10.dat");
//        fos= new FileOutputStream(file);
        while(true){
            if(!outTargetQueue.isEmpty()){
                GlobalPoint processingPoint= outTargetQueue.poll();
                AsterixCat10Type1Builder builder= new AsterixCat10Type1Builder();
                builder= builder.SetGlobalPoint(processingPoint).SetDataSourceIdentifier(1,1)
                                .SetReportDesc().SetFlightLevelDefault();
                byte[] cat10Record= builder.Build();
                try{
                    server.sendMessage(Unpooled.wrappedBuffer(cat10Record));
                    log.info("Encode data {}", Hex.encodeHexString(cat10Record));
//                    fos.write(cat10Record);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }else{
                Thread.sleep(100);
            }
        }

    }
}
