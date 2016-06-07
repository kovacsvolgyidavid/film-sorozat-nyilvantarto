package xyz.codingmentor.collective.dto;

import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class ProductDTO implements Serializable {
    
    @Min(value = 0, message = "The minimum price is 0.")
    @Max(value = 10000000, message = "The maximum price is 10000000.")
    private long price;

    private String type;

    public ProductDTO() {
    }

    public ProductDTO(Long price, String type) {
        this.price = price;
        this.type = type;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
