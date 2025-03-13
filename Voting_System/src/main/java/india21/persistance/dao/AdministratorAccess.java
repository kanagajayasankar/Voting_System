
package india21.persistence.dao;

import india21.persistence.entities.Administrator;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class AdministratorAccess extends BaseDataAccess<Administrator>{

    public AdministratorAccess() {
        super(Administrator.class);
    }
    
    public Optional<Administrator> getByUsername(String username){
        try {
            Administrator result = (Administrator) entityManager.createQuery("SELECT o FROM Administrator o WHERE o.person.userName=:username")
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException exception) {
            Logger.getLogger("OrganizerAccess").log(Level.WARNING, "NoResultException", exception);
            return Optional.empty();
        }
    }
}
