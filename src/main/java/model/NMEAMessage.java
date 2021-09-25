package model;

import lombok.Data;
import net.sf.marineapi.nmea.parser.SentenceParser;

import java.nio.charset.StandardCharsets;

@Data
public class NMEAMessage {
  public NMEAMessage(byte[] messageIn){
     this.message= new String(messageIn, StandardCharsets.UTF_8);
  }
  public NMEAMessage(String messageIN){this.message=messageIN;}
  String message;
}
