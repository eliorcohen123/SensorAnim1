package eliorcohen.com.sensoranim.RoomFavoritesPackage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {GameFavorites.class}, version = 1, exportSchema = false)
public abstract class GamesRoomDatabaseFavorites extends RoomDatabase {

    public abstract GamesDaoFavorites placesDao();
    private static volatile GamesRoomDatabaseFavorites INSTANCE;

    public static GamesRoomDatabaseFavorites getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GamesRoomDatabaseFavorites.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GamesRoomDatabaseFavorites.class, "game_table_scores")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
