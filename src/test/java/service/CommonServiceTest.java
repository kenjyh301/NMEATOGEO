package service;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import model.GlobalPoint;
import model.NMEAMessage;
import model.RadarPoint;
import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;

import java.nio.charset.StandardCharsets;

@Slf4j
public class CommonServiceTest extends TestCase {

    public void testGetSecondPoint(){
        String GPSString= "$GPGLL,1911.87939,N,10700.68404,E,100906.17,A,A*6D";
        byte[] GPSBytes= GPSString.getBytes(StandardCharsets.UTF_8);
        String TTMString= "$RATTM,01,164.41,43.5,T,300.0,120.0,T,85.92,-28.04,N,TQ,T,,025939.03,A*25";
        byte[] TTMBytes= TTMString.getBytes(StandardCharsets.UTF_8);
        NMEAMessage message1= new NMEAMessage(GPSBytes);
        NMEAMessageService service1= new NMEAMessageService(message1);
        GPSMessageService gps= new GPSMessageService(service1);

        NMEAMessage message2= new NMEAMessage(TTMBytes);
        NMEAMessageService service2= new NMEAMessageService(message2);
        TTMMessageService ttm= new TTMMessageService(service2);

        GlobalPoint globalPoint= gps.GetGeodetic();
        RadarPoint radarPoint= ttm.GetPolar();

        CommonService commonService= new CommonService();
        GeodesicData data= commonService.GetGeodesicData(globalPoint,radarPoint);
        GlobalPoint outPoint= commonService.GetSecondPoint(data);

//        log.info("Longitude point1 {} : Latitude point1 {}",globalPoint.getLongitude(),globalPoint.getLatitude());

        log.info("Longitude {} : Latitude {}",outPoint.getLongitude(),outPoint.getLatitude());
        log.info("Range {} meters Azimuth {} degree",radarPoint.getRange(),radarPoint.getAzimuth());


    }
}