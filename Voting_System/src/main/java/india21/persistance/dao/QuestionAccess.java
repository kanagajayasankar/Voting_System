package india21.persistence.dao;

import india21.persistence.entities.Question;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class QuestionAccess extends BaseDataAccess<Question> {

    public QuestionAccess() {
        super(Question.class);
    }

    public Optional<Question> getByTitleAndPoll(Long pollId, String title) {
        try {
            Question question = (Question) entityManager.createQuery("SELECT q FROM Question q WHERE q.title=:title AND q.poll.id=:pollId")
                    .setParameter("title", title)
                    .setParameter("pollId", pollId)
                    .getSingleResult();
            return Optional.ofNullable(question);
        } catch (NoResultException exception) {
            Logger.getLogger("QuestionAccess").log(Level.WARNING, "NoResultException", exception);
            return Optional.empty();
        }
    }
}
