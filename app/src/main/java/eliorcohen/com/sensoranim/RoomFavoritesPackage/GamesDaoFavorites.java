package eliorcohen.com.sensoranim.RoomFavoritesPackage;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface GamesDaoFavorites {

    @Insert
    void insert(GameFavorites place_);

    @Delete
    void deleteNew(GameFavorites place_);

    @Query("DELETE FROM game_table_scores")
    void deleteAll();

    @Query("DELETE FROM game_table_scores WHERE name= :name_")
    void deleteByName(String name_);

    @Query("DELETE FROM game_table_scores WHERE ID= :id_")
    void deleteByID(Long id_);

    @Query("SELECT * from game_table_scores ORDER BY name ASC")
    LiveData<List<GameFavorites>> getAllPlaces();
}