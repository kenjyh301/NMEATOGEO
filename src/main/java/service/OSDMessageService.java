package service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import model.GlobalPoint;
import model.NMEAMessage;

import java.util.HashMap;

@Slf4j
@Data
public class OSDMessageService {

    public enum SpeedUnit{
        K,      // km/h
        N,      // knot, NM/h
        S;       // Statute miles/h
    }
    HashMap<SpeedUnit,Float> parseUnit;
    NMEAMessageService messageService;
    float heading;
    float vesselSpeed;
    public OSDMessageService(NMEAMessageService messageService){
        this.messageService= messageService;
        heading=0;
        vesselSpeed=0;
        parseUnit= new HashMap<>();
        parseUnit.put(SpeedUnit.K,(float)0.539957);
        parseUnit.put(SpeedUnit.N,(float)1);
        parseUnit.put(SpeedUnit.S,(float)0.868976242);
    }

//    public GlobalPoint UpdateInfo(GlobalPoint pendingPlot){
//        String headingStr= messageService.GetValue(0);
//        String vesselSpeedStr= messageService.GetValue(4);
//        String speedUnitStr= messageService.GetValue(8);
//        heading= Float.parseFloat(headingStr);
//        vesselSpeed= Float.parseFloat(vesselSpeedStr);
//        SpeedUnit speedUnit= SpeedUnit.valueOf(speedUnitStr);
//        float speedScale= parseUnit.get(speedUnit);
//        pendingPlot.setHeading(heading);
//        pendingPlot.setVesselSpeed(vesselSpeed*speedScale);
//        return pendingPlot;
//    }
}
