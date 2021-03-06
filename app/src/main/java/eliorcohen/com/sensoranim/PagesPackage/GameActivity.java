package eliorcohen.com.sensoranim.PagesPackage;

import android.content.Context;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
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
    private Paint p1, p2, p3, p4, p5;
    private String startTime;
    private double myTime = 5000.0;
    private Random rand;
    private Timer myTimer;
    private CountDownTimer countDownTimer;
    private Context mContext;
    private int x, y, n1, n2, myDensity, myScore = 0, myFinish = 3, idNum1, idNum2, centerX1, centerY1, distanceX1, distanceY1, distanceX2, distanceY2,
            centerX11, centerY11, distanceX11, distanceY11, distanceX22, distanceY22, centerX111, centerY111, distanceX111, distanceY111,
            distanceX222, distanceY222, centerX1111, centerY1111, distanceX1111, distanceY1111, distanceX2222, distanceY2222,
            screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels, screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        animatedView = new AnimatedView(this);

        setContentView(animatedView);

        removeRandData();
        resetCountDownTimer();
        resetTimer();
        getTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
            myDensity = (screenHeight + screenWidth) / 1300;

            getSensorEvent(event, myDensity);

            if (idNum1 == -1 && idNum2 == -1) {
                getRandom(screenWidth - 30 * myDensity, screenHeight - 150 * myDensity);
                getRandData(n1, n2);
            }

            getCalCircle(myDensity);

            if ((distanceX1 * distanceX1) + (distanceY1 * distanceY1) < (distanceX2 * distanceX2) + (distanceY2 * distanceY2) ||
                    (distanceX11 * distanceX11) + (distanceY11 * distanceY11) < (distanceX22 * distanceX22) + (distanceY22 * distanceY22) ||
                    (distanceX111 * distanceX111) + (distanceY111 * distanceY111) < (distanceX222 * distanceX222) + (distanceY222 * distanceY222) ||
                    (distanceX1111 * distanceX1111) + (distanceY1111 * distanceY1111) < (distanceX2222 * distanceX2222) + (distanceY2222 * distanceY2222)) {
                myScore++;
                myTime = myTime / 1.1;
                myFinish = 3;

                resetTimer();
                getTimer();
            }

            getBounds(event, screenWidth - 30 * myDensity, screenHeight - 150 * myDensity, myDensity);
        }
    }

    private void getRandData(int num1, int num2) {
        idNum1 = num1;
        idNum2 = num2;
    }

    private void removeRandData() {
        idNum1 = -1;
        idNum2 = -1;
    }

    private void getBounds(SensorEvent event, int numWidth, int numHeight, int numTypePhone) {
        if (numWidth < x || x < 0) {
            x += (int) event.values[0] * numTypePhone;
        }

        if (numHeight < y || y < 0) {
            y -= (int) event.values[1] * numTypePhone;
        }
    }

    private void getSensorEvent(SensorEvent event, int num) {
        x -= (int) event.values[0] * num;
        y += (int) event.values[1] * num;
    }

    private void getRandom(int num1, int num2) {
        rand = new Random();
        n1 = rand.nextInt(num1);
        n2 = rand.nextInt(num2);
    }

    private void getCalCircle(int num) {
        centerX1 = x + 13 * num;
        centerY1 = y + 13 * num;
        distanceX1 = idNum1 - centerX1;
        distanceY1 = idNum2 - centerY1;
        distanceX2 = x - centerX1;
        distanceY2 = y - centerY1;

        centerX11 = x - 13 * num;
        centerY11 = y + 13 * num;
        distanceX11 = idNum1 - centerX11;
        distanceY11 = idNum2 - centerY11;
        distanceX22 = x - centerX11;
        distanceY22 = y - centerY11;

        centerX111 = x + 13 * num;
        centerY111 = y - 13 * num;
        distanceX111 = idNum1 - centerX111;
        distanceY111 = idNum2 - centerY111;
        distanceX222 = x - centerX111;
        distanceY222 = y - centerY111;

        centerX1111 = x - 13 * num;
        centerY1111 = y - 13 * num;
        distanceX1111 = idNum1 - centerX1111;
        distanceY1111 = idNum2 - centerY1111;
        distanceX2222 = x - centerX1111;
        distanceY2222 = y - centerY1111;
    }

    private void getTimer() {
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                timerMethod();
                getFinish();
            }
        }, 0, (int) myTime);
    }

    private void timerMethod() {
        runOnUiThread(timerTick);
    }

    private final Runnable timerTick = () -> {
        removeRandData();
        resetCountDownTimer();
        getMySeconds();
    };

    private void resetCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    private void getMySeconds() {
        countDownTimer = new CountDownTimer((long) myTime, 1) {
            public void onTick(long millisUntilFinished) {
                double seconds = millisUntilFinished / 1000;
                startTime = String.valueOf((seconds + millisUntilFinished) / 1000);

                if (myFinish == 0) {
                    startTime = "0.00";
                }
            }

            public void onFinish() {

            }
        }.start();
    }

    private void resetTimer() {
        if (myTimer != null) {
            myTimer.cancel();
            myTimer = null;
        }
    }

    private void getFinish() {
        myFinish = myFinish - 1;
        if (myFinish == 0) {
            resetTimer();

            Intent intent = new Intent(GameActivity.this, AddScoreActivity.class);
            intent.putExtra("score1", myScore);
            startActivity(intent);

            finish();
        }
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private class AnimatedView extends View {

        private final int lengthBallXY = 30;
        private final double myScreenHeight = screenHeight - 90;
        private final double myScreenWidth = screenWidth * 0.33;

        private AnimatedView(Context context) {
            super(context);

            mContext = context;
        }

        @Override
        protected void onDraw(final Canvas canvas) {
            getRectF(myDensity);

            p1 = new Paint();
            p1.setColor(Color.BLUE);
            p2 = new Paint();
            p2.setColor(Color.BLACK);
            p3 = new Paint();
            p3.setColor(Color.BLACK);
            p3.setTextSize(20 * myDensity);
            p4 = new Paint();
            p4.setColor(Color.BLACK);
            p4.setTextSize(20 * myDensity);
            p5 = new Paint();
            p5.setColor(Color.BLACK);
            p5.setTextSize(20 * myDensity);

            canvas.drawOval(oval1, p1);
            canvas.drawOval(oval2, p2);
            canvas.drawText("Score: " + myScore, dpToPx(10), (int) myScreenHeight, p3);
            canvas.drawText("Time: " + startTime, (int) myScreenWidth + dpToPx(10), (int) myScreenHeight, p4);
            canvas.drawText("Life(s): " + myFinish, (int) myScreenWidth * 2 + dpToPx(10), (int) myScreenHeight, p5);

            invalidate();
        }

        private void getRectF(int num) {
            oval1 = new RectF(x, y, x + lengthBallXY * num, y + lengthBallXY * num);
            oval2 = new RectF(idNum1, idNum2, idNum1 + lengthBallXY * num, idNum2 + lengthBallXY * num);
        }

    }

}
