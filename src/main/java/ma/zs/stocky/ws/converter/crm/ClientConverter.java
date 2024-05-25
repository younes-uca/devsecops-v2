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
import ma.zs.stocky.bean.core.crm.Client;
import ma.zs.stocky.ws.dto.crm.ClientDto;

@Component
public class ClientConverter {


    public  ClientConverter() {
    }


    public Client toItem(ClientDto dto) {
        if (dto == null) {
            return null;
        } else {
        Client item = new Client();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getDescription()))
                item.setDescription(dto.getDescription());



        return item;
        }
    }


    public ClientDto toDto(Client item) {
        if (item == null) {
            return null;
        } else {
            ClientDto dto = new ClientDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getDescription()))
                dto.setDescription(item.getDescription());


        return dto;
        }
    }


	
    public List<Client> toItem(List<ClientDto> dtos) {
        List<Client> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (ClientDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<ClientDto> toDto(List<Client> items) {
        List<ClientDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Client item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(ClientDto dto, Client t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
    }

    public List<Client> copy(List<ClientDto> dtos) {
        List<Client> result = new ArrayList<>();
        if (dtos != null) {
            for (ClientDto dto : dtos) {
                Client instance = new Client();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


}
