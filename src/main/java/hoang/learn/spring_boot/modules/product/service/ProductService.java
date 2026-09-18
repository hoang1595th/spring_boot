package hoang.learn.spring_boot.modules.product.service;

import hoang.learn.spring_boot.common.exception.ResourceNotFoundException;
import hoang.learn.spring_boot.modules.product.entity.Product;
import hoang.learn.spring_boot.modules.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProductService {
    @Autowired private ProductRepository productRepository;

    @Transactional
    public Product createProduct(Product product) { return productRepository.save(product); }

    public List<Product> getAllProducts() { return productRepository.findAll(); }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    @Transactional
    public Product updateProduct(Long id, Product productDetails) {
        Product existingProduct = getProductById(id); // Sẽ ném lỗi nếu không tìm thấy
        existingProduct.setName(productDetails.getName());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setStockQuantity(productDetails.getStockQuantity());
        return productRepository.save(existingProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product", "id", id);
        }
        productRepository.deleteById(id);
    }
}
