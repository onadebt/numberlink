package sk.tuke.gamestudio.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.numberlink.core.Game;
import sk.tuke.gamestudio.game.numberlink.field.Tile;
import sk.tuke.gamestudio.game.numberlink.levels.*;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;

@Controller
@RequestMapping("/numberlink")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class NumberlinkController {

    @Autowired
    private UserController userController;
//    private final String gameName = "numberlink";

    @Autowired
    private ScoreService scoreService;

    private boolean eraseMode = false;
    private int levelNumber = 1;
    private final int maxLevel = 4;
    private Level level = new Level1();
    private Game game = new Game(level);
    private Tile[][] tiles = game.getLevel().getTiles();


    @RequestMapping
    public String numberlink(@RequestParam(required = false) String row, @RequestParam(required = false) String column, Model model){

        processCommand(row, column);
        showScores(model);
        return "numberlink";
    }

    public void processCommand(String row, String column){
        try {
            if (!game.isWon()) {
                if (eraseMode) {
                    game.nextMove("erase");
                    eraseMode = false;
                } else {
                    game.nextMove(Integer.parseInt(column), Integer.parseInt(row));
                    if (game.isWon() && userController.isLoggedUser()) {
                        scoreService.addScore(new Score("Numberlink", userController.getLoggedUser().getUserName(), game.getLevel().getScore(), new Date()));
//                        System.out.println(userController.getLoggedUser().getUserName() + "  " + game.getLevel().getScore());
                        nextLevel(++levelNumber);
                    }

                }
            } else if (game.isWon()){
                nextLevel(++levelNumber);
            }
        } catch(Exception e){
            //Incorrect parameters
        }

    }

    @RequestMapping("/new")
    public String newGame(Model model){
        this.levelNumber = 1;
        nextLevel(levelNumber);
        showScores(model);
        return "numberlink";
    }

    @RequestMapping("/erase")
    public String eraseModeSwitch(Model model){
        this.eraseMode = !this.eraseMode;
        showScores(model);
        return "numberlink";
    }

    @RequestMapping(value = "/mode", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getEraseModeV2(){
        return this.eraseMode ? "Normal" : "Erase";
    }


    public String getLevel(){
        StringBuilder sb = new StringBuilder();
        sb.append("<table class=\"gameField\">\n");

        for (int row = 0; row < level.getNumRows(); row++){
            sb.append("<tr>\n");
            for (int column = 0; column < level.getNumCols(); column++){
                sb.append("<td>\n");
                sb.append("<a href='/numberlink?row=" + row + "&column=" + column + "'>\n");
                sb.append("<img class=\"img\" src='../images/numbers/" + getTileName(tiles[row][column]) + ".png'>");
                sb.append("</a>");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }

        sb.append("</table>\n");
        return sb.toString();
    }

    public String getLevelV2(){
        StringBuilder sb = new StringBuilder();
        sb.append("<table id = \"gfield\" class=\"gameField\">\n");

        for (int row = 0; row < level.getNumRows(); row++){
            sb.append("<tr>\n");
            for (int column = 0; column < level.getNumCols(); column++){
                sb.append("<td>\n");
                sb.append("<a href='#r" + row + "c" + column + "'>\n");
                sb.append("<img class=\"img\" src='../images/numbers/" + getTileName(tiles[row][column]) + ".png'>");
                sb.append("</a>");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }

        sb.append("</table>\n");
        return sb.toString();
    }


    private String getTileName(Tile tile){
        return switch (tile.getNumber()) {
            case 1 -> "pg1";
            case 2 -> "pg2";
            case 3 -> "pg3";
            case 4 -> "pg4";
            case 5 -> "pg5";
            case 6 -> "pg6";
            case 7 -> "pg7";
            default -> "pg0";
        };
    }

    public void nextLevel(int levelNumber){
        if (levelNumber > this.maxLevel) return;

        this.level = returnLevel(levelNumber);
        this.game = new Game(this.level);
        this.tiles = game.getLevel().getTiles();
    }


    public Level returnLevel(int levelNumber){
        return switch (levelNumber) {
            case 2 -> new Level2();
            case 3 -> new Level3();
            case 4 -> new Level4();
            default -> new Level1();
        };
    }



    @RequestMapping(value = "/field", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String numberlink(@RequestParam(required = false) String row, @RequestParam(required = false) String column){
        processCommand(row, column);
        return getLevelV2(); //IMPORTANT
    }

    @RequestMapping(value = "/api", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String numberlinkApi(@RequestParam(required = false) String row, @RequestParam(required = false) String column){
        processCommand(row, column);
        return getLevelV2();
    }

    public boolean eraseMode() {
        return eraseMode;
    }

    public String getDate(){
        return new Date().toString();
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void showScores(Model model){
        model.addAttribute("scores", scoreService.getTopScores("Numberlink"));
    }

}
