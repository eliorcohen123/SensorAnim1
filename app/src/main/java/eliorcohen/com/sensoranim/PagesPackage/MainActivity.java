package eliorcohen.com.sensoranim.PagesPackage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;

import eliorcohen.com.sensoranim.R;
import guy4444.smartrate.SmartRate;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGame, btnShowScores;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initListeners();
        initAppRater();
        getGoogleAnalytics();
    }

    private void initUI() {
        btnGame = findViewById(R.id.btnStartGame);
        btnShowScores = findViewById(R.id.btnShowScores);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    private void initListeners() {
        btnGame.setOnClickListener(this);
        btnShowScores.setOnClickListener(this);
    }

    private void initAppRater() {
        SmartRate.Rate(MainActivity.this
                , "Rate Us"
                , "Tell others what you think about this app"
                , "Continue"
                , "Please take a moment and rate us on Google Play"
                , "click here"
                , "Ask me later"
                , "Never ask again"
                , "Cancel"
                , "Thanks for the feedback"
                , Color.parseColor("#2196F3")
                , 5
                , 1
                , 1
        );
    }

    private void getGoogleAnalytics() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "2");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "catchball");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartGame:
                Intent intentNewGame = new Intent(this, GameActivity.class);
                startActivity(intentNewGame);
                break;
            case R.id.btnShowScores:
                Intent intentScores = new Intent(this, ScoreGame.class);
                startActivity(intentScores);
                break;
        }
    }

}
