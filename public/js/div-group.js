/*
function toJsonString() { // Variable parameter
  var delimiter = "";
  var tmp = "";
  for (var i = 0; i < arguments.length; i++) {
    var value = $('#'+ arguments[i]).val();
    tmp = tmp + delimiter + `${arguments[i]} : "${value}"`;
    delimiter = ",";
  }
  var rtn = "{ "+ tmp + " }";
  return rtn;
}
*/

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


/*
function toJsonString() { // Variable parameter
  var delimiter = "";
  var tmp = "";
  for (var i = 0; i < arguments.length; i++) {
    var value = $('#'+ arguments[i]).val();
    tmp = tmp + delimiter + `${arguments[i]} : "${value}"`;
    delimiter = ",";
  }
  var rtn = "{ "+ tmp + " }";
  return rtn;
}
*/

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

/*
function stringArray2jsonArray(stringArray){
  console.log(stringArray);

  if (Array.isArray(stringArray) == false){
    throw new TypeError("parameter is no Array. param=" + stringArray );
  }
  var tmp = stringArray.map(x => '"' + x + '"').join(",");
  var rtn = "{ "+ tmp + " }";
  return rtn;
}
*/

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
