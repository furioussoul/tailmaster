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

