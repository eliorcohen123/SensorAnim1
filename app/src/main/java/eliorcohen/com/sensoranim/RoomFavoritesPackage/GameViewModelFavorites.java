package eliorcohen.com.sensoranim.RoomFavoritesPackage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class GameViewModelFavorites extends AndroidViewModel {

    private GameRepositoryFavorites gameRepositoryFavorites;
    private LiveData<List<GameFavorites>> mAllPlacesFavorites;

    public GameViewModelFavorites(Application application) {
        super(application);
        gameRepositoryFavorites = new GameRepositoryFavorites(application);
        mAllPlacesFavorites = gameRepositoryFavorites.getAllPlaces();
    }

    public LiveData<List<GameFavorites>> getAllPlaces() {
        return mAllPlacesFavorites;
    }

    public void insertPlace(GameFavorites gameFavorites) {
        gameRepositoryFavorites.insertPlace(gameFavorites);
    }

    public void deleteAll() {
        gameRepositoryFavorites.deleteLastSearch();
    }

    public void deletePlace(GameFavorites places) {
        gameRepositoryFavorites.deletePlace(places);
    }

}
