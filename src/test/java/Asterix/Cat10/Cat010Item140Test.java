package Asterix.Cat10;

import junit.framework.TestCase;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Cat010Item140Test extends TestCase {

    public void testEncode() {
        Cat010Item140 item= new Cat010Item140();
        LocalDateTime date = LocalDateTime.now();
        int seconds = date.toLocalTime().toSecondOfDay();
        System.out.println(seconds);
//        item.setTime(100000);
//        byte[] value= item.encode();
//        System.out.println(Arrays.toString(value));
    }
}