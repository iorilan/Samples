/*
游戏类
*/

document.write("<script language='javascript' src='config.js'></script>");
document.write("<script language='javascript' src='utility.js'></script>");
document.write("<script language='javascript' src='global.js'></script>");
document.write("<script language='javascript' src='debug.js'></script>");
document.write("<script language='javascript' src='drawingHelper.js'></script>");
document.write("<script language='javascript' src='person.js'></script>");
document.write("<script language='javascript' src='EnemmyManager.js'></script>");

////////////////////////
////游戏入口////////////
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
////初始化场景
dh.drawBgImg();
////初始化玛丽
marry.standRight();
////初始化敌人
enemyTracer.InitEnemy();
},

RunGame : function(){
	
	////避免定时器重复
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


////重绘
g.Repaint();
////绘制场景
dh.drawBgImg();

////绘制敌人
enemyTracer.TraceEnemy();

////绘制玛丽
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

////调试器信息
//debuger.DebugTxt(GetCurrentDateTime());



},sleepTime);
},

/*
V0.2版本，碰到敌人就会死
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

////重绘
Repaint : function(){
dh.clearRect(0,0,screenWidth,screenHeight);
}


}



