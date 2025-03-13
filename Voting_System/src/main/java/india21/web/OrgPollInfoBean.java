package india21.web;

import india21.logic.OrganizerLogic;
import india21.logic.dto.ItemInfoDto;
import india21.logic.dto.PollIncreementDto;
import india21.logic.dto.QuestionDto;
import india21.logic.dto.exception.PollException;
import india21.logic.util.Pair;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class OrgPollInfoBean extends BaseBean {

    @Inject
    private OrganizerLogic organizerLogic;

    private List<String> optionType;

    private final QuestionDto questionDto = new QuestionDto();

    protected void redirectToThisWithParams() {
        redirectTo(getRequestCompletePath(), new Pair<>("pollId", this.selectedPollId.toString()));
    }
    public List<String> getOptionType() {
        if(optionType == null){
            optionType = new ArrayList<>();
            optionType.add("MULTIPLE");
            optionType.add("SINGLE");
            optionType.add("BOOLEAN");
        }
        return optionType;
    }

    public void setOptionType(List<String> optionType) {
        this.optionType = optionType;
    }

    private Long selectedPollId;

    public void setSelectedPollId(Long selectedPollId) {
        this.selectedPollId = selectedPollId;
    }

    public Long getSelectedPollId() {
        return selectedPollId;
    }
    
    public QuestionDto getCreateQuestionDto() {
        return questionDto;
    }

    public PollIncreementDto getSelectedPoll() {
        try {
            return organizerLogic.getPollById(selectedPollId);
        } catch (PollException exception) {
            parseVotingException(exception);
            return new PollIncreementDto();
        }
    }

    public List<QuestionDto> getPollQuestions() {
        try {
            return organizerLogic.getPollQuestions(selectedPollId);
        } catch (PollException exception) {
            parseVotingException(exception);
            return new ArrayList<>();
        }
    }

    public void editQuestion(QuestionDto model) {
        try {
            organizerLogic.addUpdateQuestion(model);
            redirectToThisWithParams();
        } catch (PollException exception) {
            parseVotingException(exception);
        }
    }
    
    public void createQuestion() {
        try {
            this.questionDto.setPollId(this.selectedPollId);
            organizerLogic.addUpdateQuestion(questionDto);
            redirectToThisWithParams();
        } catch (PollException exception) {
            parseVotingException(exception);
        }
    }

    public void deleteQuestion(Long questionId) {
        try {
            organizerLogic.deleteQuestion(questionId);
            redirectToThisWithParams();
        } catch (PollException exception) {
            parseVotingException(exception);
        }
    }

    public void addItem(ItemInfoDto model) {
        try {
            organizerLogic.addUpdateItem(model);
            redirectToThisWithParams();
        } catch (PollException exception) {
            parseVotingException(exception);
        }
    }

    public void editItem(ItemInfoDto model) {
        try {
            organizerLogic.addUpdateItem(model);
            redirectToThisWithParams();
        } catch (PollException exception) {
            parseVotingException(exception);
        }
    }

    public void deleteItem(Long itemId) {
        try {
            organizerLogic.deleteItem(itemId);
            redirectToThisWithParams();
        } catch (PollException exception) {
            parseVotingException(exception);
        }
    }
}
