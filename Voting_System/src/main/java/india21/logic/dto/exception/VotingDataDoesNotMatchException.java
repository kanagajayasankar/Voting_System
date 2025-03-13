package india21.logic.dto.exception;

import india21.logic.util.StringUtils;

public class VotingDataDoesNotMatchException extends PollException{

    public VotingDataDoesNotMatchException() {
        super(StringUtils.getLabel("votingDataDoesNotMatchException"));
    }
}
