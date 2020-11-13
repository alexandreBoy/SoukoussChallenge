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

import com.example.soukousschallenge.R;


public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private ImageButton mPauseButton;
    public static final int PAUSE_POPUP = 1;
    private static final String TAG = "DETECTION";
    private SensorManager mSensorManager;
    private Sensor mGravitySensor;
    private boolean mFacingDown;

    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        mPauseButton = findViewById(R.id.activity_game_pauseButton);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mGravitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        if(mGravitySensor == null){
            Log.w(TAG,"Device has no gravity sensor");
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
    protected void onResume() {
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
                    Log.i(TAG, "DOWN");
                } else {
                    Log.i(TAG, "UP");
                }
                mFacingDown = nowDown;
            }
        }
        ################################################
        ################# SHAKE ########################
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta;
        if (mAccel > 12) {
            Log.i(TAG, "Soukouss");
        }
        ################################################
        */
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
