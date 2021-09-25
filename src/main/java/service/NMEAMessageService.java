package service;

import lombok.extern.slf4j.Slf4j;
import model.NMEAMessage;
import net.sf.marineapi.nmea.parser.SentenceParser;

@Slf4j
public class NMEAMessageService extends SentenceParser {
//    SentenceParser parser;

    public NMEAMessageService(NMEAMessage message){
       super(message.getMessage());
    }

    public boolean IsValid(){
        return super.isValid();
    }

    public char GetBeginChar(){
        return super.getBeginChar();
    }

    public String GetSentenceID(){
        return super.getSentenceId();
    }

    public String GetValue(int index){
        return super.getStringValue(index);
    }

}
