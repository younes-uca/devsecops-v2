package ma.zs.stocky.bean.core.business;

import java.util.Objects;





import ma.zs.stocky.bean.core.crm.PaymentType;


import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zs.stocky.zynerator.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;
import java.math.BigDecimal;

@Entity
@Table(name = "payment")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="payment_seq",sequenceName="payment_seq",allocationSize=1, initialValue = 1)
public class Payment  extends BaseEntity     {

    private Long id;



    @Column(length = 500)
    private String reference;

    private BigDecimal amount = BigDecimal.ZERO;

    private Purchase purchase ;
    private PaymentType paymentType ;


    public Payment(){
        super();
    }

    public Payment(Long id){
        this.id = id;
    }

    public Payment(Long id,String reference){
        this.id = id;
        this.reference = reference ;
    }
    public Payment(String reference){
        this.reference = reference ;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="payment_seq")
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase")
    public Purchase getPurchase(){
        return this.purchase;
    }
    public void setPurchase(Purchase purchase){
        this.purchase = purchase;
    }
    public String getReference(){
        return this.reference;
    }
    public void setReference(String reference){
        this.reference = reference;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_type")
    public PaymentType getPaymentType(){
        return this.paymentType;
    }
    public void setPaymentType(PaymentType paymentType){
        this.paymentType = paymentType;
    }
    public BigDecimal getAmount(){
        return this.amount;
    }
    public void setAmount(BigDecimal amount){
        this.amount = amount;
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
        Payment payment = (Payment) o;
        return id != null && id.equals(payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

