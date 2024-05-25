package  ma.zs.stocky.ws.facade.admin.crm;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import java.util.Arrays;
import java.util.ArrayList;

import ma.zs.stocky.bean.core.crm.Client;
import ma.zs.stocky.dao.criteria.core.crm.ClientCriteria;
import ma.zs.stocky.service.facade.admin.crm.ClientAdminService;
import ma.zs.stocky.ws.converter.crm.ClientConverter;
import ma.zs.stocky.ws.dto.crm.ClientDto;
import ma.zs.stocky.zynerator.controller.AbstractController;
import ma.zs.stocky.zynerator.dto.AuditEntityDto;
import ma.zs.stocky.zynerator.util.PaginatedList;


import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import ma.zs.stocky.zynerator.process.Result;


import org.springframework.web.multipart.MultipartFile;
import ma.zs.stocky.zynerator.dto.FileTempDto;

@RestController
@RequestMapping("/api/admin/client/")
public class ClientRestAdmin {




    @Operation(summary = "Import Data")
    @PostMapping("import-data")
    public ResponseEntity<List<ClientDto>> importData(@RequestBody List<ClientDto> dtos) {
        List<Client> items = converter.toItem(dtos);
        List<Client> imported = this.service.importData(items);
        List<ClientDto> result = converter.toDto(imported);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Finds a list of all clients")
    @GetMapping("")
    public ResponseEntity<List<ClientDto>> findAll() throws Exception {
        ResponseEntity<List<ClientDto>> res = null;
        List<Client> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<ClientDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all clients")
    @GetMapping("optimized")
    public ResponseEntity<List<ClientDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<ClientDto>> res = null;
        List<Client> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<ClientDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a client by id")
    @GetMapping("id/{id}")
    public ResponseEntity<ClientDto> findById(@PathVariable Long id) {
        Client t = service.findById(id);
        if (t != null) {
            ClientDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a client by description")
    @GetMapping("description/{description}")
    public ResponseEntity<ClientDto> findByDescription(@PathVariable String description) {
	    Client t = service.findByReferenceEntity(new Client(description));
        if (t != null) {
            ClientDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  client")
    @PostMapping("")
    public ResponseEntity<ClientDto> save(@RequestBody ClientDto dto) throws Exception {
        if(dto!=null){
            Client myT = converter.toItem(dto);
            Client t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                ClientDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  client")
    @PutMapping("")
    public ResponseEntity<ClientDto> update(@RequestBody ClientDto dto) throws Exception {
        ResponseEntity<ClientDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Client t = service.findById(dto.getId());
            converter.copy(dto,t);
            Client updated = service.update(t);
            ClientDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of client")
    @PostMapping("multiple")
    public ResponseEntity<List<ClientDto>> delete(@RequestBody List<ClientDto> dtos) throws Exception {
        ResponseEntity<List<ClientDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            List<Client> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }
    @Operation(summary = "Delete the specified client")
    @DeleteMapping("")
    public ResponseEntity<ClientDto> delete(@RequestBody ClientDto dto) throws Exception {
		ResponseEntity<ClientDto> res;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dto != null) {
            Client t = converter.toItem(dto);
            service.delete(Arrays.asList(t));
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dto, status);
        return res;
    }

    @Operation(summary = "Delete the specified client")
    @DeleteMapping("id/{id}")
    public ResponseEntity<Long> deleteById(@PathVariable Long id) throws Exception {
        ResponseEntity<Long> res;
        HttpStatus status = HttpStatus.PRECONDITION_FAILED;
        if (id != null) {
            boolean resultDelete = service.deleteById(id);
            if (resultDelete) {
                status = HttpStatus.OK;
            }
        }
        res = new ResponseEntity<>(id, status);
        return res;
    }
    @Operation(summary = "Delete multiple clients by ids")
    @DeleteMapping("multiple/id")
    public ResponseEntity<List<Long>> deleteByIdIn(@RequestBody List<Long> ids) throws Exception {
        ResponseEntity<List<Long>> res;
        HttpStatus status = HttpStatus.CONFLICT;
        if (ids != null) {
            service.deleteByIdIn(ids);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(ids, status);
        return res;
     }



    @Operation(summary = "Finds a client and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<ClientDto> findWithAssociatedLists(@PathVariable Long id) {
        Client loaded =  service.findWithAssociatedLists(id);
        ClientDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds clients by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<ClientDto>> findByCriteria(@RequestBody ClientCriteria criteria) throws Exception {
        ResponseEntity<List<ClientDto>> res = null;
        List<Client> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<ClientDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated clients by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody ClientCriteria criteria) throws Exception {
        List<Client> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        List<ClientDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets client data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody ClientCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<ClientDto> findDtos(List<Client> list){
        List<ClientDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<ClientDto> getDtoResponseEntity(ClientDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




    @Autowired private ClientAdminService service;
    @Autowired private ClientConverter converter;





}
