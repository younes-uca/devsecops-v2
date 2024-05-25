package ma.zs.stocky.bean.core.crm;

import java.util.Objects;







import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zs.stocky.zynerator.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "payment_type")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="payment_type_seq",sequenceName="payment_type_seq",allocationSize=1, initialValue = 1)
public class PaymentType  extends BaseEntity     {

    private Long id;



    @Column(length = 500)
    private String reference;

    @Column(length = 500)
    private String code;



    public PaymentType(){
        super();
    }

    public PaymentType(Long id){
        this.id = id;
    }

    public PaymentType(Long id,String reference){
        this.id = id;
        this.reference = reference ;
    }
    public PaymentType(String reference){
        this.reference = reference ;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="payment_type_seq")
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getReference(){
        return this.reference;
    }
    public void setReference(String reference){
        this.reference = reference;
    }
    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }

    @Transient
    public String getLabel() {
        label = reference;
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentType paymentType = (PaymentType) o;
        return id != null && id.equals(paymentType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

