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
//term-> op stmt term| e
//stmt -> (stmts) | [concat] | {concat}
//concat-> factory p
//p-> , concat | e
//factory -> Number | Id
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

//term-> op stmt term | e
Parser.prototype.term = function () {
  if (this.look.tag === Token.and) {
    this.output += '\n'
    this.output += indent(this.blockDeep) + 'and'
    this.output += '\n'
    this.nearby = 'and'
    this.match(Token.and)
    this.stmt()
    this.term()
  } else if (this.look.tag === Token.or) {
    this.output += '\n'
    this.output += indent(this.blockDeep) + 'or'
    this.output += '\n'
    this.nearby = 'or'
    this.match(Token.or)
    this.stmt()
    this.term()
  } else {
    return null
  }
}

//stmt -> (stmts) | [concat] | {concat}
Parser.prototype.stmt = function () {
  if (this.look.tag === Token.lp) {
    this.match(Token.lp)
    var deep = this.blockDeep
    this.output += '\n'
    this.output += indent(deep) + '('
    this.output += '\n'
    this.blockDeep++
    this.stmts()
    this.blockDeep = deep
    this.output += '\n'
    this.output += indent(deep) + ')'
    this.output += '\n'
    this.match(Token.rp)
  } else if(this.look.tag === Token.lbraces){
    this.match(Token.lbraces)
    var deep = this.blockDeep
    this.output += indent(deep) + '{'
    this.concat()
    this.match(Token.rbraces)
    this.output += indent(deep) + '}'
  } else if(this.look.tag === Token.lbracket){
    this.match(Token.lbracket)
    var deep = this.blockDeep
    this.output += indent(deep) + '['
    this.concat()
    this.match(Token.rbracket)
    this.output += indent(deep) + ']'
  }else {
    throw new Error('syntax error')
  }
}

//concat-> factory p
Parser.prototype.concat = function () {
  this.factory()
  this.p()
}

//p-> , concat | e
Parser.prototype.p = function () {
  if(this.look.tag === Token.comma){
    this.match(Token.comma)
    this.output += ','
    this.concat()
  }
}

//factory -> Number | Id
Parser.prototype.factory = function () {
  if (this.look.tag === Token.qt) {
    this.match(Token.qt)
    if(this.before === '!'){
      this.before = ''
      this.output += indent(this.blockDeep) + '\'' + this.look.lexeme + '\''
    }else {
      this.output += indent(this.blockDeep) + '\'' + this.look.lexeme + '\''
    }
    this.nearby = this.look.lexeme ? this.look.lexeme:this.nearby
    this.match(Token.id)
    this.match(Token.qt)
  } else {
    if(this.before === '!'){
      this.before = ''
      this.output += indent(this.blockDeep) + this.look.lexeme
    }else {
      this.output += indent(this.blockDeep)+ this.look.lexeme
    }
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
    var hint = ''
    if(this.look.tag===Token.lp){
        hint+=` about '('`
    }else if(this.look.tag===Token.rp){
      hint+=` about ')'`
    }
    throw new Error('syntax error at line: ' + this.lexer.line +' ;near: ' + this.nearby + ' ; ' +  hint)
  }
}

var testContent = `

 ({错误的名字,号码错了}and({错误的名字,号码错了})and({错误的名字,号码错了}and({错误的名字,号码错了}and{错误的名字,号码错了})))or({错误的名字,号码错了}and{贵宾厅,休息室,休息厅}and[取消,退,发票,报销,票价,总价,总计])or({值机,孕妇,怀孕,健康证明,取票,取出来,轮椅,临时登机证明,登记,登机,哪个航站楼}and[订一下,退,发票,改签,短信,航班取消,保险,错了])or({行李规定,行李额规定,航班规定,航司规定}and[退票,改签,改期,退款,发票,报销,邮寄,修改,改名费,名字错了])or({多少钱,价格实时变动,机票多少钱,便宜,贵了,价格不一样}and[退,改到,改签,改期,发票,报销,经停,邮寄,托运,失败,错误的名字])


`
var lexer = new Lexer(testContent)
var parser = new Parser(lexer)
  parser.program()

  console.log(parser.output)

