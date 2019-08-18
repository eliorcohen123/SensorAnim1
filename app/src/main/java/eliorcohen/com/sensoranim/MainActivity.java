package eliorcohen.com.sensoranim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initListeners();
    }

    private void initUI() {
        btnGame = findViewById(R.id.btnStartGame);
    }

    private void initListeners() {
        btnGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartGame:
                Intent intentNewGame = new Intent(this, GameActivity.class);
                startActivity(intentNewGame);
                break;
        }
    }

}
