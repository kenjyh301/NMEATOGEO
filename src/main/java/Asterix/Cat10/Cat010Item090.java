package Asterix.Cat10;

import jlg.jade.asterix.cat048.Cat048Item090;

import java.nio.ByteBuffer;

public class Cat010Item090 extends Cat048Item090 {
    public Cat010Item090(){}

    public byte[] encode(){
        byte[] itemData= new byte[2];
        itemData=ByteBuffer.allocate(this.getSizeInBytes())
                .putShort((short) getFlightLevelFeet()).array();
        itemData[1]|=(getFlightLevelValidated()==true)?0x80:0x00;
        itemData[1]|=(getFlightLevelGarbled()==true)?0x40:0x00;
        return itemData;
    }
}
