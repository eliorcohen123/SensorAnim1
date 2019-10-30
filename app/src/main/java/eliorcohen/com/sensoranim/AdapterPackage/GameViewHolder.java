package eliorcohen.com.sensoranim.AdapterPackage;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import eliorcohen.com.sensoranim.R;

class GameViewHolder extends RecyclerView.ViewHolder {

    TextView name1, score1;
    ImageView picStat;

    GameViewHolder(View itemView) {
        super(itemView);

        name1 = itemView.findViewById(R.id.myName1);
        score1 = itemView.findViewById(R.id.myScore1);
        picStat = itemView.findViewById(R.id.picStat);
    }

}
