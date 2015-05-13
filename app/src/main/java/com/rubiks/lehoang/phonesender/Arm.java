package com.rubiks.lehoang.phonesender;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by LeHoang on 08/05/2015.
 */
public class Arm {
    public final static int CLAMP = 0;
    public final static int UNCLAMP = 1;
    public final static int CLOCKWISE = 2;
    public final static int ANTI = 3;
    public final static int ARM_CLOCK = 4;
    public final static int ARM_ANTI = 5;
    public final static int CLOCK180 = 6;
    public final static int ANTI180 = 7;

    final static int DONE = -999;
    final static int PING = -1;
    final static int TIME_SYNC = -2;
    final static int START_SEQUENCE = -3;
    final static int END_SEQUENCE = -4;
    final static int FINISH_SETUP = -5;
    final static int PERFORM_DELAYED = -6;

    boolean clamped = false;

    BluetoothConnector connector;
    BluetoothConnector.FakeConnector fake = new BluetoothConnector.FakeConnector();

    String address;

    public Arm(String address) throws Exception{
        connector = new BluetoothConnector(address);
        connector.setState(true);
        if(!connector.connect()){
            throw new Exception("Cannot connect to arm");
        }
        boolean isOver = false;
        while(!isOver){
            int message = connector.readMessage();
            Log.d("com.rubiks.lehoang.phonesender", "Got message: " + message);
            switch(message){
                case PING:
                    connector.sendMessage(DONE);
                    break;
                case TIME_SYNC:
                    connector.sendLong(System.currentTimeMillis());
                    break;
                case FINISH_SETUP:
                    isOver = true;
                    break;
            }

        }

        //connector.sendMessage(START_SEQUENCE);
    }

    public BluetoothConnector clamp(){
        if(!clamped) {
            connector.sendMessage(CLAMP);
        }else{
            return fake;
        }
        clamped = true;
        return connector;
    }

    public BluetoothConnector unclamp(){
        if(clamped) {
            connector.sendMessage(UNCLAMP);
        }else{
           return fake;
        }
        clamped = false;
        return connector;
    }

    public BluetoothConnector clockwise(){
        if(!clamped) {
            BluetoothConnector conn = clamp();
            conn.readMessage();
        }
        connector.sendMessage(CLOCKWISE);
        return connector;
    }

    public BluetoothConnector anticlockwise(){
        if(!clamped){
            BluetoothConnector conn = clamp();
            conn.readMessage();
        }
        connector.sendMessage(ANTI);
        return connector;

    }

    public BluetoothConnector clockwise180(){
        if(!clamped){
            BluetoothConnector conn = clamp();
            conn.readMessage();
        }

        connector.sendMessage(CLOCK180);
        return connector;
    }

    public BluetoothConnector armOnlyClockwise(){
        connector.sendMessage(ARM_CLOCK);
        return connector;

    }

    public BluetoothConnector armOnlyAntiClockwise(){
        connector.sendMessage(ARM_ANTI);
        return connector;

    }

    public static BluetoothConnector[] sendAllSynced(Arm[] conns, int[] instruction, int delay){

        DataOutputStream[] outStreams = new DataOutputStream[conns.length];
        BluetoothConnector[] toReturn = new BluetoothConnector[conns.length];

        for(int i = 0; i < conns.length; i++){
            try {
                DataOutputStream out = new DataOutputStream(conns[i].connector.socket.getOutputStream());
                outStreams[i] = out;
                outStreams[i].writeInt(PERFORM_DELAYED);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long timeToExec = System.currentTimeMillis() + delay;
        for(int i = 0; i< conns.length; i++){
            try {
                outStreams[i].writeUTF(instruction[i] + "," + timeToExec);
                toReturn[i] = conns[i].connector;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(DataOutputStream out: outStreams){
            try {
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return toReturn;

    }

    public static BluetoothConnector[] sendAllSyncedSame(Arm[] conns, int instr, int delay){
        int[] instrs = {instr, instr, instr, instr};
        return sendAllSynced(conns, instrs, delay);
    }


}
