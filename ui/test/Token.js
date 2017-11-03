var util = require('./Util')
var Tag = require('./Tag')

function Token(tag) {
    this.tag = tag
}

util.extend(Token, {
    'and': 42,
    'or': 47,
    'plus': 43,
    'minus': 45,
    'lp': 40,//(
    'rp': 41,//)
    'lb': 91,//[
    'rb': 93,//]
    'lc': 123,//{
    'rc': 125,//}
    'char': -2, // any char
    'eof': -1
})

function Num(value) {
    this.tag = Tag.NUM
    this.value = value
}

function Word(tag, text) {
    this.tag = tag
    this.lexeme = text
}

module.exports.Token = Token
module.exports.Num = Num
module.exports.Word = Word
