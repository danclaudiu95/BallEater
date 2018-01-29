/*
+MIT License
 +
 +Copyright (c) 2018 danclaudiu95
 +
 +Permission is hereby granted, free of charge, to any person obtaining a copy
 +of this software and associated documentation files (the "Software"), to deal
 +in the Software without restriction, including without limitation the rights
 +to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 +copies of the Software, and to permit persons to whom the Software is
 +furnished to do so, subject to the following conditions:
 */

package com.example.dannyetlv.balleater;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

import static android.util.Half.EPSILON;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private static final float NS2S = 1.0f / 1000000000.0f;
    private final float[] deltaRotationVector = new float[4];
    private float timestamp;
        private TextView scoreLabel;

        private TextView startLabel;

        private ImageView box;

        private ImageView red;

        private ImageView yellow;

        private ImageView blue;

        private ImageView bomb;

        //Size
        private int frameHeight;
        private int boxSize;
        private int screenWidth;
        private int screenHeight;


        //Position
        private int boxY;
        private int redX;
        private int redY;
        private int yellowX;
        private int yellowY;
        private int blueX;
        private int blueY;
        private int bombX;
        private int bombY;




        //Initialize class
        private Handler handler = new Handler();
        private Timer timer = new Timer();

        //Status check
        private boolean action_flg = false;
        private boolean start_flg = false;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

            mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_NORMAL);
        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        startLabel = (TextView) findViewById(R.id.startLabel);


        box = (ImageView) findViewById(R.id.box);

        red = (ImageView) findViewById(R.id.red);

        yellow = (ImageView) findViewById(R.id.yellow);

        blue = (ImageView) findViewById(R.id.blue);

        bomb = (ImageView) findViewById(R.id.bomb);


        //Get screen size
            WindowManager wm = getWindowManager();
            Display disp = wm.getDefaultDisplay();
            Point size = new Point();
            disp.getSize(size);

            screenWidth = size.x;
            screenHeight = size.y;

        //Move to out of screen

        red.setX(-80);

        red.setY(-80);

        yellow.setX(-80);

        yellow.setY(-80);

        blue.setX(-80);

        blue.setY(-80);

        bomb.setX(-80);

        bomb.setY(-80);

        //Temporary



        boxY = 500;


        }
    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.v("MainActivity","Value of X: "+event.values[0]);
        Log.v("MainActivity","Value of Y: "+event.values[1]);
        Log.v("MainActivity","Value of Z: "+event.values[2]);
        // This timestep's delta rotation to be multiplied by the current rotation
        // after computing it from the gyro sample data.
        if (timestamp != 0) {
            final float dT = (event.timestamp - timestamp) * NS2S;
            // Axis of the rotation sample, not normalized yet.
            float axisX = event.values[0];
            float axisY = event.values[1];
            float axisZ = event.values[2];

            // Calculate the angular speed of the sample
            float omegaMagnitude =(float) Math.sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);

            // Normalize the rotation vector if it's big enough to get the axis
            // (that is, EPSILON should represent your maximum allowable margin of error)
            if (omegaMagnitude > EPSILON) {
                axisX /= omegaMagnitude;
                axisY /= omegaMagnitude;
                axisZ /= omegaMagnitude;
            }

            // Integrate around this axis with the angular speed by the timestep
            // in order to get a delta rotation from this sample over the timestep
            // We will convert this axis-angle representation of the delta rotation
            // into a quaternion before turning it into the rotation matrix.
            float thetaOverTwo = omegaMagnitude * dT / 2.0f;
            float sinThetaOverTwo = (float) Math.sin(thetaOverTwo);
            float cosThetaOverTwo = (float) Math.cos(thetaOverTwo);
            deltaRotationVector[0] = sinThetaOverTwo * axisX;
            deltaRotationVector[1] = sinThetaOverTwo * axisY;
            deltaRotationVector[2] = sinThetaOverTwo * axisZ;
            deltaRotationVector[3] = cosThetaOverTwo;
        }
        timestamp = event.timestamp;
        float[] deltaRotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector);
      TextView senz=(TextView)findViewById(R.id.sensorID);
      senz.setText("X="+Float.toString(deltaRotationVector[0])+"  Y="+Float.toString(deltaRotationVector[1])+"  Z="+Float.toString(deltaRotationVector[2]));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    public void changePos() {

            //Red
            redX -= 12;
            if (redX < 0) {
                redX = screenWidth + 20;
                redY = (int) Math.floor(Math.random()*(frameHeight-red.getHeight()));
            }
            red.setX(redX);
            red.setY(redY);

            //Yellow
            yellowX -= 16;
            if (yellowX < 0) {
                yellowX = screenWidth + 10;
                yellowY = (int) Math.floor(Math.random()*(frameHeight-yellow.getHeight()));
            }
            yellow.setX(yellowX);
            yellow.setY(yellowY);


            //Blue
            blueX -= 20;
            if (blueX < 0) {
                blueX = screenWidth + 5000;
                blueY = (int) Math.floor(Math.random()*(frameHeight-blue.getHeight()));
            }
            blue.setX(blueX);
            blue.setY(blueY);

            //Bomb
            bombX -= 16;
            if (bombX < 0) {
                bombX = screenWidth + 6000;
                bombY = (int) Math.floor(Math.random()*(frameHeight-bomb.getHeight()));
            }
            bomb.setX(bombX);
            bomb.setY(bombY);


        //Move Box
            if (action_flg == true) {
                //Touching
                boxY -= 20;
            }
            else {
                //Releasing
                boxY += 20;

            }

            //Check box position
            if (boxY < 0 ) boxY = 0;

            if (boxY > frameHeight - boxSize) boxY = frameHeight - boxSize;
            box.setY(boxY);
        }


        //When I touch the screen the box moves upward
        public boolean onTouchEvent(MotionEvent me) {

            if(start_flg == false) {
                start_flg = true;

                //

                FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
                frameHeight = frame.getHeight();

                boxY = (int) box.getY();

                //the box is a square (height and width are the same)
                boxSize = box.getHeight();

                startLabel.setVisibility(View.GONE);

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //call changePos every 20 seconds
                                changePos();

                            }
                        });
                    }
                }, 0, 20);
            }
            else{
                if (me.getAction() == MotionEvent.ACTION_DOWN) {

                    action_flg = true;

                } else if (me.getAction() == MotionEvent.ACTION_UP) {
                    action_flg = false;
                }

            }
            return true;


        }


    }
