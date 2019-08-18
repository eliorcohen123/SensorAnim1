package eliorcohen.com.sensoranim.AdapterPackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import java.util.List;

import eliorcohen.com.sensoranim.R;
import eliorcohen.com.sensoranim.RoomFavoritesPackage.GameFavorites;

public class GameListAdapterFavorites extends RecyclerView.Adapter<FaceViewHolder> {

    private final LayoutInflater mInflater;
    private List<GameFavorites> mFaceList;

    public GameListAdapterFavorites(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public FaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item_favorites, parent, false);
        return new FaceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FaceViewHolder holder, final int position) {
        if (mFaceList != null) {
            final GameFavorites current = mFaceList.get(position);
            try {
                holder.name1.setText(current.getName());
                holder.score1.setText(current.getScore());
            } catch (Exception e) {

            }
            holder.linearLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            setFadeAnimation(holder.itemView);
        } else {
            // Covers the case of data not being ready yet.
            holder.name1.setText("No FaceSearch");
        }
    }

    public void setGames(List<GameFavorites> faceFavorites) {
        mFaceList = faceFavorites;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mFaceList != null)
            return mFaceList.size();
        else return 0;
    }

    public GameFavorites getGameAtPosition(int position) {
        return mFaceList.get(position);
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1500);
        view.startAnimation(anim);
    }

}
