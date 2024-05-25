package ma.zs.stocky.service.impl.admin.business;


import ma.zs.stocky.zynerator.exception.EntityNotFoundException;
import ma.zs.stocky.bean.core.business.Payment;
import ma.zs.stocky.dao.criteria.core.business.PaymentCriteria;
import ma.zs.stocky.dao.facade.core.business.PaymentDao;
import ma.zs.stocky.dao.specification.core.business.PaymentSpecification;
import ma.zs.stocky.service.facade.admin.business.PaymentAdminService;
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
import ma.zs.stocky.service.facade.admin.crm.PaymentTypeAdminService ;
import ma.zs.stocky.bean.core.crm.PaymentType ;

import java.util.List;
@Service
public class PaymentAdminServiceImpl implements PaymentAdminService {


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Payment update(Payment t) {
        Payment loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Payment.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Payment findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Payment findOrSave(Payment t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            Payment result = findByReferenceEntity(t);
            if (result == null) {
                return create(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Payment> findAll() {
        return dao.findAll();
    }

    public List<Payment> findByCriteria(PaymentCriteria criteria) {
        List<Payment> content = null;
        if (criteria != null) {
            PaymentSpecification mySpecification = constructSpecification(criteria);
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


    private PaymentSpecification constructSpecification(PaymentCriteria criteria) {
        PaymentSpecification mySpecification =  (PaymentSpecification) RefelexivityUtil.constructObjectUsingOneParam(PaymentSpecification.class, criteria);
        return mySpecification;
    }

    public List<Payment> findPaginatedByCriteria(PaymentCriteria criteria, int page, int pageSize, String order, String sortField) {
        PaymentSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(PaymentCriteria criteria) {
        PaymentSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<Payment> findByPurchaseId(Long id){
        return dao.findByPurchaseId(id);
    }
    public int deleteByPurchaseId(Long id){
        return dao.deleteByPurchaseId(id);
    }
    public long countByPurchaseReference(String reference){
        return dao.countByPurchaseReference(reference);
    }
    public List<Payment> findByPaymentTypeId(Long id){
        return dao.findByPaymentTypeId(id);
    }
    public int deleteByPaymentTypeId(Long id){
        return dao.deleteByPaymentTypeId(id);
    }
    public long countByPaymentTypeCode(String code){
        return dao.countByPaymentTypeCode(code);
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
    public int delete(Payment t) {
        int result = 0;
        if (t != null) {
            dao.deleteById(t.getId());
            result = 1;
        }
        return result;
    }



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Payment> delete(List<Payment> list) {
		List<Payment> result = new ArrayList();
        if (list != null) {
            for (Payment t : list) {
                int count = delete(t);
				if(count == 0){
					result.add(t);
				}
            }
        }
		return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Payment create(Payment t) {
        Payment loaded = findByReferenceEntity(t);
        Payment saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Payment> create(List<Payment> ts) {
        List<Payment> result = new ArrayList<>();
        if (ts != null) {
            for (Payment t : ts) {
				Payment created = create(t);
                if (created == null)
                    result.add(t);
            }
        }
        return result;
    }

    public Payment findWithAssociatedLists(Long id){
        Payment result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Payment> update(List<Payment> ts, boolean createIfNotExist) {
        List<Payment> result = new ArrayList<>();
        if (ts != null) {
            for (Payment t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Payment loadedItem = dao.findById(t.getId()).orElse(null);
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





    public Payment findByReferenceEntity(Payment t) {
        return t == null || t.getId() == null ? null : findById(t.getId());
    }
    public void findOrSaveAssociatedObject(Payment t){
        if( t != null) {
            t.setPurchase(purchaseService.findOrSave(t.getPurchase()));
            t.setPaymentType(paymentTypeService.findOrSave(t.getPaymentType()));
        }
    }



    public List<Payment> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Payment>> getToBeSavedAndToBeDeleted(List<Payment> oldList, List<Payment> newList) {
        List<List<Payment>> result = new ArrayList<>();
        List<Payment> resultDelete = new ArrayList<>();
        List<Payment> resultUpdateOrSave = new ArrayList<>();
        if (ListUtil.isEmpty(oldList) && ListUtil.isNotEmpty(newList)) {
            resultUpdateOrSave.addAll(newList);
        } else if (ListUtil.isEmpty(newList) && ListUtil.isNotEmpty(oldList)) {
            resultDelete.addAll(oldList);
        } else if (ListUtil.isNotEmpty(newList) && ListUtil.isNotEmpty(oldList)) {
            for (int i = 0; i < oldList.size(); i++) {
                Payment myOld = oldList.get(i);
                Payment t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Payment myNew = newList.get(i);
                Payment t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
        }
        result.add(resultUpdateOrSave);
        result.add(resultDelete);
        return result;
    }


    public List<Payment> importData(List<Payment> items) {
        List<Payment> list = new ArrayList<>();
        for (Payment t : items) {
            Payment founded = findByReferenceEntity(t);
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
    public List<Payment> importExcel(MultipartFile file) {
        return null;
    }








    @Autowired
    private PurchaseAdminService purchaseService ;
    @Autowired
    private PaymentTypeAdminService paymentTypeService ;

    public PaymentAdminServiceImpl(PaymentDao dao) {
        this.dao = dao;
    }

    private PaymentDao dao;
}
