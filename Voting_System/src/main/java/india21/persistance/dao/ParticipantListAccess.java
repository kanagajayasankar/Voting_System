package india21.persistence.dao;

import india21.persistence.entities.ParticipantList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class ParticipantListAccess extends BaseDataAccess<ParticipantList> {

    public ParticipantListAccess() {
        super(ParticipantList.class);
    }

    public Optional<ParticipantList> getByNameAndPerson(String name, Long ownerId, String memberEmail) {
        try {
            ParticipantList list = (ParticipantList) entityManager.createQuery("SELECT pl FROM ParticipantList pl WHERE pl.listName=:name AND pl.owner.id=:ownerId AND pl.member.userName=:memberEmail")
                    .setParameter("name", name)
                    .setParameter("ownerId", ownerId)
                    .setParameter("memberEmail", memberEmail)
                    .getSingleResult();
            return Optional.ofNullable(list);
        } catch (NoResultException exception) {
            Logger.getLogger("ParticipantListAccess").log(Level.WARNING, "NoResultException", exception);
            return Optional.empty();
        }
    }
    
    public List<ParticipantList> getAllByName(String name, Long ownerId) {
        return (List<ParticipantList>) entityManager.createQuery("SELECT pl FROM ParticipantList pl WHERE pl.listName=:name AND pl.owner.id=:ownerId")
                    .setParameter("name", name)
                    .setParameter("ownerId", ownerId)
                    .getResultList();
    }
    
    public List<ParticipantList> getAllByOwner(Long ownerId) {
        return (List<ParticipantList>) entityManager.createQuery("SELECT pl FROM ParticipantList pl WHERE pl.owner.id=:ownerId")
                    .setParameter("ownerId", ownerId)
                    .getResultList();
    }
}
