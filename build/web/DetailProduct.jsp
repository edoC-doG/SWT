<%-- 
    Document   : DetailProduct
    Created on : Jun 13, 2021, 6:23:07 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Update Product</h1>
        <form method="Post" action="MainController">
            productID: <input type="text" name="productID" disabled value="${requestScope.productDetail.productID}" /> <br/>
            <input type="hidden" name="productID"  value="${requestScope.productDetail.productID}" /> <br/>
            nameProduct: <input type="text" name="nameProduct" value="${requestScope.productDetail.nameProduct}" /><br/>
            img  <img style="width: 100px; height: 100px" class="card-img-top" src="./image/${requestScope.productDetail.image}.jpeg"> 
            <input type="text" name="image" value="${requestScope.productDetail.image}" /><br/>
            quantity <input type="number" min="0" name="quantity" value="${requestScope.productDetail.quantity}" /><br/>
            price <input type="numer" min="0" name="price" value="${requestScope.productDetail.price}" /><br/>
            description  <textarea class="form-control" rows="3" id="des" name="description">${requestScope.productDetail.description}</textarea><br/>
            status<select name="status">
                <c:forEach var="item" items="${requestScope.listStatus}">
                    <option value="${item.statusName}" ${requestScope.productDetail.status.statusID eq item.statusID ? "selected" : ""}>${item.statusName}</option>
                </c:forEach>
            </select> <br/>
            category <select name="categoryID">
                <c:forEach var="item" items="${requestScope.listCategory}">
                    <option value="${item.categoryID}" ${requestScope.productDetail.category.categoryName}>${item.categoryName}</option>
                </c:forEach>
            </select><br/>
            date: <input type="date" name="date" value="${requestScope.productDetail.date}"/><br/>
            <input type="submit" value="Updated"  name="btnAction"/>
        </form>
    </body>
</html>
