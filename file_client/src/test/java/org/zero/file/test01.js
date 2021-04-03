(function(){/*

    Copyright The Closure Library Authors.
    SPDX-License-Identifier: Apache-2.0
   */
   'use strict';function aa(a){var b=0;return function(){return b<a.length?{done:!1,value:a[b++]}:{done:!0}}}function ba(a){var b="undefined"!=typeof m.Symbol&&p(m.Symbol,"iterator")&&a[p(m.Symbol,"iterator")];return b?b.call(a):{next:aa(a)}}var ca="function"==typeof Object.create?Object.create:function(a){function b(){}b.prototype=a;return new b},q="function"==typeof Object.defineProperties?Object.defineProperty:function(a,b,d){if(a==Array.prototype||a==Object.prototype)return a;a[b]=d.value;return a};
   function da(a){a=["object"==typeof globalThis&&globalThis,a,"object"==typeof window&&window,"object"==typeof self&&self,"object"==typeof global&&global];for(var b=0;b<a.length;++b){var d=a[b];if(d&&d.Math==Math)return d}throw Error("Cannot find global object");}var r=da(this),t="function"===typeof Symbol&&"symbol"===typeof Symbol("x"),m={},v={};function p(a,b){var d=v[b];if(null==d)return a[b];d=a[d];return void 0!==d?d:a[b]}
   function x(a,b,d){if(b)a:{var c=a.split(".");a=1===c.length;var f=c[0],h;!a&&f in m?h=m:h=r;for(f=0;f<c.length-1;f++){var e=c[f];if(!(e in h))break a;h=h[e]}c=c[c.length-1];d=t&&"es6"===d?h[c]:null;b=b(d);null!=b&&(a?q(m,c,{configurable:!0,writable:!0,value:b}):b!==d&&(void 0===v[c]&&(v[c]=t?r.Symbol(c):"$jscp$"+c),q(h,v[c],{configurable:!0,writable:!0,value:b})))}}var y;
   if(t&&"function"==typeof Object.setPrototypeOf)y=Object.setPrototypeOf;})