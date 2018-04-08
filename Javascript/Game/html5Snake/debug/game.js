document.write("<script language='javascript' src='config.js'></script>");
document.write("<script language='javascript' src='utility.js'></script>");
document.write("<script language='javascript' src='player.js'></script>");
document.write("<script language='javascript' src='global.js'></script>");
document.write("<script language='javascript' src='drawingHelper.js'></script>");


function GameMaster(context,contextScore){
this.context = context;
this.contextScore = contextScore;
////绘画辅助对象
this.drawingHelper = new DrawingHelper(context);
////当前玩家
this.player = new Player();
}


GameMaster.prototype = {

////////////////////////
////游戏入口////////////
////////////////////////
GameStart : function (){
	this.InitGame();
	
	this.RunGame(this);
},
	////////////////////////
////初始化游戏////////////
////////////////////////
InitGame : function(){

////贪吃蛇
for(var i = initSize ;i > 0;i --){
snakeArr[i - 1] = new Object();
snakeArr[i - 1].x = (initSize - i + 1) * unitSize;
snakeArr[i - 1].y = 0;
}


////方向
direction = "right";
////食物
food = new Object();

////绘制屏幕方格
this.drawingHelper.DrawScreen();

////随即食物
this.RandomFood();
////分数
this.drawingHelper.DrawScore(this.contextScore,score);

},

////////////////////////
////运行游戏////////////
////////////////////////
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

gameMaster.EatFoodHandler();
gameMaster.Refresh();
gameMaster.SetPosition();
gameMaster.drawingHelper.DrawSnake();


},sleepTime);
},


////////////////////////
////刷新////////////////
////////////////////////
Refresh : function(){
this.drawingHelper.FillRect(snakeArr[snakeArr.length - 1].x,snakeArr[snakeArr.length - 1].y,unitSize,unitSize,screenColor);
this.drawingHelper.DrawRect(snakeArr[snakeArr.length - 1].x,snakeArr[snakeArr.length - 1].y,unitSize,unitSize,lineColor);
},

////////////////////////
////设置坐标////////////
////////////////////////
SetPosition : function(){

for(var i = snakeArr.length - 2;i >= 0 ;i --){
snakeArr[i + 1].x = snakeArr[i].x;
snakeArr[i + 1].y = snakeArr[i].y;

}
if(direction == "left"){
snakeArr[0].x -= unitSize;
}
else if(direction == "right"){
snakeArr[0].x += unitSize;
}
else if(direction == "up"){
snakeArr[0].y -= unitSize;
}
else if(direction == "down"){
snakeArr[0].y += unitSize;
}
},

////////////////////////
////判断是否结束游戏///
////////////////////////
IsGameOver : function(){
if(snakeArr[0].x < 0 ||snakeArr[0].x > screenWidth){
return true;
}
if(snakeArr[0].y < 0 ||snakeArr[0].y > screenHeight){
return true;
}

for(var i = 1;i < snakeArr.length;i ++){
if(snakeArr[0].x == snakeArr[i].x && snakeArr[0].y == snakeArr[i].y){
	gameStatus = 3;
return true;
}
}
return false;
},


////////////////////////
////随即食物////////////
////////////////////////
RandomFood : function(){

food.x = GetRandom((screenWidth / unitSize) - 1) * unitSize;
food.y = GetRandom((screenHeight / unitSize) - 1) * unitSize;
for(var i = 0;i < snakeArr.length;i ++){
if(food.x == snakeArr[i].x && food.y == snakeArr[i].y){
this.RandomFood();
}
}

this.drawingHelper.FillRect(food.x ,food.y ,unitSize,unitSize,foodColor);////utility.js
this.drawingHelper.DrawRect(food.x ,food.y ,unitSize,unitSize,lineColor);////utility.js

},

////////////////////////
///食物处理/////////////
////////////////////////
EatFoodHandler : function(){

if(direction == "left"){
if((snakeArr[0].x - unitSize == food.x) && snakeArr[0].y == food.y){

	this.IncreaseLen();
	this.ClearFood();
	this.RandomFood();
}
}
else if(direction == "right"){
if(snakeArr[0].x + unitSize == food.x && snakeArr[0].y == food.y){

	this.IncreaseLen();
	this.ClearFood();
	this.RandomFood();
}
}
else if(direction == "up"){
if(snakeArr[0].x == food.x && (snakeArr[0].y - unitSize == food.y)){
	
	this.IncreaseLen();
	this.ClearFood();
	this.RandomFood();
}
}
else if(direction == "down"){
if(snakeArr[0].x == food.x && snakeArr[0].y + unitSize == food.y){
	
	this.IncreaseLen();
	this.ClearFood();
	this.RandomFood();
}
}

},

////////////////////////
////清除食物////////////
////////////////////////
ClearFood : function(){
this.drawingHelper.FillRect(food.x,food.y,unitSize,unitSize,screenColor);
this.drawingHelper.DrawRect(food.x,food.y,unitSize,unitSize,lineColor);

},

////////////////////////
////增加长度////////////
////////////////////////
IncreaseLen : function(){

var newObj = new Object();
newObj.x = food.x;
newObj.y = food.y;
snakeArr.unshift(newObj);

////分数增加
this.player.IncreaseScore();////player.js
this.drawingHelper.DrawScore(this.contextScore,score);////drawingHelper.js

},

////暂停
PauseGame : function(){
	clearInterval(timer);
}

}






