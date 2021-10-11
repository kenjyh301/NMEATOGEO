package Asterix.Cat62;

import Asterix.Cat62.AsterixCat62Record;
import jlg.jade.asterix.ReservedAsterixField;
import jlg.jade.asterix.cat062.*;
import jlg.jade.asterix.cat062.item110.Cat062Item110;
import jlg.jade.asterix.cat062.item200.Cat062Item200;
import jlg.jade.asterix.cat062.item290.Cat062Item290;
import jlg.jade.asterix.cat062.item340.Cat062Item340;
import jlg.jade.asterix.cat062.item380.Cat062Item380;
import jlg.jade.asterix.cat062.item390.Cat062Item390;
import jlg.jade.asterix.cat062.item500.Cat062Item500;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class AsterixCat62RecordBuilder {
    private Cat062Item010 item010;
    private Cat062Item015 item015;
    private Item070 item070;
    private Cat062Item105 item105;
    private Cat062Item100 item100;
    private Cat062Item185 item185;
    private Cat062Item210 item210;
    private Cat062Item060 item060;
    private Cat062Item245 item245;
    private Cat062Item380 item380;
    private Cat062Item040 item040;
    private Cat062Item080 item080;
    private Cat062Item290 item290;
    private Cat062Item200 item200;
    private Cat062Item295 item295;
    private Cat062Item136 item136;
    private Cat062Item130 item130;
    private Cat062Item135 item135;
    private Cat062Item220 item220;
    private Cat062Item390 item390;
    private Cat062Item270 item270;
    private Cat062Item300 item300;
    private Cat062Item110 item110;
    private Cat062Item120 item120;
    private Cat062Item510 item510;
    private Cat062Item500 item500;
    private Cat062Item340 item340;
    private ReservedAsterixField reservedExpansionField;
    private ReservedAsterixField specialPurposeField;


//    AsterixCat62Record Build(){
//
//    }

    public void SetDataSourceIdentifier(int sic,int sac){
        item010.setSic(sic);
        item010.setSac(sac);
    }

    public void SetServiceIdentification(int value){
        item015.setServiceIdentification(value);

    }

    public void SetTime(int value){
        item070.setTime(value);
//        item185.
    }




}
