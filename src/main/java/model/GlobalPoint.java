package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class GlobalPoint {
    public enum LongPart{
        E,
        W
    }
    public enum LatPart{
        N,
        S
    }
    public  GlobalPoint(float longitude,float latitude){
        this.longitude=longitude;
        this.latitude= latitude;
    }
    float longitude;
    float latitude;
}
