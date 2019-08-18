package eliorcohen.com.sensoranim.RoomFavoritesPackage;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class GameRepositoryFavorites {

    private GamesDaoFavorites mGamesDaoFavorites;
    private LiveData<List<GameFavorites>> mAllPlacesFavorites;

    public GameRepositoryFavorites(Application application) {
        GamesRoomDatabaseFavorites db = GamesRoomDatabaseFavorites.getDatabase(application);
        mGamesDaoFavorites = db.placesDao();
        mAllPlacesFavorites = mGamesDaoFavorites.getAllPlaces();
    }

    public LiveData<List<GameFavorites>> getAllPlaces() {
        return mAllPlacesFavorites;
    }

    private static class DeleteLastSearchAsyncTask extends AsyncTask<Void, Void, Void> {

        private GamesDaoFavorites gamesDaoFavorites;

        private DeleteLastSearchAsyncTask(GamesDaoFavorites dao) {
            gamesDaoFavorites = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            gamesDaoFavorites.deleteAll();
            return null;
        }
    }

    void deleteLastSearch() {
        DeleteLastSearchAsyncTask deleteLastSearchAsyncTask = new DeleteLastSearchAsyncTask(mGamesDaoFavorites);
        deleteLastSearchAsyncTask.execute();
    }

    private static class deletePlaceAsyncTask extends AsyncTask<GameFavorites, Void, Void> {

        private GamesDaoFavorites gamesDaoFavorites;

        private deletePlaceAsyncTask(GamesDaoFavorites dao) {
            gamesDaoFavorites = dao;
        }

        @Override
        protected Void doInBackground(final GameFavorites... params) {
            gamesDaoFavorites.deleteNew(params[0]);
            return null;
        }
    }

    void deletePlace(GameFavorites places) {
        new deletePlaceAsyncTask(mGamesDaoFavorites).execute(places);
    }

    private static class insertAsyncTask extends AsyncTask<GameFavorites, Void, Void> {

        private GamesDaoFavorites gamesDaoFavorites;

        private insertAsyncTask(GamesDaoFavorites dao) {
            gamesDaoFavorites = dao;
        }

        @Override
        protected Void doInBackground(final GameFavorites... params) {
            gamesDaoFavorites.insert(params[0]);
            return null;
        }
    }

    public void insertPlace(GameFavorites place_) {
        new insertAsyncTask(mGamesDaoFavorites).execute(place_);
    }

    public void insertPlace(List<GameFavorites> placeList_) {
        for (GameFavorites p : placeList_) {
            insertPlace(p);
        }
    }

}
