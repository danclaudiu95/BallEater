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

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


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


        //Position
        private int boxY;

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

        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        startLabel = (TextView) findViewById(R.id.startLabel);


        box = (ImageView) findViewById(R.id.box);

        red = (ImageView) findViewById(R.id.red);

        yellow = (ImageView) findViewById(R.id.yellow);

        blue = (ImageView) findViewById(R.id.blue);

        bomb = (ImageView) findViewById(R.id.bomb);


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


        public void changePos() {


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
