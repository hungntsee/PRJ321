<%-- 
    Document   : signup
    Created on : Jul 12, 2020, 9:42:02 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign Up</title>
    </head>
    <body>
        <form action="SignUpS">
            <c:set var="errorMsg" value="${requestScope.CREATE_ERROR}"/>
            <h1>Sign Up</h1>
            <!--//////////////////////////////////////////////-->
            Username <input type="text" name="txtUsername" value="${param.txtUsername}" />( 6-20 characters )</br>
            <c:if test="${not empty errorMsg.usernameError}">
                <font color="red">${errorMsg.usernameError}</font></br>
            </c:if>
            <!--//////////////////////////////////////////////-->
            Password <input type="password" name="txtPassword" value="" />( 6-20 characters )</br>
            <c:if test="${not empty errorMsg.passwordError}">
                <font color="red">${errorMsg.passwordError}</font></br>
            </c:if>
            <!--//////////////////////////////////////////////-->
            Confirm <input type="password" name="txtConfirm" value="" /></br>
            <c:if test="${not empty errorMsg.confirmError}">
                <font color="red">${errorMsg.confirmError}</font></br>
            </c:if>
            <!--//////////////////////////////////////////////-->
            Full Name <input type="text" name="txtFullname" value="${param.txtFullname}" />( 2-50 character )</br>
            <c:if test="${not empty errorMsg.fullnameError}">
                <font color="red">${errorMsg.fullnameError}</font></br>
            </c:if>
            <!--//////////////////////////////////////////////-->
            <input type="submit" value="Sign up" />
        </form>
        <a href="login">Click here to return to login page</a>
    </body>
</html>
