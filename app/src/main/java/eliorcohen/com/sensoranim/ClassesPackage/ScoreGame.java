package eliorcohen.com.sensoranim.ClassesPackage;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import eliorcohen.com.sensoranim.AdapterPackage.GameListAdapterFavorites;
import eliorcohen.com.sensoranim.OthersPackage.ItemDecoration;
import eliorcohen.com.sensoranim.OthersPackage.NetworkDataProviderFavorites;
import eliorcohen.com.sensoranim.R;
import eliorcohen.com.sensoranim.RoomFavoritesPackage.GameFavorites;
import eliorcohen.com.sensoranim.RoomFavoritesPackage.IGameDataReceived;
import eliorcohen.com.sensoranim.RoomFavoritesPackage.GameViewModelFavorites;

public class ScoreGame extends AppCompatActivity implements IGameDataReceived, NavigationView.OnNavigationItemSelectedListener {

    private GameViewModelFavorites mGameViewModelFavorites;
    private RecyclerView recyclerView;
    private GameListAdapterFavorites adapterFavorites;
    private Paint p;
    private DrawerLayout drawer;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private NetworkDataProviderFavorites networkDataProviderFavorites;
    private ItemDecoration itemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        initUI();
        showUI();
        myRecyclerView();
        getData();
        enableSwipe();
    }

    private void initUI() {
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        swipeRefreshLayout = findViewById(R.id.swipe_containerFrag);
        recyclerView = findViewById(R.id.game_list);

        networkDataProviderFavorites = new NetworkDataProviderFavorites();
        adapterFavorites = new GameListAdapterFavorites(this);

        p = new Paint();
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

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorOrange));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Vibration for 0.1 second
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(100);
                }

                finish();
                startActivity(getIntent());  // Refresh activity

                Toast toast = Toast.makeText(ScoreGame.this, "The list are refreshed!", Toast.LENGTH_SHORT);
                View view = toast.getView();
                view.getBackground().setColorFilter(getResources().getColor(R.color.colorLightBlue), PorterDuff.Mode.SRC_IN);
                TextView text = view.findViewById(android.R.id.message);
                text.setTextColor(getResources().getColor(R.color.colorDarkBrown));
                toast.show();  // Toast

                swipeRefreshLayout.setRefreshing(false);
            }
        });
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

        mGameViewModelFavorites = ViewModelProviders.of(this).get(GameViewModelFavorites.class);
    }

    private void getData() {
        networkDataProviderFavorites.getGameByLocation(this);
    }

    private void enableSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                GameFavorites current = adapterFavorites.getGameAtPosition(position);

                if (direction == ItemTouchHelper.RIGHT) {
                    Toast.makeText(ScoreGame.this, "Deleting...", Toast.LENGTH_LONG).show();
                    mGameViewModelFavorites.deletePlace(current);

                    Intent intentDeleteData = new Intent(ScoreGame.this, DeleteScore.class);
                    startActivity(intentDeleteData);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.deletedicon);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.intentMainActivity) {
            Intent intentBackMainActivity = new Intent(ScoreGame.this, MainActivity.class);
            startActivity(intentBackMainActivity);
        } else if (id == R.id.deleteAllDataFavorites) {
            Intent intentDeleteAllData = new Intent(ScoreGame.this, DeleteAllDataFavorites.class);
            startActivity(intentDeleteAllData);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    @Override
    public void onGameDataReceived() {
        // pass data result to adapter
        mGameViewModelFavorites.getAllPlaces().observe(this, new Observer<List<GameFavorites>>() {
            @Override
            public void onChanged(@Nullable final List<GameFavorites> gameFavorites) {
                // Update the cached copy of the words in the adapter.
                adapterFavorites.setGames(gameFavorites);
            }
        });
    }

}
