// program -> block
// block -> (stmts) | ε
// stmts -> stmts stmt | block
// stmt -> bool loc | loc
// bool ->  OR loc | AND loc
// loc -> Number | ID
var Lexer = require('./Lexer');

class Parser {
  constructor(lexer) {
    this.lexer = lexer
  }

  loc() {
    let lexme1 = this.lexer.lookAhead()
    let lexme2 = this.lexer.lookAhead()
    console.log(lexme1,lexme2)
  }

  bool() {
    let lexme = this.lexer.lookAhead()
    if (lexme === 'O') {

    } else if (lexme === 'A') {

    }
    this.loc()
  }

  stmt() {
    let lexme = this.lexer.lookAhead()
    if (lexme === 'O' || lexme === 'A') {
      this.bool()
      this.loc()
    } else {
      this.loc()
    }
  }

  stmts() {
    let lexme = this.lexer.lookAhead()
    if (lexme === '(') {
      this.block()
    } else {
      this.stmt()
      this.stmts()
    }
  }

  block() {
    let lexme = this.lexer.lookAhead()
    if(lexme !== '('){
      throw new Error(`syntax error, loss '(' `);
    }
    this.stmts();

  }

  program() {
    this.block()
  }
}


let content = `(
  '消协'
OR '起诉'
OR '315'
OR '微博'
OR '曝光'
OR '律师'
OR '电视台'
OR '记者'
OR '工商投诉'
AND 1
)
AND (
  ('不行' OR '不可以')
AND 2
)`
let lexer = new Lexer(content)
let parser = new Parser(lexer)
parser.program()
