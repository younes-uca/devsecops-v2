package  ma.zs.stocky.ws.dto.business;

import ma.zs.stocky.zynerator.audit.Log;
import ma.zs.stocky.zynerator.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Date;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;


import ma.zs.stocky.ws.dto.crm.PaymentTypeDto;
import ma.zs.stocky.ws.dto.catalog.ProductDto;
import ma.zs.stocky.ws.dto.crm.ClientDto;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseDto  extends AuditBaseDto {

    private String reference  ;
    private String purchaseDate ;
    private BigDecimal total  ;
    private String image  ;

    private ClientDto client ;

    private List<PurchaseItemDto> purchaseItems ;
    private List<PaymentDto> payments ;


    public PurchaseDto(){
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getPurchaseDate(){
        return this.purchaseDate;
    }
    public void setPurchaseDate(String purchaseDate){
        this.purchaseDate = purchaseDate;
    }

    @Log
    public BigDecimal getTotal(){
        return this.total;
    }
    public void setTotal(BigDecimal total){
        this.total = total;
    }

    @Log
    public String getImage(){
        return this.image;
    }
    public void setImage(String image){
        this.image = image;
    }


    public ClientDto getClient(){
        return this.client;
    }

    public void setClient(ClientDto client){
        this.client = client;
    }



    public List<PurchaseItemDto> getPurchaseItems(){
        return this.purchaseItems;
    }

    public void setPurchaseItems(List<PurchaseItemDto> purchaseItems){
        this.purchaseItems = purchaseItems;
    }
    public List<PaymentDto> getPayments(){
        return this.payments;
    }

    public void setPayments(List<PaymentDto> payments){
        this.payments = payments;
    }



}
