package sk.feri.awesomegame;

public class Score {
    public int id;
    public String username;
    public int score;
    public int distance;
    public int maxDistance;
    public int gamesPlayed;
    public String lastUpdate;

    public Score(String username, int score, int distance, int maxDistance, int gamesPlayed, String lastUpdate) {
        this.username = username;
        this.score = score;
        this.distance = distance;
        this.maxDistance = maxDistance;
        this.gamesPlayed = gamesPlayed;
        this.lastUpdate = lastUpdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
