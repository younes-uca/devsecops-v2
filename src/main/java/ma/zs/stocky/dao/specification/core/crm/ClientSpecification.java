package  ma.zs.stocky.dao.specification.core.crm;

import ma.zs.stocky.dao.criteria.core.crm.ClientCriteria;
import ma.zs.stocky.bean.core.crm.Client;
import ma.zs.stocky.zynerator.specification.AbstractSpecification;


public class ClientSpecification extends  AbstractSpecification<ClientCriteria, Client>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("description", criteria.getDescription(),criteria.getDescriptionLike());
    }

    public ClientSpecification(ClientCriteria criteria) {
        super(criteria);
    }

    public ClientSpecification(ClientCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
