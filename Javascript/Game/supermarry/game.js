/*
��Ϸ��
*/

document.write("<script language='javascript' src='config.js'></script>");
document.write("<script language='javascript' src='utility.js'></script>");
document.write("<script language='javascript' src='global.js'></script>");
document.write("<script language='javascript' src='debug.js'></script>");
document.write("<script language='javascript' src='drawingHelper.js'></script>");
document.write("<script language='javascript' src='person.js'></script>");
document.write("<script language='javascript' src='EnemmyManager.js'></script>");

////////////////////////
////��Ϸ���////////////
////////////////////////
var marry ;
var dh;
var enemyTracer ;
function GameMaster(context){
this.context = context;
dh = new drawingHelper(context);
marry = new Person(context);
enemyTracer = new EnemyManager(context);
}

GameMaster.prototype = {

GameStart : function(){
	
this.InitGame();

this.RunGame();

},

InitGame : function(){
////��ʼ������
dh.drawBgImg();
////��ʼ������
marry.standRight();
////��ʼ������
enemyTracer.InitEnemy();
},

RunGame : function(){
	
	////���ⶨʱ���ظ�
if(timer){
clearInterval(timer);
}

var g = this;

timer = setInterval(function(){

if(g.IsGameOver()){
	alert("game over!");
clearInterval(timer);

return;
}


////�ػ�
g.Repaint();
////���Ƴ���
dh.drawBgImg();

////���Ƶ���
enemyTracer.TraceEnemy();

////��������
marry.jumpCheck();

if(marry.direction == "left"){
if(marry.isMoving){
marry.moveLeft();
}
else{
marry.standLeft();
}
}

else if(marry.direction == "right"){
if(marry.isMoving){
marry.moveRight();
}
else{
marry.standRight();
}
}


else if(marry.direction == "down"){

}

////��������Ϣ
//debuger.DebugTxt(GetCurrentDateTime());



},sleepTime);
},

/*
V0.2�汾���������˾ͻ���
*/
IsGameOver : function(){
	var len = enemies.length;
for(var i = 0;i < len;i ++){
if( (  ( Math.abs(marry.x - enemies[i].x) < unitWidth && marry.x < enemies[i].x) ||  
	(Math.abs(marry.x - enemies[i].x) < enemyWidth ) && marry.x > enemies[i].x) &&
	marry.y + unitHeight > enemies[i].y){
return true;
}
}

return false;

},

////�ػ�
Repaint : function(){
dh.clearRect(0,0,screenWidth,screenHeight);
}


}



