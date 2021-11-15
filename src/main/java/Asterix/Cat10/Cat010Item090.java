package Asterix.Cat10;

import jlg.jade.asterix.AsterixItemLength;
import jlg.jade.asterix.FixedLengthAsterixData;
import jlg.jade.asterix.cat048.Cat048Item090;

import java.nio.ByteBuffer;
import java.util.BitSet;

public class Cat010Item090 extends FixedLengthAsterixData {
    private int flightLevel;
    private boolean flightLevelValidated;
    private boolean flightLevelGarbled;

    public Cat010Item090() {
    }

    protected int setSizeInBytes() {
        return AsterixItemLength.TWO_BYTES.getValue();
    }

    protected void decodeFromByteArray(byte[] input, int offset) {
        BitSet firstOctetBits = BitSet.valueOf(new byte[]{input[offset]});
        int firstOctetValue = Byte.toUnsignedInt(input[offset]);

        if (firstOctetBits.get(7)) {
            this.flightLevelValidated = false;
        } else {
            this.flightLevelValidated = true;
        }

        this.appendItemDebugMsg("Flight level validated", this.flightLevelValidated);

        if (firstOctetBits.get(6)) {
            this.flightLevelGarbled = true;
        } else {
            this.flightLevelGarbled = false;
        }

        this.appendItemDebugMsg("Flight level garbled", this.flightLevelGarbled);
        if (firstOctetBits.get(7)) {
            firstOctetValue -= 128;
        }

        if (firstOctetBits.get(6)) {
            firstOctetValue -= 64;
        }

        this.flightLevel = firstOctetValue * 256 + Byte.toUnsignedInt(input[offset + 1]);
        this.appendItemDebugMsg("Flight level (ft)", this.getFlightLevel());
    }

    protected String setDisplayName() {
        return "Cat048Item090 - Flight Level in Binary Representation";
    }

    public float getFlightLevel() {
        return (this.flightLevel * (float)0.25);
    }

    public boolean getFlightLevelValidated() {
        return this.flightLevelValidated;
    }

    public boolean getFlightLevelGarbled() {
        return this.flightLevelGarbled;
    }

    public void setFlightLevelValidated(boolean value){
        this.flightLevelValidated=value;
    }

    public void setFlightLevelGarbled(boolean value){
        this.flightLevelGarbled=value;
    }

    public void setFlightLevel(float value) {
        this.flightLevel = (int)(value*25);
    }



    @Override
    public byte[] encode(){
        byte[] itemData= new byte[2];
        itemData=ByteBuffer.allocate(this.getSizeInBytes())
                .putShort((short) getFlightLevel()).array();
        itemData[1]|=(getFlightLevelValidated()==true)?0x80:0x00;
        itemData[1]|=(getFlightLevelGarbled()==true)?0x40:0x00;
        return itemData;
    }
}
