package sk.tuke.gamestudio.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.gamestudio.entity.User;

import java.util.List;

@Transactional
public class UserServiceJPA implements UserService{

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> getUsersList(String game) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.game=:game")
                .setParameter("game", game)
                .getResultList();
    }

    @Override
    public User getUser(String name) {

        try {
            return  (User) entityManager
                    .createQuery("SELECT u FROM User u WHERE u.userName=:name")
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            // If no record is found, set user to null
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public void reset() {
        entityManager.createQuery("DELETE FROM User").executeUpdate();
    }
}
