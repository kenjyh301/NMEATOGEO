import Config.ReadConfig;
import connection.TCPClient;
import lombok.extern.slf4j.Slf4j;
import model.GlobalPoint;
import service.TargetService;

@Slf4j
public class main {
    public static void main(String[] argv)  {
        TargetService targetService= new TargetService();
        TCPClient tcpClient= new TCPClient(ReadConfig.projectConfig.getTCPhost(),ReadConfig.projectConfig.getTCPPort());
        tcpClient.start();
        targetService.start();
        try{
            log.info("Joint tcp thread");
            tcpClient.join();
        }catch (Exception e){
            log.error("Cannot joint tcp thread");
        }


    }
}


