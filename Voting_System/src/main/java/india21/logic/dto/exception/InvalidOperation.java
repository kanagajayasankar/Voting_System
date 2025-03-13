package india21.logic.dto.exception;

import india21.logic.util.StringUtils;

public class InvalidOperation extends PollException {

    public InvalidOperation(String operation, String text) {
        super(StringUtils.getLabel("invalidOperation", operation, text));
    }
}
