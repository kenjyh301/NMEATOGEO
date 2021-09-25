package service;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import model.GlobalPoint;
import model.NMEAMessage;
import model.RadarPoint;
import net.sf.geographiclib.GeodesicData;

import static model.DataQueue.outTargetQueue;
import static model.DataQueue.receiveMessageQueue;

@Slf4j
public class TargetService extends Thread {
    GlobalPoint ownShipGPS=null;
    CommonService commonService;
    public TargetService(){
        commonService= new CommonService();
    }
    @SneakyThrows
    @Override
    public void run(){

        while(true){
            if(!receiveMessageQueue.isEmpty()){
                NMEAMessage nmeaMessage= receiveMessageQueue.poll();
                try{
                    NMEAMessageService service= new NMEAMessageService(nmeaMessage);
                    if(service.IsValid()){
                        HandleMessage(service);
                    }else{
                        log.info("NMEA message is not valid");

                    }
                }catch (Exception e){
                    log.warn("Invalid data {}",nmeaMessage.getMessage());
                }

            }else{
                Thread.sleep(100);
            }
        }
    }

    private void HandleMessage(NMEAMessageService service) {
        switch (service.GetSentenceID()) {
            case "GLL":
                GPSMessageService gpsMessageService = new GPSMessageService(service);
                GlobalPoint updateGPS= gpsMessageService.GetGeodetic();
                if(updateGPS!=null){
                    ownShipGPS=updateGPS;
                    log.info("New GLL message Latitude {} : Longitude {}", ownShipGPS.getLatitude(), ownShipGPS.getLongitude());
                }

                break;
            case "TTM":
                if(ownShipGPS!=null){
                    TTMMessageService ttmMessageService = new TTMMessageService(service);
                    RadarPoint radarPoint = ttmMessageService.GetPolar();
                    GeodesicData data=commonService.GetGeodesicData(ownShipGPS,radarPoint);
                    GlobalPoint targetPlot= commonService.GetSecondPoint(data);
                    outTargetQueue.add(targetPlot);
                    log.info("Add new to out queues. Queue size {}",outTargetQueue.size());
                    log.info("New TTM message Range {}  azimuth {}", radarPoint.getRange(), radarPoint.getAzimuth());
                }else{
                    log.info("Missing TTM message because GPS is null");
                }
                // if null do nothing
                break;

            default:
                log.info("{} Not common message", service.GetSentenceID());
        }
    }
}
