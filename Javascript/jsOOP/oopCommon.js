/*
一个类继承另一个类
o:子类
p:父类
*/
function extend(o,p){
for(var prop in p){
o[prop] = p[prop];
}
}


function classOf(o){
return Object.prototype.toString.call(o).slice(8,-1);
}