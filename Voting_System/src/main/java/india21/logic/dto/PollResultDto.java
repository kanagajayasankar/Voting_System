package india21.logic.dto;

import india21.logic.util.StringUtils;
import india21.persistence.entities.Poll;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PollResultDto {

    private Long pollId;
    private Date startDate;
    private Date endDate;
    private Integer submittedVotes;
    private Integer numOfInvitations;
    private List<QuestionResultDto> questionResults = new ArrayList<>();
    private String title;
    private String description;
    private String publishKey;

    public static PollResultDto fromEo(Poll poll) {
        PollResultDto dto = new PollResultDto();
        dto.setPublishKey(poll.getPublicResultKey());
        dto.setPollId(poll.getId());
        dto.setStartDate(poll.getStartDate());
        dto.setEndDate(poll.getEndDate());
        dto.setNumOfInvitations(poll.getTokenCount());
        dto.setSubmittedVotes(poll.getSubmittedVotes());
        dto.setTitle(poll.getTitle());
        dto.setDescription(poll.getDescription());
        dto.setQuestionResults(poll.getQuestions().stream()
                .map(question -> QuestionResultDto.fromEo(question))
                .collect(Collectors.toList()));
        return dto;
    }
    
    public String getPublishKey() {
        return publishKey;
    }

    public void setPublishKey(String publishKey) {
        this.publishKey = publishKey;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStartDateString() {
        return StringUtils.dateToString(startDate);
    }

    public String getEndDateString() {
        return StringUtils.dateToString(endDate);
    }

    public void setSubmittedVotes(Integer submittedVotes) {
        this.submittedVotes = submittedVotes;
    }

    public void setNumOfInvitations(Integer numOfInvitations) {
        this.numOfInvitations = numOfInvitations;
    }

    public void setQuestionResults(List<QuestionResultDto> questionResults) {
        this.questionResults = questionResults;
    }

    public List<QuestionResultDto> getQuestionResults() {
        return questionResults;
    }

    public Long getPollId() {
        return pollId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Integer getSubmittedVotes() {
        return submittedVotes;
    }

    public Integer getNumOfInvitations() {
        return numOfInvitations;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
