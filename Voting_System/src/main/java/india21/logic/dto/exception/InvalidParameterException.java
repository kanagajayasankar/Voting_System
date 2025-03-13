package india21.logic.dto.exception;

import india21.logic.util.StringUtils;

public class InvalidParameterException extends PollException {

    public InvalidParameterException(String parameter, String text) {
        super(StringUtils.getLabel("invalidParameterException", parameter, text));
    }
}
