package service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.GlobalPoint;

@Slf4j
@AllArgsConstructor
public class GPSMessageService {
    NMEAMessageService messageService;

    public float GetDegreeFromString(String latStr,int degreeLen){
        float degree= Float.parseFloat(latStr.substring(0,degreeLen));
        float minute= Float.parseFloat(latStr.substring(degreeLen));
        return (float)(degree+minute/60.0);
    }

    public GlobalPoint GetGeodetic(){
        try{
            String longStr= messageService.GetValue(2);
            String longPartStr= messageService.GetValue(3);
            String latStr= messageService.GetValue(0);
            String latPartStr= messageService.GetValue(1);
            GlobalPoint.LongPart longPart= GlobalPoint.LongPart.valueOf(longPartStr);
            GlobalPoint.LatPart latPart= GlobalPoint.LatPart.valueOf(latPartStr);
            return new GlobalPoint(GetDegreeFromString(longStr,3),GetDegreeFromString(latStr,2)
                    ,longPart,latPart);
        }catch (Exception e){
            log.info("Index not found");
            e.printStackTrace();
            return null;
        }


    }

}
