package eliorcohen.com.sensoranim.CustomAdaptersPackage;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import eliorcohen.com.sensoranim.R;

class ViewHolderFavorites extends RecyclerView.ViewHolder {

    TextView name1, score1;
    ImageView picStat;

    ViewHolderFavorites(View itemView) {
        super(itemView);

        name1 = itemView.findViewById(R.id.myName1);
        score1 = itemView.findViewById(R.id.myScore1);
        picStat = itemView.findViewById(R.id.picStat);
    }

}
