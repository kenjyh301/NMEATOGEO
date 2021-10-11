package service;

import Asterix.Cat10.AsterixCat10Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import model.GlobalPoint;

import java.util.Arrays;

import org.apache.commons.codec.binary.Hex;

import static model.DataQueue.outTargetQueue;

@Slf4j
public class AsterixService extends Thread{

    @SneakyThrows
    @Override
    public void run(){
        while(true){
            if(!outTargetQueue.isEmpty()){
                GlobalPoint processingPoint= outTargetQueue.poll();
                AsterixCat10Builder builder= new AsterixCat10Builder();
                builder= builder.SetGlobalPoint(processingPoint).SetDataSourceIdentifier(1,1);
                byte[] cat10Record= builder.Build();
                log.info("Encode data {}", Hex.encodeHexString(cat10Record));
            }else{
                Thread.sleep(100);
            }
        }

    }
}
