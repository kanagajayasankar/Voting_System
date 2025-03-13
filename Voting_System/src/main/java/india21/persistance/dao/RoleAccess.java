package india21.persistence.dao;

import india21.persistence.entities.Role;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class RoleAccess extends BaseDataAccess<Role> {
    
    public RoleAccess() {
        super(Role.class);
    }
    
}
