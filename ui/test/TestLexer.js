var Lexer = require('./Lexer');

var lexer = new Lexer()
var scan = lexer.scan('1234 //321\n //123abad');
console.log(scan)