/*
ȫ�ֺ���������
*/

////���ؿͻ��˶���
function $(clientId){
return document.getElementById(clientId);
}

////���SELECTѡ����
function GetSelectObj(clientId){
var obj = $(clientId);

var index = obj.selectedIndex; // ѡ������
return obj.options[index];
}

////���������
function GetRandom(n){return Math.floor(Math.random()*n+1)}

////�洢��ֵ��
function addKV(k,v){
localStorage.setItem(k,v);

}

////ȡ�ü�ֵ�Ե�ֵ
function getV(k){
return localStorage.getItem(k);
}

////��ñ��ش洢������ֵ��ת��Ϊ�ַ���
function getAllValueToStr(){
var content = "";
 for(var i=0;i<localStorage.length;i++){
  //key(i)�����Ӧ�ļ�������getItem()������ö�Ӧ��ֵ
   content += localStorage.key(i)+ " : " + localStorage.getItem(localStorage.key(i)) + "<br />";
}
return content;
}

////��ʾ��ǰʱ��
function GetCurrentDateTime(){
	var time ;
    with(new Date()){
		time =toLocaleString() + '����' + '��һ����������'.charAt(getDay());
	}
	return time;
	}