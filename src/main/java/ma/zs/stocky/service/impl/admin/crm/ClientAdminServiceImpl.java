package ma.zs.stocky.service.impl.admin.crm;


import ma.zs.stocky.zynerator.exception.EntityNotFoundException;
import ma.zs.stocky.bean.core.crm.Client;
import ma.zs.stocky.dao.criteria.core.crm.ClientCriteria;
import ma.zs.stocky.dao.facade.core.crm.ClientDao;
import ma.zs.stocky.dao.specification.core.crm.ClientSpecification;
import ma.zs.stocky.service.facade.admin.crm.ClientAdminService;
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
public class ClientAdminServiceImpl implements ClientAdminService {


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Client update(Client t) {
        Client loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Client.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Client findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Client findOrSave(Client t) {
        if (t != null) {
            Client result = findByReferenceEntity(t);
            if (result == null) {
                return create(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Client> findAll() {
        return dao.findAll();
    }

    public List<Client> findByCriteria(ClientCriteria criteria) {
        List<Client> content = null;
        if (criteria != null) {
            ClientSpecification mySpecification = constructSpecification(criteria);
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


    private ClientSpecification constructSpecification(ClientCriteria criteria) {
        ClientSpecification mySpecification =  (ClientSpecification) RefelexivityUtil.constructObjectUsingOneParam(ClientSpecification.class, criteria);
        return mySpecification;
    }

    public List<Client> findPaginatedByCriteria(ClientCriteria criteria, int page, int pageSize, String order, String sortField) {
        ClientSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(ClientCriteria criteria) {
        ClientSpecification mySpecification = constructSpecification(criteria);
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
    public int delete(Client t) {
        int result = 0;
        if (t != null) {
            dao.deleteById(t.getId());
            result = 1;
        }
        return result;
    }



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Client> delete(List<Client> list) {
		List<Client> result = new ArrayList();
        if (list != null) {
            for (Client t : list) {
                int count = delete(t);
				if(count == 0){
					result.add(t);
				}
            }
        }
		return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Client create(Client t) {
        Client loaded = findByReferenceEntity(t);
        Client saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Client> create(List<Client> ts) {
        List<Client> result = new ArrayList<>();
        if (ts != null) {
            for (Client t : ts) {
				Client created = create(t);
                if (created == null)
                    result.add(t);
            }
        }
        return result;
    }

    public Client findWithAssociatedLists(Long id){
        Client result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Client> update(List<Client> ts, boolean createIfNotExist) {
        List<Client> result = new ArrayList<>();
        if (ts != null) {
            for (Client t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Client loadedItem = dao.findById(t.getId()).orElse(null);
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





    public Client findByReferenceEntity(Client t) {
        return t == null || t.getId() == null ? null : findById(t.getId());
    }



    public List<Client> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Client>> getToBeSavedAndToBeDeleted(List<Client> oldList, List<Client> newList) {
        List<List<Client>> result = new ArrayList<>();
        List<Client> resultDelete = new ArrayList<>();
        List<Client> resultUpdateOrSave = new ArrayList<>();
        if (ListUtil.isEmpty(oldList) && ListUtil.isNotEmpty(newList)) {
            resultUpdateOrSave.addAll(newList);
        } else if (ListUtil.isEmpty(newList) && ListUtil.isNotEmpty(oldList)) {
            resultDelete.addAll(oldList);
        } else if (ListUtil.isNotEmpty(newList) && ListUtil.isNotEmpty(oldList)) {
            for (int i = 0; i < oldList.size(); i++) {
                Client myOld = oldList.get(i);
                Client t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Client myNew = newList.get(i);
                Client t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
        }
        result.add(resultUpdateOrSave);
        result.add(resultDelete);
        return result;
    }


    public List<Client> importData(List<Client> items) {
        List<Client> list = new ArrayList<>();
        for (Client t : items) {
            Client founded = findByReferenceEntity(t);
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
    public List<Client> importExcel(MultipartFile file) {
        return null;
    }









    public ClientAdminServiceImpl(ClientDao dao) {
        this.dao = dao;
    }

    private ClientDao dao;
}
