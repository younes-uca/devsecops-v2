package ma.zs.stocky.dao.facade.core.crm;

import org.springframework.data.jpa.repository.Query;
import ma.zs.stocky.zynerator.repository.AbstractRepository;
import ma.zs.stocky.bean.core.crm.PaymentType;
import org.springframework.stereotype.Repository;
import ma.zs.stocky.bean.core.crm.PaymentType;
import java.util.List;


@Repository
public interface PaymentTypeDao extends AbstractRepository<PaymentType,Long>  {
    PaymentType findByCode(String code);
    int deleteByCode(String code);


    @Query("SELECT NEW PaymentType(item.id,item.reference) FROM PaymentType item")
    List<PaymentType> findAllOptimized();

}
