package service;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import model.GlobalPoint;
import model.NMEAMessage;
import model.RadarPoint;
import net.sf.geographiclib.GeodesicData;


import java.util.*;

import static java.lang.Math.abs;
import static model.DataQueue.*;

@Slf4j
public class TargetService extends Thread {
    GlobalPoint ownShipGPS=null;
    CommonService commonService;

    GlobalPoint resultPoint= new GlobalPoint(0,0,-1,(float)0.0,(float)0.0);
    GlobalPoint targetPlotPending;
    float mse=0;
    List<Float> errorList= new ArrayList<>();

    float MSE(List<Float> errors){
        int count= errors.size();
        float ret=0;
        for (float value:errors) {
            ret+=value*value;
        }
        return (ret/count);
    }

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
                    e.printStackTrace();
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
                    GlobalPoint targetPlot= commonService.GetSecondPoint(data,radarPoint.getTargetNumber(),
                                            radarPoint.getHeading(),radarPoint.getVesselSpeed());
                    log.info("New TTM message Range {}  azimuth {}", radarPoint.getRange(), radarPoint.getAzimuth());

                    outTargetQueue.add(targetPlot);
                    log.info("Add new to out queues lat:{} long:{} speed:{} heading:{}. Queue size {}"
                            ,targetPlot.getLatitude(),targetPlot.getLongitude()
                            ,targetPlot.getVesselSpeed(),targetPlot.getHeading(),outTargetQueue.size());
                    //using for test
                    if(targetPlot.getTargetNumber()==resultPoint.getTargetNumber()){
                        float diffLong= abs(targetPlot.getLongitude()-resultPoint.getLongitude());
                        float diffLat= abs(targetPlot.getLatitude()-resultPoint.getLatitude());
                        errorList.add(diffLong+diffLat);
                        log.info("Evaluate error mse:{} highest value {}",MSE(errorList), Collections.max(errorList));
                    }
                    ///////////////////
                }else{
                    log.info("Missing TTM message because GPS is null");
                }
                // if null do nothing
                break;

            // Processing TLL for test calculate algorithm
            case "TLL":
                if(ownShipGPS!=null){
                    TLLMessageService tllMessageService = new TLLMessageService(service);
                    resultPoint = tllMessageService.GetGeodetic();
                    log.info("New TLL message lat:{} long{}",resultPoint.getLatitude(),resultPoint.getLongitude());
                }else{
                    log.info("Missing TLL message because GPS is null");
                }
                break;



//            case "OSD":
//                if(targetPlotPending!=null){
//                    OSDMessageService osdMessageService= new OSDMessageService(service);
//                    log.info("Receive new OSD message {}", service.GetMessage());
//                    GlobalPoint targetPlot= osdMessageService.UpdateInfo(targetPlotPending);
//                    outTargetQueue.add(targetPlot);
//                    log.info("Receive new OSD message speed:{} heading:{}"
//                            ,osdMessageService.getVesselSpeed(),osdMessageService.getHeading());
//                    log.info("Add new to out queues lat:{} long:{} speed:{} heading:{}. Queue size {}"
//                            ,targetPlot.getLatitude(),targetPlot.getLongitude()
//                            ,targetPlot.getVesselSpeed(),targetPlot.getHeading(),outTargetQueue.size());
//                    targetPlotPending=null;
//                }else{
//                    log.info("Not any pending plot drop OSD message");
//                }
//                break;

            default:
                log.trace("{} Not common message", service.GetSentenceID());

        }
    }
}
