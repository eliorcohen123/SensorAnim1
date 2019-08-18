package eliorcohen.com.sensoranim;

import android.content.Context;
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
import android.view.View;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private AnimatedView animatedView;
    private RectF oval1, oval2;
    private Paint p1, p2;
    private int x, y, n1, n2, myTime = 5000, idTime, idNum1, idNum2, centerX1, centerY1, distanceX1, distanceY1, distanceX2, distanceY2, centerX11, centerY11, distanceX11, distanceY11, distanceX22, distanceY22, centerX111, centerY111, distanceX111, distanceY111, distanceX222, distanceY222, centerX1111, centerY1111, distanceX1111, distanceY1111, distanceX2222, distanceY2222;
    private Random rand;
    private SharedPreferences.Editor editorRand, editorTime;
    private SharedPreferences prefsRand, prefsTime;
    private Timer myTimer;
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
        prefsTime = getSharedPreferences("timer", MODE_PRIVATE);
        prefsTime.edit().clear().commit();

        prefsTime = getSharedPreferences("timer", MODE_PRIVATE);
        idTime = prefsTime.getInt("timer1", 5000);

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }
        }, 0, idTime);
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
            x -= (int) event.values[0];
            y += (int) event.values[1];

            idNum1 = prefsRand.getInt("random1", 1000);
            idNum2 = prefsRand.getInt("random2", 1000);

            if (idNum1 == 1000 && idNum2 == 1000) {
                rand = new Random();
                n1 = rand.nextInt(570);
                n2 = rand.nextInt(866);
                editorRand = getSharedPreferences("random", MODE_PRIVATE).edit();
                editorRand.putInt("random1", n1);
                editorRand.putInt("random2", n2);
                editorRand.apply();
            }

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

            if ((distanceX1 * distanceX1) + (distanceY1 * distanceY1) < (distanceX2 * distanceX2) + (distanceY2 * distanceY2) ||
                    (distanceX11 * distanceX11) + (distanceY11 * distanceY11) < (distanceX22 * distanceX22) + (distanceY22 * distanceY22) ||
                    (distanceX111 * distanceX111) + (distanceY111 * distanceY111) < (distanceX222 * distanceX222) + (distanceY222 * distanceY222) ||
                    (distanceX1111 * distanceX1111) + (distanceY1111 * distanceY1111) < (distanceX2222 * distanceX2222) + (distanceY2222 * distanceY2222)) {
                myTime = myTime - 250;
                editorTime = getSharedPreferences("timer", MODE_PRIVATE).edit();
                editorTime.putInt("timer1", myTime);
                editorTime.apply();

                prefsTime = getSharedPreferences("timer", MODE_PRIVATE);
                idTime = prefsTime.getInt("timer1", 5000);

                if (myTimer != null) {
                    myTimer.cancel();
                    myTimer = null;
                }

                myTimer = new Timer();
                myTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        TimerMethod();
                    }
                }, 0, idTime);
            }
            if (screenWidth - 30 < x || x < 0) {
                x += (int) event.values[0];
            }
            if (screenHeight - 120 < y || y < 0) {
                y -= (int) event.values[1];
            }
        }
    }

    private class AnimatedView extends View {

        private int width = 30, height = 30;

        private AnimatedView(Context context) {
            super(context);

        }

        @Override
        protected void onDraw(Canvas canvas) {
            oval1 = new RectF(x, y, x + width, y + height);
            prefsRand = getSharedPreferences("random", MODE_PRIVATE);
            idNum1 = prefsRand.getInt("random1", 1000);
            idNum2 = prefsRand.getInt("random2", 1000);
            oval2 = new RectF(idNum1, idNum2, idNum1 + width, idNum2 + height);
            p1 = new Paint();
            p1.setColor(Color.BLUE);
            p2 = new Paint();
            p2.setColor(Color.BLACK);
            canvas.drawOval(oval1, p1);
            canvas.drawOval(oval2, p2);
            invalidate();
        }
    }

}