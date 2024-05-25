package ma.zs.stocky.service.impl.admin.business;


import ma.zs.stocky.zynerator.exception.EntityNotFoundException;
import ma.zs.stocky.bean.core.business.PurchaseItem;
import ma.zs.stocky.dao.criteria.core.business.PurchaseItemCriteria;
import ma.zs.stocky.dao.facade.core.business.PurchaseItemDao;
import ma.zs.stocky.dao.specification.core.business.PurchaseItemSpecification;
import ma.zs.stocky.service.facade.admin.business.PurchaseItemAdminService;
import ma.zs.stocky.zynerator.service.AbstractServiceImpl;
import ma.zs.stocky.zynerator.util.ListUtil;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import ma.zs.stocky.zynerator.util.RefelexivityUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ma.zs.stocky.service.facade.admin.business.PurchaseAdminService ;
import ma.zs.stocky.bean.core.business.Purchase ;
import ma.zs.stocky.service.facade.admin.catalog.ProductAdminService ;
import ma.zs.stocky.bean.core.catalog.Product ;

import java.util.List;
@Service
public class PurchaseItemAdminServiceImpl implements PurchaseItemAdminService {


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public PurchaseItem update(PurchaseItem t) {
        PurchaseItem loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{PurchaseItem.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public PurchaseItem findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public PurchaseItem findOrSave(PurchaseItem t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            PurchaseItem result = findByReferenceEntity(t);
            if (result == null) {
                return create(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<PurchaseItem> findAll() {
        return dao.findAll();
    }

    public List<PurchaseItem> findByCriteria(PurchaseItemCriteria criteria) {
        List<PurchaseItem> content = null;
        if (criteria != null) {
            PurchaseItemSpecification mySpecification = constructSpecification(criteria);
            if (criteria.isPeagable()) {
                Pageable pageable = PageRequest.of(0, criteria.getMaxResults());
                content = dao.findAll(mySpecification, pageable).getContent();
            } else {
                content = dao.findAll(mySpecification);
            }
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private PurchaseItemSpecification constructSpecification(PurchaseItemCriteria criteria) {
        PurchaseItemSpecification mySpecification =  (PurchaseItemSpecification) RefelexivityUtil.constructObjectUsingOneParam(PurchaseItemSpecification.class, criteria);
        return mySpecification;
    }

    public List<PurchaseItem> findPaginatedByCriteria(PurchaseItemCriteria criteria, int page, int pageSize, String order, String sortField) {
        PurchaseItemSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(PurchaseItemCriteria criteria) {
        PurchaseItemSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<PurchaseItem> findByProductId(Long id){
        return dao.findByProductId(id);
    }
    public int deleteByProductId(Long id){
        return dao.deleteByProductId(id);
    }
    public long countByProductCode(String code){
        return dao.countByProductCode(code);
    }
    public List<PurchaseItem> findByPurchaseId(Long id){
        return dao.findByPurchaseId(id);
    }
    public int deleteByPurchaseId(Long id){
        return dao.deleteByPurchaseId(id);
    }
    public long countByPurchaseReference(String reference){
        return dao.countByPurchaseReference(reference);
    }

	public boolean deleteById(Long id) {
        boolean condition = deleteByIdCheckCondition(id);
        if (condition) {
            dao.deleteById(id);
        }
        return condition;
    }

    public boolean deleteByIdCheckCondition(Long id) {
        return true;
    }

    public void deleteByIdIn(List<Long> ids) {
        //dao.deleteByIdIn(ids);
    }
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public int delete(PurchaseItem t) {
        int result = 0;
        if (t != null) {
            dao.deleteById(t.getId());
            result = 1;
        }
        return result;
    }



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<PurchaseItem> delete(List<PurchaseItem> list) {
		List<PurchaseItem> result = new ArrayList();
        if (list != null) {
            for (PurchaseItem t : list) {
                int count = delete(t);
				if(count == 0){
					result.add(t);
				}
            }
        }
		return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public PurchaseItem create(PurchaseItem t) {
        PurchaseItem loaded = findByReferenceEntity(t);
        PurchaseItem saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<PurchaseItem> create(List<PurchaseItem> ts) {
        List<PurchaseItem> result = new ArrayList<>();
        if (ts != null) {
            for (PurchaseItem t : ts) {
				PurchaseItem created = create(t);
                if (created == null)
                    result.add(t);
            }
        }
        return result;
    }

    public PurchaseItem findWithAssociatedLists(Long id){
        PurchaseItem result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<PurchaseItem> update(List<PurchaseItem> ts, boolean createIfNotExist) {
        List<PurchaseItem> result = new ArrayList<>();
        if (ts != null) {
            for (PurchaseItem t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    PurchaseItem loadedItem = dao.findById(t.getId()).orElse(null);
                    if (createIfNotExist && (t.getId() == null || loadedItem == null)) {
                        dao.save(t);
                    } else if (t.getId() != null && loadedItem != null) {
                        dao.save(t);
                    } else {
                        result.add(t);
                    }
                }
            }
        }
        return result;
    }





    public PurchaseItem findByReferenceEntity(PurchaseItem t) {
        return t == null || t.getId() == null ? null : findById(t.getId());
    }
    public void findOrSaveAssociatedObject(PurchaseItem t){
        if( t != null) {
            t.setProduct(productService.findOrSave(t.getProduct()));
            t.setPurchase(purchaseService.findOrSave(t.getPurchase()));
        }
    }



    public List<PurchaseItem> findAllOptimized() {
        return dao.findAll();
    }

    @Override
    public List<List<PurchaseItem>> getToBeSavedAndToBeDeleted(List<PurchaseItem> oldList, List<PurchaseItem> newList) {
        List<List<PurchaseItem>> result = new ArrayList<>();
        List<PurchaseItem> resultDelete = new ArrayList<>();
        List<PurchaseItem> resultUpdateOrSave = new ArrayList<>();
        if (ListUtil.isEmpty(oldList) && ListUtil.isNotEmpty(newList)) {
            resultUpdateOrSave.addAll(newList);
        } else if (ListUtil.isEmpty(newList) && ListUtil.isNotEmpty(oldList)) {
            resultDelete.addAll(oldList);
        } else if (ListUtil.isNotEmpty(newList) && ListUtil.isNotEmpty(oldList)) {
            for (int i = 0; i < oldList.size(); i++) {
                PurchaseItem myOld = oldList.get(i);
                PurchaseItem t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                PurchaseItem myNew = newList.get(i);
                PurchaseItem t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
        }
        result.add(resultUpdateOrSave);
        result.add(resultDelete);
        return result;
    }


    public List<PurchaseItem> importData(List<PurchaseItem> items) {
        List<PurchaseItem> list = new ArrayList<>();
        for (PurchaseItem t : items) {
            PurchaseItem founded = findByReferenceEntity(t);
            if (founded == null) {
                findOrSaveAssociatedObject(t);
                dao.save(t);
            } else {
                list.add(founded);
            }
        }
        return list;
    }

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }

    @Override
    public List<PurchaseItem> importExcel(MultipartFile file) {
        return null;
    }








    @Autowired
    private PurchaseAdminService purchaseService ;
    @Autowired
    private ProductAdminService productService ;


    private @Autowired PurchaseItemDao dao;

    public void setDao(PurchaseItemDao dao) {
        this.dao = dao;
    }
}
