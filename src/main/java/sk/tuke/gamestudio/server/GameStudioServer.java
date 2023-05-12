package sk.tuke.gamestudio.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import sk.tuke.gamestudio.service.*;

@SpringBootApplication
@EntityScan(basePackages = "sk.tuke.gamestudio.entity")
public class GameStudioServer {

    public static void main(String[] args) {
        System.out.println("s");
        SpringApplication.run(GameStudioServer.class, args);
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceJPA();
    }

    @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();
    }

    @Bean
    public UserService userService(){return new UserServiceJPA();}
}
