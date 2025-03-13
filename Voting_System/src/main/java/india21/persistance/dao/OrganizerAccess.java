
package india21.persistence.dao;

import india21.persistence.entities.Organizer;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class OrganizerAccess extends BaseDataAccess<Organizer>{
    
    public OrganizerAccess() {
        super(Organizer.class);
    }
    
    public Optional<Organizer> getByUsername(String username){
        try {
            Organizer result = (Organizer) entityManager.createQuery("SELECT o FROM Organizer o WHERE o.person.userName=:username")
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException exception) {
            Logger.getLogger("OrganizerAccess").log(Level.WARNING, "NoResultException", exception);
            return Optional.empty();
        }
    }
}
