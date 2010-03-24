<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil" %>
<%@ page import="com.liferay.portlet.documentlibrary.model.DLFolder" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />
<portlet:defineObjects />

<%
long groupId = layout.getGroupId();
java.util.List<DLFolder> folders = DLFolderServiceUtil.getFolders(groupId, 0);
%>>

<h3>User Data</h3>
<div><liferay-ui:user-display userId="<%= user.getUserId() %>" /></div>
<div>Company: <%=company.getCompanyId()%> </div>
<div>Group: <%=groupId%> </div>
<div>Folders: <%=folders%> </div>
