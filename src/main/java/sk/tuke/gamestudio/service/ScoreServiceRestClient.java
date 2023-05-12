package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Score;

import java.util.Arrays;
import java.util.List;

public class ScoreServiceRestClient implements ScoreService{
    private final String url = "http://localhost:8080/api/score";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addScore(Score score){
        if (score == null || score.getPlayer() == null || score.getGame() == null) {
            throw new ScoreException("Score data is missing or invalid");
        }
        restTemplate.postForEntity(url, score, Score.class);
    }

    @Override
    public List<Score> getTopScores(String game) {
        if (game == null) {
            throw new ScoreException("Score data is missing or invalid");
        }
        return Arrays.asList(restTemplate.getForEntity(url + "/" + game, Score[].class).getBody());
    }

    @Override
    public void reset(){
        throw new UnsupportedOperationException("Reset is not supported on web interface");
    }
}
