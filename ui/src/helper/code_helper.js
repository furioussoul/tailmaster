const REG_EX = new RegExp(/.([a-zA-Z]+)[\s]*=[\s]*([\s\S]*)(;*)/)

export function getConfig(code) {
  code = code.trim()
  let config = {
    children :[]
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
        configValue = eval('(' + configValue+')')
      } else if (configValue.indexOf('function') === 0) {
        //function
        configValue = '(function () { \r\n return ' + configValue + '})()'
        configValue = eval(configValue)
      } else if (configValue[0] === '[') {
        //array
        const reg = /\[([\s\S]*)\]/
        configValue = configValue.match(reg)[1].split(',')
        let emptyIndex = configValue.indexOf('')
        if(emptyIndex > -1){
          configValue.splice(emptyIndex,1)
        }
      }else if(configValue.indexOf('null') === 0){
        configValue = null
      }
      config[configKey] = configValue
    }
  })
  config.code = code
  return config
}
