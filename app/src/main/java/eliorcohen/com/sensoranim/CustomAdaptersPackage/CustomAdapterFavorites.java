package eliorcohen.com.sensoranim.CustomAdaptersPackage;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eliorcohen.com.sensoranim.ModelsPackage.GameModel;
import eliorcohen.com.sensoranim.R;

public class CustomAdapterFavorites extends RecyclerView.Adapter<ViewHolderFavorites> {

    private final LayoutInflater mInflater;
    private List<GameModel> mGameList;

    public CustomAdapterFavorites(Context context, ArrayList<GameModel> gameModel) {
        mInflater = LayoutInflater.from(context);
        this.mGameList = gameModel;
    }

    @Override
    public ViewHolderFavorites onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item_score, parent, false);
        return new ViewHolderFavorites(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderFavorites holder, final int position) {
        if (mGameList != null) {
            GameModel current = mGameList.get(position);
            try {
                holder.name1.setText(current.getName());
                holder.score1.setText(String.valueOf(current.getScore()));

                if (getItemId(position) == 0) {
                    holder.itemView.setBackgroundColor(Color.parseColor("#000000"));
                    holder.picStat.setImageResource(R.drawable.stpic);
                } else if (getItemId(position) == 1) {
                    holder.itemView.setBackgroundColor(Color.parseColor("#000033"));
                    holder.picStat.setImageResource(R.drawable.ndpic);
                } else if (getItemId(position) == 2) {
                    holder.itemView.setBackgroundColor(Color.parseColor("#1d8fba"));
                    holder.picStat.setImageResource(R.drawable.rdpic);
                }
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
            return Integer.compare(obj2.getScore(), obj1.getScore()); // To compare integer values

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

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1500);
        view.startAnimation(anim);
    }

}
