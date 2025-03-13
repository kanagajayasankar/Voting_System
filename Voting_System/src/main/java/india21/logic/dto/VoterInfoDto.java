package india21.logic.dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VoterInfoDto {

    public VoterInfoDto() {
        
    }
     
    public VoterInfoDto(String name) {
        this.name = name;
    }
    
    private String name;

    private String emailsList;

    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmailsList() {
        return "";
    }

    public void setEmailsList(String emailsList) {
        this.emailsList = emailsList;
    }

    public String getName() {
        return name;
    }

    public List<String> getEmails() {
        return Arrays.stream(emailsList.replaceAll("[ \r\n;,]", ";").split(";"))
                .filter(email->!email.isEmpty())
                .collect(Collectors.toList());
    }
}
