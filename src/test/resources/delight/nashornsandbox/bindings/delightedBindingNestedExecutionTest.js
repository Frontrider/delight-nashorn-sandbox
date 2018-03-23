//This file is used to test the binding's execution method
//yes, written in typescript because its easier to read.
var Nested = /** @class */ (function () {
    function Nested() {
    }
    Nested.prototype.final = function () {
        return 12.0;
    };
    return Nested;
}());
var NestedObject = /** @class */ (function () {
    function NestedObject() {
        this.nested = new Nested();
    }
    return NestedObject;
}());
//this is what we actually test.
var TestedNested = new NestedObject();
