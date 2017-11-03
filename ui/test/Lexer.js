var Token = require('./Token').Token;
var Word = require('./Token').Word
var Num = require('./Token').Num
var Tag = require('./Tag')

Lexer.prototype.isAlnum = function (char) {
  return this.isDigit(char) | this.isAlphabetic(char)
}

Lexer.prototype.isDigit = function (char) {
  var reg = /[+|-]?[0-9]/
  return reg.test(char)
}

Lexer.prototype.isAlphabetic = function (char) {
  if(!char){
    return false
  }
  var reg = /[\u4e00-\u9fa5a-zA-Z]/
  return reg.test(char)
}

var words = {}
function reserve(word) {
  words[word.lexeme] = word
}

function Lexer(input) {

  reserve(new Word('and', Tag.AND))
  reserve(new Word('or', Tag.OR))

  this.line = 1
  this.input = input
  this.index = 0
  this.peek = ' '

  this.scan = function () {

    this.peek = this.input[this.index]
    while(this.peek === ' ' || this.peek === '\t' || this.peek === '\n'){
      if(this.peek === ' ' || this.peek === '\t'){
        this.index++
        this.peek = this.input[this.index]
      }else if(this.peek === '\n'){
        this.index++
        this.peek = this.input[this.index]
        this.line += 1;
      }else {
        break
      }
    }

    if(undefined === this.peek){
      return new Token(Token.eof)
    }

    switch (this.peek){
      case '(':
        this.index++
        return new Token(Tag.LP)
      case ')':
        this.index++
        return new Token(Tag.RP)
      case '\'':
        this.index++
        return new Token(Tag.QT)
    }

    if (this.isDigit(this.peek)) {
      var val = 0
      do {
        val = 10 * val + Number(this.peek)
        this.peek = this.input[++this.index]
      } while (this.isDigit(this.peek))
      return new Num(val)
    }

    if (this.isAlphabetic(this.peek)) {
      var val=''
      do {
        val += this.peek
        this.peek = input[++this.index]
      } while (this.isAlnum(this.peek));

      var word = words[val];
      if (word) return word;
      return new Word(val, Tag.ID);
    }

    this.index ++;
    var token = new Token(this.peek);
    this.peek = ' ';
    return token;
  }

  return this
}

module.exports = Lexer
