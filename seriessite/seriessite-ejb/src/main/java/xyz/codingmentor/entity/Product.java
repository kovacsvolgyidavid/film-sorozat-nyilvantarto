package xyz.codingmentor.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import xyz.codingmentor.collective.enums.Type;

@Entity
public class Product implements Serializable{
    @Id
    @GeneratedValue
    private Long id;
    
    @Min(value = 0, message = "The minimum price is 0.")
    @Max(value = 10000000, message = "The maximum price is 10000000.")
    private Long price;
    
    @Enumerated(EnumType.STRING)
    private Type type;

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    } 
}
