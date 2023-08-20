package com.ecommerce.ShopZenbe.models.product;

import com.ecommerce.ShopZenbe.models.product.dto.ProductDTO;
import com.ecommerce.ShopZenbe.models.product.dto.UpdateProductDTO;
import com.ecommerce.ShopZenbe.models.productCategory.ProductCategory;
import com.ecommerce.ShopZenbe.models.productCategory.ProductCategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        System.out.println('all product=============', products);

        return products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    public ProductDTO getProduct(UUID productId) {
        Product product = productRepository.findById(productId).orElse(null);
        return modelMapper.map(product, ProductDTO.class);
    }

    public ProductDTO addProduct(ProductDTO productDto){
        ProductCategory productCategory = productCategoryRepository.findByCategoryName(productDto.getCategory().name());

        Product savedProduct = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .sku(productDto.getSku())
                .image(productDto.getImage())
                .active(productDto.getActive())
                .stock(productDto.getStock())
                .category(productCategory)
                .build();

        Product product = productRepository.save(savedProduct);
        return modelMapper.map(product, ProductDTO.class);
    }

    public ProductDTO updateProduct(UUID productId, UpdateProductDTO dto){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product Not Found", new Throwable("Product does not exist in the system!")
                ));

        boolean hasChanged = false;

        if (dto.name() != null && !product.getName().equals(dto.name())) {
            product.setName(dto.name());
            hasChanged = true;
        }

        if (dto.description() != null && !product.getDescription().equals(dto.description())) {
            product.setDescription(dto.description());
            hasChanged = true;
        }

        if (dto.price() != null && !product.getPrice().equals(dto.price())) {
            product.setPrice(dto.price());
            hasChanged = true;
        }

        if (dto.sku() != null && !product.getSku().equals(dto.sku())) {
            product.setSku(dto.sku());
            hasChanged = true;
        }

        if (dto.image() != null && !product.getImage().equals(dto.image())) {
            product.setImage(dto.image());
            hasChanged = true;
        }

        if (dto.active() != null && !product.getActive().equals(dto.active())) {
            product.setActive(dto.active());
            hasChanged = true;
        }

        if (dto.stock() != null && product.getStock().equals(dto.stock())) {
            product.setStock(dto.stock());
            hasChanged = true;
        }

        if (hasChanged) {
            productRepository.save(product);
        }

        return modelMapper.map(product, ProductDTO.class);
    }
}
