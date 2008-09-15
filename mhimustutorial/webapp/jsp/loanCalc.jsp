<%@ page contentType="text/html;charset=UTF-8" language="java" %>  
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>  
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>  
<html>  
<head>  
   <title>Calculate Loan</title>  
</head>  
  <body>  
  <center>  
    <!-- one way to display error messages � globally -->  
    <spring:hasBindErrors name="loanInfo">  
      <h3>You have errors in your input!</h3>  
      <font color="red">  
         <c:forEach items="${errors.allErrors}" var="error">  
             <spring:message code="${error.code}" text="${error.defaultMessage}"/>  
         </c:forEach>  
      </font>  
    </spring:hasBindErrors>  
   
     <!-- note second way of displaying error messages � by field -->  
     <form:form commandName="loanInfo" method="POST" action="loancalc.htm">  
         Principal: <form:input path="principal" /><form:errors path="principal" />  
         APR: <form:input path="apr" /><form:errors path="apr" />  
         Number of Years: <form:input path="years" /><form:errors path="years" />  
         Periods Per Year: <form:input path="periodPerYear" /><form:errors path="periodPerYear" />  
         <input type="submit" title="Calculate" />  
     </form:form>  
   
  </center>  
  </body>  
</html>  