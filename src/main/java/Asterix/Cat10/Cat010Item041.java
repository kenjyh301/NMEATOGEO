package Asterix.Cat10;

import jlg.jade.asterix.cat062.Cat062Item105;

public class Cat010Item041 extends Cat062Item105 {
    public Cat010Item041(){}
    public static final double LSB= 8.38E-8D;

    @Override
    public double getLatitudeDecimalWsg84() {
        return (double)this.getLatitudeWsg84() * Cat010Item041.LSB;
    }

    @Override
    public double getLongitudeDecimalWsg84() {
        return (double)this.getLongitudeWsg84() * Cat010Item041.LSB;
    }
}
