package Asterix.Cat10;

import junit.framework.TestCase;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

public class AsterixCat10BuilderTest extends TestCase {

    public void testBuild() {
        AsterixCat10Builder builder= new AsterixCat10Builder();
        System.out.println(Arrays.toString(builder.fspec));
        byte[] cat010Record=builder.SetDataSourceIdentifier(1,1)
                .SetMessageType(Cat010Item000.MessageType.EVENT_TRIGGERED_STATUS_MESSAGE)
                .SetTimeOfDay(200).SetTrackHeading(100).SetTrackNumber(100)
                .SetTrackVelocity(100).SetWGS84Coord(100,100).Build();
        System.out.println(Arrays.toString(cat010Record));

        assertEquals(builder.GetMessageLength(),25);
//        Cat010Item000 item00=new Cat010Item000();
//        item00.SetMessageType(Cat010Item000.MessageType.EVENT_TRIGGERED_STATUS_MESSAGE);
//        try{
//            Method m= Cat010Item000.class.getDeclaredMethod("encode",null);
//
//            m.invoke(null,null);
////            Byte[] dataEncode= ArrayUtils.toObject((byte[])m.invoke(null,null));
////            System.out.println(Arrays.toString(data));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }
}