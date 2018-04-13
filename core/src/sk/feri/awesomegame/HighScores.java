package sk.feri.awesomegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;


public class HighScores {
    public static final String SERVER_URL = "http://awesomegame.tomart.sk/";
    public static final String SECRET_KEY = "totojenajtajnejsikluc";
    public static ArrayList<Score> scoresList = new ArrayList<Score>();

    public static void loadHighScores(String orger_by, int limit, int difficulty) {
        HttpRequestBuilder builder = new HttpRequestBuilder();
        final String url = SERVER_URL + "getscore.php?order_by=" + orger_by + "&limit=" +limit + "&difficulty=" + difficulty;
        Net.HttpRequest request = builder.newRequest().method(Net.HttpMethods.GET).url(url).build();
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");

        final long start = System.nanoTime(); //for checking the time until response
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                Gdx.app.log("WebRequest", "HTTP url: " + url);
                Gdx.app.log("WebRequest", "HTTP Response code: " + httpResponse.getStatus().getStatusCode());
                Gdx.app.log("WebRequest", "Response time: " + ((System.nanoTime() - start) / 1000000) + "ms");

                JsonReader json = new JsonReader();
                JsonValue base = json.parse(httpResponse.getResultAsString());
                Gdx.app.log("WebRequest", "HTTP Response json: " + base);

                try {
                    if (base.getBoolean("success")){
                        scoresList.clear();
                        for (JsonValue componentData : base.get("data"))
                        {
                            HighScores.scoresList.add(new Score(
                                    componentData.getString("username"),
                                    componentData.getInt("score"),
                                    componentData.getInt("distance"),
                                    componentData.getInt("max_distance"),
                                    componentData.getInt("games_played"),
                                    componentData.getString("last_update")
                            ));
                        }
                    }
                } catch(Exception exception) {
                    Gdx.app.log("WebRequest 123A", "Exception" + exception);
                }

            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.log("WebRequest", "HTTP request failed");
            }

            @Override
            public void cancelled() {
                Gdx.app.log("WebRequest", "HTTP request cancelled");
            }
        });
    }

    public static void updateScore() {
        addScore(1);
        addScore(2);
        addScore(3);
    }

    private static void addScore(int difficulty){
        HttpRequestBuilder builder = new HttpRequestBuilder();
        final String url = SERVER_URL + "addscore.php?username=" + Settings.username +
                "&score=" + Settings.highscores[difficulty - 1][0] +
                "&max_distance=" + Settings.getMaxDistance() +
                "&distance=" + Settings.distance +
                "&difficulty=" + difficulty +
                "&games_played=" + Settings.attempts +
                "&hash=" + MD5(SECRET_KEY + Settings.username);

        Net.HttpRequest request = builder.newRequest().method(Net.HttpMethods.GET).url(url).build();
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");

        final long start = System.nanoTime(); //for checking the time until response
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                Gdx.app.log("WebRequest", "HTTP url: " + url);
                Gdx.app.log("WebRequest", "HTTP Response code: " + httpResponse.getStatus().getStatusCode());
                Gdx.app.log("WebRequest", "HTTP Response data: " + httpResponse.getResultAsString());
                Gdx.app.log("WebRequest", "Response time: " + ((System.nanoTime() - start) / 1000000) + "ms");
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.log("WebRequest", "HTTP request failed");
            }

            @Override
            public void cancelled() {
                Gdx.app.log("WebRequest", "HTTP request cancelled");
            }
        });
    }

    private static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static void addToScoresList(Score score){
        scoresList.add(score);
    }
}
