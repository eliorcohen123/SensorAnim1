package eliorcohen.com.sensoranim.PagesPackage;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.Date;

import eliorcohen.com.sensoranim.R;

public class AddScore extends AppCompatActivity implements View.OnClickListener {

    private EditText name;
    private TextView textViewOK, score;
    private Button btnBack;
    private int myScore;
    private Firebase firebase;

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
        assert extras != null;
        myScore = extras.getInt("score1");

        name = findViewById(R.id.editTextName);  // ID of the name
        score = findViewById(R.id.textViewScore);  // ID of the score

        textViewOK = findViewById(R.id.textViewOK);

        btnBack = findViewById(R.id.btnBack);

        Firebase.setAndroidContext(this);
        firebase = new Firebase(getString(R.string.FirebaseKey));
    }

    private void initListeners() {
        textViewOK.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private void getData() {
        score.setText(String.valueOf(myScore));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewOK:
                String name1 = name.getText().toString();  // GetText of the name
                int score1 = myScore;

                if (TextUtils.isEmpty(name1)) {  // If the text are empty the score will not be approved
                    name.setError("Name is required...");  // Print text of error if the text are empty
                } else {
                    Date date = new Date();
                    String time = date.toString();
                    firebase.child(time).child("name").setValue(name1);
                    firebase.child(time).child("score").setValue(score1);

//                // Pass from AddScore to ScoreGame
                    Intent intentAddInternetToMain = new Intent(AddScore.this, ScoreGame.class);
                    startActivity(intentAddInternetToMain);

                    finish();
                }
                break;
            case R.id.btnBack:
                Intent intentMainActivity = new Intent(AddScore.this, MainActivity.class);
                startActivity(intentMainActivity);
                break;
        }
    }

}
