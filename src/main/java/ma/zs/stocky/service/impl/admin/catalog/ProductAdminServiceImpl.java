package ma.zs.stocky.service.impl.admin.catalog;


import ma.zs.stocky.zynerator.exception.EntityNotFoundException;
import ma.zs.stocky.bean.core.catalog.Product;
import ma.zs.stocky.dao.criteria.core.catalog.ProductCriteria;
import ma.zs.stocky.dao.facade.core.catalog.ProductDao;
import ma.zs.stocky.dao.specification.core.catalog.ProductSpecification;
import ma.zs.stocky.service.facade.admin.catalog.ProductAdminService;
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
public class ProductAdminServiceImpl implements ProductAdminService {


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Product update(Product t) {
        Product loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Product.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Product findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Product findOrSave(Product t) {
        if (t != null) {
            Product result = findByReferenceEntity(t);
            if (result == null) {
                return create(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Product> findAll() {
        return dao.findAll();
    }

    public List<Product> findByCriteria(ProductCriteria criteria) {
        List<Product> content = null;
        if (criteria != null) {
            ProductSpecification mySpecification = constructSpecification(criteria);
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


    private ProductSpecification constructSpecification(ProductCriteria criteria) {
        ProductSpecification mySpecification =  (ProductSpecification) RefelexivityUtil.constructObjectUsingOneParam(ProductSpecification.class, criteria);
        return mySpecification;
    }

    public List<Product> findPaginatedByCriteria(ProductCriteria criteria, int page, int pageSize, String order, String sortField) {
        ProductSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(ProductCriteria criteria) {
        ProductSpecification mySpecification = constructSpecification(criteria);
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
    public int delete(Product t) {
        int result = 0;
        if (t != null) {
            dao.deleteById(t.getId());
            result = 1;
        }
        return result;
    }



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Product> delete(List<Product> list) {
		List<Product> result = new ArrayList();
        if (list != null) {
            for (Product t : list) {
                int count = delete(t);
				if(count == 0){
					result.add(t);
				}
            }
        }
		return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Product create(Product t) {
        Product loaded = findByReferenceEntity(t);
        Product saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Product> create(List<Product> ts) {
        List<Product> result = new ArrayList<>();
        if (ts != null) {
            for (Product t : ts) {
				Product created = create(t);
                if (created == null)
                    result.add(t);
            }
        }
        return result;
    }

    public Product findWithAssociatedLists(Long id){
        Product result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Product> update(List<Product> ts, boolean createIfNotExist) {
        List<Product> result = new ArrayList<>();
        if (ts != null) {
            for (Product t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Product loadedItem = dao.findById(t.getId()).orElse(null);
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





    public Product findByReferenceEntity(Product t){
        return t==null? null : dao.findByCode(t.getCode());
    }



    public List<Product> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Product>> getToBeSavedAndToBeDeleted(List<Product> oldList, List<Product> newList) {
        List<List<Product>> result = new ArrayList<>();
        List<Product> resultDelete = new ArrayList<>();
        List<Product> resultUpdateOrSave = new ArrayList<>();
        if (ListUtil.isEmpty(oldList) && ListUtil.isNotEmpty(newList)) {
            resultUpdateOrSave.addAll(newList);
        } else if (ListUtil.isEmpty(newList) && ListUtil.isNotEmpty(oldList)) {
            resultDelete.addAll(oldList);
        } else if (ListUtil.isNotEmpty(newList) && ListUtil.isNotEmpty(oldList)) {
            for (int i = 0; i < oldList.size(); i++) {
                Product myOld = oldList.get(i);
                Product t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Product myNew = newList.get(i);
                Product t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
        }
        result.add(resultUpdateOrSave);
        result.add(resultDelete);
        return result;
    }


    public List<Product> importData(List<Product> items) {
        List<Product> list = new ArrayList<>();
        for (Product t : items) {
            Product founded = findByReferenceEntity(t);
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
    public List<Product> importExcel(MultipartFile file) {
        return null;
    }









    public ProductAdminServiceImpl(ProductDao dao) {
        this.dao = dao;
    }

    private ProductDao dao;
}
