package eliorcohen.com.sensoranim;

import android.os.AsyncTask;

import java.util.ArrayList;

import eliorcohen.com.sensoranim.RoomFavoritesPackage.GameFavorites;
import eliorcohen.com.sensoranim.RoomFavoritesPackage.GameRepositoryFavorites;
import eliorcohen.com.sensoranim.RoomFavoritesPackage.IGameDataReceived;

public class NetWorkDataProviderFavorites {

    public void getGameByLocation(IGameDataReceived resultListener_) {

        //go get data from google API
        // take time....
        //more time...
        //Data received -> resultListener_

        GetGameAsyncTask getGameAsyncTask = new GetGameAsyncTask(resultListener_);
        getGameAsyncTask.execute();
    }

    private class GetGameAsyncTask extends AsyncTask<String, Integer, IGameDataReceived> {

        private ArrayList<GameModel> mGameModels;
        private IGameDataReceived mIFaceDataReceived;

        public GetGameAsyncTask(IGameDataReceived iFaceDataReceived) {
            mIFaceDataReceived = iFaceDataReceived;
        }

        @Override
        protected IGameDataReceived doInBackground(String... urls) {
            mGameModels = new ArrayList<GameModel>();
            GameRepositoryFavorites faceRepository = new GameRepositoryFavorites(NearByApplication.getApplication());
            ArrayList<GameFavorites> listGame = new ArrayList<>();
            for (GameModel gameModel : mGameModels) {
                try {
                    GameFavorites game = new GameFavorites(gameModel.getName(), gameModel.getScore());
                    listGame.add(game);
                } catch (Exception e) {

                }
            }
            faceRepository.insertPlace(listGame);
            return mIFaceDataReceived;
        }

        @Override
        protected void onPostExecute(IGameDataReceived iFaceDataReceived_) {
            try {
                iFaceDataReceived_.onGameDataReceived();
            } catch (Exception e) {
                iFaceDataReceived_.onGameDataReceived();
            }
        }
    }

}
