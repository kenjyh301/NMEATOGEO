package service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.RadarPoint;

@Slf4j
@AllArgsConstructor
public class TTMMessageService {
    NMEAMessageService messageService;

    public RadarPoint GetPolar(){
        final float NM2meter=(float)1852.0;
        String targetNumberStr= messageService.GetValue(0);
        String distanceStr= messageService.GetValue(1);
        String bearStr= messageService.GetValue(2);
//        String bearSignStr= messageService.GetValue(3);
//        int bearSign= bearSignStr.equals("T")?1:-1;
        return new RadarPoint(Float.parseFloat(distanceStr)*NM2meter,Float.parseFloat(bearStr),Integer.parseInt(targetNumberStr));
    }
}
