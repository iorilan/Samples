/////����滭������

function drawPerson(context){
var isMoving = isMouseMoving | isKeyBoardMoving;

if(direction == "left"){
if(isMoving){
HorizontalMove(context,-moveUnit,personImgMoveLeft1,personImgMoveLeft2);
}
else{
drawImg(context,personImgStandLeft);
}

}
else if(direction == "right"){
if(isMoving){
HorizontalMove(context,moveUnit,personImgMoveRight1,personImgMoveRight2);
}
else{
drawImg(context,personImgStandRight);
}

}
else if(direction == "up"){
if(isMoving){
VerticalMove(context,-moveUnit,personImgMoveUp1,personImgMoveUp2);
}
else{
drawImg(context,personImgStandUp);
}
}
else if(direction == "down"){
if(isMoving){
VerticalMove(context,moveUnit,personImgMoveDown1,personImgMoveDown2);
}
else{
drawImg(context,personImgStandDown);
}
}

else if(direction == "leftUp"){
	if(isMoving){
ModifyPersonX(-moveUnit);
VerticalMove(context,-moveUnit,personImgMoveLeft1,personImgMoveLeft2);
	}
	else{
	drawImg(context,personImgStandLeft);
	}
}

else if(direction == "leftDown"){
	if(isMoving){
ModifyPersonX(-moveUnit);
VerticalMove(context,moveUnit,personImgMoveLeft1,personImgMoveLeft2);
}
else{
drawImg(context,personImgStandLeft);
}
}

else if(direction == "rightUp"){
if(isMoving){
	ModifyPersonX(moveUnit);
VerticalMove(context,-moveUnit,personImgMoveUp1,personImgMoveUp2);
}
else{
drawImg(context,personImgStandUp);
}
}

else if(direction == "rightDown"){
	if(isMoving){
	ModifyPersonX(moveUnit);
VerticalMove(context,moveUnit,personImgMoveRight1,personImgMoveRight2);
	}
	else{
	drawImg(context,personImgStandRight);
	}

}
}



////��ͼƬ
function drawImg(context,imgPosition){
context.drawImage(currentImgSource,imgPosition.x,imgPosition.y,unitWidth,unitHeight,currentPerson.x, currentPerson.y, unitWidth, unitHeight);
}

////��¶����������ӿڣ������������������ͼƬ
function ModifyPersonX(unit){
currentPerson.x += unit;
}

function ModifyPersonY(unit){
currentPerson.y += unit;
}

////�Զ�������ƶ��ľ����Լ�toggle��ʾ��ͼƬ
function HorizontalMove(context,unit,imgPosition1,imgPosition2){
	
	currentPerson.x += unit;
	if(hMoveToggle == "hMove1"){
drawImg(context,imgPosition1);

hMoveToggle = "hMove2";
	}
	else {
drawImg(context,imgPosition2);
hMoveToggle = "hMove1";
}


}

////�Զ��������ƶ��ľ��룬toggle��ʾ��ͼƬ
function VerticalMove(context,unit,imgPosition1,imgPosition2){
	currentPerson.y += unit;
	if(vMoveToggle == "vMove1"){
drawImg(context,imgPosition1);
vMoveToggle = "vMove2";
	}
	else {
drawImg(context,imgPosition2);
vMoveToggle = "vMove1";
}
}






