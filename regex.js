var authorizedEmails = ['ibelayev@krfs.com'];

var ldif = '# jdeitrick@krfs.com, people, krfs  \n\
dn: CN=jdeitrick@krfs.com,ou=people,o=krfs  \n\
uid: jd  \n\
sn: Deitrick  \n\
mail: jdeitrick@krfs.com  \n\
forcePasswordChange: true  \n\
objectClass: organizationalPerson  \n\
objectClass: person  \n\
objectClass: inetOrgPerson  \n\
objectClass: top  \n\
givenName: Justin  \n\
CN: jdeitrick@krfs.com  \n\
userPassword:: e1NIQX1xVXFQNWN5eG02WWNUQWh6MDVIcGg1Z3Z1OU09 \n\
\n\
# ibelayev@krfs.com, people, krfs \n\
dn: CN=ibelayev@krfs.com,ou=people,o=krfs \n\
uid: ib \n\
sn: Belayev \n\
mail: ibelayev@krfs.com \n\
forcePasswordChange: false  \n\
objectClass: organizationalPerson  \n\
objectClass: person  \n\
objectClass: inetOrgPerson  \n\
objectClass: top  \n\
givenName: Igor  \n\
CN: ibelayev@krfs.com  \n\
userPassword:: e1NIQX1mSnBSa1NOZmkrTlBLVzNiOURkNklWSzJEQk09  \n\
ou: cn=99999000_KRFS Reviewer,ou=groups,o=krfs';

//var matches = ldif.match(/[^\s=,]*@[^\s=,]*/g);

var pattern = /[^\s=,]*@[^\s=,]*/g
var matches = ldif.match(pattern);

for ( var matchIndex in matches ) {
	var match = matches[matchIndex];
	for ( var authorizedEmailIndex in authorizedEmails ) {
		if( match == authorizedEmails[authorizedEmailIndex] ) {
			//ldif = ldif.replac
		}
	}
	if(match )
	console.log(match);
}

