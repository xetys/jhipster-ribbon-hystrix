"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = list;
var stop = /,$/;
function list(args) {
  var res = [];
  var temp = [];

  args.forEach(function (arg) {
    if (stop.test(arg)) {
      temp.push(arg.slice(0, -1));
      res.push(temp);
      temp = [];
    } else {
      temp.push(arg);
    }
  });
  res.push(temp);

  return res;
}
module.exports = exports['default'];