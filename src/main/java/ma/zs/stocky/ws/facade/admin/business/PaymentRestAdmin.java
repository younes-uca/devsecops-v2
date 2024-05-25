package  ma.zs.stocky.ws.facade.admin.business;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import java.util.Arrays;
import java.util.ArrayList;

import ma.zs.stocky.bean.core.business.Payment;
import ma.zs.stocky.dao.criteria.core.business.PaymentCriteria;
import ma.zs.stocky.service.facade.admin.business.PaymentAdminService;
import ma.zs.stocky.ws.converter.business.PaymentConverter;
import ma.zs.stocky.ws.dto.business.PaymentDto;
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
@RequestMapping("/api/admin/payment/")
public class PaymentRestAdmin {




    @Operation(summary = "Import Data")
    @PostMapping("import-data")
    public ResponseEntity<List<PaymentDto>> importData(@RequestBody List<PaymentDto> dtos) {
        List<Payment> items = converter.toItem(dtos);
        List<Payment> imported = this.service.importData(items);
        List<PaymentDto> result = converter.toDto(imported);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Finds a list of all payments")
    @GetMapping("")
    public ResponseEntity<List<PaymentDto>> findAll() throws Exception {
        ResponseEntity<List<PaymentDto>> res = null;
        List<Payment> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
            converter.initObject(true);
        List<PaymentDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all payments")
    @GetMapping("optimized")
    public ResponseEntity<List<PaymentDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<PaymentDto>> res = null;
        List<Payment> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initObject(true);
        List<PaymentDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a payment by id")
    @GetMapping("id/{id}")
    public ResponseEntity<PaymentDto> findById(@PathVariable Long id) {
        Payment t = service.findById(id);
        if (t != null) {
            converter.init(true);
            PaymentDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a payment by reference")
    @GetMapping("reference/{reference}")
    public ResponseEntity<PaymentDto> findByReference(@PathVariable String reference) {
	    Payment t = service.findByReferenceEntity(new Payment(reference));
        if (t != null) {
            converter.init(true);
            PaymentDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  payment")
    @PostMapping("")
    public ResponseEntity<PaymentDto> save(@RequestBody PaymentDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            Payment myT = converter.toItem(dto);
            Payment t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                PaymentDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  payment")
    @PutMapping("")
    public ResponseEntity<PaymentDto> update(@RequestBody PaymentDto dto) throws Exception {
        ResponseEntity<PaymentDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Payment t = service.findById(dto.getId());
            converter.copy(dto,t);
            Payment updated = service.update(t);
            PaymentDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of payment")
    @PostMapping("multiple")
    public ResponseEntity<List<PaymentDto>> delete(@RequestBody List<PaymentDto> dtos) throws Exception {
        ResponseEntity<List<PaymentDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<Payment> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }
    @Operation(summary = "Delete the specified payment")
    @DeleteMapping("")
    public ResponseEntity<PaymentDto> delete(@RequestBody PaymentDto dto) throws Exception {
		ResponseEntity<PaymentDto> res;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dto != null) {
            converter.init(false);
            Payment t = converter.toItem(dto);
            service.delete(Arrays.asList(t));
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dto, status);
        return res;
    }

    @Operation(summary = "Delete the specified payment")
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
    @Operation(summary = "Delete multiple payments by ids")
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


    @Operation(summary = "find by purchase id")
    @GetMapping("purchase/id/{id}")
    public List<PaymentDto> findByPurchaseId(@PathVariable Long id){
        return findDtos(service.findByPurchaseId(id));
    }
    @Operation(summary = "delete by purchase id")
    @DeleteMapping("purchase/id/{id}")
    public int deleteByPurchaseId(@PathVariable Long id){
        return service.deleteByPurchaseId(id);
    }
    @Operation(summary = "find by paymentType id")
    @GetMapping("paymentType/id/{id}")
    public List<PaymentDto> findByPaymentTypeId(@PathVariable Long id){
        return findDtos(service.findByPaymentTypeId(id));
    }
    @Operation(summary = "delete by paymentType id")
    @DeleteMapping("paymentType/id/{id}")
    public int deleteByPaymentTypeId(@PathVariable Long id){
        return service.deleteByPaymentTypeId(id);
    }

    @Operation(summary = "Finds a payment and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<PaymentDto> findWithAssociatedLists(@PathVariable Long id) {
        Payment loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        PaymentDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds payments by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<PaymentDto>> findByCriteria(@RequestBody PaymentCriteria criteria) throws Exception {
        ResponseEntity<List<PaymentDto>> res = null;
        List<Payment> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initObject(true);
        List<PaymentDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated payments by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody PaymentCriteria criteria) throws Exception {
        List<Payment> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initObject(true);
        List<PaymentDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets payment data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody PaymentCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<PaymentDto> findDtos(List<Payment> list){
        converter.initObject(true);
        List<PaymentDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<PaymentDto> getDtoResponseEntity(PaymentDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




    @Autowired private PaymentAdminService service;
    @Autowired private PaymentConverter converter;





}
