<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Reports</title>

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

<h1>Reports List</h1>

<c:if test="${!empty listReports}">
    <table class="tg">
        <tr>
            <th width="30">Abonent ID</th>
            <th width="80">DateTime</th>
            <th width="120">Destination IP address</th>
			<th width="120">Source IP address</th>
			<th width="120">Nat IP address</th>
            <th width="120">Bytes</th>
        </tr>
        <c:forEach items="${listReports}" var="report">
            <tr>
                <td>${report.userId}</td>
                <td>${report.dateTime}</td>
                <td>${report.srcIp}</td>
                <td>${report.dstIp}</td>
                <td>${report.natIp}</td>
                <td>${report.bytes}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>


</body>
</html>
