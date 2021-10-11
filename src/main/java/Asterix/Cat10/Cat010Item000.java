package Asterix.Cat10;

import jlg.jade.asterix.FixedLengthAsterixData;
import jlg.jade.asterix.cat034.Cat034Item000MessageType;
import jlg.jade.common.UnsignedNumericDecoder;

import java.nio.ByteBuffer;

public class Cat010Item000 extends FixedLengthAsterixData {
    public enum MessageType {
        UNKNOWN(0),
        TARGET_REPORT(1),
        START_OF_UPDATE_CYCLE(2),
        PERIODIC_STATUS_MESSAGE(3),
        EVENT_TRIGGERED_STATUS_MESSAGE(4);

        private int value;

        private MessageType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    private MessageType messageType;
    public Cat010Item000(){}
    public void SetMessageType(MessageType type){
        this.messageType=type;
    }

    protected int setSizeInBytes() {
        return 1;
    }

    protected void decodeFromByteArray(byte[] input, int offset) {
        int messageTypeValue = UnsignedNumericDecoder.decodeFromOneByte(input, offset);
        switch(messageTypeValue) {
            case 1:
                this.messageType = MessageType.TARGET_REPORT;
                break;
            case 2:
                this.messageType = MessageType.START_OF_UPDATE_CYCLE;
                break;
            case 3:
                this.messageType = MessageType.PERIODIC_STATUS_MESSAGE;
                break;
            case 4:
                this.messageType = MessageType.EVENT_TRIGGERED_STATUS_MESSAGE;
                break;
            default:
                this.messageType = MessageType.UNKNOWN;
        }

        this.appendItemDebugMsg("Message Type", this.messageType);
    }

    protected String setDisplayName() {
        return "Cat010Item000 - Message Type";
    }

    public byte[] encode(){
        byte[] ret= ByteBuffer.allocate(1).put((byte)this.messageType.value).array();
        return ret;
    }
}
