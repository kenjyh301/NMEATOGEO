import Config.ReadConfig;
import connection.TCPClient;
import lombok.extern.slf4j.Slf4j;
import service.AsterixService;
import service.TargetService;

@Slf4j
public class main {
    public static void main(String[] argv)  {
        TargetService targetService= new TargetService();
        TCPClient tcpClient= new TCPClient(ReadConfig.projectConfig.getTCPHost()
                                            ,ReadConfig.projectConfig.getTCPPort());
        AsterixService asterixService= new AsterixService();

        tcpClient.start();
        targetService.start();
        asterixService.start();
        try{
            log.info("Joint tcp thread");
            tcpClient.join();
        }catch (Exception e){
            log.error("Cannot joint tcp thread");
        }
    }
}


