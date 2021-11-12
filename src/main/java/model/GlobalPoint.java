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
        this.targetNumber=0;
    }
    public  GlobalPoint(float longitude,float latitude,int targetNumber,float heading,float vesselSpeed){
        this.longitude=longitude;
        this.latitude= latitude;
        this.targetNumber= targetNumber;
        this.heading=heading;
        this.vesselSpeed= vesselSpeed;
    }
    int targetNumber;
    float longitude;       // degrees
    float latitude;     // degrees
    float heading;     // degrees
    float vesselSpeed; // knots
}
