package  ma.zs.stocky.dao.specification.core.business;

import ma.zs.stocky.dao.criteria.core.business.PaymentCriteria;
import ma.zs.stocky.bean.core.business.Payment;
import ma.zs.stocky.zynerator.specification.AbstractSpecification;


public class PaymentSpecification extends  AbstractSpecification<PaymentCriteria, Payment>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("reference", criteria.getReference(),criteria.getReferenceLike());
        addPredicateBigDecimal("amount", criteria.getAmount(), criteria.getAmountMin(), criteria.getAmountMax());
        addPredicateFk("purchase","id", criteria.getPurchase()==null?null:criteria.getPurchase().getId());
        addPredicateFk("purchase","id", criteria.getPurchases());
        addPredicateFk("purchase","reference", criteria.getPurchase()==null?null:criteria.getPurchase().getReference());
        addPredicateFk("paymentType","id", criteria.getPaymentType()==null?null:criteria.getPaymentType().getId());
        addPredicateFk("paymentType","id", criteria.getPaymentTypes());
        addPredicateFk("paymentType","code", criteria.getPaymentType()==null?null:criteria.getPaymentType().getCode());
    }

    public PaymentSpecification(PaymentCriteria criteria) {
        super(criteria);
    }

    public PaymentSpecification(PaymentCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
