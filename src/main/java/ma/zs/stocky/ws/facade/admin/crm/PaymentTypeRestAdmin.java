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

import ma.zs.stocky.bean.core.crm.PaymentType;
import ma.zs.stocky.dao.criteria.core.crm.PaymentTypeCriteria;
import ma.zs.stocky.service.facade.admin.crm.PaymentTypeAdminService;
import ma.zs.stocky.ws.converter.crm.PaymentTypeConverter;
import ma.zs.stocky.ws.dto.crm.PaymentTypeDto;
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
@RequestMapping("/api/admin/paymentType/")
public class PaymentTypeRestAdmin {




    @Operation(summary = "Import Data")
    @PostMapping("import-data")
    public ResponseEntity<List<PaymentTypeDto>> importData(@RequestBody List<PaymentTypeDto> dtos) {
        List<PaymentType> items = converter.toItem(dtos);
        List<PaymentType> imported = this.service.importData(items);
        List<PaymentTypeDto> result = converter.toDto(imported);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Finds a list of all paymentTypes")
    @GetMapping("")
    public ResponseEntity<List<PaymentTypeDto>> findAll() throws Exception {
        ResponseEntity<List<PaymentTypeDto>> res = null;
        List<PaymentType> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<PaymentTypeDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all paymentTypes")
    @GetMapping("optimized")
    public ResponseEntity<List<PaymentTypeDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<PaymentTypeDto>> res = null;
        List<PaymentType> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<PaymentTypeDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a paymentType by id")
    @GetMapping("id/{id}")
    public ResponseEntity<PaymentTypeDto> findById(@PathVariable Long id) {
        PaymentType t = service.findById(id);
        if (t != null) {
            PaymentTypeDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a paymentType by reference")
    @GetMapping("reference/{reference}")
    public ResponseEntity<PaymentTypeDto> findByReference(@PathVariable String reference) {
	    PaymentType t = service.findByReferenceEntity(new PaymentType(reference));
        if (t != null) {
            PaymentTypeDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  paymentType")
    @PostMapping("")
    public ResponseEntity<PaymentTypeDto> save(@RequestBody PaymentTypeDto dto) throws Exception {
        if(dto!=null){
            PaymentType myT = converter.toItem(dto);
            PaymentType t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                PaymentTypeDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  paymentType")
    @PutMapping("")
    public ResponseEntity<PaymentTypeDto> update(@RequestBody PaymentTypeDto dto) throws Exception {
        ResponseEntity<PaymentTypeDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            PaymentType t = service.findById(dto.getId());
            converter.copy(dto,t);
            PaymentType updated = service.update(t);
            PaymentTypeDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of paymentType")
    @PostMapping("multiple")
    public ResponseEntity<List<PaymentTypeDto>> delete(@RequestBody List<PaymentTypeDto> dtos) throws Exception {
        ResponseEntity<List<PaymentTypeDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            List<PaymentType> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }
    @Operation(summary = "Delete the specified paymentType")
    @DeleteMapping("")
    public ResponseEntity<PaymentTypeDto> delete(@RequestBody PaymentTypeDto dto) throws Exception {
		ResponseEntity<PaymentTypeDto> res;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dto != null) {
            PaymentType t = converter.toItem(dto);
            service.delete(Arrays.asList(t));
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dto, status);
        return res;
    }

    @Operation(summary = "Delete the specified paymentType")
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
    @Operation(summary = "Delete multiple paymentTypes by ids")
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



    @Operation(summary = "Finds a paymentType and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<PaymentTypeDto> findWithAssociatedLists(@PathVariable Long id) {
        PaymentType loaded =  service.findWithAssociatedLists(id);
        PaymentTypeDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds paymentTypes by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<PaymentTypeDto>> findByCriteria(@RequestBody PaymentTypeCriteria criteria) throws Exception {
        ResponseEntity<List<PaymentTypeDto>> res = null;
        List<PaymentType> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<PaymentTypeDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated paymentTypes by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody PaymentTypeCriteria criteria) throws Exception {
        List<PaymentType> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        List<PaymentTypeDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets paymentType data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody PaymentTypeCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<PaymentTypeDto> findDtos(List<PaymentType> list){
        List<PaymentTypeDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<PaymentTypeDto> getDtoResponseEntity(PaymentTypeDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




    @Autowired private PaymentTypeAdminService service;
    @Autowired private PaymentTypeConverter converter;





}
