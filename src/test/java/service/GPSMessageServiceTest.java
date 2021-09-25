package service;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import model.GlobalPoint;
import model.NMEAMessage;

import java.nio.charset.StandardCharsets;

@Slf4j
public class GPSMessageServiceTest extends TestCase {
    String TCPString= "$GPGLL,1911.87939,N,10700.68404,E,100906.17,A,A*6D";
    byte[] TCPBytes= TCPString.getBytes(StandardCharsets.UTF_8);
    public void testGetGeodetic() {
        log.info("Start GPSMessage test");
        NMEAMessage message= new NMEAMessage(TCPBytes);
        NMEAMessageService service= new NMEAMessageService(message);
        if(service.IsValid()){
            if(service.GetSentenceID().equals("GLL")){
                GPSMessageService gpsMessageService= new GPSMessageService(service);
                GlobalPoint geodetic= gpsMessageService.GetGeodetic();
                log.info("Longitude {} : Latitude {}",geodetic.getLongitude(),geodetic.getLatitude());
            }else{
                log.info("{} Not GLL Message",service.GetSentenceID());
            }
        }else{
            log.info("NMEA message is not valid");

        }
    }
}