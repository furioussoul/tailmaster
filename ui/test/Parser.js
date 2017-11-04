// (
//   '消协'
// or '起诉'
// or '315'
// or '微博'
// or '曝光'
// or '律师'
// or '电视台'
// or '记者'
// or '工商投诉'
// and 1
// )
// and (
//   ('不行' or '不可以')
// and 2
// )

var Lexer = require('./Lexer');
var Token = require('./Token').Token;

function Parser(lexer) {
  this.output = ''
  this.blockDeep = 0
  this.nearby = ''
  this.lexer = lexer
  this.move()
}

function indent(deep) {
  var space = ''
  for (var i = 0; i < deep; i++) {
    space+=' '
  }
  return space
}

//program -> block
//block -> (stmts) | e
//stmts -> stmt term
//term-> op stmt | e
//stmt -> (stmts) | factory
//factory -> Number | 'Id'
Parser.prototype.program = function () {
  this.block()
}

//block -> (stmts) | e
Parser.prototype.block = function () {
  while (this.look.tag !== Token.eof) {
    if (this.look === Token.lp) {
      this.match(Token.lp)
      var deep = this.blockDeep
      this.output += indent(deep) + '('
      this.output += '\n'
      this.blockDeep++
      this.stmts()
      this.blockDeep = deep
      this.output += '\n'
      this.output += indent(deep) + ')'
      this.match(Token.rp)
    } else {
      this.stmts()
    }
  }
}

//stmts -> stmt term
Parser.prototype.stmts = function () {
  this.stmt()
  this.term()
}

//term-> op stmt | e
Parser.prototype.term = function () {
  if (this.look.tag === Token.and) {
    this.output += '\n'
    this.output += indent(this.blockDeep) + 'AND'
    this.nearby = 'AND'
    this.match(Token.and)
    this.stmt()
    this.term()
  } else if (this.look.tag === Token.or) {
    this.output += '\n'
    this.output += indent(this.blockDeep) + 'OR'
    this.nearby = 'OR'
    this.match(Token.or)
    this.stmt()
    this.term()
  } else {
    return null
  }
}

//stmt -> (stmts) | factory
Parser.prototype.stmt = function () {
  if (this.look.tag === Token.lp) {

    this.match(Token.lp)
    var deep = this.blockDeep
    this.output += indent(deep) + '('
    this.output += '\n'
    this.blockDeep++
    this.stmts()
    this.blockDeep = deep
    this.output += '\n'
    this.output += indent(deep) + ')'
    this.match(Token.rp)
  } else {
    this.factory()
  }
}

//factory -> Number | 'Id'
Parser.prototype.factory = function () {
  if (this.look.tag === Token.qt) {
    this.match(Token.qt)
    this.output += indent(this.blockDeep) + '\'' + this.look.lexeme + '\''
    this.nearby = this.look.lexeme ? this.look.lexeme:this.nearby
    this.match(Token.id)
    this.match(Token.qt)
  } else {
    this.output += indent(this.blockDeep) + this.look.lexeme
    this.nearby = this.look.lexeme ? this.look.lexeme:this.nearby
    this.match(Token.id)
  }
}

Parser.prototype.move = function () {
  this.look = this.lexer.scan();
}

Parser.prototype.match = function (t) {
  if (this.look.tag === t) this.move();
  else {
    throw new Error('syntax error at line: ' + this.lexer.line +' ;near: ' + this.nearby);
  }
}

var content = `
('微博'
or '起诉'
Or '315a'
or '微博'
OR '曝光'
Or '律师'
or '电视台'
or '记者'
or '工商投诉'
and 1
)
and (
  ('不行' or '不可以'
  and (
    '起诉' and
    '投诉' and '曝光'
  )and (
  ('不行' or '不可以'
  and (
    '起诉' and
    '投诉' and '曝光'
  )and (
  ('不行' or '不可以'
  and 
    '起诉' and
    '投诉' and '曝光'
  )
  )
and 2
  )
and 2
)
  )
and 2
)
and (
  ('不行' or '不可以'
  and (
    '起诉' and
    '投诉' and '曝光'
  )
  )
and 2
)
and (
  ('不行' or '不可以'
  and (
    '起诉' and
    '投诉' and '曝光'
  )
  )
and 2
)
`
var lexer = new Lexer(content)
var parser = new Parser(lexer)
parser.program()
console.log(parser.output)

