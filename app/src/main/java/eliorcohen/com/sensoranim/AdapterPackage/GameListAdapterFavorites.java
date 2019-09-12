package eliorcohen.com.sensoranim.AdapterPackage;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import eliorcohen.com.sensoranim.OthersPackage.GameModel;
import eliorcohen.com.sensoranim.R;

public class GameListAdapterFavorites extends RecyclerView.Adapter<GameViewHolder> {

    private final LayoutInflater mInflater;
    private List<GameModel> mGameList;

    public GameListAdapterFavorites(Context context, ArrayList<GameModel> gameModel) {
        mInflater = LayoutInflater.from(context);
        this.mGameList = gameModel;
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item_score, parent, false);
        return new GameViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final GameViewHolder holder, final int position) {
        if (mGameList != null) {
            final GameModel current = mGameList.get(position);
            try {
                holder.name1.setText(current.getName());
                holder.score1.setText(String.valueOf(current.getScore()));
            } catch (Exception e) {

            }

            setFadeAnimation(holder.itemView);
        } else {
            // Covers the case of data not being ready yet.
            holder.name1.setText("No GameSearch");
        }
    }

    public void setGames(List<GameModel> gameFavorites) {
        mGameList = gameFavorites;
        Collections.sort(mGameList, (obj1, obj2) -> {
            // ## Ascending order
//                return obj1.getDistance().compareToIgnoreCase(obj2.getDistance()); // To compare string values
            return Integer.valueOf(obj2.getScore()).compareTo(obj1.getScore()); // To compare integer values

            // ## Descending order
//                 return obj1.getCompanyName().compareToIgnoreCase(obj2.getCompanyName()); // To compare string values
            // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values
        });
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mGameList != null)
            return mGameList.size();
        else return 0;
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1500);
        view.startAnimation(anim);
    }

}
