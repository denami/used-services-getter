<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Requests</title>

    <style type="text/css">
        .tg {
            border-collapse: collapse;
            border-spacing: 0;
            border-color: #ccc;
        }

        .tg td {
            font-family: Arial, sans-serif;
            font-size: 14px;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #fff;
        }

        .tg th {
            font-family: Arial, sans-serif;
            font-size: 14px;
            font-weight: normal;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #f0f0f0;
        }

        .tg .tg-4eph {
            background-color: #f9f9f9
        }
    </style>
</head>
<body>
<a href="/">Back to main menu</a>

<br/>
<br/>

<h1>Requests List</h1>

<c:if test="${!empty listRequests}">
    <table class="tg">
        <tr>
            <th width="30">ID</th>
            <th width="120">Create time</th>
            <th width="80">Start date</th>
            <th width="80">End date</th>
            <th width="120">Requested IP address</th>
            <th width="120">Requested domain address</th>
            <th width="120">Status</th>
        </tr>
        <c:forEach items="${listRequests}" var="request">
            <tr>
                <td><a href="/report/${request.id}" target="_blank">${request.id}</a></td>
                <td>${request.createDateTime}</td>
                <td>${request.startDate}</td>
                <td>${request.endDate}</td>
                <td>${request.requestedIpAddressComaList}</td>
                <td>${request.requestedDomainAddressComaList}</td>
                <td>${request.status}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<h1>Add request</h1>

<c:url var="addAction" value="/request/edit"/>

<form:form action="${addAction}" commandName="request">
    <table>
        <c:if test="${!empty request.id}">
            <tr>
                <td>
                    <form:label path="id">
                        <spring:message text="ID"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="id" readonly="true" size="8" disabled="true"/>
                    <form:hidden path="id"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td>
                <form:label path="requestedIpAddress">
                    <spring:message text="Requested IP address"/>
                </form:label>
            </td>
            <td>
                <form:input path="requestedIpAddress"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="requestedDomainAddress">
                    <spring:message text="Requested domain address"/>
                </form:label>
            </td>
            <td>
                <form:input path="requestedDomainAddress"/>
            </td>
        </tr>

        <tr>
            <td>
                <form:label path="startDate">
                    <spring:message text="Start date"/>
                </form:label>
            </td>
            <td>
                <form:input path="startDate" type="date"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="endDate">
                    <spring:message text="End eate"/>
                </form:label>
            </td>
            <td>
                <form:input path="endDate" type="date"/>
            </td>
        </tr>

        <tr>
            <td colspan="2">
                <c:if test="${!empty request.id}">
                    <input type="submit"
                           value="<spring:message text="Edit request"/>"/>
                </c:if>
                <c:if test="${empty request.id}">
                    <input type="submit"
                           value="<spring:message text="Add request"/>"/>
                </c:if>
            </td>
        </tr>
    </table>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form:form>
</body>
</html>
