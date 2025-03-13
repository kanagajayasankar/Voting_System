package india21.persistence.dao;

import india21.persistence.entities.Item;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class ItemAccess extends BaseDataAccess<Item> {

    public ItemAccess() {
        super(Item.class);
    }

    public Optional<Item> getByNameAndQuestion(Long questionId, String name) {
        try {
            Item item = (Item) entityManager.createQuery("SELECT i FROM Item i WHERE i.name=:name AND i.question.id=:questionId")
                    .setParameter("name", name)
                    .setParameter("questionId", questionId)
                    .getSingleResult();
            return Optional.ofNullable(item);
        } catch (NoResultException exception) {
            Logger.getLogger("ItemAccess").log(Level.WARNING, "NoResultException", exception);
            return Optional.empty();
        }
    }
}
