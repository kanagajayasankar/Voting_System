package india21.logic.dto.exception;

import india21.logic.util.StringUtils;

public class MissingException extends PollException {

    public MissingException(String parameter) {
        super(StringUtils.getLabel("missingException", parameter));
    }
    
}
