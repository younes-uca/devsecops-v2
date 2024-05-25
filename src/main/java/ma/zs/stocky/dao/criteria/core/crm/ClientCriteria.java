package  ma.zs.stocky.dao.criteria.core.crm;



import ma.zs.stocky.zynerator.criteria.BaseCriteria;
import java.util.List;

public class ClientCriteria extends  BaseCriteria  {

    private String description;
    private String descriptionLike;



    public ClientCriteria(){}

    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescriptionLike(){
        return this.descriptionLike;
    }
    public void setDescriptionLike(String descriptionLike){
        this.descriptionLike = descriptionLike;
    }


}
