package Asterix.Cat62;

import junit.framework.TestCase;

import java.util.ArrayList;

public class AsterixCat62RecordTest extends TestCase {

    public void testFunc() {
        ArrayList<Byte> data= new ArrayList<>();
        byte x=10;
        data.add(x);
        x=11;
        data.add(x);
        AsterixCat62Record record= new AsterixCat62Record(data);
        record.Show();
    }
}