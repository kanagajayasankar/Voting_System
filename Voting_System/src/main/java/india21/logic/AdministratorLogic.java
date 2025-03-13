package india21.logic;

import india21.logic.dto.exception.PollException;

public interface AdministratorLogic{
    void deletePoll(Long pollId) throws PollException;
}

