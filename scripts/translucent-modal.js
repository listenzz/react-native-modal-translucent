const fs = require("fs-extra");
const path = require("path");

let modal;
let modalComponent;
if (__dirname.search("node_modules") === -1) {
  modal = path.resolve(
    __dirname,
    "../node_modules/react-native/Libraries/Modal/Modal.js"
  );
  modalComponent = path.resolve(
    __dirname,
    "../node_modules/react-native/Libraries/Modal/RCTModalHostViewNativeComponent.js"
  );
} else {
  modal = path.resolve(
    __dirname,
    "../../react-native/Libraries/Modal/Modal.js"
  );
  modalComponent = path.resolve(
    __dirname,
    "../../react-native/Libraries/Modal/RCTModalHostViewNativeComponent.js"
  );
}

if (fs.existsSync(modalComponent)) {
  fs.readFile(modalComponent, "utf8", function(err, data) {
    if (data.search("TranslucentModalHostView") === -1) {
      // const Platform = require('Platform');
      let str = data.replace(
        /'RCTModalHostView'/gm,
        `Platform.OS === 'ios' ? 'RCTModalHostView' : 'TranslucentModalHostView'`
      );
      str = str.replace(
        /module\.exports/,
        `const Platform = require('Platform');\nmodule\.exports`
      );
      fs.outputFile(modalComponent, str);
    }
  });
} else {
  fs.readFile(modal, "utf8", function(err, data) {
    if (data.search("TranslucentModalHostView") === -1) {
      let str = data.replace(
        /^ *const +RCTModalHostView.*$/gm,
        `const RCTModalHostView = Platform.OS === 'ios' ? requireNativeComponent('RCTModalHostView') : requireNativeComponent('TranslucentModalHostView');`
      );
      fs.outputFile(modal, str);
    }
  });
}
