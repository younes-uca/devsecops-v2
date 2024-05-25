package ma.zs.stocky.bean.core.business;

import java.util.Objects;
import java.util.List;

import java.time.LocalDateTime;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;


import ma.zs.stocky.bean.core.crm.PaymentType;
import ma.zs.stocky.bean.core.catalog.Product;
import ma.zs.stocky.bean.core.crm.Client;


import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zs.stocky.zynerator.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;
import java.math.BigDecimal;

@Entity
@Table(name = "purchase")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="purchase_seq",sequenceName="purchase_seq",allocationSize=1, initialValue = 1)
public class Purchase  extends BaseEntity     {

    private Long id;



    @Column(length = 500)
    private String reference;

    private LocalDateTime purchaseDate ;

    private BigDecimal total = BigDecimal.ZERO;

    @Column(length = 500)
    private String image;

    private Client client ;

    private List<PurchaseItem> purchaseItems ;
    private List<Payment> payments ;

    public Purchase(){
        super();
    }

    public Purchase(Long id){
        this.id = id;
    }

    public Purchase(Long id,String reference){
        this.id = id;
        this.reference = reference ;
    }
    public Purchase(String reference){
        this.reference = reference ;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="purchase_seq")
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getReference(){
        return this.reference;
    }
    public void setReference(String reference){
        this.reference = reference;
    }
    public LocalDateTime getPurchaseDate(){
        return this.purchaseDate;
    }
    public void setPurchaseDate(LocalDateTime purchaseDate){
        this.purchaseDate = purchaseDate;
    }
    public BigDecimal getTotal(){
        return this.total;
    }
    public void setTotal(BigDecimal total){
        this.total = total;
    }
    public String getImage(){
        return this.image;
    }
    public void setImage(String image){
        this.image = image;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client")
    public Client getClient(){
        return this.client;
    }
    public void setClient(Client client){
        this.client = client;
    }
    @OneToMany(mappedBy = "purchase")
    public List<PurchaseItem> getPurchaseItems(){
        return this.purchaseItems;
    }

    public void setPurchaseItems(List<PurchaseItem> purchaseItems){
        this.purchaseItems = purchaseItems;
    }
    @OneToMany(mappedBy = "purchase")
    public List<Payment> getPayments(){
        return this.payments;
    }

    public void setPayments(List<Payment> payments){
        this.payments = payments;
    }

    @Transient
    public String getLabel() {
        label = reference;
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return id != null && id.equals(purchase.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

