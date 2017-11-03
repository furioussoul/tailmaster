let Token = require('./Token');
let Word = require('./Token').Word
let Num = require('./Token').Num
let Tag = require('./Tag')

Lexer.prototype.test = function (token) {
    return token === this.getCurrentToken()
}

Lexer.prototype.match = function (token) {
    if (token === this.getCurrentToken()) {
        this.advance()
        return true
    } else {
        throw new Error('syntax error');
    }
}

Lexer.prototype.advance = function () {
    if (this.input.length === this.charIndex + 1) {
        this.currentToken = Token.eof
        return
    }
    this.charIndex++
    this.lexeme = this.input[this.charIndex]

    switch (this.lexeme) {
        case '&':
            this.currentToken = Token.and
            break
        case '|':
            this.currentToken = Token.or
            break
        case'(':
            this.currentToken = Token.lp
            break
        case')':
            this.currentToken = Token.rp
            break
        default:
            if (this.isAlnum(this.lexeme)) {
                this.currentToken = Token.char
                break
            }
            throw new Error("syntax error")
    }
}

Lexer.prototype.getCurrentToken = function () {
    return this.currentToken
}

Lexer.prototype.getLexeme = function () {
    return this.lexeme
}

Lexer.prototype.isAlnum = function (char) {
    return this.isDigit(char) | this.isAlphabetic(char)
}

Lexer.prototype.isDigit = function (char) {
    let reg = /[+|-]?[0-9]/
    return reg.test(char)
}

Lexer.prototype.isAlphabetic = function (char) {
    let reg = /[a-zA-Z]/
    return reg.test(char)
}

function Lexer(input) {
    let TokenMap = []
    const ASCII_COUNT = 128
    ;(function initTokenMap() {
        for (let i = 0; i < ASCII_COUNT; i++) {
            TokenMap[i] = Token.char
          this.reserve('AND','AND')
          this.reserve('OR','OR')
        }
    })()

    this.charIndex = -1
    this.line = 1
    this.input = ''

    let peek = '',
        words = {}

    this.reserve = function (word) {
        words[word.lexeme] = word
    }
    this.scan = function (input) {
        let inputNoBlank = ''
        let breakLineIndex = -1
        for (let i = 0; i < input.length; i++) {
            peek = input[i]
            if (peek === ' ' || peek === '\t') {
            } else if (peek === '/' && input[i + 1] === '/') {
                breakLineIndex = input.indexOf('\n', breakLineIndex);
                if (breakLineIndex === -1) {
                    // 注释后没有换行
                    i = input.length
                } else {
                    i = ++breakLineIndex
                }
            } else if (peek === '\n') {
                this.line++
            }else if(peek === 'A' && input[i+1] === 'N' && input[i+2] === 'D'){

            }else if(peek === 'O' && input[i+1] === 'R') {

            } else {
                inputNoBlank += input[i]
            }
        }

        let i = 0
        peek = inputNoBlank[i]
        if (this.isDigit(peek)) {
            let val = 0
            do {
                val = 10 * val + Number(peek)
                i++
                peek = inputNoBlank[i]
            } while (this.isDigit(peek))
            return new Num(val)
        }

      if (this.isAlnum(peek)) {
        let val = 0
        do {
          val += peek
          i++
          peek = inputNoBlank[i]
        } while (this.isAlnum(peek));

        let word = words.get(val);
        if (word) return word;
        return word;
      }
    }



    this.reserve(new Word(Tag.TRUE, 'true'))
    this.reserve(new Word(Tag.FALSE, 'false'))

    this.advance()
}

module.exports = Lexer
