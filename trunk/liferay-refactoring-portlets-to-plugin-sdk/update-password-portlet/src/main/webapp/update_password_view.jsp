<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil"%>

<%@ page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@ page import="com.liferay.portal.model.*"%>
<%@ page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@ page import="com.liferay.portal.UserPasswordException"%>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil"%>

<liferay-theme:defineObjects />
<portlet:defineObjects />

<%
PasswordPolicy passwordPolicy = user.getPasswordPolicy();
String doAsUserId = themeDisplay.getDoAsUserId();
boolean impersonating = false;
String referer = themeDisplay.getPathMain();
if(doAsUserId != null && doAsUserId.length() > 0){
	doAsUserId = HtmlUtil.escape(doAsUserId);
	doAsUserId = doAsUserId.replaceAll("\\+", "%2B");
	doAsUserId = doAsUserId.replaceAll("=", "%3D");
	impersonating = true;
	referer += "?doAsUserId=" + doAsUserId;
}
%>
<form action="<%= themeDisplay.getPathMain() %>/portal/update_password"
	class="uni-form" method="post" name="fm"
	onSubmit="submitForm(document.fm); return false;">
<%if(impersonating) {%> <input name="doAsUserId" type="hidden"
	value="<%=doAsUserId  %>" /> <%} %> <input
	name="<%= com.liferay.portal.kernel.util.Constants.CMD %>"
	type="hidden"
	value="<%= com.liferay.portal.kernel.util.Constants.UPDATE %>" /> <input
	name="referer" type="hidden" value="<%=referer %>" /> <span
	class="portlet-msg-info"> <liferay-ui:message
	key="please-set-a-new-password" /> </span> <c:if
	test="<%= SessionErrors.contains(request, UserPasswordException.class.getName()) %>">

	<%
	UserPasswordException upe = (UserPasswordException)SessionErrors.get(request, UserPasswordException.class.getName());
	%>
	<% if(upe != null) {%>
		<span class="portlet-msg-error"> <c:if
			test="<%= upe.getType() == UserPasswordException.PASSWORD_ALREADY_USED %>">
			<liferay-ui:message
				key="that-password-has-already-been-used-please-enter-in-a-different-password" />
		</c:if> <c:if
			test="<%= upe.getType() == UserPasswordException.PASSWORD_CONTAINS_TRIVIAL_WORDS %>">
			<liferay-ui:message
				key="that-password-uses-common-words-please-enter-in-a-password-that-is-harder-to-guess-i-e-contains-a-mix-of-numbers-and-letters" />
		</c:if> <c:if
			test="<%= upe.getType() == UserPasswordException.PASSWORD_INVALID %>">
			<liferay-ui:message
				key="that-password-is-invalid-please-enter-in-a-different-password" />
		</c:if> <c:if
			test="<%= upe.getType() == UserPasswordException.PASSWORD_LENGTH %>">
			<%= LanguageUtil.format(pageContext, "that-password-is-too-short-or-too-long-please-make-sure-your-password-is-between-x-and-512-characters", String.valueOf(passwordPolicy.getMinLength()), false) %>
		</c:if> <c:if
			test="<%= upe.getType() == UserPasswordException.PASSWORD_NOT_CHANGEABLE %>">
			<liferay-ui:message key="your-password-cannot-be-changed" />
		</c:if> <c:if
			test="<%= upe.getType() == UserPasswordException.PASSWORD_SAME_AS_CURRENT %>">
			<liferay-ui:message
				key="your-new-password-cannot-be-the-same-as-your-old-password-please-enter-in-a-different-password" />
		</c:if> <c:if
			test="<%= upe.getType() == UserPasswordException.PASSWORD_TOO_YOUNG %>">
			<%= LanguageUtil.format(pageContext, "you-cannot-change-your-password-yet-please-wait-at-least-x-before-changing-your-password-again", LanguageUtil.getTimeDescription(pageContext, passwordPolicy.getMinAge() * 1000), false) %>
		</c:if> <c:if
			test="<%= upe.getType() == UserPasswordException.PASSWORDS_DO_NOT_MATCH %>">
			<liferay-ui:message
				key="the-passwords-you-entered-do-not-match-each-other-please-re-enter-your-password" />
		</c:if>
	<%} %>
	</span>
</c:if>

<fieldset class="block-labels"><legend><liferay-ui:message
	key="new-password" /></legend>

<div class="ctrl-holder"><label for="password1"><liferay-ui:message
	key="password" /></label> <input class="lfr-input-text" name="password1"
	type="password" /></div>

<div class="ctrl-holder"><label for="password2"><liferay-ui:message
	key="enter-again" /></label> <input class="lfr-input-text" name="password2"
	type="password" /></div>
</fieldset>

<input type="submit" value="<liferay-ui:message key="save" />" /></form>

<script type="text/javascript">
	Liferay.Util.focusFormField(document.fm.password1);
</script>
