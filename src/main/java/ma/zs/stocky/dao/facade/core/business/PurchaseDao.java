package ma.zs.stocky.dao.facade.core.business;

import org.springframework.data.jpa.repository.Query;
import ma.zs.stocky.zynerator.repository.AbstractRepository;
import ma.zs.stocky.bean.core.business.Purchase;
import org.springframework.stereotype.Repository;
import ma.zs.stocky.bean.core.business.Purchase;
import java.util.List;


@Repository
public interface PurchaseDao extends AbstractRepository<Purchase,Long>  {
    Purchase findByReference(String reference);
    int deleteByReference(String reference);

    List<Purchase> findByClientId(Long id);
    int deleteByClientId(Long id);
    long countByClientId(Long id);

    @Query("SELECT NEW Purchase(item.id,item.reference) FROM Purchase item")
    List<Purchase> findAllOptimized();

}
