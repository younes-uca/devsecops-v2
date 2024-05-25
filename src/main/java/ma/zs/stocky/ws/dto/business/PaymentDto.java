package  ma.zs.stocky.ws.dto.business;

import ma.zs.stocky.zynerator.audit.Log;
import ma.zs.stocky.zynerator.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;


import ma.zs.stocky.ws.dto.crm.PaymentTypeDto;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDto  extends AuditBaseDto {

    private String reference  ;
    private BigDecimal amount  ;

    private PurchaseDto purchase ;
    private PaymentTypeDto paymentType ;



    public PaymentDto(){
        super();
    }



    @Log
    public String getReference(){
        return this.reference;
    }
    public void setReference(String reference){
        this.reference = reference;
    }

    @Log
    public BigDecimal getAmount(){
        return this.amount;
    }
    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }


    public PurchaseDto getPurchase(){
        return this.purchase;
    }

    public void setPurchase(PurchaseDto purchase){
        this.purchase = purchase;
    }
    public PaymentTypeDto getPaymentType(){
        return this.paymentType;
    }

    public void setPaymentType(PaymentTypeDto paymentType){
        this.paymentType = paymentType;
    }






}
