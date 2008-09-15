<%@ page contentType="text/html;charset=UTF-8" language="java" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>  
<html>  
  <head>  
     <title>Spring MVC Tutorial (Home Page)</title>  
  </head>  
  <body>  
    <h1>Good <c:out value="${greeting}"/>! Welcome to Spring MVC Tutorial</h1>  
   
    The time on the server is <c:out value="${time}"/>  
   
    <b>Here are ten random integers:</b>  
    <c:forEach items="${randList}" var="num">  
         <c:out value="${num}"/><br/>  
    </c:forEach>    
    <img src="<c:url value="/images/poweredBySpring.jpg"/>" alt="Powered By Spring"/>  
  </body>  
</html>  