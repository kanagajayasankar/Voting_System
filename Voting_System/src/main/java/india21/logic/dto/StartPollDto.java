package india21.logic.dto;

public class StartPollDto {

    private Long pollId;
    private String VoterListName;
    private Boolean isAnonymous;
    private Boolean autoNotify;

    public StartPollDto() {

    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public void setVoterListName(String voterListName) {
        this.VoterListName = voterListName;
    }

    public void setIsAnonymous(Boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public void setAutoNotify(Boolean autoNotify) {
        this.autoNotify = autoNotify;
    }

    public StartPollDto(Long pollId, String VoterListName, Boolean isAnonymous, Boolean autoNotify) {
        this.pollId = pollId;
        this.VoterListName = VoterListName;
        this.isAnonymous = isAnonymous;
        this.autoNotify = autoNotify;
    }

    public Long getPollId() {
        return pollId;
    }

    public String getVoterListName() {
        return VoterListName;
    }

    public Boolean getIsAnonymous() {
        return isAnonymous;
    }

    public Boolean getAutoNotify() {
        return autoNotify;
    }

}
