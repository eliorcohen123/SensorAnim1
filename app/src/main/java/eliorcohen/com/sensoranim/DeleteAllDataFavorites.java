package eliorcohen.com.sensoranim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import eliorcohen.com.sensoranim.RoomFavoritesPackage.GameViewModelFavorites;

public class DeleteAllDataFavorites extends AppCompatActivity implements View.OnClickListener {

    private Button btnOK, btnCancel;
    private GameViewModelFavorites gameViewModelFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_all_data_favorites);

        initUI();
        initListeners();
    }

    private void initUI() {
        btnOK = findViewById(R.id.btnOK);
        btnCancel = findViewById(R.id.btnCancel);

        gameViewModelFavorites = new GameViewModelFavorites(NearByApplication.getApplication());
    }

    private void initListeners() {
        btnOK.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOK:
                gameViewModelFavorites.deleteAll();

                Toast.makeText(DeleteAllDataFavorites.this, "All the data of Scores are deleted!", Toast.LENGTH_LONG).show();

                Intent intentDeleteAllDataToMain = new Intent(DeleteAllDataFavorites.this, MainActivity.class);
                startActivity(intentDeleteAllDataToMain);
                break;
            case R.id.btnCancel:
                onBackPressed();
                break;
        }
    }

}
