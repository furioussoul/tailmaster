var path = require('path');
var webpack = require('webpack');
var merge = require('webpack-merge');
var webpackBaseConfig = require('./webpack.dist.base.conf.js');

process.env.NODE_ENV = 'production';

module.exports = merge(webpackBaseConfig, {
  entry: {
    main: './src/external/index.js'
  },
  output: {
    path: path.resolve(__dirname, '../dist/client'),
    publicPath: '/dist/client',
    filename: 'esview.min.js',
    library: 'esview',
    libraryTarget: 'umd',
    umdNamedDefine: true
  },
  externals: {
    vue: {
      root: 'Vue',
      commonjs: 'vue',
      commonjs2: 'vue',
      amd: 'vue'
    }
  },
  plugins: [
    // @todo
    new webpack.DefinePlugin({
      'process.env.NODE_ENV': '"production"'
    }),
    new webpack.optimize.UglifyJsPlugin({
      compress: {
        warnings: false
      }
    })
  ]
});
