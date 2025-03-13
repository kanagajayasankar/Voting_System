package india21.persistence.dao;

import india21.persistence.entities.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

/**
 * @param <T> generic parameter
 */
public abstract class BaseDataAccess<T> {

    @PersistenceContext(unitName = "IndiaEvs2021")
    protected EntityManager entityManager;

    protected Class<T> entityClass;

    public BaseDataAccess(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T getByID(Long id) {
        return entityManager.find(entityClass, id);
    }

    public void deleteByID(Long id) {
        entityManager.remove(getByID(id));
    }

    public void persist(T model) {
        entityManager.persist(model);
        //return model.getId();
        
    }
}
