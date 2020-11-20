package com.example.soukousschallenge.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.soukousschallenge.R;
import com.example.soukousschallenge.model.Action;
import com.example.soukousschallenge.model.ActionBottomSwipe;
import com.example.soukousschallenge.model.ActionDoubleTap;
import com.example.soukousschallenge.model.ActionHold;
import com.example.soukousschallenge.model.ActionLeftSwipe;
import com.example.soukousschallenge.model.ActionOneTap;
import com.example.soukousschallenge.model.ActionRightSwipe;
import com.example.soukousschallenge.model.ActionShake;
import com.example.soukousschallenge.model.ActionTopSwipe;
import com.example.soukousschallenge.model.ActionTurnDown;
import com.example.soukousschallenge.model.Partie;

import java.util.ArrayList;
import java.util.Random;


public class GameActivity extends AppCompatActivity implements SensorEventListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{


    private ImageButton mPauseButton;
    private TextView mLabelTimer;
    private TextView mScoreNumber;
    private TextView mLabelAction;
    public static final int PAUSE_POPUP = 1;
    private static final String TAG = "DETECTION";
    private SensorManager mSensorManager;
    private Sensor mAccelerometerSensor;
    private Sensor mGravitySensor;
    private boolean mFacingDown;

    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private GestureDetectorCompat mDetector;

    private Partie partie;

    private ArrayList<Action> liste_actions;
    private int actionSelected;
    private int previousAction = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mPauseButton = findViewById(R.id.activity_game_pauseButton);
        mLabelTimer = findViewById(R.id.activity_game_labelTimer);
        mScoreNumber = findViewById(R.id.activity_game_scoreNumber);
        mLabelAction = findViewById(R.id.activity_game_labelAction);

        partie = new Partie(mLabelTimer);

        liste_actions = new ArrayList<>(); // On crée la liste des différentes actions
        initListe(); // On initialise la liste

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGravitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        mDetector = new GestureDetectorCompat(this, this);
        mDetector.setOnDoubleTapListener(this);

        partie.startChrono();
        pickAction();

        if (mGravitySensor == null){
            Log.w(TAG, "Device has no gravity sensor");
        }

        mPauseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                partie.pauseChrono();
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
            if (mAccel > 12 && actionSelected == 0 && !partie.isFinished()){
                Log.i(TAG, "Soukouss");
                ActionShake actionShake = new ActionShake();
                incrementScore((String) mScoreNumber.getText());
                pickAction();
            }
        }
        // ################################################
        // ################# TURN OVER #####################
        final float factor = 0.95F;

        if (event.sensor == mGravitySensor){
            boolean nowDown = event.values[2] < -SensorManager.GRAVITY_EARTH * factor;
            if (nowDown != mFacingDown){
                if (nowDown && actionSelected == 1 && !partie.isFinished()){
                    Log.i(TAG, "DOWN");
                    incrementScore((String) mScoreNumber.getText());
                    pickAction();
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

        partie.startChrono();

        if (mGravitySensor != null){
            mSensorManager.registerListener(this, mGravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onPause(){
        partie.pauseChrono();
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent event){
        if(actionSelected == 6 && !partie.isFinished()){
            Log.i(TAG,"Single Tap");
            incrementScore((String) mScoreNumber.getText());
            pickAction();
        }
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event){
        if(actionSelected == 7 && !partie.isFinished()){
            Log.i(TAG,"Double Tap");
            incrementScore((String) mScoreNumber.getText());
            pickAction();
        }
        return true;
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
    public void onLongPress(MotionEvent event){
        if(actionSelected == 8 && !partie.isFinished()){
            Log.i(TAG,"Long press");
            incrementScore((String) mScoreNumber.getText());
            pickAction();
        }
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY){
        if(event1.getX() - event2.getX() > SWIPE_MIN_DISTANCE &&
        Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY && actionSelected == 2 &&!partie.isFinished())
        {
            Log.i(TAG, "LEFT SWIPE");
            incrementScore((String) mScoreNumber.getText());
            pickAction();
            return true;
        }
        else if(event2.getX() - event1.getX() > SWIPE_MIN_DISTANCE &&
        Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY && actionSelected == 3 && !partie.isFinished())
        {
            Log.i(TAG, "RIGHT SWIPE");
            incrementScore((String) mScoreNumber.getText());
            pickAction();
            return true;
        }
        else if(event1.getY() - event2.getY() > SWIPE_MIN_DISTANCE &&
        Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY && actionSelected == 4 && !partie.isFinished())
        {
            Log.i(TAG, "SWIPE UP");
            incrementScore((String) mScoreNumber.getText());
            pickAction();
            return true;
        }
        else if(event2.getY() - event1.getY() > SWIPE_MIN_DISTANCE &&
        Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY && actionSelected == 5 && !partie.isFinished())
        {
            Log.i(TAG, "SWIPE DOWN");
            incrementScore((String) mScoreNumber.getText());
            pickAction();

            return true;
        }
        else return false;
    }

    public void vibrate()
    {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(150);
        }
    }

    public void incrementScore(String oldScore)
    {
        int oldIntScore = Integer.parseInt(oldScore);
        int newIntScore = oldIntScore + 1;
        String newScore = Integer.toString(newIntScore);
        mScoreNumber.setText(newScore);
    }

    public void initListe(){
        liste_actions.add(new ActionShake()); // Action dont l'ID = 0
        liste_actions.add(new ActionTurnDown()); // Action dont l'ID = 1
        liste_actions.add(new ActionLeftSwipe()); // Action dont l'ID = 2
        liste_actions.add(new ActionRightSwipe()); // Action dont l'ID = 3
        liste_actions.add(new ActionTopSwipe()); // Action dont l'ID = 4
        liste_actions.add(new ActionBottomSwipe()); // Action dont l'ID = 5
        liste_actions.add(new ActionOneTap()); // Action dont l'ID = 6
        liste_actions.add(new ActionDoubleTap()); // Action dont l'ID = 7
        liste_actions.add(new ActionHold()); // Action dont l'ID = 8
    }

    public void pickAction(){
        if(!partie.isFinished()){
            previousAction = actionSelected;
            do{
                Random rand = new Random();
                actionSelected = rand.nextInt(liste_actions.size());
            }while(actionSelected == previousAction);
            mLabelAction.setText(liste_actions.get(actionSelected).getActionText(this.getApplicationContext()));
        }
    }
}
