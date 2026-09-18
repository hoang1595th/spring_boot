package hoang.learn.spring_boot.modules.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private Double price;
    private int stockQuantity;

    // Constructors, Getters, Setters (Lombok có thể giúp rút gọn)
    public Product() {}
    public Product(String name, Double price, int stockQuantity) {
        this.name = name; this.price = price; this.stockQuantity = stockQuantity;
    }

}