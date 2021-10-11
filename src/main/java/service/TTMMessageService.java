package service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.RadarPoint;

import java.util.HashMap;

@Slf4j
//@AllArgsConstructor
public class TTMMessageService {
    public enum SpeedUnit{
        K,      // km/h, km
        N,      // knot, NM
        S;       // Statue miles/h, Statue miles
    }
    private final float NM2meter=(float)1852.0;
    NMEAMessageService messageService;
    HashMap<TTMMessageService.SpeedUnit,Float> parseUnit;


    float heading;
    float vesselSpeed;
    public TTMMessageService(NMEAMessageService messageService){
        this.messageService= messageService;
        parseUnit= new HashMap<>();
        heading=0;
        vesselSpeed=0;
        parseUnit.put(SpeedUnit.K,(float)0.539957);
        parseUnit.put(SpeedUnit.N,(float)1);
        parseUnit.put(SpeedUnit.S,(float)0.868976242);
    }

    public RadarPoint GetPolar(){
        log.info("TTM Message {}",messageService.GetMessage());
        String targetNumberStr= messageService.GetValue(0);
        String distanceStr= messageService.GetValue(1);
        String bearStr= messageService.GetValue(2);
        String headingStr= messageService.GetValue(5);
        String vesselSpeedStr= messageService.GetValue(4);
        String speedUnitStr= messageService.GetValue(9);
        heading= Float.parseFloat(headingStr);
        vesselSpeed= Float.parseFloat(vesselSpeedStr);
        SpeedUnit speedUnit= SpeedUnit.valueOf(speedUnitStr);
        float unitScale=parseUnit.get(speedUnit);
        return new RadarPoint(Float.parseFloat(distanceStr)*unitScale,Float.parseFloat(bearStr)
                                ,Integer.parseInt(targetNumberStr),heading,vesselSpeed*unitScale);
    }
}
