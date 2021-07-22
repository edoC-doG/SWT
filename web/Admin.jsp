<%-- 
    Document   : Admin
    Created on : Jun 12, 2021, 5:21:02 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <title>ADMIN Page</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <a class="navbar-brand" href="#">Navbar</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                    </li>
                    
                </ul>
                <c:if test="${not empty sessionScope.account}">
                    <div style="display: flex;align-items: center">
                        <h1 style="color: white">Hello, ${sessionScope.account.userName}</h1>
                        <a class="btn btn-success"  href="MainController?btnAction=Logout">logout</a>
                    </div>
                </c:if>
            </div>
        </nav>
        <h1  style="text-align: center; color: green">${requestScope.messSuccess}</h1>
        <h1 style="text-align: center"> PNJ Management</h1>
        <h1 style="text-align: center">${requestScope.addSuccess}</h1>
        <a class="btn btn-danger" href="MainController?btnAction=Insert">Add new Products</a>
        <a class="btn btn-danger" href="MainController?btnAction=ViewDiscount">View Discount</a>
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <table  style="margin-inline: auto; text-align: center; border: none">
                        <thead>
                            <tr>
                                <th>productID</th>
                                <th>nameProduct</th>
                                <th>image</th>
                                <th>description</th>                       
                                <th>CategoryName</th>
                                <th>Status</th>
                                <th>Quantity</th>
                                <th>Date</th>
                                <th>Delete</th>
                                <th>Update</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${requestScope.listProduct}">
                                <tr>
                                    <td>${item.productID}</td>
                                    <td>${item.nameProduct}</td>
                                    <td> <img style="width: 100px; height: 100px" class="card-img-top" src="./image/${item.image}.jpeg"></td>
                                    <td>${item.description}</td>
                                    <td>${item.status.statusName}</td>
                                    <td>${item.category.categoryName}</td>
                                    <td>${item.quantity}</td>
                                    <td>${item.date}</td>
                                    <c:if test="${item.status.statusName ne 'InActive'}">
                                        <td>
                                            <form method="POST" action="MainController">
                                                <input type="hidden" name="productID" value="${item.productID}" />
                                                <input   id="${item.productID} Delete" style="display: none" type="submit" value="Delete"  name="btnAction" />
                                            </form>
                                            <button class="btn btn-success" onclick="ConfirmDelete('${item.productID} Delete')">Delete</button>
                                        </td>
                                    </c:if>
                                    <c:if test="${item.status.statusName eq 'InActive'}">
                                        <td>
                                        </td>
                                    </c:if>  
                                    <td> 
                                        <form method="POST" action="MainController">
                                            <input type="hidden" name="productID" value="${item.productID}" />
                                            <input style="display: none" id="${item.productID} Update" type="submit" value="Update"  name="btnAction" />
                                        </form>
                                        <button class="btn btn-success" onclick="ConfirmUpdate('${item.productID} Update')">Update</button>
                                    </td>
                                    <c:if test="${not empty requestScope.mess && requestScope.productID eq item.productID}">
                                        <td style="color: green">${requestScope.mess}</td>
                                    </c:if>
                                </tr>    
                            </c:forEach>
                        </tbody>
                    </table>

                </div>
            </div>
        </div>
        <script>
            function ConfirmDelete(productID) {
                confirmModal = confirm("Do you want to  " + productID);
                if (confirmModal) {
                    document.getElementById(productID).click();
                }
            }
            function ConfirmUpdate(productID) {
                confirmModal = confirm("Do you want to " + productID);
                if (confirmModal) {
                    document.getElementById(productID).click();
                }
            }
        </script>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </body>
</html>
