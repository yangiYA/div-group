function toHashById() { // Variable parameter
  var delimiter = "";
  var tmp = {};
  for (var i = 0; i < arguments.length; i++) {
    var value = $('#'+ arguments[i]).val();
    if (typeof value === "undefined") {
      console.log("id " + arguments[i] + "is undefined !");
    }else{
      tmp[arguments[i]] = $('#'+ arguments[i]).val();
    }
  }
  return tmp;
}

/*
 * return : textarea empty line is not contained.
 */
function toHashByIdTextareaAsArray() { // Variable parameter
  var delimiter = "";
  var tmp = {};
  for (var i = 0; i < arguments.length; i++) {
    var tag = $('#'+ arguments[i]);
    if (typeof tag === "undefined") {
      console.log("id " + arguments[i] + "is undefined !");
    }else {
      var tagName = tag.prop("tagName");
      var value = tag.val();
      if (tagName.toUpperCase() == "TEXTAREA" ) {
        tmp[arguments[i]] = lines2Array(value , false);
      }else{
        tmp[arguments[i]] = value;
      }
    }
  }
  return tmp;
}

var addEventById = function(id , eventName , fun){
  document.getElementById(id).addEventListener(eventName,fun ,false);
};

function textarea2ArrayById(id){
  var tarea = $('#'+ id);
  if (!tarea){
    throw new TypeError("There is no element in id. id:" + id );
  }
  var tagName = tarea.prop("tagName");
  if (tagName.toUpperCase() != "TEXTAREA" ){
    throw new TypeError("id="+ id + ": element is not textare. tagName=" + tagName + " : " +  tagName.toUpperCase() );
  }
  var lines = tarea.val().replace(/\r\n/g,"\n").split("\n");
  return lines.filter(x => x.trim() != "");
}

function lines2Array(stringLines,needEmptyString){
  var lines = (stringLines + "").replace(/\r\n/g,"\n").split("\n");
  if (needEmptyString){
    return lines;
  }else{
    return lines.filter(x => x.trim() != "");
  }
}

function getByJsonPath(data, path) {
  var val = data;
  var segments = path.split('.');
  for (var i = 0; i < segments.length && val !== undefined; i++) {
    var seg = segments[i];
    if (seg === "") {
      return data;
    } else {
      if (typeof val === 'object') {
        val = val[seg];
      } else {
        return undefined;
      }
    }
  }
  return val;
}

function curry(f){
    return function _curry(xs){
        return xs.length < f.length ? function(x){ return _curry(xs.concat([x])); } : f.apply(undefined, xs);
    }([]);
}

/*
 * arg0      : expires
 * arg1,2.3. : id list
 * @see : js-cookie : https://github.com/js-cookie/js-cookie
 */
function saveCookieByIdList() { // Variable parameter
  var exp = arguments[0];
  // console.log("*********** saveCookieByIdList fn="+fn);
  for (var i = 1; i < arguments.length; i++) {
    var id = arguments[i];
    // console.log("pathname="+location.pathname);
    Cookies.set(id, $('#' + id ).val() , { expires: exp , path: location.pathname });
  }
}

/*
 * arg0,1,2.3 ... : cookie name list
 */
function deleteCookiesCurrentPass() { // Variable parameter
  for (var i = 0; i < arguments.length; i++) {
    Cookies.remove(arguments[i] , { path: location.pathname });
  }
}

function cookieGetOrEmpty(id){
  var tmp = Cookies.get(id)
  if (typeof tmp === "undefined") {
    return "";
  }else{
    return tmp;
  }
}

function addLoadImg(){
  document.body.insertAdjacentHTML('beforeend','<div id="loadContainer"><div class="loadimg"><img src="/img/load.gif" width="200" height="200"><div></div>');
}

function loadingOn() { $("#loadContainer").fadeIn(400); }
function loadingOff(){ $("#loadContainer").fadeOut(100); }
