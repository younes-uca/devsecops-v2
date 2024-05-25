package ma.zs.stocky.service.impl.admin.crm;


import ma.zs.stocky.zynerator.exception.EntityNotFoundException;
import ma.zs.stocky.bean.core.crm.PaymentType;
import ma.zs.stocky.dao.criteria.core.crm.PaymentTypeCriteria;
import ma.zs.stocky.dao.facade.core.crm.PaymentTypeDao;
import ma.zs.stocky.dao.specification.core.crm.PaymentTypeSpecification;
import ma.zs.stocky.service.facade.admin.crm.PaymentTypeAdminService;
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


import java.util.List;
@Service
public class PaymentTypeAdminServiceImpl implements PaymentTypeAdminService {


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public PaymentType update(PaymentType t) {
        PaymentType loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{PaymentType.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public PaymentType findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public PaymentType findOrSave(PaymentType t) {
        if (t != null) {
            PaymentType result = findByReferenceEntity(t);
            if (result == null) {
                return create(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<PaymentType> findAll() {
        return dao.findAll();
    }

    public List<PaymentType> findByCriteria(PaymentTypeCriteria criteria) {
        List<PaymentType> content = null;
        if (criteria != null) {
            PaymentTypeSpecification mySpecification = constructSpecification(criteria);
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


    private PaymentTypeSpecification constructSpecification(PaymentTypeCriteria criteria) {
        PaymentTypeSpecification mySpecification =  (PaymentTypeSpecification) RefelexivityUtil.constructObjectUsingOneParam(PaymentTypeSpecification.class, criteria);
        return mySpecification;
    }

    public List<PaymentType> findPaginatedByCriteria(PaymentTypeCriteria criteria, int page, int pageSize, String order, String sortField) {
        PaymentTypeSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(PaymentTypeCriteria criteria) {
        PaymentTypeSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
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
    public int delete(PaymentType t) {
        int result = 0;
        if (t != null) {
            dao.deleteById(t.getId());
            result = 1;
        }
        return result;
    }



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<PaymentType> delete(List<PaymentType> list) {
		List<PaymentType> result = new ArrayList();
        if (list != null) {
            for (PaymentType t : list) {
                int count = delete(t);
				if(count == 0){
					result.add(t);
				}
            }
        }
		return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public PaymentType create(PaymentType t) {
        PaymentType loaded = findByReferenceEntity(t);
        PaymentType saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<PaymentType> create(List<PaymentType> ts) {
        List<PaymentType> result = new ArrayList<>();
        if (ts != null) {
            for (PaymentType t : ts) {
				PaymentType created = create(t);
                if (created == null)
                    result.add(t);
            }
        }
        return result;
    }

    public PaymentType findWithAssociatedLists(Long id){
        PaymentType result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<PaymentType> update(List<PaymentType> ts, boolean createIfNotExist) {
        List<PaymentType> result = new ArrayList<>();
        if (ts != null) {
            for (PaymentType t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    PaymentType loadedItem = dao.findById(t.getId()).orElse(null);
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





    public PaymentType findByReferenceEntity(PaymentType t){
        return t==null? null : dao.findByCode(t.getCode());
    }



    public List<PaymentType> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<PaymentType>> getToBeSavedAndToBeDeleted(List<PaymentType> oldList, List<PaymentType> newList) {
        List<List<PaymentType>> result = new ArrayList<>();
        List<PaymentType> resultDelete = new ArrayList<>();
        List<PaymentType> resultUpdateOrSave = new ArrayList<>();
        if (ListUtil.isEmpty(oldList) && ListUtil.isNotEmpty(newList)) {
            resultUpdateOrSave.addAll(newList);
        } else if (ListUtil.isEmpty(newList) && ListUtil.isNotEmpty(oldList)) {
            resultDelete.addAll(oldList);
        } else if (ListUtil.isNotEmpty(newList) && ListUtil.isNotEmpty(oldList)) {
            for (int i = 0; i < oldList.size(); i++) {
                PaymentType myOld = oldList.get(i);
                PaymentType t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                PaymentType myNew = newList.get(i);
                PaymentType t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
        }
        result.add(resultUpdateOrSave);
        result.add(resultDelete);
        return result;
    }


    public List<PaymentType> importData(List<PaymentType> items) {
        List<PaymentType> list = new ArrayList<>();
        for (PaymentType t : items) {
            PaymentType founded = findByReferenceEntity(t);
            if (founded == null) {
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
    public List<PaymentType> importExcel(MultipartFile file) {
        return null;
    }









    public PaymentTypeAdminServiceImpl(PaymentTypeDao dao) {
        this.dao = dao;
    }

    private PaymentTypeDao dao;
}
