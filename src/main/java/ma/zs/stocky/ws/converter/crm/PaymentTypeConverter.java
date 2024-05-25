package  ma.zs.stocky.ws.converter.crm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.zs.stocky.zynerator.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;




import ma.zs.stocky.zynerator.util.StringUtil;
import ma.zs.stocky.zynerator.converter.AbstractConverter;
import ma.zs.stocky.zynerator.util.DateUtil;
import ma.zs.stocky.bean.core.crm.PaymentType;
import ma.zs.stocky.ws.dto.crm.PaymentTypeDto;

@Component
public class PaymentTypeConverter {


    public  PaymentTypeConverter() {
    }


    public PaymentType toItem(PaymentTypeDto dto) {
        if (dto == null) {
            return null;
        } else {
        PaymentType item = new PaymentType();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getReference()))
                item.setReference(dto.getReference());
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());



        return item;
        }
    }


    public PaymentTypeDto toDto(PaymentType item) {
        if (item == null) {
            return null;
        } else {
            PaymentTypeDto dto = new PaymentTypeDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getReference()))
                dto.setReference(item.getReference());
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());


        return dto;
        }
    }


	
    public List<PaymentType> toItem(List<PaymentTypeDto> dtos) {
        List<PaymentType> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (PaymentTypeDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<PaymentTypeDto> toDto(List<PaymentType> items) {
        List<PaymentTypeDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (PaymentType item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(PaymentTypeDto dto, PaymentType t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
    }

    public List<PaymentType> copy(List<PaymentTypeDto> dtos) {
        List<PaymentType> result = new ArrayList<>();
        if (dtos != null) {
            for (PaymentTypeDto dto : dtos) {
                PaymentType instance = new PaymentType();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


}
