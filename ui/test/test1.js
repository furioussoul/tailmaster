var Lexer = require('./Lexer');
var Token = require('./Token').Token;

// a) S -> + S S | - S S | a
Parser.prototype.S1 = function () {
  var lookahead = this.lexer.getCurrentToken()
  switch (lookahead) {
    case Token.plus:
      this.output += '+'
      lexer.match(Token.plus);
      this.S1();
      this.S1();
      break;
    case Token.minus:
      this.output += '-'
      lexer.match(Token.minus);
      this.S1();
      this.S1();
      break;
    case Token.mul:
      this.output += '*'
      lexer.match(Token.mul);
      this.S1();
      this.S1();
      break;
    case Token.div:
      this.output += '/'
      lexer.match(Token.div);
      this.S1();
      this.S1();
      break;
    case Token.char:
      this.output += lexer.getLexeme()
      lexer.match(Token.char);
      break;
    default:
      throw new Error('syntax error');
  }
}

// b) S -> S ( S ) S | e
/*This grammar is left recursive
 * There is a scheme to eliminate left recursive
 * Grammar like this : A -> Aa | b can be transferred to:
 * A -> bR
 * R -> aR
 * | epsilon
 * S -> S ( S ) S | e can be transferred to:
 * S -> R
 * R -> (S)SR | epsilon
 * */
Parser.prototype.S2 = function () {
  this.R2()
}
Parser.prototype.R2 = function () {

  if(!lexer.test(Token.lp)){
    //for epsilon
    return
  }

  if(lexer.match(Token.lp)){
    this.output += '('
    this.S2()
  }
  if(!lexer.match(Token.rp)){
    throw new Error('syntax error');
  }
  this.output += ')'
  this.S2();
  this.R2()
}

Parser.prototype.S3 = function () {

}

// c) S -> 0 S 1 | 0 1
function Parser(lexer) {
  this.output = ''
  this.lexer = lexer
}

var lexer = new Lexer("(()()((())))");
var parser = new Parser(lexer);
parser.S2();
console.log(parser.output)

