var Student = function(name){
this.name = name;
}

Student.prototype = {
constructor : Student,
getName : function(){
return this.name;
}
}