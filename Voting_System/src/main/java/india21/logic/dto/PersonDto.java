package india21.logic.dto;

import india21.persistence.entities.Person;

public class PersonDto {

    private Long id;
    private String firstName;
    private String lastName;

    public static PersonDto fromEo(Person model) {
        PersonDto dto = new PersonDto();
        dto.setId(model.getId());
        dto.setFirstName(model.getFirstName());
        dto.setLastName(model.getLastName());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }
}
