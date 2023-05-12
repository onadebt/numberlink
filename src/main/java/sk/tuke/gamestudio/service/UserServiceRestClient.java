package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.User;

import java.util.Arrays;
import java.util.List;

public class UserServiceRestClient implements UserService{

    private final String url = "http://localhost:8080/api/users";

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public void addUser(User user) {
        if (user == null) {
            throw new ScoreException("User data is missing or invalid");
        }
        restTemplate.postForEntity(url, user, User.class);
    }

    @Override
    public List<User> getUsersList(String game) {
        if (game == null) {
            throw new ScoreException("User data is missing or invalid");
        }
        return Arrays.asList(restTemplate.getForEntity(url + "/" + game, User[].class).getBody());
    }

    @Override
    public User getUser(String name) {
        return restTemplate.getForObject(url + "/" + name , User.class);

    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Reset is not supported on web interface");
    }
}
