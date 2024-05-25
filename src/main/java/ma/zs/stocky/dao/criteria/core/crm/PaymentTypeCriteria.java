package  ma.zs.stocky.dao.criteria.core.crm;



import ma.zs.stocky.zynerator.criteria.BaseCriteria;
import java.util.List;

public class PaymentTypeCriteria extends  BaseCriteria  {

    private String reference;
    private String referenceLike;
    private String code;
    private String codeLike;



    public PaymentTypeCriteria(){}

    public String getReference(){
        return this.reference;
    }
    public void setReference(String reference){
        this.reference = reference;
    }
    public String getReferenceLike(){
        return this.referenceLike;
    }
    public void setReferenceLike(String referenceLike){
        this.referenceLike = referenceLike;
    }

    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCodeLike(){
        return this.codeLike;
    }
    public void setCodeLike(String codeLike){
        this.codeLike = codeLike;
    }


}
