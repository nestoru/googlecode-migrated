var asyncArg = 'Async Arguments:';
var syncArg = 'Sync Arguments:';

var argv = process.argv;
argv.forEach(function (val, index, array) {
	setTimeout(function() {asyncArg += ' ' + val; console.log( asyncArg ); }, 0);
	
});
console.log( asyncArg );

for( argIndex in argv ) {
	syncArg += ' ' + argv[argIndex];
}
console.log( syncArg );