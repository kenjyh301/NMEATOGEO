package service;

import lombok.extern.slf4j.Slf4j;
import model.GlobalPoint;
import model.RadarPoint;
import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;

import java.util.HashMap;

@Slf4j
public class CommonService {

    public GeodesicData GetGeodesicData(GlobalPoint globPoint, RadarPoint radarPoint){
        return Geodesic.WGS84.Direct(globPoint.getLatitude(),globPoint.getLongitude(),
                                     radarPoint.getAzimuth(),radarPoint.getRange());
    }

    public GlobalPoint GetSecondPoint(GeodesicData data,int targetNumber){
        return new GlobalPoint((float)data.lon2,(float)data.lat2,targetNumber);
    }


}
