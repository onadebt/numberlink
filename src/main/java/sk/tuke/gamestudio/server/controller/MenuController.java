package sk.tuke.gamestudio.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.UserService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Controller
@RequestMapping("/menu")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MenuController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserController userController;

    @RequestMapping
    public String menu(Model model){
        model.addAttribute("comments", commentService.getComments("Numberlink"));

        return "menu";
    }


    @RequestMapping("/comment")
    public String comment(@RequestParam String comment){
        if (comment.length() == 0){
            return "redirect:/menu";
        }

        System.out.println(userController.toString());
        commentService.addComment(
                new Comment(
                        userController.getLoggedUser().getUserName(),
                        "Numberlink",
                        comment,
                        new Date()
                )
        );

        return "redirect:/menu";
    }


    @RequestMapping("/rating")
    public String rating(@RequestParam Integer rating){
        if(rating < 1 || rating > 5){
            return "redirect:/menu";
        }

        ratingService.setRating(
                new Rating(
                        userController.getLoggedUser().getUserName(),
                        "Numberlink",
                        rating,
                        new Date()
                )
        );

        return "redirect:/menu";
    }


    @RequestMapping("/game")
    public String gameSection(){
        return "redirect:/numberlink";
    }


    @RequestMapping("/get-comments")
    public String getComments(){
        return "redirect:/menu";
    }


    @RequestMapping("/get-avg-rating")
    public String getAvgRating(){
        return Double.toString(round(ratingService.getAverageRating("Numberlink"), 1));
    }


    @RequestMapping("/get-user-rating")
    public String getUserRating(){
        return ((Integer) ratingService.getRating("Numberlink", userController.getLoggedUser().getUserName())).toString();
    }


    private double round(double value, int places) { // rounds according to number of "places"
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
