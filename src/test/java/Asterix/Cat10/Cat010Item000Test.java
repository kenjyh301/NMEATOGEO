package Asterix.Cat10;

import junit.framework.TestCase;

public class Cat010Item000Test extends TestCase {

    public void testEncode() {
        Cat010Item000 item= new Cat010Item000();
        item.SetMessageType(Cat010Item000.MessageType.EVENT_TRIGGERED_STATUS_MESSAGE);
        byte[] value= item.encode();
        assertEquals(value.length,1);
        assertEquals(value[0], Cat010Item000.MessageType.EVENT_TRIGGERED_STATUS_MESSAGE.getValue());
    }
}