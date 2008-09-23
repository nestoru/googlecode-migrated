<%@ page contentType="text/html;charset=UTF-8" language="java" %>  
<%@ page import="java.util.*" %>

<%String title = "Server Headers from Pure JSP";%>
<html>
  <head>
    <title><%=title%></title>
  </head>
  <h1><%=title%></h1>
  <table border=1 cellspacing=0 cellpadding=2>
    <tr><td colspan="2"><%= new Date() %>| Remote User: <%=request.getRemoteUser()%>|</td></tr>
    <%
      String strHeaderName = "";
      for(Enumeration e = request.getHeaderNames(); e.hasMoreElements() ;){
        strHeaderName = (String)e.nextElement();
    %>
        <tr>
          <td><%= strHeaderName %></td>
          <td><%= request.getHeader(strHeaderName)%> </td>
        </tr>
    <%
      }
    %>
  </table>
</html>