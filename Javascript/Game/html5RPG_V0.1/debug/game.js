document.write("<script language='javascript' src='config.js'></script>");
document.write("<script language='javascript' src='global.js'></script>");
document.write("<script language='javascript' src='player.js'></script>");
document.write("<script language='javascript' src='utility.js'></script>");
document.write("<script language='javascript' src='debug.js'></script>");
document.write("<script language='javascript' src='personHelper.js'></script>");
document.write("<script language='javascript' src='gravity.js'></script>");
document.write("<script language='javascript' src='scenarioHelper.js'></script>");
function GameStart(context){
LoadGame(context);
RunGame(context);
}

////����
function LoadGame(context){
	////��ʼ������
currentPerson.x = 0;
currentPerson.y = 0;

////ѡ��ǰ����(δ���)
ImgPositionLoader();////���ص�ǰ�������Դ��Ϣ

}

function ImgPositionLoader(){
	
currentImgSource = wushiImg;
//////��ǰ����վ��ͼƬ�Ͻ�ȡλ��
personImgStandLeft.x = 450;
personImgStandLeft.y = 0;

personImgStandRight.x = 450;
personImgStandRight.y = 150;

personImgStandUp.x = 450;
personImgStandUp.y = 300;

personImgStandDown.x = 450;
personImgStandDown.y = 150;

//////////////////////

////��ǰ�����ƶ�ͼƬ��ȡλ��
personImgMoveLeft1.x = 0;
personImgMoveLeft1.y = 0;

personImgMoveLeft2.x = 150;
personImgMoveLeft2.y = 0;

personImgMoveRight1.x = 0;
personImgMoveRight1.y = 150;

personImgMoveRight2.x = 150;
personImgMoveRight2.y = 150;

personImgMoveUp1.x = 0;
personImgMoveUp1.y = 300;

personImgMoveUp2.x = 150;
personImgMoveUp2.y = 300;

personImgMoveDown1.x = 0;
personImgMoveDown1.y = 150;

personImgMoveDown2.x = 150;
personImgMoveDown2.y = 150;
/////////////////////////////
}



////����
function RunGame(context){
if(timer){
clearInterval(timer);
}

timer = setInterval(function(){
if(IsGameOver()){
	alert("game over!");
clearInterval(timer);
return;
}

Repaint(context);

},sleepTime);
}

////�ػ�
function Repaint(context){
context.clearRect(0,0,screenWidth,screenHeight);

////����¼�
MouseListener(context);
////����������
drawPerson(context);
////���Ƴ���
DrawSenario(context);

}



function MouseListener(context){
	////�ѵ���λ��
if(clickX == -1 || clickY == -1){
	isMouseMoving = false;
return;
}
////���λ��Խ��
else if(clickX > screenWidth || clickY > screenHeight){
clickX = -1;
clickY = -1;
isMouseMoving = false;
return ;
}

else{
	
	if(clickX == currentPerson.x && clickY == currentPerson.y){
		clickX = -1;
		clickY = -1;
		//DebugTxt(currentPerson.x + "," + currentPerson.y + "   " + clickX + "," + clickY);
		isMouseMoving = false;
	return;
	}

var moveX = clickX - currentPerson.x;
var moveY = clickY - currentPerson.y;

if(moveX > 0 && moveY >= 0){
if(moveY > 0){
	isMouseMoving = true;
direction = "rightDown";
return;
}
isMouseMoving = true;
direction = "right";
return;
}

else if(moveX > 0 && moveY < 0){
	isMouseMoving = true;
direction = "rightUp";
return;
}

else if(moveX <= 0 && moveY >= 0){

if(moveX == 0 && moveY == 0){
	return;
}
else if(moveX < 0 && moveY == 0){
	isMouseMoving = true;
direction = "left";
return;
}
else if(moveX == 0 && moveY > 0){
	isMouseMoving = true;
direction = "down";
return;
}
else if(moveX < 0 && moveY > 0){
	isMouseMoving = true;
direction = "leftDown";
return;
}

}

else if(moveX <= 0 && moveY < 0){
if(moveX < 0){
	isMouseMoving = true;
direction = "leftUp";
return;
}
isMouseMoving = true;
direction = "up";
return;
}

}

}

////�Ƿ����
function IsGameOver(){
return false;
}