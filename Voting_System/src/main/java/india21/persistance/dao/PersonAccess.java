package india21.persistence.dao;

import india21.persistence.entities.ParticipantList;
import india21.persistence.entities.Person;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class PersonAccess extends BaseDataAccess<Person> {

    public PersonAccess() {
        super(Person.class);
    }

    public Optional<Person> getByUsername(String username) {
        try {
            Person person = (Person) entityManager.createQuery("SELECT p FROM Person p WHERE p.userName=:username")
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.ofNullable(person);
        } catch (NoResultException exception) {
            Logger.getLogger("PersonAccess").log(Level.WARNING, "NoResultException", exception);
            return Optional.empty();
        }
    }
}
