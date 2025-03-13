package india21.logic.impl;

import india21.logic.AdministratorLogic;
import india21.logic.dto.exception.NotFoundException;
import india21.logic.dto.exception.PollException;
import india21.logic.util.StringUtils;
import india21.persistence.dao.AdministratorAccess;
import india21.persistence.dao.PollAccess;
import india21.persistence.entities.Administrator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.security.Principal;
import java.util.Optional;

@Stateless
public class AdministratorLogicImpl implements AdministratorLogic {

    @Inject
    private PollAccess pollAccess;

    @Inject
    private ValidationLogicImp validationLogic;

    @Inject
    private AdministratorAccess administratorAccess;

    @Inject
    private Principal principal;

    private Administrator getAdministrator() throws PollException {
        Optional<Administrator> administrator = administratorAccess.getByUsername(principal.getName());
        if (!administrator.isPresent()) {
            throw new NotFoundException(StringUtils.getLabel("administrator"));
        }
        return administrator.get();
    }

    @Override
    public void deletePoll(Long id) throws PollException {
//        validationLogic.validateAdminDeletablePoll(id, getAdministrator());
        pollAccess.deleteByID(id);
    }
}
