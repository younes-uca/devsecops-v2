package ma.zs.stocky.service.facade.admin.business;

import java.util.List;
import ma.zs.stocky.bean.core.business.PurchaseItem;
import ma.zs.stocky.dao.criteria.core.business.PurchaseItemCriteria;
import ma.zs.stocky.zynerator.service.IService;


import org.springframework.web.multipart.MultipartFile;

public interface PurchaseItemAdminService {



    List<PurchaseItem> findByProductId(Long id);
    int deleteByProductId(Long id);
    long countByProductCode(String code);
    List<PurchaseItem> findByPurchaseId(Long id);
    int deleteByPurchaseId(Long id);
    long countByPurchaseReference(String reference);




	PurchaseItem create(PurchaseItem t);

    PurchaseItem update(PurchaseItem t);

    List<PurchaseItem> update(List<PurchaseItem> ts,boolean createIfNotExist);

    PurchaseItem findById(Long id);

    PurchaseItem findOrSave(PurchaseItem t);

    PurchaseItem findByReferenceEntity(PurchaseItem t);

    PurchaseItem findWithAssociatedLists(Long id);

    List<PurchaseItem> findAllOptimized();

    List<PurchaseItem> findAll();

    List<PurchaseItem> findByCriteria(PurchaseItemCriteria criteria);

    List<PurchaseItem> findPaginatedByCriteria(PurchaseItemCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(PurchaseItemCriteria criteria);

    List<PurchaseItem> delete(List<PurchaseItem> ts);

    void deleteByIdIn(List<Long> ids);

    boolean deleteById(Long id);

    List<List<PurchaseItem>> getToBeSavedAndToBeDeleted(List<PurchaseItem> oldList, List<PurchaseItem> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

    List<PurchaseItem> importExcel(MultipartFile file);

    List<PurchaseItem> importData(List<PurchaseItem> items);

}
