<%@ taglib prefix="g" uri="/web-app/WEB-INF/tld/grails.tld" %>
<div id="menu">
	<nobr>
		<g:isLoggedIn>
			<b><g:loggedInUserInfo field="firstName"/> <g:loggedInUserInfo field="lastName"/> </b> |
			<g:link controller="logout" action="">
				<g:message code="Logout" />
			</g:link>
		</g:isLoggedIn>
		<g:isNotLoggedIn>
			<g:link controller="login" action="">
				<g:message code="Login" />
			</g:link>
		</g:isNotLoggedIn>
	</nobr>
</div>