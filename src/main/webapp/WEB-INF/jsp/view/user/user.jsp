
<%-- @elvariable id="users" type="ru.solon4ak.jm_crudapp.model.User" --%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User</title>
</head>
<body>

<c:if test="${user != null}">
<h2>Edit user</h2>
<form action="update" method="post">
    <input type="hidden" name="id" value="${user.id}"/>
    </c:if>
    <c:if test="${user == null}">
    <h2>New user</h2>
    <form action="insert" method="post">
        </c:if>
        <table border="0" width="300" cellpadding="5">
            <tbody>

            <tr>
                <td>First name</td>
                <td><input type="text" name="firstName" value="${user.firstName}"/></td>
            </tr>
            <tr>
                <td>Last name</td>
                <td><input type="text" name="lastName" value="${user.lastName}"/></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><input type="text" name="email" value="${user.email}"/></td>
            </tr>
            <tr>
                <td>Address</td>
                <td><input type="text" name="address" value="${user.address}"/></td>
            </tr>
            <tr>
                <td>Phone number</td>
                <td><input type="text" name="phoneNumber" value="${user.phoneNumber}"/></td>
            </tr>
            <tr>
                <td>Age</td>
                <td><input type="text" name="age" value="${user.age}"/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Submit"/></td>
            </tr>
            </tbody>
        </table>

    </form>
    <br/>
    <br/>
    <a href="<c:url value="list" />">List users</a>
</body>
</html>
