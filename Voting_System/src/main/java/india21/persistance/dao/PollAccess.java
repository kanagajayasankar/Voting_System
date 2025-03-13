package india21.persistence.dao;

import india21.persistence.entities.Poll;
import india21.persistence.entities.enums.PollStates;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class PollAccess extends BaseDataAccess<Poll> {

    public PollAccess() {
        super(Poll.class);
    }

    public Optional<Poll> getByTitle(String title) {
        try {
            Poll poll = (Poll) entityManager.createQuery("SELECT p FROM Poll p WHERE p.title=:title")
                    .setParameter("title", title)
                    .getSingleResult();
            return Optional.ofNullable(poll);
        } catch (NoResultException exception) {
            Logger.getLogger("PollAccess").log(Level.WARNING, "NoResultException", exception);
            return Optional.empty();
        }
    }

    public List<Poll> getAllByOrganizer(Long organizerId) {
        return (List<Poll>) entityManager.createQuery("SELECT p FROM Poll p WHERE p.organizer.id=:organizerId")
                .setParameter("organizerId", organizerId)
                .getResultList();
    }

    public List<Poll> getAll() {
        return (List<Poll>) entityManager.createQuery("SELECT p FROM Poll p")
                .getResultList();
    }

    public List<Poll> getAllByState(PollStates state) {
        return (List<Poll>) entityManager.createQuery("SELECT p FROM Poll p WHERE p.state=:state")
                .setParameter("state", state)
                .getResultList();
    }

    public Optional<Poll> getByOrganizerAndId(Long organizerId, Long pollId) {
        try {
            Poll poll = (Poll) entityManager.createQuery("SELECT p FROM Poll p WHERE p.organizer.id=:organizerId AND p.id=:pollId")
                    .setParameter("organizerId", organizerId)
                    .setParameter("pollId", pollId)
                    .getSingleResult();
            return Optional.ofNullable(poll);
        } catch (NoResultException exception) {
            Logger.getLogger("PollAccess").log(Level.WARNING, "NoResultException", exception);
            return Optional.empty();
        }
    }
    
    public Optional<Poll> getByPublishKey(String publishKey){
        try {
            Poll poll = (Poll) entityManager.createQuery("SELECT p FROM Poll p WHERE p.publicResultKey=:publishKey")
                    .setParameter("publishKey", publishKey)
                    .getSingleResult();
            return Optional.ofNullable(poll);
        } catch (NoResultException exception) {
            Logger.getLogger("PollAccess").log(Level.WARNING, "NoResultException", exception);
            return Optional.empty();
        }
    }
}
