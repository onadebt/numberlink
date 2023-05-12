package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {
    @Autowired
    private RatingService ratingService;

    //GET -> http://localhost:8080/api/rating/Numberlink
    @GetMapping("/{game}")
    public Double getAverageRating(@PathVariable String game){
        return ratingService.getAverageRating(game);
    }

    //GET -> http://localhost:8080/api/rating/Numberlink/me
    @GetMapping("/{game}/{player}")
    public int getUserRating(@PathVariable("game") String game, @PathVariable("player") String player){
        return ratingService.getRating(game, player);
    }

    //POST -> http://localhost:8080/api/rating
    @PostMapping
    public void setRating(@RequestBody Rating rating){
        ratingService.setRating(rating);
    }
}
