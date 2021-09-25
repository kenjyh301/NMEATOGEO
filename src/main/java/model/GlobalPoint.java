package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
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
        this.longitude= latitude;
        this.longPart= LongPart.E;
        this.latPart= LatPart.N;
    }
    float longitude;
    float latitude;
    LongPart longPart;
    LatPart latPart;
}
