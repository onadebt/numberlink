package sk.tuke.gamestudio.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.gamestudio.entity.Comment;

import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(String game) {
//        return entityManager.createNamedQuery("Comment.getComments")
//                .setParameter("game", game).setMaxResults(10).getResultList();
        return entityManager.createQuery("SELECT c FROM Comment c WHERE c.game=:game ORDER BY c.commentedOn DESC")
                .setParameter("game", game)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public void reset() {
//        entityManager.createNamedQuery("Comment.resetComments").executeUpdate();
        entityManager.createQuery("DELETE FROM Comment").executeUpdate();
    }
}
