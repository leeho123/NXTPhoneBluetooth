package com.rubiks.lehoang.phonesender;

/**
 * Created by LeHoang on 08/05/2015.
 */
public class Robot {
    enum Move{
        F,R,U,B,L,D,F2,R2,U2,B2,L2,D2,F3,R3,U3,B3,L3,D3,RL,RL3;
    }
    public static final int FRONT = 0;
    public static final int RIGHT = 1;
    public static final int BACK = 2;
    public static final int LEFT = 3;

    public static final int delay = 300;

    Arm[] arms;

    boolean flipped = false;

    public Robot() throws Exception {
        arms = new Arm[4];
        arms[FRONT] = new Arm(BluetoothConnector.masterAddress);
        arms[RIGHT] = new Arm(BluetoothConnector.slaveRAddress);
        arms[LEFT] = new Arm(BluetoothConnector.slaveLAddress);
        arms[BACK] = new Arm(BluetoothConnector.slaveBAddress);
    }


    public void clampAll(){
        BluetoothConnector[] conns = Arm.sendAllSyncedSame(arms, Arm.CLAMP, delay);
        for(BluetoothConnector conn: conns){
            conn.readMessage();
        }
    }

    public void unclampAll(){
        BluetoothConnector[] conns = Arm.sendAllSyncedSame(arms, Arm.UNCLAMP, delay);
        for(BluetoothConnector conn: conns){
            conn.readMessage();
        }
    }

    public void perfSubSame(int instr, int... subArms){

        Arm[] ar = new Arm[subArms.length];
        for(int i = 0; i < subArms.length;i++){
            ar[i] = arms[subArms[i]];
        }
        BluetoothConnector[] conns = Arm.sendAllSyncedSame(ar, instr, delay);
        for(BluetoothConnector conn: conns){
            conn.readMessage();
        }
    }


    public void move(Move move){
        BluetoothConnector sync1 = null;
        BluetoothConnector sync2 = null;
        switch(move){
            case F:
                if(flipped){
                    move(Move.RL3);
                }
                sync1 = arms[FRONT].clockwise();
                sync1.readMessage();
                break;
            case R:
                sync1 = arms[RIGHT].clockwise();
                sync1.readMessage();
                break;
            case U:
                if(!flipped){
                    move(Move.RL);
                }
                sync1 = arms[BACK].clockwise();
                sync1.readMessage();
                break;
            case B:
                if(flipped){
                    move(Move.RL3);
                }
                sync1 = arms[BACK].clockwise();
                sync1.readMessage();
                break;
            case L:
                sync1 = arms[LEFT].clockwise();
                sync1.readMessage();
                break;
            case D:
                if(!flipped){
                    move(Move.RL);
                }
                sync1 = arms[FRONT].clockwise();
                sync1.readMessage();
                break;
            case F3:
                if(flipped){
                    move(Move.RL3);
                }
                sync1 = arms[FRONT].anticlockwise();
                sync1.readMessage();
                break;
            case R3:
                sync1 = arms[RIGHT].anticlockwise();
                sync1.readMessage();
                break;
            case U3:
                if(!flipped){
                    move(Move.RL);
                }
                sync1 = arms[BACK].anticlockwise();
                sync1.readMessage();
                break;
            case L3:
                sync1 = arms[LEFT].anticlockwise();
                sync1.readMessage();
                break;
            case B3:
                if(flipped){
                    move(Move.RL3);
                }
                sync1 = arms[BACK].anticlockwise();
                sync1.readMessage();
                break;
            case D3:
                if(!flipped){
                    move(Move.RL);
                }
                sync1 = arms[FRONT].anticlockwise();
                sync1.readMessage();
                break;
            case RL:
                perfSubSame(Arm.UNCLAMP,FRONT,BACK);
                BluetoothConnector[] conns = Arm.sendAllSynced(new Arm[]{arms[LEFT], arms[RIGHT]}, new int[]{Arm.ARM_ANTI, Arm.ARM_CLOCK}, delay);
                for(BluetoothConnector conn: conns){
                    conn.readMessage();
                }
                perfSubSame(Arm.CLAMP, FRONT, BACK);
                perfSubSame(Arm.UNCLAMP, LEFT, RIGHT);
                conns =Arm.sendAllSynced(new Arm[]{arms[LEFT], arms[RIGHT]}, new int[]{Arm.ARM_CLOCK, Arm.ARM_ANTI}, delay);
                for(BluetoothConnector conn: conns){
                    conn.readMessage();
                }
                perfSubSame(Arm.CLAMP, LEFT, RIGHT);
                flipped = !flipped;


                break;
            case RL3:
                perfSubSame(Arm.UNCLAMP,FRONT,BACK);
                BluetoothConnector[] conns2 = Arm.sendAllSynced(new Arm[]{arms[LEFT], arms[RIGHT]}, new int[]{Arm.ARM_CLOCK, Arm.ARM_ANTI}, delay);
                for(BluetoothConnector conn: conns2){
                    conn.readMessage();
                }
                perfSubSame(Arm.CLAMP, FRONT, BACK);
                perfSubSame(Arm.UNCLAMP, LEFT, RIGHT);
                conns2 = Arm.sendAllSynced(new Arm[]{arms[LEFT], arms[RIGHT]}, new int[]{Arm.ARM_ANTI, Arm.ARM_CLOCK}, delay);
                for(BluetoothConnector conn: conns2){
                    conn.readMessage();
                }
                perfSubSame(Arm.CLAMP, LEFT, RIGHT);
                flipped = !flipped;


                break;
            case R2:
                sync1 = arms[RIGHT].clockwise180();
                sync1.readMessage();
                break;
            case F2:
                if(flipped){
                    move(Move.RL3);
                }
                sync1 = arms[FRONT].clockwise180();
                sync1.readMessage();
                break;
            case U2:
                if(!flipped){
                    move(Move.RL);
                }
                sync1 = arms[BACK].clockwise180();
                sync1.readMessage();
                break;
            case L2:
                sync1 = arms[LEFT].clockwise180();
                sync1.readMessage();
                break;
            case B2:
                if(flipped){
                    move(Move.RL3);
                }
                sync1 = arms[BACK].clockwise180();
                sync1.readMessage();
                break;
            case D2:
                if(!flipped){
                    move(Move.RL);
                }
                sync1 = arms[FRONT].clockwise180();
                sync1.readMessage();

            default: break;
        }

    }
}
