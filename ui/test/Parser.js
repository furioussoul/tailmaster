// (
//   '消协'
// OR '起诉'
// OR '315'
// OR '微博'
// OR '曝光'
// OR '律师'
// OR '电视台'
// OR '记者'
// OR '工商投诉'
// AND 1
// )
// AND (
//   ('不行' OR '不可以')
// AND 2
// )

var Lexer = require('./Lexer');
var Token = require('./Token').Token;

function Parser(lexer)  {
    this.lexer = lexer
    this.move()
}

// program -> block
Parser.prototype.program =function ()  {
  this.block()
}

//block -> (stmts) | e
Parser.prototype.block =function () {
  if(this.look.tag === Token.lp){
    this.match(Token.lp)
    console.log('(')
    this.stmts()
    console.log(')')
    this.match(Token.rp)
  }else {
    return null
  }
}

//stmts -> stmt term
Parser.prototype.stmts =function () {
  this.stmt()
  this.term()
}

//term-> op stmt | e
Parser.prototype.term =function () {
  if(this.look.tag===Token.and){
    console.log('and')
    this.match(Token.and)
    this.stmt()
    this.term()
  }else if(this.look.tag===Token.or){
    console.log('or')
    this.match(Token.or)
    this.stmt()
    this.term()
  }else {
    return null
  }
}

//stmt -> (stmts) | factory
Parser.prototype.stmt =function ()  {
  if(this.look.tag === Token.lp){
    this.match(Token.lp)
    console.log('(')
    this.stmts()
    console.log(')')
    this.match(Token.rp)
  }else {
   this.factory()
  }
}

//factory -> Number | 'Id'
Parser.prototype.factory =function ()  {
  if(this.look.tag === Token.qt){
    this.match(Token.qt)
    console.log('\''+this.look.lexeme+'\'')
    this.match(Token.id)
    this.match(Token.qt)
  }else {
    console.log(this.look.lexeme)
    this.match(Token.number)
  }
}

Parser.prototype.move = function() {
  this.look = this.lexer.scan();
}

Parser.prototype.match = function(t) {
  if (this.look.tag === t) this.move();
  else {
    throw new Error('syntax error');
  }
}

var content = `
('不好a1123' or '不可以' and('你麻痹') or ('呵呵' and '哈哈' and('哇哈哈哈' and ('草拟吗'))))

`
var lexer = new Lexer(content)
var parser = new Parser(lexer)
parser.program()
