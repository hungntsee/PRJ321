<%-- 
    Document   : onlineStore
    Created on : Jul 11, 2020, 8:13:50 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Online Store</title>
    </head>
    <body>
        <c:set var="curUser" value="${sessionScope.curUSER}"/><%--curUSERNAME is located at StarUpServler and LoginServlet--%>
        <c:if test="${not empty curUser}">
            <h2>
                <font color="Red">Welcome, ${curUser.fullname}</font>  
            </h2>
        </c:if>
        <c:if test="${empty curUser}">
            <a href="login">Click here to login</a>
        </c:if>
        <h1>Online Shopping Cart</h1>
        <form action="cart">
            Choose Book 
            <select name="cbxBook">
                <c:set var="listBook" value="${requestScope.LIST_PRODUCT}"/><%--LIST_PRODUCT is located at LoadProductContextListenerServlet--%>
                <c:forEach var="dto" items="${listBook}">
                    <option>
                      ${dto.name}
                    </option>
                </c:forEach>
            </select>
                <input type="submit" value="addToCart" name="btAction"/>
                <input type="submit" value="viewToCart" name="btAction"/>
        </form>
        <c:if test="${not empty curUser}">
            <jsp:include page="logout.html" flush="true"/>
        </c:if>
    </body>
</html>
