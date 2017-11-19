import {
  typeOf,
  isPlain
}from'../util/assist'
import pretty from 'pretty'
const REG_EX = new RegExp(/.([a-zA-Z]+)[\s]*=[\s]*([\s\S]*)(;*)/)

function getVueHtml(soul,data) {
  let temp = soul.template,
    childCode = '     '

  temp = temp.trim()
  temp = temp.replace(';', '')
  temp = temp.substring(1, temp.length)
  temp = temp.substring(0, temp.length - 1)
  temp = temp.trim()

  soul.children.forEach(child => {
    childCode += getVueHtml(child,data)
  })

  let model = soul.model,
    props = '',
    prop = {},
    slotName='',
    innerHTML = ''

  if(soul.slotName){
    //is a slot of parent
    if(!soul.showSlot){
      return ''
    }

    slotName = 'slot="'+soul.slotName+'"'
  }

  for (let key in model) {

    let propStr,
      prop = {}

    if (model[key].script === true) {
      if(model[key].vModel){
        if(model[key].type=== 'text'){
          data[model[key].value] = ''
        }else if(model[key].type=== 'json'){
          data[model[key].value] = {}
        }else {
          data[model[key].value] = []
        }

        props +=  key+ '=' + '"' + model[key].value + '"' + ' '
      }else {
        data[key] = model[key].value

        if(model[key].addModel){
          props += ' :' + key+ '=' + '"' + key + '"' + ' '
        }
      }

    }else if (model[key].exclude || false===model[key].value || '' === model[key].value || isPlain(model[key].value)) {
      //don't need compile this model value
      propStr = ''

    }else if(soul.type ==='Icon'){
      //is Icon
      prop[key] = model[key].value + ''
      propStr = JSON.stringify(prop)
      propStr = propStr.trim()
      //remove {}
      propStr = propStr.substring(1, propStr.length)
      propStr = propStr.substring(0, propStr.length - 1)
      //delete comma
      let match = propStr.match(/("([\s\S]*?)":)/)
      propStr = match[2] + '=' + propStr.substring(match[1].length, propStr.length + 1)
      propStr = propStr.trim()
      props += ' ' + propStr + ' '
    }else if(model[key].compileType==='innerHTML'){

      innerHTML+=model[key].value

    }else {
      if (model[key].ignore){
        props =''
      }else if (model[key].type === 'json') {
        //model value is object
        prop[key] = model[key].value
        propStr = prop[key] = JSON.stringify(prop)
        propStr = propStr.substring(1, propStr.length)
        propStr = propStr.substring(0, propStr.length - 1)
        let match = propStr.match(/("([\s\S]*?)":)/)
        propStr = match[2] + '=' + propStr.substring(match[1].length, propStr.length + 1)
        propStr = propStr.replace(/,/g, ';')
        propStr = propStr.replace(/"/g, '')
        propStr = propStr.replace('{', '"')
        propStr = propStr.replace('}', '"')
        propStr = propStr.trim()
        props += ' ' + propStr + ' '
      }else {
        prop[key] = model[key].value + ''
        propStr = JSON.stringify(prop)
        propStr = propStr.trim()
        //remove {}
        propStr = propStr.substring(1, propStr.length)
        propStr = propStr.substring(0, propStr.length - 1)
        //delete comma
        let match = propStr.match(/("([\s\S]*?)":)/)
        propStr = match[2] + '=' + propStr.substring(match[1].length, propStr.length + 1)
        propStr = propStr.trim()
        props += ' ' + propStr + ' '
      }
    }
  }

  temp = temp.replace('{slot}', childCode)
  temp = temp.replace('{slotName}', slotName)
  temp = temp.replace('{innerHTML}', innerHTML).trim()
  return temp.replace('{model}', props).trim()
}

function getVueScript(data) {
  let script=`
  <script>
    export default{
      data(){
        return{
          {dataSlot}
        }
      }
    }
  </script>
  `
  let dataStr =  JSON.stringify(data)
  dataStr=dataStr.substring(1,dataStr.length)
  dataStr=dataStr.substring(0,dataStr.length - 1)
  return script.replace('{dataSlot}',dataStr)
}

export function getVueCode(soul) {
  let data = {},
    vueHtml = '<template>' + getVueHtml(soul,data) + '</template>',
  vueScript = getVueScript(data)
  return vueHtml+'\r\n'+pretty(vueScript)
}

export function addRenderFn(soul) {
  let config = makeControl(soul.code);
  soul.render = config.render
  soul.renderProd = config.renderProd
  soul.children.forEach(child => {
    addRenderFn(child)
  })
}

export function makeControl(code) {
  code = code.trim()
  let config = {
    children: []
  }

  let fragments = code.split('exports')
  fragments.forEach(fragment => {
    let match = fragment.match(REG_EX);
    if (match) {
      let configKey = match[1]
      let configValue = match[2]

      if (!isNaN(Number(configValue))) {
        //数字
        configValue = Number(configValue)

      } else if (configValue[0] === '\'' || configValue[0] === '\"') {
        //string
        const reg = /['"](\S+)['"]/
        configValue = configValue.match(reg)[1]

      } else if (configValue[0] === '{') {
        //obj
        configValue = configValue.replace(/\r\n/, "")
        configValue = configValue.replace(/;/, "")
        configValue = eval('(' + configValue + ')')
      } else if (configValue.indexOf('function') === 0) {
        //function
        configValue = '(function () { \r\n return ' + configValue + '})()'
        configValue = eval(configValue)
      } else if (configValue[0] === '[') {
        //array
        const reg = /\[([\s\S]*)\]/
        configValue = configValue.match(reg)[1].split(',')
        let emptyIndex = configValue.indexOf('')
        if (emptyIndex > -1) {
          configValue.splice(emptyIndex, 1)
        }
      } else if (configValue.indexOf('null') === 0) {
        configValue = null
      }
      config[configKey] = configValue
    }
  })
  config.code = code
  return config
}
