package Asterix.Cat10;

import jlg.jade.asterix.cat048.Cat048Item010;
import lombok.extern.slf4j.Slf4j;
import model.GlobalPoint;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
//@Data
public class AsterixCat10LightPSRTrackBuilder {
    private Cat048Item010 item010;
    private Cat010Item000 item000;
    private Cat010Item020 item020;
    private Cat010Item140 item140;
    private Cat010Item041 item041;
    private Cat010Item200 item200;
    private Cat010Item161 item161;
    private Cat010Item170 item170;
    byte[] fspec;
    int fspecEndIndex;  //number used bytes in fspec
    private final int CATSIZE=1;
    private final int LENSIZE=2;
    private final float speedLSB=(float)6.103E-5D; // unit knot
    private final float angleLSB=(float)0.0055; // unit degree
    private final float timeLSB= (float)(1.0/128.0); // LSB of time 1/128 second

    public void SetFspec(int index){
        int byteIndex= (index-1)/8;
        int bitIndex=7-(index-1)%8;
        fspec[byteIndex]=(byte)(fspec[byteIndex]|(1<<bitIndex));
    }
    public void SetFspecFx(){
        int fspecLength= fspec.length;
        fspecEndIndex=0;
        for(;fspecEndIndex<fspecLength;fspecEndIndex++){
            if(fspec[fspecEndIndex]==0)break;
        }
        for(int i=0;i<fspecEndIndex-1;i++){
            fspec[i]=(byte)(fspec[i]|1);
        }
    }
//    private Cat034Item000 test;
    public AsterixCat10LightPSRTrackBuilder(){
        fspec= new byte[5];
        Arrays.fill(fspec,(byte)0);
        item010= new Cat048Item010();
        SetFspec(Fspec.CAT10_010);
        item000= new Cat010Item000();
        SetFspec(Fspec.CAT10_000);
        item020= new Cat010Item020();
        SetFspec(Fspec.CAT10_020);
        item140= new Cat010Item140();
        SetFspec(Fspec.CAT10_140);
        item041= new Cat010Item041();
        SetFspec(Fspec.CAT10_041);
        item200= new Cat010Item200();
        SetFspec(Fspec.CAT10_200);
        item161= new Cat010Item161();
        SetFspec(Fspec.CAT10_161);
        item170= new Cat010Item170();
        SetFspec(Fspec.CAT10_170);
        SetFspecFx();

    }

    public AsterixCat10LightPSRTrackBuilder SetDataSourceIdentifier(int sic, int sac){
        item010.setSic(sic);
        item010.setSac(sac);
        return this;
    }
    public AsterixCat10LightPSRTrackBuilder SetMessageType(Cat010Item000.MessageType type){
        item000.SetMessageType(type);
        return this;
    }
    public AsterixCat10LightPSRTrackBuilder SetTimeOfDay(int time){
        item140.setTime(time);
        return this;
    }
    public AsterixCat10LightPSRTrackBuilder SetTimeOfDay(){
        LocalDateTime date = LocalDateTime.now();
        int seconds = date.toLocalTime().toSecondOfDay();
        item140.setTime((int)(seconds/timeLSB));
        return this;
    }

    public AsterixCat10LightPSRTrackBuilder SetWGS84Coord(int latitude, int longitude){
        item041.setLatitudeWsg84(latitude);
        item041.setLongitudeWsg84(longitude);
        return this;
    }
    public AsterixCat10LightPSRTrackBuilder SetTrackVelocity(int value){
        item200.setCalculatedGroundSpeed(value);
        return this;
    }
    public AsterixCat10LightPSRTrackBuilder SetTrackHeading(int value){
        item200.setCalculatedHeading(value);
        return this;
    }
    public AsterixCat10LightPSRTrackBuilder SetTrackNumber(int number){
        item161.setTrackNb(number);
        return this;
    }
    public AsterixCat10LightPSRTrackBuilder SetReportDesc(){
        item020.setTyp(Cat010Item020.TYP.ModeSMultilateration);
        return this;
    }
    public AsterixCat10LightPSRTrackBuilder SetTrackStatus(){
        item170.setCnf(Cat010Item170.CNF.TrackInInitialisationPhase);
        return this;
    }
    public AsterixCat10LightPSRTrackBuilder SetGlobalPoint(GlobalPoint point){
        SetMessageType(Cat010Item000.MessageType.TARGET_REPORT);
        SetTimeOfDay();
        float speedInKnot= point.getVesselSpeed();
        int speedValue= (int)(speedInKnot/speedLSB);
        float angleInDegree= point.getHeading();
        int angleValue= (int)(angleInDegree/angleLSB);
        SetTrackVelocity(speedValue);
        SetTrackHeading(angleValue);
        SetTrackNumber(point.getTargetNumber());
        int latValue=(int)( point.getLatitude()/ Cat010Item041.LSB);
        int longValue=(int)( point.getLongitude()/ Cat010Item041.LSB);
        SetWGS84Coord(latValue,longValue);
        return this;
    }
    public short GetMessageLength(){
        int ret= CATSIZE+ LENSIZE+ fspecEndIndex+ item010.getSizeInBytes()
                + item000.getSizeInBytes()+ item020.getSizeInBytes()
                + item140.getSizeInBytes() + item041.getSizeInBytes()
                + item200.getSizeInBytes() +item161.getSizeInBytes()
                +item170.getSizeInBytes();
        return (short)ret;
    }

    public byte[] Build(){
        ArrayList<Byte> data= new ArrayList<>();
        data.add((byte)10);//Cat010
        byte[] bytesLen= ByteBuffer.allocate(2).putShort(GetMessageLength()).array();
        Byte[] tmp=ArrayUtils.toObject(bytesLen);
        Collections.addAll(data,tmp);
        byte[] fspecData= Arrays.copyOfRange(fspec,0,fspecEndIndex);
        tmp=ArrayUtils.toObject(fspecData);
        Collections.addAll(data,tmp);
//        Field[] fields= AsterixCat10Builder.class.getDeclaredFields();
//        for(Field field:fields){
//            try{
//                Method method=field.getClass().getDeclaredMethod("encode");
//                Byte[] dataEncode= ArrayUtils.toObject((byte[])method.invoke(this.,null));
//                Collections.addAll(data,dataEncode);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//        }
        tmp= ArrayUtils.toObject(item010.encode());
        Collections.addAll(data,tmp);
        tmp= ArrayUtils.toObject(item000.encode());
        Collections.addAll(data,tmp);
        tmp=ArrayUtils.toObject(item020.encode());
        Collections.addAll(data,tmp);
        tmp= ArrayUtils.toObject(item140.encode());
        Collections.addAll(data,tmp);
        tmp= ArrayUtils.toObject(item041.encode());
        Collections.addAll(data,tmp);
        tmp= ArrayUtils.toObject(item200.encode());
        Collections.addAll(data,tmp);
        tmp= ArrayUtils.toObject(item161.encode());
        Collections.addAll(data,tmp);
        tmp= ArrayUtils.toObject(item170.encode());
        Collections.addAll(data,tmp);

        byte[] ret = new byte[data.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = (byte) data.get(i);
        }
        return ret;
    }
}
