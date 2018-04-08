
/*
person class
*/
function Person(name,age){
this.name = name;
this.age = age;
this.hobby = new Array();
}

Person.prototype ={

/*
name prop
*/
getName : function(){
return this.name;
},

setName : function(name){
this.name = name;
},

showName : function(){
alert(this.name);
},

/*
age prop
*/
getAge : function(){
return this.age;
},

setAge : function(age){
this.age = age;
},


/*
hobby prop
*/
addHobby: function(key,value){
this.hobby[key] = value;
},

showHobby: function(){
var hobbyStr = "";
for(var h in this.hobby){
hobbyStr += h + "\r\n";
}
alert(hobbyStr);
}



}

/*
extend class(dynamic)
*/
Person.prototype.getGreeting = function(){
return "hello," + this.name;
}

