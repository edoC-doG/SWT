/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pnj.products;

/**
 *
 * @author Admin
 */
public class ProductError {

    private String productIDEmpty, productIDDuplicate, productIDFormat;
    private String nameProductEmpty, nameProductLength;
    private String imageEmpty, imageFormat;
    private String descriptionEmpty, descriptionLength;
    private String priceEmpty, priceFormat;
    private String quanityEmpty;


    public ProductError(String bookIDEmpty, String bookIDDuplicate, String bookIDFormat, String titleEmpty, String titleLength, String imageEmpty, String imageFormat, String descriptionEmpty, String descriptionLength, String priceEmpty, String priceFormat, String quanityEmpty, String authorEmpty, String authorLength) {
        this.productIDEmpty = bookIDEmpty;
        this.productIDDuplicate = bookIDDuplicate;
        this.productIDFormat = bookIDFormat;
        this.nameProductEmpty = titleEmpty;
        this.nameProductLength = titleLength;
        this.imageEmpty = imageEmpty;
        this.imageFormat = imageFormat;
        this.descriptionEmpty = descriptionEmpty;
        this.descriptionLength = descriptionLength;
        this.priceEmpty = priceEmpty;
        this.priceFormat = priceFormat;
        this.quanityEmpty = quanityEmpty;
    
    }

    @Override
    public String toString() {
        return "ProductError{" + "productIDEmpty=" + productIDEmpty + ", productIDDuplicate=" + productIDDuplicate + ", productIDFormat=" + productIDFormat + ", nameProductEmpty=" + nameProductEmpty + ", nameProductLength=" + nameProductLength + ", imageEmpty=" + imageEmpty + ", imageFormat=" + imageFormat + ", descriptionEmpty=" + descriptionEmpty + ", descriptionLength=" + descriptionLength + ", priceEmpty=" + priceEmpty + ", priceFormat=" + priceFormat + ", quanityEmpty=" + quanityEmpty +  '}';
    }

    public String getProductIDEmpty() {
        return productIDEmpty;
    }

    public void setProductIDEmpty(String bookIDEmpty) {
        this.productIDEmpty = bookIDEmpty;
    }

    public String getProductIDDuplicate() {
        return productIDDuplicate;
    }

    public void setProductIDDuplicate(String bookIDDuplicate) {
        this.productIDDuplicate = bookIDDuplicate;
    }

    public String getProductIDFormat() {
        return productIDFormat;
    }

    public void setProductIDFormat(String bookIDFormat) {
        this.productIDFormat = bookIDFormat;
    }

    public String getnameProductEmpty() {
        return nameProductEmpty;
    }

    public void setnameProductEmpty(String titleEmpty) {
        this.nameProductEmpty = titleEmpty;
    }

    public String getnameProductLength() {
        return nameProductLength;
    }

    public void setnameProductLength(String titleLength) {
        this.nameProductLength = titleLength;
    }

    public String getImageEmpty() {
        return imageEmpty;
    }

    public void setImageEmpty(String imageEmpty) {
        this.imageEmpty = imageEmpty;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public void setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
    }

    public String getDescriptionEmpty() {
        return descriptionEmpty;
    }

    public void setDescriptionEmpty(String descriptionEmpty) {
        this.descriptionEmpty = descriptionEmpty;
    }

    public String getDescriptionLength() {
        return descriptionLength;
    }

    public void setDescriptionLength(String descriptionLength) {
        this.descriptionLength = descriptionLength;
    }

    public String getPriceEmpty() {
        return priceEmpty;
    }

    public void setPriceEmpty(String priceEmpty) {
        this.priceEmpty = priceEmpty;
    }

    public String getPriceFormat() {
        return priceFormat;
    }

    public void setPriceFormat(String priceFormat) {
        this.priceFormat = priceFormat;
    }

    public String getQuanityEmpty() {
        return quanityEmpty;
    }

    public void setQuanityEmpty(String quanityEmpty) {
        this.quanityEmpty = quanityEmpty;
    }

}
