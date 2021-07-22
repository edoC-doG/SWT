/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pnj.products;

import pnj.categories.CategoriesDTO;
import pnj.status.StatusDTO;
import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Admin
 */
public class ProductDTO implements Serializable{

    private String productID, nameProduct, image, description;
    private StatusDTO status;
    private CategoriesDTO category;
    private int quantity;
    private float price;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getproductID() {
        return productID;
    }

    public void setproductID(String productID) {
        this.productID = productID;
    }

    public String getnameProduct() {
        return nameProduct;
    }

    public void setnameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    public CategoriesDTO getCategory() {
        return category;
    }

    public void setCategory(CategoriesDTO category) {
        this.category = category;
    }

    public ProductDTO(String productID, String nameProduct, String image, 
            String description, int quantity, 
            StatusDTO status, CategoriesDTO category, float price, Date date) {
        this.productID = productID;
        this.nameProduct = nameProduct;
        this.image = image;
        this.description = description;
        this.quantity = quantity;
        this.status = status;
        this.category = category;
        this.price = price;
        this.date = date;
    }

    public ProductDTO() {
    }

    @Override
    public String toString() {
        return "ProductDTO{" + "productID=" + productID + ", nameProduct=" + nameProduct + ", image=" + image + ", description=" + description + ", status=" + status + ", category=" + category + ", quantity=" + quantity + ", price=" + price + ", date=" + date + '}';
    }

    
}
