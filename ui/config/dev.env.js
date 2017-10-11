var merge = require('webpack-merge')
var prodEnv = require('./prod.env.js')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"'
})
