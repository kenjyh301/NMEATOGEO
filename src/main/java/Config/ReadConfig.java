package Config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import model.RadarPoint;

import java.io.*;
import java.util.Properties;

@Slf4j
@Data
public class ReadConfig {
    Properties properties;
    int TCPPort;
    String TCPHost;
    int TCPServerPort;
    public static ReadConfig projectConfig;
    static{
        try {
            projectConfig= new ReadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ReadConfig() throws IOException {
        properties= new Properties();
        InputStream inputStream=ClassLoader.getSystemResourceAsStream("application.properties");
        if(inputStream!=null){
            properties.load(inputStream);
            TCPPort= Integer.parseInt(properties.getProperty("TCP.Port"));
            TCPHost= properties.getProperty("TCP.Host");
            TCPServerPort= Integer.parseInt(properties.getProperty("TCPServerPort","5353"));
        }
    }
}
