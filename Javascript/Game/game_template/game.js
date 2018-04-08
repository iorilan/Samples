document.write("<script language='javascript' src='config.js'></script>");
document.write("<script language='javascript' src='utility.js'></script>");
document.write("<script language='javascript' src='global.js'></script>");
document.write("<script language='javascript' src='debug.js'></script>");
////////////////////////
////游戏入口////////////
////////////////////////
var debuger;
function GameMaster(context){
this.context = context;
debuger = new Debuger("debug");
}

GameMaster.prototype = {

GameStart : function(){
this.InitGame();
this.RunGame(this);
},

InitGame : function(){

},

RunGame : function(gameMaster){
if(timer){
clearInterval(timer);
}

timer = setInterval(function(){
if(gameMaster.IsGameOver()){
	alert("game over!");
clearInterval(timer);
return;
}

debuger.DebugTxt(GetCurrentDateTime());
gameMaster.Repaint();

},sleepTime);
},
	
IsGameOver : function(){
return false;
},

////重绘
Repaint : function(){

}

}



