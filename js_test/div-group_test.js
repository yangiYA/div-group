// var assert = require('assert');
var assert = chai.assert;
describe('div-group.js test', function() {

/*
  describe('Test : #toJsonString()', function() {
    it('should return String as json', function() {
      console.log("*****toJsonString")
      var jsn = toJsonString("text1","text2","text3","text4");
      console.log("toJsonString return = " + jsn)
      assert.equal(jsn , '{ text1 : "text1",text2 : "text2",text3 : "text3",text4 : "text4" }',"toJson ok?");
    });
  });
*/

  describe('Test : #textarea2ArrayById()', function() {
    it('when textarea id ,it should return array', function() {
      console.log("*****textarea2ArrayById")
      var tarea = textarea2ArrayById("textarea1");
      console.log("textarea2ArrayById return = " + tarea);

      assert.isArray(tarea , "tarea must be Array. tarea="+tarea);

      var expected = ["textarea1-1","textarea1-2","textarea1-3"];
      assert.equal(tarea.length , expected.length);
      for(let i = 0; i < tarea.length; i++) {
        assert.equal(tarea[i] , expected[i] , "idx="+i);
      }
      // assert.deepEqual(tarea,["textarea1-1","textarea1-2","textarea1-3"]);
    });
  });

/*
  describe('Test : #stringArray2jsonArray()', function() {
    it('when textarea id ,it should return json array', function() {
      console.log("*****stringArray2jsonArray")
      var jArray = stringArray2jsonArray(["a1" , "b2" , "c3"]);
      console.log("textarea2ArrayById return = " + jArray);

      var expected = ['{ "a1","b2","c3" }'];
      assert.equal(jArray , expected);
    });
  });
*/

  describe('Test : #toHashById()', function() {
    var rtn = toHashById("text1","text2","text3","text4");
    it('should return Hash', function() {
      console.log("*****toHashById")
      console.log("toHashById return = " + rtn)
      var expected = { text1 : "text1" , text2 : "text2" , text3 : "text3" ,text4 : "text4" };
      assert.equal(Object.keys(rtn).length ,Object.keys(expected).length);
      var key;
      for (key in rtn) {
        assert.equal(rtn[key] , expected[key] , "key=" + key);
      }
    });
  });

  describe('Test : #toHashByIdTextareaAsArray()', function() {
    var rtn0 = toHashByIdTextareaAsArray("text1","text2","text3","text4");
    it('When No textarea, should return Hash', function() {
      console.log("*****toHashByIdTextareaAsArray_1")
      console.log("toHashByIdTextareaAsArray return = " + rtn0)
      var expected = { text1 : "text1" , text2 : "text2" , text3 : "text3" ,text4 : "text4" };
      assert.equal(Object.keys(rtn0).length ,Object.keys(expected).length);
      var key;
      for (key in rtn0) {
        assert.equal(rtn0[key] , expected[key] , "key=" + key);
      }
    });

    rtn1 = toHashByIdTextareaAsArray("text1","textarea1");
    it('When it contains textarea, should return Hash that contains array.', function() {
      console.log("*****toHashByIdTextareaAsArray_2")
      console.log("toHashByIdTextareaAsArray_2 return = " + rtn1)
      var expected = { text1 : "text1" , text2 : "textarea1" };
      assert.equal(Object.keys(rtn1).length ,Object.keys(expected).length);

      assert.equal(rtn1['text1'] , expected['text1'] , "text1");
      var textarea1 = rtn1['textarea1'];

      var textarea1Expected = ["textarea1-1","textarea1-2","textarea1-3"];
      // assert.equal(textarea1.length , textarea1Expected.length);
      for(let i = 0; i < textarea1.length; i++) {
        assert.equal(textarea1[i] , textarea1Expected[i] , "idx="+i);
      }

    });

  });

  describe('Test : #getByJsonPath()', function() {
    var hash0 = {a1: "a1"
               , arry1 : ["arry1_0","arry1_1" ]
               , b1: {  b1_1 : "b1_1"
                         , b1_2 : {b1_2_1 : "b1_2_1" }} };
    it('"a1" should return value', function() {
      var rtn = getByJsonPath(hash0 , "a1");
      console.log("a1 = " + rtn)
      assert.equal(rtn ,"a1");
    });

    it('"arry1.0" should return value', function() {
      var rtn = getByJsonPath(hash0 , "arry1.0");
      console.log("arry1.0 = " + rtn)
      assert.equal(rtn ,"arry1_0");
    });

    it('"arry1" should return Array', function() {
      var rtn = getByJsonPath(hash0 , "arry1");
      console.log("arry1 = " + rtn)
      assert.equal(Array.isArray(rtn) ,true);
    });

    it('"arry1.1" should return value', function() {
      var rtn = getByJsonPath(hash0 , "arry1.1");
      console.log("arry1.1 = " + rtn)
      assert.equal(rtn ,"arry1_1");
    });

    it('"b1.b1_2.b1_2_1" should return value', function() {
      var rtn = getByJsonPath(hash0 , "b1.b1_2.b1_2_1");
      console.log("b1.b1_2.b1_2_1 = " + rtn)
      assert.equal(rtn ,"b1_2_1");
    });

    it('"nodata" should return undefined', function() {
      var rtn = getByJsonPath(hash0 , "nodata");
      console.log("nodata = " + rtn)
      assert.equal(rtn ,undefined);
    });

    it('"arry1.99" should return undefined', function() {
      var rtn = getByJsonPath(hash0 , "arry1.99");
      console.log("arry1.99 = " + rtn)
      assert.equal(rtn ,undefined);
    });

    it('"b1.b1_2.nodata" should return undefined', function() {
      var rtn = getByJsonPath(hash0 , "b1.b1_2.nodata");
      console.log("b1.b1_2.nodata = " + rtn)
      assert.equal(rtn ,undefined);
    });

  });

});
