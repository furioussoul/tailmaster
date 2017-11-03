class Node {
  constructor(label, left, right) {
    this.label = label;
    this.leftChild = left;
    this.rightChild = right
  }
}

class BinaryTreeParser {

  constructor(inputString){
    this.m_inputString = inputString
    this.m_index = 0
  }

  //初始化输入字符串和索引的构造函数，略
  parseNode() {
    let lookAheadIndex = this.m_index;

    let lookAheadChar = this.m_inputString[lookAheadIndex];

    if (lookAheadChar === 'N') {
      //采用N → a(N, N)继续分析
      let label = this.m_inputString[this.m_index++]; //解析字母
      this.m_index++; //解析左括号，因为不需要使用它的值，所以直接跳过

      let left = this.parseNode(); //非终结符N，递归调用
      this.m_index++; //解析逗号，跳过

      let right = this.parseNode(); //非终结符N，递归调用

      this.m_index++; //解析右括号，跳过

      return new Node(label, left, right);
    }
    else if (lookAheadChar === ',' || lookAheadChar === ')') {
      //采用N → ε继续分析
      return null;
    }
    else {
      throw new Error('syntax error')
    }
  }
}


program -> block
block -> (stmts) | ε
stmts -> stmts stmt | block
stmt -> bool loc | loc
bool ->  OR loc | AND loc
loc -> Number | ID
/*
 (
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
 )
 */
