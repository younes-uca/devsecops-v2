package ma.zs.stocky.service.facade.admin.crm;

import java.util.List;
import ma.zs.stocky.bean.core.crm.PaymentType;
import ma.zs.stocky.dao.criteria.core.crm.PaymentTypeCriteria;
import ma.zs.stocky.zynerator.service.IService;


import org.springframework.web.multipart.MultipartFile;

public interface PaymentTypeAdminService {







	PaymentType create(PaymentType t);

    PaymentType update(PaymentType t);

    List<PaymentType> update(List<PaymentType> ts,boolean createIfNotExist);

    PaymentType findById(Long id);

    PaymentType findOrSave(PaymentType t);

    PaymentType findByReferenceEntity(PaymentType t);

    PaymentType findWithAssociatedLists(Long id);

    List<PaymentType> findAllOptimized();

    List<PaymentType> findAll();

    List<PaymentType> findByCriteria(PaymentTypeCriteria criteria);

    List<PaymentType> findPaginatedByCriteria(PaymentTypeCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(PaymentTypeCriteria criteria);

    List<PaymentType> delete(List<PaymentType> ts);

    void deleteByIdIn(List<Long> ids);

    boolean deleteById(Long id);

    List<List<PaymentType>> getToBeSavedAndToBeDeleted(List<PaymentType> oldList, List<PaymentType> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

    List<PaymentType> importExcel(MultipartFile file);

    List<PaymentType> importData(List<PaymentType> items);

}
