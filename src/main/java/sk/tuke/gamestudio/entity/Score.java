package sk.tuke.gamestudio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import java.util.Date;

@Entity
//@NamedQuery( name = "Score.getTopScores",
//        query = "SELECT s FROM Score s WHERE s.game=:game ORDER BY s.points DESC")
//@NamedQuery( name = "Score.resetScores",
//        query = "DELETE FROM Score")

public class Score {
    @Id
    @GeneratedValue
    private int id;
    private String game;
    private String player;
    private int points;
    private Date playedOn;

    public Score() {
    }

    public Score(String game, String player, int points, Date playedOn) {
        this.game = game;
        this.player = player;
        this.points = points;
        this.playedOn = playedOn;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getPlayedOn() {
        return playedOn;
    }

    public void setPlayedOn(Date playedOn) {
        this.playedOn = playedOn;
    }

    public int getId() {
        return id;
    }

    public void setId(int ident) {
        this.id = ident;
    }

    @Override
    public String toString() {
        return "Score{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", points=" + points +
                ", playedOn=" + playedOn +
                '}';
    }
}
