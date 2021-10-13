package Asterix.Cat10;

import jlg.jade.asterix.VariableLengthAsterixData;
import jlg.jade.asterix.cat048.Cat048Item020;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Data
public class Cat010Item020 {
    enum TYP {
        SSRMultilateration,
        ModeSMultilateration,
        ADS_B,
        PRS,
        MagneticLoopSystem,
        HFMultilateration,
        NotDefined,
        OtherTypes
    };

    enum DCR {
        NoDifferentialCorrection, DifferentialCorrection
    };

    enum CHN {
        Chain1, Chain2
    };

    enum GBS {
        TransponderGroundBitNotSet, TransponderGroundBitSet
    };

    enum CRT {
        NoCorruptedReplyInMultilateration, CorruptedRepliesInMultilateration
    };

    enum SIM {
        ActualTargetReport, SimulatedTargetReport
    };

    enum TST {
        Default, TestTarget
    };

    enum RAB {
        ReportFromTargetTransponder, ReportFromFieldMonitor
    };
    enum LOP {
        Undetermined, LoopStart, LoopFinish
    };

    enum TOT {
        Undetermined, Aircraft, GroundVehicle, Helicopter
    };

    enum SPI{
        AbsenceOfSPI,SpecialPositionIdentification
    };

    @Getter(AccessLevel.NONE)
    int sizeInByte;

    TYP typ;
    DCR dcr;
    CHN chn;
    GBS gbs;
    CRT crt;
    @Setter(AccessLevel.NONE)
    boolean extend1;
    SIM sim;
    TST tst;
    RAB rab;
    LOP lop;
    TOT tot;
    @Setter(AccessLevel.NONE)
    boolean extend2;
    SPI spi;
    Cat010Item020() {
        typ = TYP.SSRMultilateration;
        dcr = DCR.NoDifferentialCorrection;
        chn = CHN.Chain1;
        gbs = GBS.TransponderGroundBitNotSet;
        crt = CRT.NoCorruptedReplyInMultilateration;
        sim = SIM.ActualTargetReport;
        tst = TST.Default;
        rab = RAB.ReportFromTargetTransponder;
        lop = LOP.Undetermined;
        tot = TOT.Undetermined;
        spi = SPI.AbsenceOfSPI;
        extend1 = false;
        extend2 = false;
        sizeInByte=1;
    }

    public void SetExtend1(boolean value){
        extend1=value;
        if(extend2==true){
            sizeInByte=3;
        }else if(extend1==true){
            sizeInByte=2;
        }else{
            sizeInByte=1;
        }
    }

    public void SetExtend2(boolean value){
        extend2=value;
        if(extend2==true){
            sizeInByte=3;
        }else if(extend1==true){
            sizeInByte=2;
        }else{
            sizeInByte=1;
        }
    }


    public byte[] encode(){
        byte tmp=0;
        byte[] itemData= new byte[3];
        int itemDataLen=1;
        Arrays.fill(itemData,(byte)0);
        tmp|=typ.ordinal()<<5;
        tmp|=dcr.ordinal()<<4;
        tmp|=chn.ordinal()<<3;
        tmp|=gbs.ordinal()<<2;
        tmp|=crt.ordinal()<<1;
        itemData[0]=tmp;
        if(extend1){
            tmp|=sim.ordinal()<<7;
            tmp|=tst.ordinal()<<6;
            tmp|=rab.ordinal()<<5;
            tmp|=lop.ordinal()<<3;
            tmp|=tot.ordinal()<<1;
            itemData[1]=tmp;
            itemDataLen++;
            if(extend2){
                tmp|=spi.ordinal()<<7;
                itemData[2]=tmp;
                itemDataLen++;
            }
        }
        byte[] ret= new byte[itemDataLen];
        ret= Arrays.copyOfRange(itemData,0,itemDataLen);
        return ret;
    }

    public int getSizeInBytes(){
        return sizeInByte;
    }
}
