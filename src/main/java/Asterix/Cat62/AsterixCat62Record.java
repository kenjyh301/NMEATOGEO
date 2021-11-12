package Asterix.Cat62;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class AsterixCat62Record {
    Byte[] record;
     public AsterixCat62Record(ArrayList<Byte> data){
        record=data.toArray(new Byte[0]);
    }
    public void Show(){
        System.out.println(Arrays.toString(record));
    }
}
