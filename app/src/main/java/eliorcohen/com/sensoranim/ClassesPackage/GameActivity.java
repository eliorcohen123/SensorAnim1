package eliorcohen.com.sensoranim.ClassesPackage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private AnimatedView animatedView;
    private RectF oval1, oval2;
    private Paint p1, p2, p3;
    private int x, y, n1, n2, myScore = 0, myFinish = 0, myTime = 5000, idNum1, idNum2, centerX1, centerY1, distanceX1, distanceY1, distanceX2, distanceY2, centerX11, centerY11, distanceX11, distanceY11, distanceX22, distanceY22, centerX111, centerY111, distanceX111, distanceY111, distanceX222, distanceY222, centerX1111, centerY1111, distanceX1111, distanceY1111, distanceX2222, distanceY2222;
    private double diagonalInches;
    private Random rand;
    private SharedPreferences.Editor editorRand;
    private SharedPreferences prefsRand;
    private Timer myTimer;
    private Context mContext;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels,
            screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        animatedView = new AnimatedView(this);

        setContentView(animatedView);

        prefsRand = getSharedPreferences("random", MODE_PRIVATE);
        prefsRand.edit().clear().commit();

        resetTimer();
        getTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Tablet/Phone mode
            DisplayMetrics metrics = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(metrics);

            float yInches = metrics.heightPixels / metrics.ydpi;
            float xInches = metrics.widthPixels / metrics.xdpi;
            diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);

            if (diagonalInches >= 6.5) {
                x -= (int) event.values[0];
                y += (int) event.values[1];
            } else {
                x -= (int) event.values[0] * 3;
                y += (int) event.values[1] * 3;
            }

            idNum1 = prefsRand.getInt("random1", 5000);
            idNum2 = prefsRand.getInt("random2", 5000);

            if (idNum1 == 5000 && idNum2 == 5000) {
                rand = new Random();
                if (diagonalInches >= 6.5) {
                    n1 = rand.nextInt(screenWidth - 30);
                    n2 = rand.nextInt(screenHeight - 150);
                } else {
                    n1 = rand.nextInt(screenWidth - 90);
                    n2 = rand.nextInt(screenHeight - 300);
                }

                editorRand = getSharedPreferences("random", MODE_PRIVATE).edit();
                editorRand.putInt("random1", n1);
                editorRand.putInt("random2", n2);
                editorRand.apply();
            }

            if (diagonalInches >= 6.5) {
                centerX1 = x + 13;
                centerY1 = y + 13;
                distanceX1 = idNum1 - centerX1;
                distanceY1 = idNum2 - centerY1;
                distanceX2 = x - centerX1;
                distanceY2 = y - centerY1;

                centerX11 = x - 13;
                centerY11 = y + 13;
                distanceX11 = idNum1 - centerX11;
                distanceY11 = idNum2 - centerY11;
                distanceX22 = x - centerX11;
                distanceY22 = y - centerY11;

                centerX111 = x + 13;
                centerY111 = y - 13;
                distanceX111 = idNum1 - centerX111;
                distanceY111 = idNum2 - centerY111;
                distanceX222 = x - centerX111;
                distanceY222 = y - centerY111;

                centerX1111 = x - 13;
                centerY1111 = y - 13;
                distanceX1111 = idNum1 - centerX1111;
                distanceY1111 = idNum2 - centerY1111;
                distanceX2222 = x - centerX1111;
                distanceY2222 = y - centerY1111;
            } else {
                centerX1 = x + 13 * 3;
                centerY1 = y + 13 * 3;
                distanceX1 = idNum1 - centerX1;
                distanceY1 = idNum2 - centerY1;
                distanceX2 = x - centerX1;
                distanceY2 = y - centerY1;

                centerX11 = x - 13 * 3;
                centerY11 = y + 13 * 3;
                distanceX11 = idNum1 - centerX11;
                distanceY11 = idNum2 - centerY11;
                distanceX22 = x - centerX11;
                distanceY22 = y - centerY11;

                centerX111 = x + 13 * 3;
                centerY111 = y - 13 * 3;
                distanceX111 = idNum1 - centerX111;
                distanceY111 = idNum2 - centerY111;
                distanceX222 = x - centerX111;
                distanceY222 = y - centerY111;

                centerX1111 = x - 13 * 3;
                centerY1111 = y - 13 * 3;
                distanceX1111 = idNum1 - centerX1111;
                distanceY1111 = idNum2 - centerY1111;
                distanceX2222 = x - centerX1111;
                distanceY2222 = y - centerY1111;
            }

            if ((distanceX1 * distanceX1) + (distanceY1 * distanceY1) < (distanceX2 * distanceX2) + (distanceY2 * distanceY2) ||
                    (distanceX11 * distanceX11) + (distanceY11 * distanceY11) < (distanceX22 * distanceX22) + (distanceY22 * distanceY22) ||
                    (distanceX111 * distanceX111) + (distanceY111 * distanceY111) < (distanceX222 * distanceX222) + (distanceY222 * distanceY222) ||
                    (distanceX1111 * distanceX1111) + (distanceY1111 * distanceY1111) < (distanceX2222 * distanceX2222) + (distanceY2222 * distanceY2222)) {
                myScore = myScore + 1;
                myTime = myTime - 250;
                myFinish = 0;

                resetTimer();
                getTimer();
            }
            if (diagonalInches >= 6.5) {
                if (screenWidth - 30 < x || x < 0) {
                    x += (int) event.values[0];
                }
                if (screenHeight - 150 < y || y < 0) {
                    y -= (int) event.values[1];
                }
            } else {
                if (screenWidth - 90 < x || x < 0) {
                    x += (int) event.values[0] * 3;
                }
                if (screenHeight - 300 < y || y < 0) {
                    y -= (int) event.values[1] * 3;
                }
            }
        }
    }

    private void getTimer() {
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
                getFinish();
            }
        }, 0, myTime);
    }

    private void TimerMethod() {
        this.runOnUiThread(Timer_Tick);
    }

    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            prefsRand = getSharedPreferences("random", MODE_PRIVATE);
            prefsRand.edit().clear().commit();
        }
    };

    private void resetTimer() {
        if (myTimer != null) {
            myTimer.cancel();
            myTimer = null;
        }
    }

    private void getFinish() {
        myFinish = myFinish + 1;
        if (myFinish == 3) {
            if (myTimer != null) {
                myTimer.cancel();
                myTimer = null;
            }

            Intent intent = new Intent(GameActivity.this, AddScore.class);
            intent.putExtra("score1", myScore);
            startActivity(intent);

            finish();
        }
    }

    private class AnimatedView extends View {

        private int width = 30, height = 30;

        private AnimatedView(Context context) {
            super(context);

            mContext = context;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            prefsRand = getSharedPreferences("random", MODE_PRIVATE);
            idNum1 = prefsRand.getInt("random1", 1000);
            idNum2 = prefsRand.getInt("random2", 1000);

            // Tablet/Phone mode
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(metrics);

            float yInches = metrics.heightPixels / metrics.ydpi;
            float xInches = metrics.widthPixels / metrics.xdpi;
            diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
            if (diagonalInches >= 6.5) {
                oval1 = new RectF(x, y, x + width, y + height);
                oval2 = new RectF(idNum1, idNum2, idNum1 + width, idNum2 + height);
            } else {
                oval1 = new RectF(x, y, x + width * 3, y + height * 3);
                oval2 = new RectF(idNum1, idNum2, idNum1 + width * 3, idNum2 + height * 3);
            }
            p1 = new Paint();
            p1.setColor(Color.BLUE);
            p2 = new Paint();
            p2.setColor(Color.BLACK);
            p3 = new Paint();
            p3.setColor(Color.BLACK);

            if (diagonalInches >= 6.5) {
                p3.setTextSize(20);
            } else {
                p3.setTextSize(60);
            }

            canvas.drawOval(oval1, p1);
            canvas.drawOval(oval2, p2);

            if (diagonalInches >= 6.5) {
                canvas.drawText("Score: " + myScore, 10, 880, p3);
            } else {
                canvas.drawText("Score: " + myScore, 10, 2400, p3);
            }
            invalidate();
        }
    }

}