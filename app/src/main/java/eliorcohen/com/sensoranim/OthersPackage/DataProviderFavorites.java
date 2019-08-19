package eliorcohen.com.sensoranim.OthersPackage;

import android.os.AsyncTask;

import java.util.ArrayList;

import eliorcohen.com.sensoranim.RoomFavoritesPackage.GameFavorites;
import eliorcohen.com.sensoranim.RoomFavoritesPackage.GameRepositoryFavorites;
import eliorcohen.com.sensoranim.RoomFavoritesPackage.IGameDataReceived;

public class DataProviderFavorites {

    public void getGame(IGameDataReceived resultListener_) {

        //go get data from google API
        // take time....
        //more time...
        //Data received -> resultListener_

        GetGameAsyncTask getGameAsyncTask = new GetGameAsyncTask(resultListener_);
        getGameAsyncTask.execute();
    }

    private class GetGameAsyncTask extends AsyncTask<String, Integer, IGameDataReceived> {

        private ArrayList<GameModel> mGameModels;
        private IGameDataReceived mIGameDataReceived;

        public GetGameAsyncTask(IGameDataReceived iGameDataReceived) {
            mIGameDataReceived = iGameDataReceived;
        }

        @Override
        protected IGameDataReceived doInBackground(String... urls) {
            mGameModels = new ArrayList<GameModel>();
            GameRepositoryFavorites gameRepositoryFavorites = new GameRepositoryFavorites(NearByApplication.getApplication());
            ArrayList<GameFavorites> listGame = new ArrayList<>();
            for (GameModel gameModel : mGameModels) {
                try {
                    GameFavorites game = new GameFavorites(gameModel.getName(), gameModel.getScore());
                    listGame.add(game);
                } catch (Exception e) {

                }
            }
            gameRepositoryFavorites.insertPlace(listGame);
            return mIGameDataReceived;
        }

        @Override
        protected void onPostExecute(IGameDataReceived iGameDataReceived) {
            try {
                iGameDataReceived.onGameDataReceived();
            } catch (Exception e) {
                iGameDataReceived.onGameDataReceived();
            }
        }
    }

}
