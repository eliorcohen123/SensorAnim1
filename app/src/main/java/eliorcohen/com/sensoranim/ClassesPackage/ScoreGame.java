package eliorcohen.com.sensoranim.ClassesPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import eliorcohen.com.sensoranim.AdapterPackage.GameListAdapterFavorites;
import eliorcohen.com.sensoranim.OthersPackage.GameModel;
import eliorcohen.com.sensoranim.OthersPackage.ItemDecoration;
import eliorcohen.com.sensoranim.R;

public class ScoreGame extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private GameListAdapterFavorites adapterFavorites;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ItemDecoration itemDecoration;
    private Firebase firebase;
    private ArrayList<GameModel> gameModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        initUI();
        showUI();
        myRecyclerView();
        getReadFirebase();
    }

    private void initUI() {
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        recyclerView = findViewById(R.id.game_list);

        gameModel = new ArrayList<>();

        Firebase.setAndroidContext(this);
        firebase = new Firebase(getString(R.string.FirebaseKey));
    }

    private void showUI() {
        (this).setSupportActionBar(toolbar);

        findViewById(R.id.myButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open right drawer

                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else
                    drawer.openDrawer(GravityCompat.END);
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void myRecyclerView() {
        try {
            recyclerView.setAdapter(adapterFavorites);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            if (itemDecoration == null) {
                itemDecoration = new ItemDecoration(20);
                recyclerView.addItemDecoration(itemDecoration);
            }
        } catch (Exception e) {

        }
    }

    private void getReadFirebase() {
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gameModel.clear();
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        GameModel lossModel = snapshot.getValue(GameModel.class);
                        gameModel.add(lossModel);
                    }
                    adapterFavorites = new GameListAdapterFavorites(ScoreGame.this, gameModel);
                    adapterFavorites.setGames(gameModel);
                    recyclerView.setAdapter(adapterFavorites);
                } catch (Exception e) {

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.intentMainActivity) {
            Intent intentBackMainActivity = new Intent(ScoreGame.this, MainActivity.class);
            startActivity(intentBackMainActivity);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

}
