package eliorcohen.com.sensoranim.ClassesPackage;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import eliorcohen.com.sensoranim.R;
import eliorcohen.com.sensoranim.RoomFavoritesPackage.GameFavorites;
import eliorcohen.com.sensoranim.RoomFavoritesPackage.GameViewModelFavorites;

public class AddScore extends AppCompatActivity implements View.OnClickListener {

    private GameViewModelFavorites gameViewModelFavorites;
    private EditText name;
    private TextView textViewOK, score;
    private Button btnBack;
    private int myScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_place_me);

        initUI();
        initListeners();
        getData();
    }

    private void initUI() {
        // GetSerializable for the texts
        Bundle extras = getIntent().getExtras();
        myScore = extras.getInt("score1");

        name = findViewById(R.id.editTextName);  // ID of the name
        score = findViewById(R.id.textViewScore);  // ID of the score

        textViewOK = findViewById(R.id.textViewOK);

        btnBack = findViewById(R.id.btnBack);
    }

    private void initListeners() {
        textViewOK.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private void getData() {
        score.setText(String.valueOf(myScore));  // GetSerializable of myScore
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewOK:
                String name1 = name.getText().toString();  // GetText of the name
                int score1 = myScore;

                GameFavorites gameFavorites = new GameFavorites(name1, score1);
                gameViewModelFavorites = ViewModelProviders.of(AddScore.this).get(GameViewModelFavorites.class);
                gameViewModelFavorites.insertPlace(gameFavorites);

//                // Pass from AddScore to ScoreGame
                Intent intentAddInternetToMain = new Intent(AddScore.this, ScoreGame.class);
                startActivity(intentAddInternetToMain);

                finish();
                break;
            case R.id.btnBack:
                Intent intentMainActivity = new Intent(AddScore.this, MainActivity.class);
                startActivity(intentMainActivity);
                break;
        }
    }

}
