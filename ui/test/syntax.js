var parser = function (content) {

  function extend(to, _from) {
    for (var key in _from) {
      to[key] = _from[key]
    }
    return to
  }

  function Token(tag) {
    this.tag = tag
  }

  extend(Token, {
    'and': 258,
    'or': 259,
    'id': 257,
    'number': 256,
    'lp': 260,//(
    'rp': 261,//)
    'qt': 262,//'
    'not': 263,//'
    'eof': -1
  })

  function Num(value) {
    this.tag = Tag.NUM
    this.lexeme = value
  }

  function Word(text, tag) {
    this.lexeme = text
    this.tag = tag
  }

  var Tag = {
    NUM: 256,
    ID: 257,
    AND: 258,
    OR: 259,
    LP: 260,
    RP: 261,
    QT: 262,
    NOT: 263
  }

  Lexer.prototype.isAlnum = function (char) {
    return this.isDigit(char) | this.isAlphabetic(char)
  }

  Lexer.prototype.isDigit = function (char) {
    var reg = /[+|-]?[0-9]/
    return reg.test(char)
  }

  Lexer.prototype.isAlphabetic = function (char) {
    if (!char) {
      return false
    }
    var reg = /[\u4e00-\u9fa5a-zA-Z0-9!]/
    return reg.test(char)
  }

  var words = {}

  function reserve(word) {
    words[word.lexeme] = word
  }

  function compareStrIgnoreCase(n1, n2) {
    if (undefined === n1 || undefined === n2) {
      return false
    }
    if (null === n1 || null === n2) {
      return false
    }
    return n1.toUpperCase() === n2.toUpperCase()
  }

  function Lexer(input) {

    reserve(new Word('AND', Tag.AND))
    reserve(new Word('OR', Tag.OR))
    reserve(new Word('!', Tag.NOT))

    this.line = 0
    this.input = input
    this.index = 0
    this.peek = ' '

    this.scan = function () {

      this.peek = this.input[this.index]
      while (this.peek === ' ' || this.peek === '\t' || this.peek === '\n') {
        if (this.peek === ' ' || this.peek === '\t') {
          this.index++
          this.peek = this.input[this.index]
        } else if (this.peek === '\n') {
          this.index++
          this.peek = this.input[this.index]
          this.line += 1;
        } else {
          break
        }
      }

      if (undefined === this.peek) {
        return new Token(Token.eof)
      }

      switch (this.peek) {
        case '(':
          this.index++
          return new Token(Tag.LP)
        case ')':
          this.index++
          return new Token(Tag.RP)
        case '\'':
          this.index++
          return new Token(Tag.QT)
        case '!':
          ++this.index
          return new Token(Tag.NOT)
      }

      if (this.isAlphabetic(this.peek)) {
        var val = ''
        do {
          val += this.peek
          this.peek = input[++this.index]
        } while (this.isAlnum(this.peek));

        if (compareStrIgnoreCase(val, 'AND')) {
          val = 'AND'
        }
        if (compareStrIgnoreCase(val, 'OR')) {
          val = 'OR'
        }
        var word = words[val];
        if (word) return word;
        return new Word(val, Tag.ID);
      }

      this.index++;
      var token = new Token(this.peek);
      this.peek = ' ';
      return token;
    }

    return this
  }

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
      space += ' '
    }
    return space
  }

//program -> block
//block -> (stmts) | e
//stmts -> stmt term
//term-> op stmt | e
//stmt -> (stmts) | factory | ! factory
//factory -> Number | 'Id'
  Parser.prototype.parse = function () {
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

//stmt -> (stmts) | factory | ! factory
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
    } else if (this.look.tag === Token.not) {
      this.match(Token.not)
      this.before = '!'
      this.factory()
    } else {
      this.factory()
    }
  }

//factory -> Number | 'Id'
  Parser.prototype.factory = function () {
    if (this.look.tag === Token.qt) {
      this.match(Token.qt)
      if (this.before === '!') {
        this.before = ''
        this.output += indent(this.blockDeep) + 'content<>' + '\'' + this.look.lexeme + '\''
      } else {
        this.output += indent(this.blockDeep) + 'content=' + '\'' + this.look.lexeme + '\''
      }
      this.nearby = this.look.lexeme ? this.look.lexeme : this.nearby
      this.match(Token.id)
      this.match(Token.qt)
    } else {
      if (this.before === '!') {
        this.before = ''
        this.output += indent(this.blockDeep) + 'content<>' + this.look.lexeme
      } else {
        this.output += indent(this.blockDeep) + 'content=' + this.look.lexeme
      }
      this.nearby = this.look.lexeme ? this.look.lexeme : this.nearby
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
      if (this.look.tag === Token.lp) {
        hint += ` about '('`
      } else if (this.look.tag === Token.rp) {
        hint += ` about ')'`
      }
      throw new Error('syntax error at line: ' + this.lexer.line + ' ;near: ' + this.nearby + ' ; ' + hint)
    }
  }

  var lexer = new Lexer(content)
  return new Parser(lexer)
}

var testCorrectContent1 = `
(
 !1 and '1投诉' or '315啊' and ('微博' or ! '律师' and (! 2123) ) and  !1 and '1投诉' or '315啊' and ('微博' or ! '律师' and (! 2123) )
 and ( !1 and '1投诉' or '315啊' and ('微博' or ! '律师' and (! 2123) ) and ( !1 and '1投诉' or '315啊' and ('微博' or ! '律师' and (! 2123) )) or ( !1 and '1投诉' or '315啊' and ('微博' or ! '律师' and (! 2123) )))
)
`
var testWrongContent1 = `(
 !!1 and '1投诉' or '315啊' and ('微博' or ! '律师' and (! 2123) ) and  !1 and '1投诉' or '315啊' and ('微博' or ! '律师' and (! 2123) )
 and ( !1 and '1投诉' or '315啊' and ('微博' or ! '律师' and (! 2123) ) and ( !1 and '1投诉' or '315啊' and ('微博' or ! '律师' and (! 2123) )) or ( !1 and '1投诉' or '315啊' and ('微博' or ! '律师' and (! 2123) )))
)
`
var testWrongContent2 = `(\n
  !1 and '1投诉' or '315啊' and ('微博' or ! '律师' and (! 2123) ) and  !1 and '1投诉' or '315啊' and ('微博' or ! '律师' and (! 2123) )
and ( !1 and '1投诉' or '315啊' and ('微博' or ! '律师' and (! 2123) ) and ( !1 and '1投诉' or '315啊' and ('微博' or ! '律师' and (! 2123) )) or ( !1 and '1投诉' or '315啊' and ('微博' or ! '律师' and (! 2123) )))
)
`
var ps = new parser(testWrongContent2)
ps.parse()
console.log(ps.output)

