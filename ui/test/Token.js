var util = require('./Util')
var Tag = require('./Tag')

function Token(tag) {
    this.tag = tag
}

util.extend(Token, {
    'and': 258,
    'or': 259,
    'id': 257,
    'number': 256,
    'lp': 260,//(
    'rp': 261,//)
    'qt': 262,//)
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

module.exports.Token = Token
module.exports.Num = Num
module.exports.Word = Word
