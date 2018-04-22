const fs = require('fs-extra');

// 找到 modal 文件
const modal = './node_modules/react-native/Libraries/Modal/Modal.js';

fs.readFile(modal, 'utf8', function(err, data) { 
  let str = data.replace(/(requireNativeComponent\('RCTModalHostView', null\))/gm, 
  `Platform.OS === 'ios' ? $1 : requireNativeComponent('TranslucentModalHostView', null)`)
  fs.outputFile(modal, str)
});
