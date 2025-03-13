package india21.logic.dto.exception;

import india21.logic.util.StringUtils;

public class NotFoundException extends PollException {
    public NotFoundException(String obj){
        super(StringUtils.getLabel("notFoundException", obj));
    }
}
