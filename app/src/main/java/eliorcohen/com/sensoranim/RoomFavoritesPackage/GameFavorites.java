package eliorcohen.com.sensoranim.RoomFavoritesPackage;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "game_table_scores")
public class GameFavorites implements Serializable {

    public GameFavorites(@NonNull String name1, @NonNull String score1) {
        this.mName = name1;
        this.mScore = score1;
    }

    public GameFavorites() {

    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private long ID;

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "score")
    private String mScore;


    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public void setName(@NonNull String mName) {
        this.mName = mName;
    }

    @NonNull
    public String getScore() {
        return mScore;
    }

    public void setScore(@NonNull String mScore) {
        this.mScore = mScore;
    }

}
