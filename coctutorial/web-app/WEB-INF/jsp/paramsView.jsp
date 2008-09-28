<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
  <head>
    <title><%=request.getAttribute("title") %></title>
  </head>
  <h1><%=request.getAttribute("title") %></h1>
  <body>
    <b>parameter as request attribute: </b><%=request.getAttribute("par") %><br/>
    <b>parameter as JSTL output: </b><c:out value="${par}" escapeXml="false"/>
  </body>
</html>
