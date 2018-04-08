/*
drawing helper class
*/

function DrawingHelper(context){
this.context = context;
}


DrawingHelper.prototype = {

////////////////////////
////������//////////////
////////////////////////
DrawSnake : function(){

for(var i = 0;i < snakeArr.length;i ++){
this.FillRect(snakeArr[i].x,snakeArr[i].y,unitSize,unitSize,snakeColor);
this.DrawRect(snakeArr[i].x,snakeArr[i].y,unitSize,unitSize,lineColor);
}

},

////////////////////////
////����Ļ//////////////
////////////////////////
DrawScreen : function(){
for(var i = screenLeft;i < screenLeft + screenWidth / unitSize;i ++){
for(var j = screenTop;j < screenTop + screenHeight / unitSize;j ++){
this.FillRect(i * unitSize,j * unitSize,unitSize,unitSize,screenColor);
this.DrawRect(i * unitSize,j * unitSize,unitSize,unitSize,lineColor);
}
}
},

////�滭����
DrawScore: function(ctx,txt){

 ctx.moveTo(0,0);
 ctx.clearRect(0,0,500,500);

 ctx.font='60px impact';
 ctx.fillStyle=fontColor;
 ctx.textAlign='center';
// context.shadowColor="#00ff00";
// context.shadowOffsetX = 15;
// context.shadowOffsetY=-10;
txt = "����:" + txt;
 ctx.fillText(txt,100,100,fontSize);  

},

////���ƽ���(��ʱ����)
ScreenGradient : function(){

var grd = this.context.createLinearGradient(0,0,screenWidth,screenHeight);
grd.addColorStop(0,"#FFCC00");
grd.addColorStop(1,"#99FFFF");
this.context.fillStyle = grd;
this.context.fillRect(0,0,screenWidth,screenHeight);
},

////������
DrawRect : function(left,right,width,height,color){
		//���������ʽ
		this.context.strokeStyle = color;
		this.context.strokeRect(left,right, width, height);
},

////��䷽��
FillRect : function(left,right,width,height,color){
		//���������ʽ
		this.context.fillStyle = color;
		this.context.fillRect(left,right,width,height);
},


////�������
DrawFont : function(ctx,txt,size){
 ctx.font='30px impact';
 ctx.fillStyle=fontColor;
 ctx.textAlign='left';
// context.shadowColor="#00ff00";
// context.shadowOffsetX = 15;
// context.shadowOffsetY=-10;
 ctx.fillText(txt,0,50,size);
}

}

