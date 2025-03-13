package india21.logic.dto;

import india21.persistence.entities.Item;

public class ItemInfoDto {

    // <editor-fold defaultstate="collapsed" desc="properties">
    private Long questionId;
    
    private Long id;
    
    private String description;
    
    private String name;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="access methods">

    public Long getQuestionId() {
        return questionId;
    }
    
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="">
    public static ItemInfoDto fromEo(Item item) {
        ItemInfoDto itemInfoDto = new ItemInfoDto();
        itemInfoDto.setQuestionId(item.getQuestion().getId());
        itemInfoDto.setId(item.getId());
        itemInfoDto.setName(item.getName());
        itemInfoDto.setDescription(item.getDescription());
        return itemInfoDto;
    }
    // </editor-fold>

}
