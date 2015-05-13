package com.rubiks.lehoang.phonesender;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    Robot robot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button testBtn = (Button) findViewById(R.id.testBtn);

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    robot = new Robot();
                }catch(Exception e){
                    Log.d("com.rubiks.lehoang.phonesender", e.getMessage());
                }
            }
        });

        Button clamp = (Button) findViewById(R.id.clampBtn);
        clamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                robot.clampAll();
            }
        });

        Button unclamp = (Button) findViewById(R.id.unclampBtn);
        unclamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                robot.unclampAll();
            }
        });

        Button Rclockwise = (Button) findViewById(R.id.Rclockwise);
        Rclockwise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                robot.move(Robot.Move.R);
            }
        });

        Button Ranti = (Button) findViewById(R.id.Ranticlockwise);
        Ranti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                robot.move(Robot.Move.R3);
            }
        });

        Button Lanti = (Button) findViewById(R.id.Lanticlockwise);
        Lanti.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                robot.move(Robot.Move.L3);
            }
        });

        Button Lclock = (Button) findViewById(R.id.Lclockwise);
        Lclock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                robot.move(Robot.Move.L);
            }
        });

        Button Fclock = (Button) findViewById(R.id.Fclockwise);
        Fclock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                robot.move(Robot.Move.F);
            }
        });

        Button Fanti = (Button) findViewById(R.id.Fanticlockwise);
        Fanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                robot.move(Robot.Move.F3);
            }
        });

        Button Banti = (Button) findViewById(R.id.Banticlockwise);
        Banti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                robot.move(Robot.Move.B3);
            }
        });

        Button Bclock = (Button) findViewById(R.id.Bclockwise);
        Bclock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                robot.move(Robot.Move.B);
            }
        });

        Button RLClock = (Button) findViewById(R.id.RLClock);
        RLClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                robot.move(Robot.Move.RL);
            }
        });

        Button RLAnti = (Button) findViewById(R.id.RLAnti);
        RLAnti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                robot.move(Robot.Move.RL3);
            }
        });

        Button solveBtn = (Button) findViewById(R.id.solveBtn);
        solveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    final CubeSolver solver = new CubeSolver();
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            String solution = solver.solve("SFJFIJI\n");
                            solver.robotSolve(solution);
                        }
                    });
                    thread.start();
                } catch (Exception e) {
                    Log.d("com.rubiks.lehoang.phonesender","CANNOT CONNECT");
                    e.printStackTrace();
                }
            }
        });

        Button R2Btn = (Button) findViewById(R.id.R2Btn);
        R2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                robot.move(Robot.Move.R2);
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
