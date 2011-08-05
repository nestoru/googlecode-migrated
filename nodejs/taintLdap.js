#!/usr/local/bin/node
/*
** WARNING: This program will wipe out the specified BASE_DN from the target LDAP Server
**
** taintLdap.js A nodejs script to taint data from ldap. Use it to change passwords for all users to a known value and to change their emails to avoid sending messages from testing environment to real users
**
** Example: node taintLdap.js 'ldap://jnestorurquiza:10389' 'uid=admin,ou=system' 'secret' 'ldap://localhost:10389' 'uid=admin,ou=system' 'secret'
**
** @Author: Nestor Urquiza
** @Date: 08/02/2011
**
*/

/*
** Imports
*/
var sys = require('sys')
var exec = require('child_process').exec;
var Client = require('mysql').Client;
var client = new Client();

/*
** Constants
*/
var BASE_DN = "o=nestorurquiza";
var EXCLUSION_DOMAINS = ['nestorurquiza.com','nestoru.com'];
var APPEND_DOMAIN = 'nestorurquiza.com'
var COMMON_PASSWORD = 'e1NIQX1JcnFMUVdMT1o3ZXF0WHRBdUlFSFRlUnZkRFk9' //Testtest1 after SHA1. To generate a different password use: echo -n "mypassword" | openssl dgst -sha1

/*
** Arguments
*/
var argv = process.argv;
if( argv.length != 8 ) {
	usage();
	process.exit(1);
}
var fromUrl = argv[2];
var fromUser = argv[3];
var fromPasword = argv[4];
var toUrl = argv[5];
var toUser = argv[6];
var toPasword = argv[7];

client.user = 'root';
client.password = 'root';
client.host = 'localhost';
client.port = '3306';
client.database = 'nestorurquiza'

/*
** Main
*/
//console.log('Cloning and tainting from ' + fromUrl + ' to ' + toUrl);
var ldif;
var authorizedEmails = new Array();
var pattern = /[^\s=,]*@[^\s=,]*/g
var child = exec("ldapsearch -x -v -H '" + fromUrl + "' -D '" + fromUser + "' -w '" + fromPasword + "' -b '" + BASE_DN + "'", function (error, stdout, stderr) {
  if (error) {
    throw error;
  }
  ldif = stdout;
  client.connect();
  var authorizedEmailQuery = client.query(
  'SELECT name FROM authorized_test_email',
  function (error, results, fields) {
    if (error) {
      throw error;
    }
    for (var resultIndex in results){
      var result = results[resultIndex];
      authorizedEmails[resultIndex] = result.name;
    }
    client.end();
    //console.log('****************'  + authorizedEmails);
    ldif = taintLdifEmail();
	
	child = exec("ldapdelete -r -x -H '" + toUrl + "' -D '" + toUser + "' -w '" + toPasword + "' '" + BASE_DN + "'", function (error, stdout, stderr) {
	  if (error) {
        //throw error;
        console.log("WARNING: Could not delete " + BASE_DN);
      }
      var command = "echo '" + ldif + "' | ldapmodify -x -c -a -H '" + toUrl + "' -D '" + toUser + "' -w '" + toPasword + "'";
      //console.log(command);
      child = exec(command, function (error, stdout, stderr) {
	    
		if (error) {
			throw error;
		}
	  });
    });  	
	//ldapmodify -x -c -a -H ldap://localhost:10389 -D "uid=admin,ou=system" -w 'secret' < ~/Downloads/taintedLdap.ldif
  }
);
});

/*
** Functions
*/
function taintLdifEmail() {
	var matches = ldif.match(pattern);

	for ( var matchIndex in matches ) {
		var taint = true;
		var match = matches[matchIndex];
		for( var exclusionDomainIndex in EXCLUSION_DOMAINS ) {
			if( match.indexOf(EXCLUSION_DOMAINS[exclusionDomainIndex]) >= 0 ) {
				taint = false;
				break;
			}
		}
		if( !taint ) {
			continue;
		}
		for ( var authorizedEmailIndex in authorizedEmails ) {
			var authorizedEmail = authorizedEmails[authorizedEmailIndex];
			if( match == authorizedEmail ) {
				taint = false;
				continue;
			}
		}
		if( taint ) {
			var replacement = match.replace('.', '') + APPEND_DOMAIN;
			//console.log( match + " >>> " + replacement );
			ldif = ldif.replace(match, replacement);
		}
	}
	ldif = ldif.replace(/userPassword.*/g, 'userPassword:: ' + COMMON_PASSWORD);
	return ldif;
}

function usage() {
	console.log("Usage: " + "./taintLdap.js <fromUrl> <fromUser> <fromPasword> <toUrl> <toUser> <toPasword>");
}


