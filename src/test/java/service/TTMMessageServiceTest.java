package service;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import model.GlobalPoint;
import model.NMEAMessage;
import model.RadarPoint;

import java.nio.charset.StandardCharsets;

@Slf4j
public class TTMMessageServiceTest extends TestCase {
    String TCPString= "$RATTM,01,164.41,43.5,T,300.0,120.0,T,85.92,-28.04,N,TQ,T,,025939.03,A*25";
    byte[] TCPBytes= TCPString.getBytes(StandardCharsets.UTF_8);
    public void testGetPolar() {
        NMEAMessage message= new NMEAMessage(TCPBytes);
        NMEAMessageService service= new NMEAMessageService(message);
        if(service.IsValid()){
            if(service.GetSentenceID().equals("TTM")){
                TTMMessageService ttmMessageService= new TTMMessageService(service);
                RadarPoint radarPoint= ttmMessageService.GetPolar();
                log.info ("Range {}  azimuth {}",radarPoint.getRange(),radarPoint.getAzimuth());
            }else{
                log.info("{} Not TTM Message",service.GetSentenceID());
            }
        }else{
            log.info("NMEA message is not valid");

        }
    }
}