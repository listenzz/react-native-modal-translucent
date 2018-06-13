const fs = require("fs-extra");
const path = require("path");

let modal;
if (__dirname.search("node_modules") === -1) {
  modal = path.resolve(
    __dirname,
    "../node_modules/react-native/Libraries/Modal/Modal.js"
  );
} else {
  modal = path.resolve(
    __dirname,
    "../../react-native/Libraries/Modal/Modal.js"
  );
}

fs.readFile(modal, "utf8", function(err, data) {
  if (data.search("TranslucentModalHostView") === -1) {
    let str = data.replace(
      /(const RCTModalHostView = )(requireNativeComponent\('RCTModalHostView', null\))/gm,
      `$1Platform.OS === 'ios' ? $2 : requireNativeComponent('TranslucentModalHostView', null)`
    );
    fs.outputFile(modal, str);
  }
});
