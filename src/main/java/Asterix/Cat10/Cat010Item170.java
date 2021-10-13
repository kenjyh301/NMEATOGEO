package Asterix.Cat10;

import jlg.jade.asterix.cat048.Cat048Item170;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Data
public class Cat010Item170 {
        enum CNF {
            ConfirmedTrack, TrackInInitialisationPhase
        };

        enum TRE {
            Default, LastReportForATrack
        };


        enum CST {
            NoExtrapolation,
            PredictableExtrapolationSensorRefresh,
            PredictableExtrapolationInMaskedArea,
            ExtrapolationUnpredictableAbsenceOfDetection
        };

        enum MAH {
            Default, HorizontalManoeuvre
        };

        enum TCC {
            TrackingPerformedInSensorPlane,
            SlantRangeCorrectionAndASuitableProjection
        };

        enum STH {
            MeasuredPosition, SmoothedPosition
        };

        enum TOM {
            UnknownTypeOfMovement, TakingOff, Landing, OtherTypes
        };

        enum DOU {
            NoDoubt,
            DoubtfulCorrelationCundeterminedReason,
            DoubtfulCorrelationInClutter,
            LossOfAccuracy,
            LossOfAccuracyInClutter,
            UnstableTrack,
            PreviouslyCoasted
        };

        enum MRS {
            Undetermined,
            TrackMergedByAssociationToPlot,
            TrackMergedByNonAssociationToPlot,
            SplitTrack
        };

        enum GHO {
            Default, GhostTrack
        };

    @Getter(AccessLevel.NONE)
    int sizeInByte;

    CNF cnf;
    TRE tre;
    CST cst;
    MAH mah;
    TCC tcc;
    STH sth;
    @Setter(AccessLevel.NONE)
    boolean extend1;
    TOM tom;
    DOU dou;
    MRS mrs;
    @Setter(AccessLevel.NONE)
    boolean extend2;
    GHO gho;
    Cat010Item170() {
        cnf = CNF.ConfirmedTrack;
        tre = TRE.Default;
        cst = CST.NoExtrapolation;
        mah = MAH.Default;
        tcc = TCC.TrackingPerformedInSensorPlane;
        sth = STH.MeasuredPosition;
        tom = TOM.UnknownTypeOfMovement;
        dou = DOU.NoDoubt;
        mrs = MRS.Undetermined;
        gho = GHO.Default;
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
    public int getSizeInBytes(){
        return sizeInByte;
    }

    public byte[] encode(){
        byte tmp=0;
        byte[] itemData= new byte[3];
        int itemDataLen=1;
        Arrays.fill(itemData,(byte)0);
        tmp|=cnf.ordinal()<<7;
        tmp|=tre.ordinal()<<6;
        tmp|=cst.ordinal()<<4;
        tmp|=mah.ordinal()<<3;
        tmp|=tcc.ordinal()<<2;
        tmp|=sth.ordinal()<<1;
        itemData[0]=tmp;
        if(extend1){
            tmp|=tom.ordinal()<<6;
            tmp|=dou.ordinal()<<3;
            tmp|=mrs.ordinal()<<1;
            itemData[1]=tmp;
            itemDataLen++;
            if(extend2){
                tmp|=gho.ordinal()<<7;
                itemData[2]=tmp;
                itemDataLen++;
            }
        }
        byte[] ret= new byte[itemDataLen];
        ret= Arrays.copyOfRange(itemData,0,itemDataLen);
        return ret;
    }

}
