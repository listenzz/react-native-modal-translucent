const fs = require('fs-extra');
const path = require('path');

// 找到 modal 文件
const modal = path.resolve(__dirname, '../../react-native/Libraries/Modal/Modal.js')

fs.readFile(modal, 'utf8', function(err, data) { 
  let str = data.replace(/(const RCTModalHostView = )(requireNativeComponent\('RCTModalHostView', null\))/gm, 
  `$1Platform.OS === 'ios' ? $2 : requireNativeComponent('TranslucentModalHostView', null)`)
  fs.outputFile(modal, str)
});
