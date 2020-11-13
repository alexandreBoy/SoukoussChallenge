package com.example.soukousschallenge.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.example.soukousschallenge.R;


public class GameActivity extends AppCompatActivity implements SensorEventListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{


    private ImageButton mPauseButton;
    public static final int PAUSE_POPUP = 1;
    private static final String TAG = "DETECTION";
    private SensorManager mSensorManager;
    private Sensor mAccelerometerSensor;
    private Sensor mGravitySensor;
    private boolean mFacingDown;

    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        mPauseButton = findViewById(R.id.activity_game_pauseButton);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGravitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        if (mGravitySensor == null){
            Log.w(TAG, "Device has no gravity sensor");
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
    public void onSensorChanged(SensorEvent event){

        // ################ DETECTION ######################
        // ################# SHAKE ########################
        if (event.sensor == mAccelerometerSensor){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel > 12){
                Log.i(TAG, "Soukouss");
            }
        }
        // ################################################
        // ################# TURN OVER #####################
        final float factor = 0.95F;

        if (event.sensor == mGravitySensor){
            boolean nowDown = event.values[2] < -SensorManager.GRAVITY_EARTH * factor;
            if (nowDown != mFacingDown){
                if (nowDown){
                    Log.i(TAG, "DOWN");
                } else{
                    Log.i(TAG, "UP");
                }
                mFacingDown = nowDown;
            }
        }
        // ################################################

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i){

    }

    @Override
    protected void onResume(){

        if (mGravitySensor != null){
            mSensorManager.registerListener(this, mGravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onPause(){
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent){
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent){
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent){
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent){
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent){

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent){
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1){
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent){

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1){
        return false;
    }
}
