var sys = require('sys');
 
var Client = require('mysql').Client;
var client = new Client();
 
client.user = 'root';
client.password = 'root';
client.host = 'localhost';
client.port = '3306';
client.database = 'krfs'
 
client.connect();

var authorizedEmailQuery = client.query(
  'SELECT name FROM authorized_test_email',
  function (err, results, fields) {
    if (err) {
      throw err;
    }

    //console.log(results);
    //console.log(fields);
    for (var i in results){
      var result = results[i];
      console.log(i + ':' + result.name);
    }
    client.end();
  }
);



