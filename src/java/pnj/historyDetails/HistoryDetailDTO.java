/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pnj.historyDetails;

import pnj.products.ProductDTO;
import pnj.histories.HistoryDTO;
import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class HistoryDetailDTO implements Serializable{
    private HistoryDTO cart;
    private ProductDTO product;
    private int quantity;

    @Override
    public String toString() {
        return "CartDetailDTO{" + "cart=" + cart + ", product=" + product + ", quantity=" + quantity + '}';
    }

    public HistoryDTO getCart() {
        return cart;
    }

    public void setCart(HistoryDTO cart) {
        this.cart = cart;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public HistoryDetailDTO(HistoryDTO cart, ProductDTO product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }
    
}
