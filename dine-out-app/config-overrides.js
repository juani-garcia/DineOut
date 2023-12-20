const path = require('path')

module.exports = function override (config) {
  config.resolve.alias['@'] = path.resolve(__dirname, 'src')
  return config
}

// module.exports = {
//   // ... other overrides
//   jest: function (config) {
//     // Modify the Jest configuration
//     config.transform = {
//       '^.+\\.jsx?$': 'babel-jest',
//       '^.+\\.tsx?$': 'ts-jest'
//     }
//     config.moduleNameMapper = {
//       '^@/(.*)$': '<rootDir>/src/$1'
//     }
//     config.transformIgnorePatterns = ['\\.pnp\\.[^\\/]+$', '/node_modules/(?!(axios)/)']
//
//     config.testEnvironment = 'jsdom'
//     return config
//   }
// }
