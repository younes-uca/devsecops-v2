package ma.zs.stocky.bean.core.crm;

import java.util.Objects;







import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zs.stocky.zynerator.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "client")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="client_seq",sequenceName="client_seq",allocationSize=1, initialValue = 1)
public class Client  extends BaseEntity     {

    private Long id;



    @Column(length = 500)
    private String description;



    public Client(){
        super();
    }

    public Client(Long id){
        this.id = id;
    }

    public Client(Long id,String description){
        this.id = id;
        this.description = description ;
    }
    public Client(String description){
        this.description = description ;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="client_seq")
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    @Transient
    public String getLabel() {
        label = description;
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id != null && id.equals(client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

