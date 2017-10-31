const REG_EX = new RegExp(/.([a-zA-Z]+)[\s]*=[\s]*([\s\S]*)(;*)/)

export function getVueCode(soul) {
  let temp = soul.template,
    childCode = ''

  temp = temp.trim()
  temp = temp.replace(';','')
  temp = temp.substring(1,temp.length)
  temp = temp.substring(0,temp.length-1)
  temp = temp.trim()

  soul.children.forEach(child => {
    childCode += getVueCode(child)
  })

  let model = soul.model;
  let props = ''
  let prop = {}
  for (let key in model) {
    if(model[key].exclude){
      continue
    }
    prop = {}
    prop[key] = model[key].value
    let propStr = JSON.stringify(prop)
    propStr = propStr.substring(1, propStr.length)
    propStr = propStr.substring(0, propStr.length - 1)

    //delete comma
    let match = propStr.match(/("([\s\S]*?)":)/)
    propStr = match[2]+'='+propStr.substring(match[1].length,propStr.length+1)

    props += ' ' + propStr + ' '
  }

  temp = temp.replace('{slot}', childCode)
  return temp.replace('{model}', props).trim()
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
