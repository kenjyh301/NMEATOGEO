package Config;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ReadConfigTest extends TestCase {
    public void test() throws IOException {
        log.info("{}",ReadConfig.projectConfig.TCPPort);
    }
}