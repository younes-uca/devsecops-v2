package ma.zs.stocky.dao.facade.core.crm;

import org.springframework.data.jpa.repository.Query;
import ma.zs.stocky.zynerator.repository.AbstractRepository;
import ma.zs.stocky.bean.core.crm.Client;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ClientDao extends AbstractRepository<Client,Long>  {


    @Query("SELECT NEW Client(item.id,item.description) FROM Client item")
    List<Client> findAllOptimized();

}
