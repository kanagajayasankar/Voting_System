package india21.logic.dto;

import india21.persistence.entities.Question;
import india21.persistence.entities.enums.DecisionMode;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionDto {

    // <editor-fold defaultstate="collapsed" desc="properties">
    private Long pollId;

    private Long id;

    private String title;

    private DecisionMode decisionMode;

    private Boolean canAddAnswers;

    private Integer maxAnswers;

    private final ItemInfoDto createItemInfo = new ItemInfoDto();

    private List<ItemInfoDto> itemsList;

    private List<Long> selectedAnswers;

    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="access methods">
    public ItemInfoDto getCreateItemInfo() {
        return createItemInfo;
    }

    public Long getPollId() {
        return pollId;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DecisionMode getDecisionMode() {
        return decisionMode;
    }

    public void setDecisionMode(DecisionMode decisionMode) {
        this.decisionMode = decisionMode;
    }

    public Boolean getCanAddAnswers() {
        return canAddAnswers;
    }

    public void setCanAddAnswers(Boolean canAddAnswers) {
        this.canAddAnswers = canAddAnswers;
    }

    public Integer getMaxAnswers() {
        return maxAnswers;
    }

    public void setMaxAnswers(Integer maxAnswers) {
        this.maxAnswers = maxAnswers;
    }

    public List<ItemInfoDto> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<ItemInfoDto> itemsList) {
        this.itemsList = itemsList;
    }

    public List<Long> getSelectedAnswer() {
        return selectedAnswers; 
    }

    public void setSelectedAnswer(List<Long> selectedAnswer) {
        this.selectedAnswers = selectedAnswer;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="transformers">
    public static QuestionDto fromEo(Question question) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setPollId(question.getPoll().getId());
        questionDto.setTitle(question.getTitle());
        questionDto.setDecisionMode(question.getMode());
        questionDto.setCanAddAnswers(question.getCanAddAnswers());
        questionDto.setMaxAnswers(question.getMaxAnswers());
        questionDto.setId(question.getId());
        questionDto.setItemsList(question.getItems()
                .stream()
                .map(item -> ItemInfoDto.fromEo(item))
                .collect(Collectors.toList()));
        questionDto.createItemInfo.setQuestionId(question.getId());
        return questionDto;
    }
    // </editor-fold>

}
