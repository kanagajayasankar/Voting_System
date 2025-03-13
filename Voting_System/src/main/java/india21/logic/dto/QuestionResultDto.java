package india21.logic.dto;

import india21.persistence.entities.Question;
import india21.persistence.entities.enums.DecisionMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionResultDto {

    private String title;
    private DecisionMode decisionMode;
    private Long questionID;
    private List<ItemResultDto> itemResults = new ArrayList<>();
    private Integer participantsCount;
    private Integer votesCount;
    private Integer absentationsCount;

    public static QuestionResultDto fromEo(Question model) {
        QuestionResultDto questionResultDto = new QuestionResultDto();
        questionResultDto.setVotesCount(model.getSubmittedVotes());
        questionResultDto.setParticipantsCount(model.getPoll().getSubmittedVotes());
        questionResultDto.setDecisionMode(model.getMode());
        questionResultDto.setAbsentationsCount(model.getAbstentions());
        questionResultDto.setQuestionID(model.getId());
        questionResultDto.setTitle(model.getTitle());
        questionResultDto.setItemResults(model.getItems().stream()
                .map(item -> ItemResultDto.fromEo(item))
                .collect(Collectors.toList()));
        return questionResultDto;
    }

    public ItemResultDto getDecisionItem() {
        Double halfParticipants = Double.valueOf(getParticipantsCount()) / 2;
        Double halfVotes = Double.valueOf(getVotesCount()) / 2;
        ItemResultDto topItem = getItemResults().stream()
                .sorted((a, b) -> b.getVoteCounts() - a.getVoteCounts())
                .findFirst()
                .get();
        switch (decisionMode) {
            case ABSOLUTE_MAJORITY:
                return topItem.getVoteCounts() > halfParticipants ? topItem : null;
            case RELATIVE_MAJORITY:
                return topItem.getVoteCounts() > halfVotes ? topItem : null;
            default:
                return topItem;
        }
    }

    public Integer getParticipantsCount() {
        return participantsCount;
    }

    public Integer getAbsentationsCount() {
        return absentationsCount;
    }

    public void setAbsentationsCount(Integer absentationsCount) {
        this.absentationsCount = absentationsCount;
    }

    public void setVotesCount(Integer votesCount) {
        this.votesCount = votesCount;
    }

    public Integer getVotesCount() {
        return votesCount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setParticipantsCount(Integer participantsCount) {
        this.participantsCount = participantsCount;
    }

    public void setDecisionMode(DecisionMode decisionMode) {
        this.decisionMode = decisionMode;
    }

    public void setItemResults(List<ItemResultDto> itemResults) {
        this.itemResults = itemResults;
    }

    public void setQuestionID(Long questionID) {
        this.questionID = questionID;
    }

    public List<ItemResultDto> getItemResults() {
        return itemResults;
    }

    public String getItemTitles() {
        return itemResults.stream()
                .map(item -> "\"" + item.getName().replace("\\", "\\\\").replace("\"", "\\\"") + "\"")
                .collect(Collectors.joining(", "));
    }

    public String getItemVotes() {
        return itemResults.stream()
                .map(item -> item.getVoteCounts().toString())
                .collect(Collectors.joining(", "));
    }

    public DecisionMode getDecisionMode() {
        return decisionMode;
    }

    public String getTitle() {
        return title;
    }

    public Long getQuestionID() {
        return questionID;
    }
}
