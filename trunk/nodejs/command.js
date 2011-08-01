var sys = require('sys')
var exec = require('child_process').exec;
var child;

child = exec("ldapsearch -x -v -H 'ldap://jkrfs:10389' -D 'uid=admin,ou=system' -w 'secret' -b 'o=krfs'", function (error, stdout, stderr) {
  //sys.print('stdout: ' + stdout);
  //sys.print('stderr: ' + stderr);
  console.log(stdout);
  if (error !== null) {
    console.log('exec error: ' + error);
  }
});