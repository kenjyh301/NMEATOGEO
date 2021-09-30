import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import model.GlobalPoint;
import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;

@Slf4j
public class mainTest extends TestCase {

    public void testMain() {
        GlobalPoint glob1= new GlobalPoint((float)105.87,(float)21.03);
        GlobalPoint glob2= new GlobalPoint((float)105.7562,(float)21.2271);

        GeodesicData data= Geodesic.WGS84.Inverse(glob2.getLatitude(),glob2.getLongitude(),glob1.getLatitude(),glob1.getLongitude());
        log.info("distance {} azimuth {}",data.s12,data.azi1);
    }
}