package eliorcohen.com.sensoranim.ClassesPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import eliorcohen.com.sensoranim.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGame, btnShowScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initListeners();
    }

    private void initUI() {
        btnGame = findViewById(R.id.btnStartGame);
        btnShowScores = findViewById(R.id.btnShowScores);

        AppRater.app_launched(this);
    }

    private void initListeners() {
        btnGame.setOnClickListener(this);
        btnShowScores.setOnClickListener(this);
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
