var authorizedEmails = ['jdoe2@foo.com'];
var exclusionDomain = 'bar2.com';
var pattern = /[^\s=,]*@[^\s=,]*/g
var ldif = '# jdoe@foo.com, people, sample  \n\
dn: CN=jdoe@foo.com,ou=people,o=sample  \n\
uid: jd  \n\
sn: Doe  \n\
mail: jdoe@foo.com  \n\
forcePasswordChange: true  \n\
objectClass: organizationalPerson  \n\
objectClass: person  \n\
objectClass: inetOrgPerson  \n\
objectClass: top  \n\
givenName: John  \n\
CN: jdoe@foo.com  \n\
userPassword:: e1NIQX1xVXFQNWN5eG02WWNUQWh6MDVIcGg1Z3Z1OU09 \n\
\n\
# rroe@bar.com, people, sample \n\
dn: CN=rroe@bar.com,ou=people,o=sample \n\
uid: rr \n\
sn: Roe \n\
mail: rroe@bar.com \n\
forcePasswordChange: false  \n\
objectClass: organizationalPerson  \n\
objectClass: person  \n\
objectClass: inetOrgPerson  \n\
objectClass: top  \n\
givenName: Richard  \n\
CN: rroe@bar.com  \n\
userPassword:: e1NIQX1mSnBSa1NOZmkrTlBLVzNiOURkNklWSzJEQk09';

taintLdifEmail();
console.log(ldif);

function taintLdifEmail() {
	var matches = ldif.match(pattern);

	for ( var matchIndex in matches ) {
		var taint = true;
		var match = matches[matchIndex];
		if( match.indexOf(exclusionDomain) >= 0 ) continue;
		for ( var authorizedEmailIndex in authorizedEmails ) {
			var authorizedEmail = authorizedEmails[authorizedEmailIndex];
			if( match == authorizedEmail ) {
				taint = false;
				continue;
			}
		}
		if( taint ) {
			ldif = ldif.replace(match, match.substring(0, match.indexOf('@')) + "@sample.com");
		}
	}
	return ldif;
}
