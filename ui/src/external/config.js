let configs = {}
export function addConfig(key ,value) {
  configs[key] = value
}

export function getConfig(key){
  return configs[key]
}

