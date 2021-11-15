package Asterix.Cat10;

import junit.framework.TestCase;

import java.util.Arrays;

public class AsterixCat10LightPSRTrackBuilderTest extends TestCase {

    public void testBuild() {
        AsterixCat10LightPSRTrackBuilder builder= new AsterixCat10LightPSRTrackBuilder();
        System.out.println(Arrays.toString(builder.fspec));
        byte[] cat010Record=builder.SetDataSourceIdentifier(1,1)
                .SetMessageType(Cat010Item000.MessageType.EVENT_TRIGGERED_STATUS_MESSAGE)
                .SetTimeOfDay(200).SetTrackHeading(100).SetTrackNumber(100)
                .SetTrackVelocity(100).SetWGS84Coord(100,100).
                        SetReportDesc().SetTrackStatus().Build();
        System.out.println(Arrays.toString(cat010Record));

        assertEquals(builder.GetMessageLength(),27);
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