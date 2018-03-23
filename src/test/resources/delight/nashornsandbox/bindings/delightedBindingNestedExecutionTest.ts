//This file is used to test the binding's execution method
//yes, written in typescript because its easier to read.
class Nested {
    final() {
        return 12.0;
    }
}
class NestedObject {
    nested= new Nested();
}

//this is what we actually test.
let TestedNested = new NestedObject();