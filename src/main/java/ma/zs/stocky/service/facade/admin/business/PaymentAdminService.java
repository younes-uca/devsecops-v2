package ma.zs.stocky.service.facade.admin.business;

import java.util.List;
import ma.zs.stocky.bean.core.business.Payment;
import ma.zs.stocky.dao.criteria.core.business.PaymentCriteria;
import ma.zs.stocky.zynerator.service.IService;


import org.springframework.web.multipart.MultipartFile;

public interface PaymentAdminService {



    List<Payment> findByPurchaseId(Long id);
    int deleteByPurchaseId(Long id);
    long countByPurchaseReference(String reference);
    List<Payment> findByPaymentTypeId(Long id);
    int deleteByPaymentTypeId(Long id);
    long countByPaymentTypeCode(String code);




	Payment create(Payment t);

    Payment update(Payment t);

    List<Payment> update(List<Payment> ts,boolean createIfNotExist);

    Payment findById(Long id);

    Payment findOrSave(Payment t);

    Payment findByReferenceEntity(Payment t);

    Payment findWithAssociatedLists(Long id);

    List<Payment> findAllOptimized();

    List<Payment> findAll();

    List<Payment> findByCriteria(PaymentCriteria criteria);

    List<Payment> findPaginatedByCriteria(PaymentCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(PaymentCriteria criteria);

    List<Payment> delete(List<Payment> ts);

    void deleteByIdIn(List<Long> ids);

    boolean deleteById(Long id);

    List<List<Payment>> getToBeSavedAndToBeDeleted(List<Payment> oldList, List<Payment> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

    List<Payment> importExcel(MultipartFile file);

    List<Payment> importData(List<Payment> items);

}
