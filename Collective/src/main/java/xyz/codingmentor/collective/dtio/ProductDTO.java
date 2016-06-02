package xyz.codingmentor.collective.dtio;

import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ProductDTO implements Serializable {
    
    //@Digits(integer = 18, fraction = 0, message = "The number format is incorrect.")
//    @Min(value = 0, message = "The number format is incorrect.")
//    @NotNull(message = "This field has to be filled.")
    private Long price;

    private String type;

    public ProductDTO() {
    }

    public ProductDTO(Long price, String type) {
        this.price = price;
        this.type = type;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
