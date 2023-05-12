package sk.tuke.gamestudio.service;

import jakarta.persistence.NoResultException;
import sk.tuke.gamestudio.entity.Rating;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Transactional
public class RatingServiceJPA implements RatingService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
        try{
            Rating isCorrect =  entityManager.createQuery("SELECT r FROM Rating r WHERE r.game=:game AND r.player=:player", Rating.class)
                    .setParameter("game", rating.getGame())
                    .setParameter("player", rating.getPlayer())
                    .getSingleResult();

            isCorrect.setRating(rating.getRating());
        }
        catch (Exception e) {
            entityManager.persist(rating);
        };
    }

    @Override
    public Double getAverageRating(String game) {
//        return (Double) entityManager.createNamedQuery("Rating.getAvgRating").getSingleResult();
        return  (Double) entityManager
                .createQuery("SELECT AVG(r.rating) from Rating r WHERE r.game = :game")
                .setParameter("game", game)
                .getSingleResult();
    }

    @Override
    public int getRating(String game, String player) {
        try {
            return (int) entityManager
                    .createQuery("Select r.rating from Rating r WHERE r.game = :game AND r.player = :player")
                    .setParameter("game", game)
                    .setParameter("player", player)
                    .getSingleResult();
        } catch (NoResultException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void reset() {
//        entityManager.createNamedQuery("Rating.resetRatings").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM Rating").executeUpdate();
    }
}
