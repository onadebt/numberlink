package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;

public class RatingServiceRestClient implements RatingService{
    private final String url = "http://localhost:8080/api/rating";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating){
        if (rating == null) {
            throw new RatingException("Rating data is missing or invalid");
        }
        restTemplate.postForEntity(url, rating, Rating.class);
    }

    @Override
    public Double getAverageRating(String game){
        Double rating = restTemplate.getForEntity(url + "/" + game, Double.class).getBody();
        if (rating == null) {
            throw new RatingException("Rating not found for game: " + game);
        }
        return Math.round(rating * 10.0) / 10.0;
    }

    @Override
    public int getRating(String game, String player){
        if (game == null || player == null) {
            throw new RatingException("Rating not found for game: " + game);
        }
        return restTemplate.getForEntity(url + "/" + game, Integer.class).getBody();
    }

    @Override
    public void reset(){
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
