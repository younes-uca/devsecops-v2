package  ma.zs.stocky.dao.specification.core.business;

import ma.zs.stocky.dao.criteria.core.business.PurchaseCriteria;
import ma.zs.stocky.bean.core.business.Purchase;
import ma.zs.stocky.zynerator.specification.AbstractSpecification;


public class PurchaseSpecification extends  AbstractSpecification<PurchaseCriteria, Purchase>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("reference", criteria.getReference(),criteria.getReferenceLike());
        addPredicate("purchaseDate", criteria.getPurchaseDate(), criteria.getPurchaseDateFrom(), criteria.getPurchaseDateTo());
        addPredicateBigDecimal("total", criteria.getTotal(), criteria.getTotalMin(), criteria.getTotalMax());
        addPredicate("image", criteria.getImage(),criteria.getImageLike());
        addPredicateFk("client","id", criteria.getClient()==null?null:criteria.getClient().getId());
        addPredicateFk("client","id", criteria.getClients());
    }

    public PurchaseSpecification(PurchaseCriteria criteria) {
        super(criteria);
    }

    public PurchaseSpecification(PurchaseCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
