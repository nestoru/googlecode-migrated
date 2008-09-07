<html>
	<head>
	 <title>Login Page</title>
	 <meta name="layout" content="main" />
	</head>
	<body>
		<div class="body">
	    <g:if test="${flash.message}">
				<div class="message">${flash.message}</div>
			</g:if>
		  <p>Welcome to Your ToDo List. Login below</p>
		  <g:form>
        <span class='nameClear'>
          <label	for="login"> Sign In: </label> 
        </span> 
        <g:select name='userName' from="${User.list()}" optionKey="userName" optionValue="userName"></g:select>
		      <br />
		    <div class="buttons"><span class="button"><g:actionSubmit	action="handleLogin" value="Login" /></span></div>
		  </g:form>
		</div>
	</body>
</html>