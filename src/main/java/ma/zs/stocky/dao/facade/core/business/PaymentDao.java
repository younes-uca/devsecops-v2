package ma.zs.stocky.dao.facade.core.business;

import org.springframework.data.jpa.repository.Query;
import ma.zs.stocky.zynerator.repository.AbstractRepository;
import ma.zs.stocky.bean.core.business.Payment;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PaymentDao extends AbstractRepository<Payment,Long>  {

    List<Payment> findByPurchaseId(Long id);
    int deleteByPurchaseId(Long id);
    long countByPurchaseReference(String reference);
    List<Payment> findByPaymentTypeId(Long id);
    int deleteByPaymentTypeId(Long id);
    long countByPaymentTypeCode(String code);

    @Query("SELECT NEW Payment(item.id,item.reference) FROM Payment item")
    List<Payment> findAllOptimized();

}
