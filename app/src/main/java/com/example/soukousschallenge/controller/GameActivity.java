package com.example.soukousschallenge.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
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


public class GameActivity extends AppCompatActivity implements SensorEventListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {


    private TextView mLabelTimer;
    private TextView mScoreNumber;
    private TextView mLabelAction;
    public static final int ENDGAME_POPUP = 3;
    public static final int RC_GAME = 1;
    private SensorManager mSensorManager;
    private Sensor mAccelerometerSensor;
    private Sensor mGravitySensor;
    private boolean mFacingDown;
    private MediaPlayer mPlayer;

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
    private int error = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

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


        partie.setCl(new Partie.CustomListener() {
            @Override
            public void onChange() {
                Intent endGamePopUpActivity = new Intent(GameActivity.this, EndGamePopUpActivity.class);
                endGamePopUpActivity.putExtra("scoreNumber", Integer.valueOf((String) mScoreNumber.getText()));
                startActivityForResult(endGamePopUpActivity, ENDGAME_POPUP);
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // ################ DETECTION ######################
        // ################# SHAKE ########################
        if (event.sensor == mAccelerometerSensor) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel > 12 && !partie.isFinished()) {
                if (actionSelected == 0) {
                    incrementScore((String) mScoreNumber.getText());
                    play(mScoreNumber);
                    pickAction();
                } else {
                    vibrate();
                    mLabelAction.setText(mLabelAction.getText() + "\n" + getString(R.string.actionError));
                }
            }
        }
        // ################################################
        // ################# TURN OVER #####################
        final float factor = 0.95F;

        if (event.sensor == mGravitySensor) {
            boolean nowDown = event.values[2] < -SensorManager.GRAVITY_EARTH * factor;
            if (nowDown != mFacingDown) {
                if (nowDown && !partie.isFinished()) {
                    if (actionSelected == 1) {
                        incrementScore((String) mScoreNumber.getText());
                        error = 0;
                        play(mScoreNumber);
                        pickAction();
                    } else {
                        error++;
                        if (error <= 1) {
                            vibrate();
                            mLabelAction.setText(mLabelAction.getText() + "\n" + getString(R.string.actionError));
                        } else {
                            vibrate();
                        }
                    }

                }
                mFacingDown = nowDown;
            }
        }
        // ################################################

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {

        /*if(!partie.isFinished()){
            partie.startChrono();
        }*/

        if (mGravitySensor != null) {
            mSensorManager.registerListener(this, mGravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onPause() {
        partie.pauseChrono();
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        if (!partie.isFinished()) {
            if (actionSelected == 6) {
                incrementScore((String) mScoreNumber.getText());
                error = 0;
                play(mScoreNumber);
                pickAction();
            } else {
                error++;
                if (error <= 1) {
                    vibrate();
                    mLabelAction.setText(mLabelAction.getText() + "\n" + getString(R.string.actionError));
                } else {
                    vibrate();
                }
            }

        }
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        if (!partie.isFinished()) {
            if (actionSelected == 7) {
                incrementScore((String) mScoreNumber.getText());
                error = 0;
                play(mScoreNumber);
                pickAction();
            } else {
                error++;
                if (error <= 1) {
                    vibrate();
                    mLabelAction.setText(mLabelAction.getText() + "\n" + getString(R.string.actionError));
                } else {
                    vibrate();
                }
            }

        }
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        if (!partie.isFinished()) {
            if (actionSelected == 8) {
                incrementScore((String) mScoreNumber.getText());
                error = 0;
                play(mScoreNumber);
                pickAction();
            } else {
                error++;
                if (error <= 1) {
                    vibrate();
                    mLabelAction.setText(mLabelAction.getText() + "\n" + getString(R.string.actionError));
                } else {
                    vibrate();
                }
            }

        }
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
        if (event1.getX() - event2.getX() > SWIPE_MIN_DISTANCE &&
                Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY && !partie.isFinished()) {
            if (actionSelected == 2) {
                incrementScore((String) mScoreNumber.getText());
                error = 0;
                play(mScoreNumber);
                pickAction();
            } else {
                error++;
                if (error <= 1) {
                    vibrate();
                    mLabelAction.setText(mLabelAction.getText() + "\n" + getString(R.string.actionError));
                } else {
                    vibrate();
                }
            }

            return true;
        } else if (event2.getX() - event1.getX() > SWIPE_MIN_DISTANCE &&
                Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY && !partie.isFinished()) {
            if (actionSelected == 3) {
                incrementScore((String) mScoreNumber.getText());
                error = 0;
                play(mScoreNumber);
                pickAction();
            } else {
                error++;
                if (error <= 1) {
                    vibrate();
                    mLabelAction.setText(mLabelAction.getText() + "\n" + getString(R.string.actionError));
                } else {
                    vibrate();
                }
            }
            return true;
        } else if (event1.getY() - event2.getY() > SWIPE_MIN_DISTANCE &&
                Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY && !partie.isFinished()) {
            if (actionSelected == 4) {
                incrementScore((String) mScoreNumber.getText());
                error = 0;
                play(mScoreNumber);
                pickAction();
            } else {
                error++;
                if (error <= 1) {
                    vibrate();
                    mLabelAction.setText(mLabelAction.getText() + "\n" + getString(R.string.actionError));
                } else {
                    vibrate();
                }
            }
            return true;
        } else if (event2.getY() - event1.getY() > SWIPE_MIN_DISTANCE &&
                Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY && !partie.isFinished()) {
            if (actionSelected == 5) {
                incrementScore((String) mScoreNumber.getText());
                error = 0;
                play(mScoreNumber);
                pickAction();
            } else {
                error++;
                if (error <= 1) {
                    vibrate();
                    mLabelAction.setText(mLabelAction.getText() + "\n" + getString(R.string.actionError));
                } else {
                    vibrate();
                }
            }
            return true;
        } else return false;
    }

    public void vibrate() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(150);
        }
    }

    public void incrementScore(String oldScore) {
        int oldIntScore = Integer.parseInt(oldScore);
        int newIntScore = oldIntScore + 1;
        String newScore = Integer.toString(newIntScore);
        mScoreNumber.setText(newScore);
    }

    public void initListe() {
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

    public void pickAction() {
        if (!partie.isFinished()) {
            previousAction = actionSelected;
            do {
                Random rand = new Random();
                actionSelected = rand.nextInt(liste_actions.size());
            } while (actionSelected == previousAction);
            mLabelAction.setText(liste_actions.get(actionSelected).getActionText(this.getApplicationContext()));
        }
    }

    public void stop() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    public void play(View v) {
        stop();
        mPlayer = MediaPlayer.create(this, R.raw.successsoundeffect);
        mPlayer.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stop();
                    }
                }
        );
        mPlayer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == ENDGAME_POPUP) {
                if (data.hasExtra("score")) {
                    int score = data.getIntExtra("score", -1);
                    if (score != -1) {
                        Intent i = new Intent();
                        i.putExtra("score", score);
                        setResult(RC_GAME, i);
                        finish();
                    }
                } else {
                    if (data.hasExtra("replay")) {
                        int replay = data.getIntExtra("replay", -1);
                        if (replay != -1) {
                            Intent i = new Intent();
                            i.putExtra("replay", replay);
                            setResult(RC_GAME, i);
                            finish();
                        }
                    }
                }
            }
        }
    }
}
