/**
 *  license(MIT)
 *  @author iview
 *  @github https://github.com/iview/iview
 */

import Vue from 'vue';
const isServer = Vue.prototype.$isServer;

export function stringify(obj) {
  return JSON.stringify(obj, function (key, val) {
    if (typeof val === 'function') {
      return val.toString();
    }
    return val;
  });
}

export function parse(string) {
  return JSON.parse(string, function (k, v) {
    if (k === 'render' ||
      k === 'renderProd' ||
      k === 'script') {
      return eval("(function(){return " + v + " })()")
    }
    return v;
  });
}

export function getQueryParam(name) {
  let hash = window.location.hash.substring(1),
    paramIndex = hash.indexOf('?') + 1,
    query = hash.substring(paramIndex, hash.length),
    lets = query.split("&");

  for (let i = 0; i < lets.length; i++) {
    let pair = lets[i].split("=");
    if (pair[0] === name) {
      return pair[1];
    }
  }
  return '';
}

//拷贝一层
export function copyProperties(target, source) {
  for (let key in source) {
    if (!target.hasOwnProperty(key)) {
      target[key] = source[key]
    }
  }
}

//非负整数
export function isNumber(obj) {
  const reg = /^-?\d+$/
  return reg.test(obj)
}

export function dataFormat(date, fmt) {
  if (!(date instanceof Date)) {
    date = new Date(date)
    if (date.toString() === 'Invalid Date') {
      return '无效时间戳'
    }
  }
  fmt = fmt || 'yyyy-M-d hh:mm:ss'
  let o = {
    "M+": date.getMonth() + 1, //月份
    "d+": date.getDate(), //日
    "h+": date.getHours(), //小时
    "m+": date.getMinutes(), //分
    "s+": date.getSeconds(), //秒
    "q+": Math.floor((date.getMonth() + 3) / 3), //季度
    "S": date.getMilliseconds() //毫秒
  };
  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (let k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
}

//获取 ?天?小时?分
const dayStamp = 24 * 60 * 60 * 1000
const hourStamp = 60 * 60 * 1000
const minStamp = 60 * 1000
export function getTimeSpan(timeStamp) {
  if (timeStamp <= 0) {
    return '1分钟'
  }
  let remainTime
  let day = Math.floor(timeStamp / dayStamp)
  day = day > 0 ? day + '天' : ''
  remainTime = timeStamp % dayStamp

  let hour = Math.floor(remainTime / hourStamp)
  hour = hour > 0 ? hour + '小时' : ''
  remainTime = remainTime % hourStamp

  let min = Math.floor(remainTime / minStamp)
  min = min > 0 ? min + '分钟' : ''

  let timeSpan = day + hour + min
  return !timeStamp ?
    '1分钟' :
    timeSpan
}

function s2ab(s) { //字符串转字符流
  let buf = new ArrayBuffer(s.length);
  let view = new Uint8Array(buf);
  for (let i = 0; i !== s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
  return buf;
}
// 将指定的自然数转换为26进制表示。映射关系：[0-25] -> [A-Z]。
function getCharCol(n) {
  let temCol = '',
    s = '',
    m = 0
  while (n > 0) {
    m = n % 26 + 1
    s = String.fromCharCode(m + 64) + s
    n = (n - m) / 26
  }
  return s
}

export function isPlain(obj) {
  return JSON.stringify(obj) === "{}"
}

export function clearProperties(obj) {
  for (let key in obj) {
    obj[key] = ''
  }
}

//拷贝el中的content
export function jsCopy(el, content) {
  let copyDom = document.getElementById(el)
  copyDom.value = content
  copyDom.select()
  document.execCommand('Copy')
}

export function exportExcel(json, el, type) {
  if (!json || !json.length) {
    this.$Message.destroy()
    this.$Message.warning('查询不到数据')
    return
  }

  for (let i = 0; i < json.length; i++) {
    let row = json[i];
    for (let key in row) {
      if (row[key] !== '' && row[key] !== 0 && !row[key]) {
        row[key] = ''
      }
    }
  }

  let tmpDown; //导出的二进制对象
  let tmpdata = json[0];
  json.unshift({});
  let keyMap = []; //获取keys
  for (let k in tmpdata) {
    keyMap.push(k);
    json[0][k] = k;
  }
  tmpdata = []; //用来保存转换好的json
  json.map((v, i) => keyMap.map((k, j) => Object.assign({}, {
    v: v[k],
    position: (j > 25 ? getCharCol(j) : String.fromCharCode(65 + j)) + (i + 1)
  }))).reduce((prev, next) => prev.concat(next)).forEach((v, i) => tmpdata[v.position] = {
    v: v.v
  });
  let outputPos = Object.keys(tmpdata); //设置区域,比如表格从A1到D10
  let tmpWB = {
    SheetNames: ['mySheet'], //保存的表标题
    Sheets: {
      'mySheet': Object.assign({}, tmpdata, //内容
        {
          '!ref': outputPos[0] + ':' + outputPos[outputPos.length - 1] //设置填充区域
        })
    }
  };
  tmpDown = new Blob([s2ab(XLSX.write(tmpWB, {
      bookType: (type === undefined ? 'xlsx' : type),
      bookSST: false,
      type: 'binary'
    } //这里的数据是用来定义导出的格式类型
  ))], {
    type: ""
  }); //创建二进制对象写入转换好的字节流
  let href = URL.createObjectURL(tmpDown); //创建对象超链接
  document.getElementById(el).href = href; //绑定a标签
  document.getElementById(el).click(); //模拟点击实现下载
  setTimeout(function () { //延时释放
    URL.revokeObjectURL(tmpDown); //用URL.revokeObjectURL()来释放这个object URL
  }, 100);
}

export function checkPhone(value) {
  return /^1[34578]\d{9}$/.test(value)
}

export function getCookie(name) {
  const reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)")
  let arr
  if (arr = document.cookie.match(reg)) {
    return decodeURIComponent(arr[2])
  }
  return null
}

/**
 * 节流阀，设置time（毫秒）防止time时间内重复请求接口
 * 将http请求方法作为callback传入闭包函数即可
 */
export function lock() {
  let lock = false;
  let releaseLock = function () {
    lock = false
  }
  //###这个闭包只能给同一个函数使用!!
  return function (callback) {
    if (lock) {
      return
    }
    lock = true
    callback(function () {
      //接口响应后设置lock=false, 定时是因为接口响应快于手速
      setTimeout(releaseLock, 1000)
    })
  }
}

/**
 * 判断字符串中是否存在中文
 * @param str {string} 需要判断的字符串
 * @returns {boolean}
 */
export function containsChineseChar(str) {
  const reg = /[\u4E00-\u9FA5\uF900-\uFA2D]/;
  return reg.test(str);
}

//获取url中的参数
export function getParamInUrl(url) {
  const pattern = /(\w+)=(\w+)/ig;
  var param = {};
  url.replace(pattern, function (a, key, val) {
    param[key] = val;
  });
  return param;
}

// 判断参数是否是其中之一
export function oneOf(value, validList) {
  for (let i = 0; i < validList.length; i++) {
    if (value === validList[i]) {
      return true;
    }
  }
  return false;
}

export function camelcaseToHyphen(str) {
  return str.replace(/([a-z])([A-Z])/g, '$1-$2').toLowerCase();
}

// For Modal scrollBar hidden
let cached;
export function getScrollBarSize(fresh) {
  if (isServer) return 0;
  if (fresh || cached === undefined) {
    const inner = document.createElement('div');
    inner.style.width = '100%';
    inner.style.height = '200px';

    const outer = document.createElement('div');
    const outerStyle = outer.style;

    outerStyle.position = 'absolute';
    outerStyle.top = 0;
    outerStyle.left = 0;
    outerStyle.pointerEvents = 'none';
    outerStyle.visibility = 'hidden';
    outerStyle.width = '200px';
    outerStyle.height = '150px';
    outerStyle.overflow = 'hidden';

    outer.appendChild(inner);

    document.body.appendChild(outer);

    const widthContained = inner.offsetWidth;
    outer.style.overflow = 'scroll';
    let widthScroll = inner.offsetWidth;

    if (widthContained === widthScroll) {
      widthScroll = outer.clientWidth;
    }

    document.body.removeChild(outer);

    cached = widthContained - widthScroll;
  }
  return cached;
}

// watch DOM change
export const MutationObserver = isServer ? false : window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver || false;

const SPECIAL_CHARS_REGEXP = /([\:\-\_]+(.))/g;
const MOZ_HACK_REGEXP = /^moz([A-Z])/;

function camelCase(name) {
  return name.replace(SPECIAL_CHARS_REGEXP, function (_, separator, letter, offset) {
    return offset ? letter.toUpperCase() : letter;
  }).replace(MOZ_HACK_REGEXP, 'Moz$1');
}
// getStyle
export function getStyle(element, styleName) {
  if (!element || !styleName) return null;
  styleName = camelCase(styleName);
  if (styleName === 'float') {
    styleName = 'cssFloat';
  }
  try {
    const computed = document.defaultView.getComputedStyle(element, '');
    return element.style[styleName] || computed ? computed[styleName] : null;
  } catch (e) {
    return element.style[styleName];
  }
}

// firstUpperCase
function firstUpperCase(str) {
  return str.toString()[0].toUpperCase() + str.toString().slice(1);
}
export {firstUpperCase};

// Warn
export function warnProp(component, prop, correctType, wrongType) {
  correctType = firstUpperCase(correctType);
  wrongType = firstUpperCase(wrongType);
  console.error(`[iView warn]: Invalid prop: type check failed for prop ${prop}. Expected ${correctType}, got ${wrongType}. (found in component: ${component})`);    // eslint-disable-line
}

export function typeOf(obj) {
  const toString = Object.prototype.toString;
  const map = {
    '[object Boolean]': 'boolean',
    '[object Number]': 'number',
    '[object String]': 'string',
    '[object Function]': 'function',
    '[object Array]': 'array',
    '[object Date]': 'date',
    '[object RegExp]': 'regExp',
    '[object Undefined]': 'undefined',
    '[object Null]': 'null',
    '[object Object]': 'object'
  };
  return map[toString.call(obj)];
}

// deepCopy
function deepCopy(data) {
  const t = typeOf(data);
  let o;

  if (t === 'array') {
    o = [];
  } else if (t === 'object') {
    o = {};
  } else {
    return data;
  }

  if (t === 'array') {
    for (let i = 0; i < data.length; i++) {
      o.push(deepCopy(data[i]));
    }
  } else if (t === 'object') {
    for (let i in data) {
      o[i] = deepCopy(data[i]);
    }
  }
  return o;
}

export {deepCopy};

// scrollTop animation
export function scrollTop(el, from = 0, to, duration = 500) {
  if (!window.requestAnimationFrame) {
    window.requestAnimationFrame = (
      window.webkitRequestAnimationFrame ||
      window.mozRequestAnimationFrame ||
      window.msRequestAnimationFrame ||
      function (callback) {
        return window.setTimeout(callback, 1000 / 60);
      }
    );
  }
  const difference = Math.abs(from - to);
  const step = Math.ceil(difference / duration * 50);

  function scroll(start, end, step) {
    if (start === end) return;

    let d = (start + step > end) ? end : start + step;
    if (start > end) {
      d = (start - step < end) ? end : start - step;
    }

    if (el === window) {
      window.scrollTo(d, d);
    } else {
      el.scrollTop = d;
    }
    window.requestAnimationFrame(() => scroll(d, end, step));
  }

  scroll(from, to, step);
}

// Find components upward
function findComponentUpward(context, componentName, componentNames) {
  if (typeof componentName === 'string') {
    componentNames = [componentName];
  } else {
    componentNames = componentName;
  }

  let parent = context.$parent;
  let name = parent.$options.name;
  while (parent && (!name || componentNames.indexOf(name) < 0)) {
    parent = parent.$parent;
    if (parent) name = parent.$options.name;
  }
  return parent;
}
export {findComponentUpward};

// Find component downward
function findComponentDownward(context, componentName) {
  const childrens = context.$children;
  let children = null;

  if (childrens.length) {
    childrens.forEach(child => {
      const name = child.$options.name;
      if (name === componentName) {
        children = child;
      }
    });

    for (let i = 0; i < childrens.length; i++) {
      const child = childrens[i];
      const name = child.$options.name;
      if (name === componentName) {
        children = child;
        break;
      } else {
        children = findComponentDownward(child, componentName);
        if (children) break;
      }
    }
  }
  return children;
}
export {findComponentDownward};

// Find components downward
function findComponentsDownward(context, componentName, components = []) {
  const childrens = context.$children;

  if (childrens.length) {
    childrens.forEach(child => {
      const name = child.$options.name;
      const childs = child.$children;

      if (name === componentName) components.push(child);
      if (childs.length) {
        const findChilds = findComponentsDownward(child, componentName, components);
        if (findChilds) components.concat(findChilds);
      }
    });
  }
  return components;
}
export {findComponentsDownward};

/* istanbul ignore next */
const trim = function (string) {
  return (string || '').replace(/^[\s\uFEFF]+|[\s\uFEFF]+$/g, '');
};

/* istanbul ignore next */
export function hasClass(el, cls) {
  if (!el || !cls) return false;
  if (cls.indexOf(' ') !== -1) throw new Error('className should not contain space.');
  if (el.classList) {
    return el.classList.contains(cls);
  } else {
    return (' ' + el.className + ' ').indexOf(' ' + cls + ' ') > -1;
  }
}

/* istanbul ignore next */
export function addClass(el, cls) {
  if (!el) return;
  let curClass = el.className;
  const classes = (cls || '').split(' ');

  for (let i = 0, j = classes.length; i < j; i++) {
    const clsName = classes[i];
    if (!clsName) continue;

    if (el.classList) {
      el.classList.add(clsName);
    } else {
      if (!hasClass(el, clsName)) {
        curClass += ' ' + clsName;
      }
    }
  }
  if (!el.classList) {
    el.className = curClass;
  }
}

/* istanbul ignore next */
export function removeClass(el, cls) {
  if (!el || !cls) return;
  const classes = cls.split(' ');
  let curClass = ' ' + el.className + ' ';

  for (let i = 0, j = classes.length; i < j; i++) {
    const clsName = classes[i];
    if (!clsName) continue;

    if (el.classList) {
      el.classList.remove(clsName);
    } else {
      if (hasClass(el, clsName)) {
        curClass = curClass.replace(' ' + clsName + ' ', ' ');
      }
    }
  }
  if (!el.classList) {
    el.className = trim(curClass);
  }
}
export function getBreadcrumb(menus, path) {
  let breadcrumb = [];
  if (menus && menus.length) {
    let flag = false;
    for (let i = 0; i < menus.length; i++) {

      if (flag) {
        return breadcrumb;
      }

      //mtype === 1 说明menu是个页面
      if ((menus[i].mtype === 1 || menus[i].mtype === -1) && path.indexOf(menus[i].url) !== -1 && menus[i].url.substr(-1, 1) !== '/') {
        flag = true;
        return [menus[i]];
      }

      breadcrumb = getBreadcrumb(menus[i].subMenuList, path);
      if (breadcrumb.length) {
        return [menus[i]].concat(breadcrumb);
      }
    }
  }
  return breadcrumb;
}

// make vue router path
export function getPages(menus) {
  let pages = [];
  if (menus && menus.length > 0) {
    for (let i = 0; i < menus.length; i++) {
      if (!menus[i].subMenuList || menus[i].subMenuList.length === 0) {
        pages.push(menus[i]);
      } else {
        pages = pages.concat(getPages(menus[i].subMenuList));
      }
    }
  }
  return pages;
}
