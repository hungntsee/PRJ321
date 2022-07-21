<%-- 
    Document   : viewcart.jsp
    Created on : Jul 12, 2020, 11:06:30 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View cart</title>
    </head>
    <body>
<%--/////////////////WELCOME USER SECTION///////////////////////////----%>
        <c:set var="curUser" value="${sessionScope.curUSER}"/>
        <c:if test="${not empty curUser}">
            <h2>
                <font color="red">Welcome, ${curUser.fullname}</font>
            </h2>
        </c:if>
        <c:if test="${empty curUser}">
            <a href="login">Click here to login</a>
        </c:if>
                <%--SHOW YOUR CART--%>
        <h1>Your cart</h1>
        <c:if test="${not empty curUser}">
            <jsp:include page="logout.html" flush="true"/>
        </c:if>
        <c:set var="cart" value="${sessionScope.CART}"/><%--attribute cart is located at AddToCartServlet--%>
        <c:choose>
            <%--If cart is existed--%>
            <c:when test="${not empty cart}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Title</th>
                            <th>Quantity</th>
                            <th>Check</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:set var="items" value="${cart.items}"/>
                        <form action="ProcessCart">
                            <c:forEach var="dto" items="${items}" varStatus="Counter">
                                <tr>
                                    <td>
                                        ${Counter.count}
                                    </td>
                                    <td>
                                        ${dto.key}
                                    </td>
                                    <td>
                                        ${dto.value}
                                    </td>
                                    <td>
                                        <input type="checkbox" name="chkTitle" value="${dto.key}"/>
                                    </td>
                                </tr>
                            </c:forEach>
                                <tr>
                                    <td colspan="2">
                                        <a href="loadProductS">Keep Shopping</a>
                                    </td>
                                    <td>
                                        <input type="submit" value="Delete" name="btAction"/>
                                    </td>
                                    <td>
                                        <input type="submit" value="Check Out" name="btAction"/>
                                    </td>
                                </tr>
                        </form>
                    </tbody>
                </table>
            </c:when>
            <%--IF cart is not exist--%>
            <c:otherwise>
                <h3>
                    No cart existed !!
                </h3>
                <a href="loadProductS">Keep Shopping</a>
            </c:otherwise>
        </c:choose>
        </br>
                    <%--CHECKOUT CONFIRM FORM--%>
        <c:set var="confirm" value="${requestScope.CONFIRM}"/>
        <c:if test="${not empty confirm && not empty cart}">
            <form action="confirmCheckOut" style="border: solid red; margin-right: 75% ">
                <h2 style="color: blue">Confirm form</h2>
                Customer Name
                <c:choose>
                    <c:when test="${not empty curUser}">
                        <input type="text" name="customerName" value="${curUser.fullname}" />
                    </c:when>
                    <c:otherwise>
                        <input type="text" name="customerName" value="${param.customerName}" />
                    </c:otherwise>
                </c:choose>
                </br>
                Customer Address <input type="text" name="customerAddress" value="${param.customerAddress}" /></br>
                <%--NOTIFY ERROR IF customerName or customerAddress is empty--%>
                <c:if test="${not empty requestScope.CONFIRM_ERROR}">
                    <font color="Red">${requestScope.CONFIRM_ERROR}</font></br>
                </c:if>
                <input type="submit" value="Confirm" name="btAction" />
                <input type="submit" value="Cancel" name="btAction" />
                </br>
            </form>
        </c:if>
            <%--NOTIFY IF CHECK OUT SUCCESS--%>
        <c:if test="${not empty requestScope.CHECK_OUT_SUCCESS_MSG}">
            <h2>
                <font color="GREEN">CHECK OUT SUCCESS</font>
            </h2>
        </c:if>
    </body>
</html>
