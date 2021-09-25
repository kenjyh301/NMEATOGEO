package model;

import java.util.LinkedList;
import java.util.Queue;

public class DataQueue {
    public static Queue<NMEAMessage> receiveMessageQueue= new LinkedList<>();
    public static Queue<GlobalPoint> outTargetQueue= new LinkedList<>();
}
