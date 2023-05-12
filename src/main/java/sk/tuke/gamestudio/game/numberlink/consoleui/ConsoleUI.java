package sk.tuke.gamestudio.game.numberlink.consoleui;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.numberlink.core.Game;
import sk.tuke.gamestudio.game.numberlink.levels.*;
import sk.tuke.gamestudio.service.*;
import java.util.Date;
import java.util.Scanner;

public class ConsoleUI {
    private String input;
    private String playerName;
    private Level level;

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private CommentService commentService;

    public ConsoleUI() {
        this.scoreService = new ScoreServiceJPA();
        this.ratingService = new RatingServiceJPA();
        this.commentService = new CommentServiceJPA();
    }

    public void start() {
        System.out.print("Please, enter your name: ");
        Scanner scanner = new Scanner(System.in);
        this.input = scanner.nextLine();
        this.playerName = input;

        do {
            System.out.println();
            System.out.println("MAIN MENU");
            System.out.println("[g] game");
            System.out.println("[s] show scores");
            System.out.println("[c] comments");
            System.out.println("[ac] add comment");
            System.out.println("[r] ratings");
            System.out.println("[ar] add rating");
            System.out.println("[e] exit");

            this.input = scanner.nextLine();
            mainMenu(this.input);

        } while (!input.equalsIgnoreCase("e"));
    }

    private void mainMenu(String input) {
        switch (input) {
            case "g" -> play();
            case "s" -> printScore();
            case "c" -> printComments();
            case "ac" -> writeComment();
            case "r" -> printRatings();
            case "ar" -> writeRating();
        }
    }

    private void writeRating() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your rating (from 1 to 5): ");
        int points = scanner.nextInt();

        while (points < 1 || points > 5) {
            System.out.println("Enter your rating (from 1 to 5): ");
            points = scanner.nextInt();
        }

        this.ratingService.setRating(
                new Rating(playerName, "Numberlink", points, new Date())
        );
    }

    private void writeComment() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your comment: ");
        this.input = scanner.nextLine();
        this.commentService.addComment(
                new Comment(playerName, "Numberlink", this.input, new Date())
        );
    }

    private void printScore() {
        System.out.println();

        for (Score score : this.scoreService.getTopScores("Numberlink")) {
            System.out.println(score.getGame());
            System.out.println(score.getPlayer());
            System.out.println(score.getPoints());
            System.out.println(score.getPlayedOn());
            System.out.println();
        }
    }

    private void printComments() {
        System.out.println();

        for (Comment comment : this.commentService.getComments("Numberlink")) {
            System.out.println(comment.getGame());
            System.out.println(comment.getPlayer());
            System.out.println(comment.getComment());
            System.out.println(comment.getCommentedOn());
            System.out.println();
        }
    }

    private void printRatings() {
        System.out.println();
        System.out.println(ratingService.getAverageRating("Numberlink"));
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        int score = 0;

        do {
            System.out.println(Frame.GREEN + "\n\nWelcome to NUMBERLINK!");
            System.out.println("Correct input format is \"X Y\" where X and Y are coordinates of game field");
            System.out.println("Please, start" + Frame.ANSI_RESET);
            System.out.println("\n");

            int levelInt;
            do {
                System.out.println("Choose level: 1, 2, 3 or 4");
                levelInt = scanner.nextLine().charAt(0) - 48;
            } while (levelInt < 1 || levelInt > 4);

            switch (levelInt) {
//            case 1 -> level = new Level1();
                case 2 -> level = new Level2();
                case 3 -> level = new Level3();
                case 4 -> level = new Level4();
                default -> level = new Level1();
            }

            Game game = new Game(level);
            Frame.drawFrame(game.getLevel().getNumCols(), game.getLevel().getNumRows(), game.getLevel().getTiles());

            while (!game.isWon()) {
                System.out.println("Enter your next move: ");

                String input = scanner.nextLine();
                int x, y;
                if (input.length() == 3) {
                    if (input.charAt(1) == ' ' && (input.charAt(0) - 48 >= 0 || input.charAt(0) - 48 < game.getLevel().getNumCols()) &&
                            input.charAt(2) - 48 >= 0 || input.charAt(0) - 48 < game.getLevel().getNumRows()) {

                        x = input.charAt(0) - 48;
                        y = input.charAt(2) - 48;

                        game.nextMove(x, y);
                    } else {
                        System.out.println("Please, enter valid coordinates");
                        continue;
                    }
                } else {
                    game.nextMove(input);
                }

                Frame.drawFrame(game.getLevel().getNumCols(), game.getLevel().getNumRows(), game.getLevel().getTiles());

            }

            score += level.getScore();
            System.out.println("Congratulations! You won! You got " + level.getScore() + " points");
            System.out.println("Do you want to try again? yes/no ");
        } while (scanner.nextLine().equalsIgnoreCase("yes"));

        this.scoreService.addScore(new Score("Numberlink", playerName, score, new Date()));
    }
}
