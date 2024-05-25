package  ma.zs.stocky.ws.converter.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.zs.stocky.zynerator.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;

import ma.zs.stocky.ws.converter.business.PurchaseConverter;
import ma.zs.stocky.bean.core.business.Purchase;
import ma.zs.stocky.ws.converter.crm.PaymentTypeConverter;
import ma.zs.stocky.bean.core.crm.PaymentType;

import ma.zs.stocky.bean.core.business.Purchase;


import ma.zs.stocky.zynerator.util.StringUtil;
import ma.zs.stocky.zynerator.converter.AbstractConverter;
import ma.zs.stocky.zynerator.util.DateUtil;
import ma.zs.stocky.bean.core.business.Payment;
import ma.zs.stocky.ws.dto.business.PaymentDto;

@Component
public class PaymentConverter {

    @Autowired
    private PurchaseConverter purchaseConverter ;
    @Autowired
    private PaymentTypeConverter paymentTypeConverter ;
    private boolean purchase;
    private boolean paymentType;

    public  PaymentConverter() {
        initObject(true);
    }


    public Payment toItem(PaymentDto dto) {
        if (dto == null) {
            return null;
        } else {
        Payment item = new Payment();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getReference()))
                item.setReference(dto.getReference());
            if(StringUtil.isNotEmpty(dto.getAmount()))
                item.setAmount(dto.getAmount());
            if(dto.getPurchase() != null && dto.getPurchase().getId() != null){
                item.setPurchase(new Purchase());
                item.getPurchase().setId(dto.getPurchase().getId());
                item.getPurchase().setReference(dto.getPurchase().getReference());
            }

            if(this.paymentType && dto.getPaymentType()!=null)
                item.setPaymentType(paymentTypeConverter.toItem(dto.getPaymentType())) ;




        return item;
        }
    }


    public PaymentDto toDto(Payment item) {
        if (item == null) {
            return null;
        } else {
            PaymentDto dto = new PaymentDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getReference()))
                dto.setReference(item.getReference());
            if(StringUtil.isNotEmpty(item.getAmount()))
                dto.setAmount(item.getAmount());
            if(this.purchase && item.getPurchase()!=null) {
                dto.setPurchase(purchaseConverter.toDto(item.getPurchase())) ;

            }
            if(this.paymentType && item.getPaymentType()!=null) {
                dto.setPaymentType(paymentTypeConverter.toDto(item.getPaymentType())) ;

            }


        return dto;
        }
    }

    public void init(boolean value) {
        initObject(value);
    }

    public void initObject(boolean value) {
        this.purchase = value;
        this.paymentType = value;
    }
	
    public List<Payment> toItem(List<PaymentDto> dtos) {
        List<Payment> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (PaymentDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<PaymentDto> toDto(List<Payment> items) {
        List<PaymentDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Payment item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(PaymentDto dto, Payment t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if(t.getPurchase() == null  && dto.getPurchase() != null){
            t.setPurchase(new Purchase());
        }
        if(t.getPaymentType() == null  && dto.getPaymentType() != null){
            t.setPaymentType(new PaymentType());
        }
        if (dto.getPurchase() != null)
        purchaseConverter.copy(dto.getPurchase(), t.getPurchase());
        if (dto.getPaymentType() != null)
        paymentTypeConverter.copy(dto.getPaymentType(), t.getPaymentType());
    }

    public List<Payment> copy(List<PaymentDto> dtos) {
        List<Payment> result = new ArrayList<>();
        if (dtos != null) {
            for (PaymentDto dto : dtos) {
                Payment instance = new Payment();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


    public PurchaseConverter getPurchaseConverter(){
        return this.purchaseConverter;
    }
    public void setPurchaseConverter(PurchaseConverter purchaseConverter ){
        this.purchaseConverter = purchaseConverter;
    }
    public PaymentTypeConverter getPaymentTypeConverter(){
        return this.paymentTypeConverter;
    }
    public void setPaymentTypeConverter(PaymentTypeConverter paymentTypeConverter ){
        this.paymentTypeConverter = paymentTypeConverter;
    }
    public boolean  isPurchase(){
        return this.purchase;
    }
    public void  setPurchase(boolean purchase){
        this.purchase = purchase;
    }
    public boolean  isPaymentType(){
        return this.paymentType;
    }
    public void  setPaymentType(boolean paymentType){
        this.paymentType = paymentType;
    }
}
