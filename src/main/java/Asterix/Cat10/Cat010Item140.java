package Asterix.Cat10;

import jlg.jade.asterix.cat048.Cat048Item140;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Cat010Item140 extends Cat048Item140 {
    public Cat010Item140(){

    }
    @Override
    public byte[] encode(){
        byte[] value= ByteBuffer.allocate(4).putInt(this.getTime()).array();
        byte[] ret= Arrays.copyOfRange(value,4-this.sizeInBytes,4);
        return ret;
    }
}
