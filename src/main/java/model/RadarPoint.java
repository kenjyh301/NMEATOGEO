package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RadarPoint {
    float range;
    float azimuth;
    int targetNumber;
    float heading;     // degrees
    float vesselSpeed; // knots
}
