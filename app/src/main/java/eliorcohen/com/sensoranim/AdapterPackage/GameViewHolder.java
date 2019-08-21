package eliorcohen.com.sensoranim.AdapterPackage;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import eliorcohen.com.sensoranim.R;

class GameViewHolder extends RecyclerView.ViewHolder {

    TextView name1;
    TextView score1;
    LinearLayout linearLayout1;

    GameViewHolder(View itemView) {
        super(itemView);
        name1 = itemView.findViewById(R.id.myName1);
        score1 = itemView.findViewById(R.id.myScore1);
        linearLayout1 = itemView.findViewById(R.id.linearLayout1);
    }

}
