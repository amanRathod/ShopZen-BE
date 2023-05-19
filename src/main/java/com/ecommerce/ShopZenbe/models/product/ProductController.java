package com.ecommerce.ShopZenbe.models.product;

import com.ecommerce.ShopZenbe.common.utils.ApiResponse;
import com.ecommerce.ShopZenbe.models.product.dto.ProductDTO;
import com.ecommerce.ShopZenbe.models.product.dto.UpdateProductDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();

        ApiResponse<List<ProductDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), "", products,"products");
        return ResponseEntity.ok(response);
    }

    @GetMapping("{productId}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProduct(@PathVariable("productId") UUID productId) {
        ProductDTO product = productService.getProduct(productId);

        ApiResponse<ProductDTO> response = new ApiResponse<>(200, "", product, "product");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<ProductDTO>> addProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO product = productService.addProduct(productDTO);

        ApiResponse<ProductDTO> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Product created successfully!", product, "product");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(@PathVariable("productId") UUID productId, @Valid @RequestBody UpdateProductDTO productDTO) {
        ProductDTO product = productService.updateProduct(productId, productDTO);

        ApiResponse<ProductDTO> response = new ApiResponse<>(200, "Product updated successfully!", product, "product");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteProduct() {
        return "Delete product";
    }
}
