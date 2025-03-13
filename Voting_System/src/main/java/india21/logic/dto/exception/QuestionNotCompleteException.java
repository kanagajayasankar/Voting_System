package india21.logic.dto.exception;

import india21.logic.util.StringUtils;

public class QuestionNotCompleteException extends PollException{

    public QuestionNotCompleteException(String question, String text) {
        super(StringUtils.getLabel("questionNotCompleteException", question, text));
    }
}
