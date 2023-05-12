package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.User;

import java.util.List;

public interface UserService {
    void addUser(User user) throws UserException;
    List<User> getUsersList(String game) throws UserException;
    User getUser(String name) throws UserException;
    void reset() throws UserException;
}
