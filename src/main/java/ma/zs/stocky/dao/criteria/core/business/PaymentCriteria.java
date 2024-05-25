package  ma.zs.stocky.dao.criteria.core.business;


import ma.zs.stocky.dao.criteria.core.crm.PaymentTypeCriteria;

import ma.zs.stocky.zynerator.criteria.BaseCriteria;
import java.util.List;

public class PaymentCriteria extends  BaseCriteria  {

    private String reference;
    private String referenceLike;
    private String amount;
    private String amountMin;
    private String amountMax;

    private PurchaseCriteria purchase ;
    private List<PurchaseCriteria> purchases ;
    private PaymentTypeCriteria paymentType ;
    private List<PaymentTypeCriteria> paymentTypes ;


    public PaymentCriteria(){}

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

    public String getAmount(){
        return this.amount;
    }
    public void setAmount(String amount){
        this.amount = amount;
    }   
    public String getAmountMin(){
        return this.amountMin;
    }
    public void setAmountMin(String amountMin){
        this.amountMin = amountMin;
    }
    public String getAmountMax(){
        return this.amountMax;
    }
    public void setAmountMax(String amountMax){
        this.amountMax = amountMax;
    }
      

    public PurchaseCriteria getPurchase(){
        return this.purchase;
    }

    public void setPurchase(PurchaseCriteria purchase){
        this.purchase = purchase;
    }
    public List<PurchaseCriteria> getPurchases(){
        return this.purchases;
    }

    public void setPurchases(List<PurchaseCriteria> purchases){
        this.purchases = purchases;
    }
    public PaymentTypeCriteria getPaymentType(){
        return this.paymentType;
    }

    public void setPaymentType(PaymentTypeCriteria paymentType){
        this.paymentType = paymentType;
    }
    public List<PaymentTypeCriteria> getPaymentTypes(){
        return this.paymentTypes;
    }

    public void setPaymentTypes(List<PaymentTypeCriteria> paymentTypes){
        this.paymentTypes = paymentTypes;
    }
}
