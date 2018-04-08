/*
全局函数工具类
*/

////返回客户端对象
function $(clientId){
return document.getElementById(clientId);
}

////获得SELECT选中项
function GetSelectObj(clientId){
var obj = $(clientId);

var index = obj.selectedIndex; // 选中索引
return obj.options[index];
}

////生产随机数
function GetRandom(n){return Math.floor(Math.random()*n+1)}

////存储键值对
function addKV(k,v){
localStorage.setItem(k,v);

}

////取得键值对的值
function getV(k){
return localStorage.getItem(k);
}

////获得本地存储的所有值并转化为字符串
function getAllValueToStr(){
var content = "";
 for(var i=0;i<localStorage.length;i++){
  //key(i)获得相应的键，再用getItem()方法获得对应的值
   content += localStorage.key(i)+ " : " + localStorage.getItem(localStorage.key(i)) + "<br />";
}
return content;
}

////显示当前时间
function GetCurrentDateTime(){
	var time ;
    with(new Date()){
		time =toLocaleString() + '星期' + '日一二三四五六'.charAt(getDay());
	}
	return time;
	}