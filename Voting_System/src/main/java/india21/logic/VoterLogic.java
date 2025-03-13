package india21.logic;

import india21.logic.dto.PollIncreementDto;
import india21.logic.dto.PollResultDto;
import india21.logic.dto.exception.PollException;

public interface VoterLogic {

    PollIncreementDto getPollByToken(String tokenValue) throws PollException;

    PollResultDto viewResult(String publishToken) throws PollException;

    
    Long addVote(String token, PollIncreementDto poll) throws PollException;
}
