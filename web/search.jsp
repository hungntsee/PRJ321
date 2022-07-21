<%-- 
    Document   : search.jsp
    Created on : Jul 11, 2020, 10:46:53 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Account Page</title>
    </head>
    <body>
        <c:set var="curUser" value="${sessionScope.curUSER}"/>
        <c:choose>
            <c:when test="${not empty curUser}">
                <h2>
                    <font color="red"> Welcome,${curUser.fullname}</font>
                </h2>
            </c:when>
            <c:otherwise>
                <c:redirect url="login"/>
            </c:otherwise>
        </c:choose>
        <!--///////////////////////-->
        <a href="loadProductS">Click Here to go to Online Store</a>
        <h1>Search Page</h1>
        <form action="searchS">
            Search <input type="text" name="txtSearch" value="${param.txtSearch}" /></br>
            <input type="submit" value="Search" />
        </form>
        <jsp:include page="logout.html" flush="true"/>
        <h2>
            Your search value is: ${param.txtSearch}
        </h2>
            <c:set var="listDTO" value="${requestScope.LIST_DTO}"/>
            <c:set var="searchValue" value="${param.txtSearch}"/>
            <c:if test="${not empty searchValue}">
                <c:choose>
                    <c:when test="${not empty listDTO}">
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>No.</th>
                                    <th>Username</th>
                                    <th>Password</th>
                                    <th>Full name</th>
                                    <th>Role</th>
                                    <th>Delete</th>
                                    <th>Update</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="dto" items="${listDTO}" varStatus="Count">
                                    <form action="updateRecord">
                                    <tr>
                                        <td>
                                           ${Count.count} 
                                        </td>
                                        <td>
                                           ${dto.username}
                                           <input type="hidden" name="txtUsername" value="${dto.username}" />
                                        </td>
                                        <td>
                                            <input type="text" name="txtPassword" value="${dto.password}" />
                                            <%--///////////Password update errorr//////--%>
                                            <c:set var="errorMsg" value="${requestScope.EMPTY_PASSWORD_ERROR}"/>
                                            <c:set var="userError" value="${requestScope.USERNAME_ERROR}"/>
                                            <c:if test="${not empty errorMsg}">
                                                <c:set var="username" value="${dto.username}"/>
                                                <c:if test="${username eq userError}">
                                                    </br>
                                                    <font color="red">${errorMsg}</font>
                                                </c:if>
                                            </c:if>
                                        </td>
                                        <td>
                                           ${dto.fullname} 
                                        </td>
                                        <td>
                                            <input type="checkbox" name="chkAdmin" value="ON" 
                                                <c:if test="${dto.role}">
                                                    checked="checked"
                                                </c:if>
                                            /> 
                                        </td>
                                        <td>
                                            <c:url var="deleteLink" value="deleteRecord">
                                                <c:param name="pk" value="${dto.username}"/>
                                                <c:param name="lastSearchValue" value="${searchValue}"/>
                                            </c:url>
                                            <c:if test="${dto.username != curUser.username}">
                                                <a href="${deleteLink}">Delete</a>
                                            </c:if>
                                        </td>
                                        <td>
                                            <input type="submit" value="Update" />
                                            <input type="hidden" name="lastSearchValue" value="${searchValue}"/>
                                        </td>
                                    </tr>
                                    </form>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <h1>No record exist</h1>
                    </c:otherwise>
                </c:choose>
            </c:if>
    </body>
</html>
