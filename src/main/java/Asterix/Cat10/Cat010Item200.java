package Asterix.Cat10;

import jlg.jade.asterix.AsterixItemLength;
import jlg.jade.asterix.FixedLengthAsterixData;
import jlg.jade.asterix.cat048.Cat048Item200;
import jlg.jade.common.UnsignedNumericDecoder;

import java.nio.ByteBuffer;

public class Cat010Item200 extends FixedLengthAsterixData {
    public Cat010Item200(){

    }
    private int calculatedGroundSpeed;
    private int calculatedHeading;


    protected int setSizeInBytes() {
        return AsterixItemLength.FOUR_BYTES.getValue();
    }

    protected void decodeFromByteArray(byte[] input, int offset) {
        this.calculatedGroundSpeed = UnsignedNumericDecoder.decodeFromTwoBytes(input, offset);
        this.calculatedHeading = UnsignedNumericDecoder.decodeFromTwoBytes(input, offset + 2);
        this.appendItemDebugMsg("Calculated ground speed", this.calculatedGroundSpeed);
        this.appendItemDebugMsg("Calculated heading", this.calculatedHeading);
    }

    protected String setDisplayName() {
        return "Cat010Item200 - Calculated Track Velocity in Polar Co-ordinates";
    }

    public int getCalculatedGroundSpeed() {
        return this.calculatedGroundSpeed;
    }

    public int getCalculatedHeading() {
        return this.calculatedHeading;
    }

    public void setCalculatedGroundSpeed(int value){
        this.calculatedGroundSpeed= value;
    }

    public void setCalculatedHeading(int value){
        this.calculatedHeading=value;
    }

    @Override
    public byte[] encode(){
        byte[] ret= ByteBuffer.allocate(this.sizeInBytes).putShort(0,(short)this.calculatedGroundSpeed)
                                                            .putShort(2,(short)this.calculatedHeading).array();
        return ret;

    }

}
