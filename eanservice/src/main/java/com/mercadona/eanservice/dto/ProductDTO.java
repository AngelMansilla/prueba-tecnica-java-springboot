package com.mercadona.eanservice.dto;

import com.mercadona.eanservice.model.Product;

public class ProductDTO {

    private Long id;
    private String ean;
    private String providerName;
    private String destinationName;

    public ProductDTO(Long id, String ean, String providerName, String destinationName) {
        this.id = id;
        this.ean = ean;
        this.providerName = providerName;
        this.destinationName = destinationName;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    // Método de conveniencia para crear un ProductDTO a partir de un Product
    public static ProductDTO fromProduct(Product product) {
        return new ProductDTO(
            product.getId(),
            product.getEan(),
            product.getProvider().getName(),
            product.getDestination().getName()
        );
    }
}
