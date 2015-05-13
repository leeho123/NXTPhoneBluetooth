package com.rubiks.lehoang.phonesender;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LeHoang on 11/05/2015.
 */
public class CubeSolver {
    public static final String IP = "192.168.0.3";
    Socket socket;
    BufferedReader in;
    PrintWriter out;

    Robot robot;

    public CubeSolver() throws Exception{
        robot = new Robot();
        robot.clampAll();
    }

    public String solve(String state){
        String solution = "";
        try {
            socket = new Socket(IP, 12345);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            out.println(state);
            out.flush();
            Log.d("com.rubiks.lehoang.phonesender", "Printing state");
            solution = in.readLine();
            Log.d("com.rubiks.lehoang.phonesender", "Got something back");



        } catch (IOException e) {
            e.printStackTrace();
        }

        return solution;
    }

    private static String transform(String solution){
        String replaced = solution.replace('\'', '3');

        return replaced;
    }

    public void robotSolve(String solution){
        String trans = transform(solution);
        List<Robot.Move> moves = parseSolution(trans);
        for(Robot.Move move: moves){
            robot.move(move);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Robot.Move> parseSolution(String replaced){

        List<Robot.Move> moves = new ArrayList<>();
        for(int i = replaced.length()-1; i >= 0; i--){
            switch(replaced.charAt(i)){
                case '3':
                case '2':
                    moves.add(0, Robot.Move.valueOf(replaced.substring(i, i+2)));
                    i--;
                    break;
                default:
                    moves.add(0, Robot.Move.valueOf(replaced.substring(i, i+1)));
            }
        }

        return moves;
    }

}
