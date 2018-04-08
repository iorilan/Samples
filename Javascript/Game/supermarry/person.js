/*
����������
*/

var dh ;

function Person(context){
this.context = context;
dh = new drawingHelper(context);
this.x = 0;
this.y = screenHeight - unitHeight - earthHeight;

this.hMoveToggle = "hmove1";
this.vMoveToggle = "vMove1";
////��ʼ������վ��
this.isMoving = false;
this.direction = "right";
this.moveUnit = 8;

////�Ƿ���Ծ
this.isJumping = false;
////�����ٶ�
this.jumpSpeed = 40;
////�������ڿ��е�ʱ��
this.jumpTime = 1;
////���ٶ�
this.jumpA = 5;

}

Person.prototype = {

setMoving : function(m){
this.isMoving = m;
},
setDirection : function(d){
this.direction = d;
},
setJump : function(b){
this.isJumping = b;
},
standLeft : function(){
dh.drawMarryImg(personImgStandLeft,this);
},
standRight : function(){
dh.drawMarryImg(personImgStandRight,this);
},
moveLeft : function(){
this.HorizontalMove(this.context,-this.moveUnit,personImgMoveLeft1,personImgMoveLeft2);
},
moveRight : function(){
this.HorizontalMove(this.context,this.moveUnit,personImgMoveRight1,personImgMoveRight2);
},

jumpCheck : function(){
	if(this.isJumping){
var a = this.jumpA;
var t = this.jumpTime;
var movement = a * t - this.jumpSpeed;

if(this.y + movement >= screenHeight - unitHeight - earthHeight){
this.y = screenHeight - unitHeight - earthHeight;
this.jumpTime = 1;
this.isJumping = false;
}
else{
this.y += movement;
}
this.jumpTime ++;
}

},


////�Զ�������ƶ��ľ����Լ�toggle��ʾ��ͼƬ
HorizontalMove : function(context,unit,imgPosition1,imgPosition2){
	
	this.x += unit;
	if(this.hMoveToggle == "hMove1"){
dh.drawMarryImg(imgPosition1,this);
this.hMoveToggle = "hMove2";
	}
	else {
dh.drawMarryImg(imgPosition2,this);
this.hMoveToggle = "hMove1";
}
},

////�Զ��������ƶ��ľ��룬toggle��ʾ��ͼƬ
VerticalMove : function(context,unit,imgPosition1,imgPosition2){
	currentPerson.y += unit;
	if(this.vMoveToggle == "vMove1"){
dh.drawMarryImg(imgPosition1,this);
this.vMoveToggle = "vMove2";
	}
	else {
dh.drawMarryImg(imgPosition2,this);
this.vMoveToggle = "vMove1";
}
}

}