package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.service.UserService;


@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {
    private User loggedUser;

    @Autowired
    private UserService userService;


    @RequestMapping("/")
    public String index(){
        return "index";
    }


    @RequestMapping("/login")
    public String login(String login, String password) {
        if (login.length() == 0 ||
                password.length() == 0 ||
                userService.getUser(login) == null) {
            return "index";
        }

        if (userService.getUser(login).getUserName().equals(login) &&
                userService.getUser(login).getPassword().equals(password)){

            loggedUser = userService.getUser(login);
            return "redirect:/menu";
        }

        return "index";
    }


    @RequestMapping("/register")
    public String register(String login, String password, String passwordVerify){
        if (login.length() == 0 || password.length() == 0 || passwordVerify.length() == 0 ||
                !password.equals(passwordVerify) ||
                loggedUser != null ||
                userService.getUser(login) != null
        ){
            return "index";
        }


        loggedUser = new User(login, password, "Numberlink");
        userService.addUser(loggedUser);

        return "redirect:/menu";
    }


    @RequestMapping("/loginAsGuest")
    public String loginAsGuest() {
        loggedUser = null;
        return "redirect:/menu";
    }


    @RequestMapping("/logout")
    public String logout() {
        loggedUser = null;
        return "redirect:/";
    }

    @RequestMapping("/menu-redirect")
    public String ratingSection(){
        return "redirect:/menu";
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public String getLoggedUserName() { return loggedUser.getUserName();}

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public boolean isLoggedUser(){
        return loggedUser != null;
    }

    public boolean isLogged() {
        return isLoggedUser();
    }
}
