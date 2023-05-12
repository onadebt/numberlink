package sk.tuke.gamestudio.server.webservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserServiceRest {
    @Autowired
    private UserService userService;


    //GET -> http://localhost:8080/api/users/Numberlink
    @GetMapping("/{game}")
    public List<User> getUsersList(@PathVariable String game) {
        return userService.getUsersList(game);
    }

    //POST -> http://localhost:8080/api/users
    @PostMapping
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    //GET -> http://localhost:8080/api/score/Numberlink/{parameter: name}
    @GetMapping("/{game}/{name}")
    public User getUser(@PathVariable String game, @PathVariable String name) {
        return userService.getUser(name);
    }
}
