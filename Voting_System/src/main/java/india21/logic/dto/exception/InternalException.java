package india21.logic.dto.exception;

import india21.logic.util.StringUtils;

public class InternalException extends PollException{

    public InternalException(String text) {
        super(StringUtils.getLabel("internalException", text));
    }
}
