package com.example.soukousschallenge.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.soukousschallenge.R;

public class GameActivity extends AppCompatActivity implements SensorEventListener{

    private ImageButton mPauseButton;
    public static final int PAUSE_POPUP = 1;
    private SensorManager mSensorManager;
    private Sensor mGravitySensor;
    private boolean mFacingDown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mPauseButton = findViewById(R.id.activity_game_pauseButton);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mGravitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        if(mGravitySensor == null){
            Log.w("TURN_OVER","Device has no gravity sensor");
        }

        mPauseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent pausePopUpActivity = new Intent(GameActivity.this, PausePopUpActivity.class);
                startActivityForResult(pausePopUpActivity, PAUSE_POPUP);
            }
        });


    }

    @Override
    protected void onResume(){
        super.onResume();
        if (mGravitySensor != null) {
            mSensorManager.registerListener(this, mGravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        /*
        ################ DETECTION ######################
        ################# TURN OVER #####################
        final float factor = 0.95F;

        if (sensorEvent.sensor == mGravitySensor) {
            boolean nowDown = sensorEvent.values[2] < -SensorManager.GRAVITY_EARTH * factor;
            if (nowDown != mFacingDown) {
                if (nowDown) {
                    Log.i("TURN_OVER", "DOWN");
                } else {
                    Log.i("TURN_OVER", "UP");
                }
                mFacingDown = nowDown;
            }
        }
        ################################################
        */
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i){

    }
}