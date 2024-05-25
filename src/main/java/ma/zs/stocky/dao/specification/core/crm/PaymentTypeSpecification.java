package  ma.zs.stocky.dao.specification.core.crm;

import ma.zs.stocky.dao.criteria.core.crm.PaymentTypeCriteria;
import ma.zs.stocky.bean.core.crm.PaymentType;
import ma.zs.stocky.zynerator.specification.AbstractSpecification;


public class PaymentTypeSpecification extends  AbstractSpecification<PaymentTypeCriteria, PaymentType>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("reference", criteria.getReference(),criteria.getReferenceLike());
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
    }

    public PaymentTypeSpecification(PaymentTypeCriteria criteria) {
        super(criteria);
    }

    public PaymentTypeSpecification(PaymentTypeCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
