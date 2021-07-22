<%-- 
    Document   : Insert
    Created on : Jun 13, 2021, 3:12:42 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert Page</title>
    </head>
    <body>
        <h1>Insert Page</h1>
        ${requestScope.error}
        ${requestScope.addFail}
        <form method="Post" action="MainController">
            productID <input type="text" name="productID" value="${requestScope.productID}" /><br/>
            <p style="color: red">${requestScope.error.productIDEmpty}</p>
            <p style="color: red">${requestScope.error.productIDDuplicate}</p>
            <p style="color: red">${requestScope.error.productIDFormat}</p>
            Name <input type="text" name="nameProduct" value="${requestScope.nameProduct}" /><br/>
            <p style="color: red">${requestScope.error.nameProductEmpty}</p>
            <p style="color: red">${requestScope.error.nameProductLength}</p>
            Image <input type="text" name="image" value="" /><br/>
            <p style="color: red">${requestScope.error.imageEmpty}</p>
            <p style="color: red">${requestScope.error.imageFormat}</p>
            Price <input type="number" name="price" value="${requestScope.price}" min="0"/><br/>
            <p style="color: red">${requestScope.error.priceEmpty}</p>
            Quantity <input type="number" name="quantity" value="${requestScope.quantity}" min="0"/><br/>
            <p style="color: red">${requestScope.error.quanityEmpty}</p>
            Category name <select name="categoryID">
                <c:forEach var="item" items="${requestScope.listCategory}">
                    <option value="${item.categoryID}" ${requestScope.categoryID eq item.categoryID ? "selected" : ""}>${item.categoryName}</option>
                </c:forEach>
            </select><br/>
            <label for="des">description</label><br/>
            <textarea class="form-control" rows="3" id="des" name="description">${requestScope.description}</textarea><br/>
             <p style="color: red">${requestScope.error.descriptionEmpty}</p>
             <p style="color: red">${requestScope.error.descriptionLength}</p>
            <input type="submit" value="Submit Insert" name="btnAction"/>
        </form>
    </body>
</html>
