package service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import model.GlobalPoint;
import model.RadarPoint;

@Slf4j
@Data
@AllArgsConstructor
public class TLLMessageService {
    NMEAMessageService messageService;

    public float GetDegreeFromString(String latStr, int degreeLen) {
        float degree = Float.parseFloat(latStr.substring(0, degreeLen));
        float minute = Float.parseFloat(latStr.substring(degreeLen));
        return (float) (degree + minute / 60.0);
    }

    public GlobalPoint GetGeodetic() {
        try {
            String longStr = messageService.GetValue(3);
            String longPartStr = messageService.GetValue(4);
            String latStr = messageService.GetValue(1);
            String latPartStr = messageService.GetValue(2);
            String targetNumberStr= messageService.GetValue(0);
            GlobalPoint.LongPart longPart = GlobalPoint.LongPart.valueOf(longPartStr);
            GlobalPoint.LatPart latPart = GlobalPoint.LatPart.valueOf(latPartStr);
            float longitude = GetDegreeFromString(longStr, 3);
            ;
            float latitude = GetDegreeFromString(latStr, 2);
            if (longPart == GlobalPoint.LongPart.W) {
                longitude = -longitude;
            }
            if (latPart == GlobalPoint.LatPart.S) {
                latitude = -latitude;
            }
            return new GlobalPoint(longitude, latitude,Integer.parseInt(targetNumberStr),(float)0.0,(float)0.0);
        } catch (Exception e) {
            log.info("Index not found");
            e.printStackTrace();
            return null;
        }
    }
}
