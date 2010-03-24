<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil" %>
<%@ page import="com.liferay.portlet.documentlibrary.model.DLFolder" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%
long groupId = layout.getGroupId();
java.util.List<DLFolder> folders = DLFolderServiceUtil.getFolders(groupId, 0);
String phrase = ParamUtil.getString(request, "phrase");
if(phrase == null || phrase.length() == 0){
  phrase = "Nothing";
}
%>

<h3>User Data</h3>
<div><liferay-ui:user-display userId="<%= user.getUserId() %>" /></div>
<div>Company: <%=company.getCompanyId()%> </div>
<div>Group: <%=groupId%> </div>
<div>Folders: <%=folders%> </div>

<h3>What you said</h3>
<%=phrase%>

<h3>Form Submission</h3>
<form name="testForm" action="<portlet:renderURL/>" method="post">
  <input type="text" name="phrase" value="<%=phrase%>">
  <input type="submit" name="submit" value="submit">
</form>
