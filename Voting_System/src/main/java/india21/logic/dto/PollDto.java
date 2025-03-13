package india21.logic.dto;

import india21.logic.util.StringUtils;

import java.util.Date;
import java.util.List;

public class PollDto {

    private List<QuestionDto> questions;
    private Long pollId;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Date extendedDate;

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStartDateString(String startDate) {
        this.startDate = StringUtils.parseDate(startDate);
    }

    public void setEndDateString(String endDate) {
        this.endDate = StringUtils.parseDate(endDate);
    }

    public void setExtendedDateString(String extended) {
        this.extendedDate = StringUtils.parseDate(extended);
    }

    public Long getPollId() {
        return pollId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getExtendedDate() {
        return extendedDate;
    }

    public String getStartDateString() {
        return StringUtils.dateToString(startDate);
    }

    public String getEndDateString() {
        return StringUtils.dateToString(endDate);
    }

    public String getExtendedDateString() {
        return StringUtils.dateToString(extendedDate);
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }
}
