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
// program -> block
// block -> (stmts) | ε
// stmts -> stmts stmt | ε
// stmt -> bool loc | loc
// bool ->  OR loc | AND loc
// loc -> Number | ID | block
let Lexer = require('./Lexer');
let Token = require('./Token').Token;

class Parser {

  constructor(lexer) {
    this.lexer = lexer
    this.move()
  }

  loc() {
    if(this.look.tag===Token.NUM){
      console.log(this.look.lexeme)
    }else if(this.look.tag === Token.ID){
      console.log(this.look.lexeme)
    }else if(this.look.tag === Token.lp){
      this.block()
    }
  }

  bool() {

    if (this.look.tag === Token.and) {

    } else if (this.look.tag === Token.or) {

    }
    this.loc()
  }

  stmt() {
    if (this.look.tag === Token.and || this.look.tag === Token.or) {
      this.bool()
      this.loc()
    } else {
      this.loc()
    }
  }

  stmts() {
    while(this.look.tag !== Token.lp){
      this.stmt()
      this.stmts()
    }
  }

  block() {
    this.match(Token.lp)
    this.stmts();
    this.match(Token.rp)
  }

  program() {
    this.block()
  }

  move() {
    this.look = this.lexer.scan();
  }

  match(t) {
    if (this.look.tag === t) this.move();
    else {
      throw new Error('syntax error');
    }
  }
}


let content = `
( abc and aa)
`
let lexer = new Lexer(content)
let parser = new Parser(lexer)
parser.program()
