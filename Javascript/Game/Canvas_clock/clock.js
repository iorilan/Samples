document.write("<script src='config.js'></script>");
document.write("<script src='drawingHelper.js'></script>");

function Clock(context){
this.drawingHelper = new drawingHelper(context);
}


Clock.prototype = {
Run : function(){
this.HandleDate();
var c = this;
var dh = this.drawingHelper;
if(timer){
clearInterval(timer);
}

timer = setInterval(function(){
dh.clear(0,0,1200,500);
c.HandleDate();

},1000);

},

HandleDate : function(){
var dt = new Date();
var h = dt.getHours();
this.DateNumProcess(h,startX,startY);
var m = dt.getMinutes();
this.DateNumProcess(m,2 * unitWidth + 4 * intervalWidth + startX,startY);
var s = dt.getSeconds();
this.DateNumProcess(s,4 * unitWidth + 8 * intervalWidth + startX,startY);
},

DateNumProcess : function(num,startX,startY){
if(num > 9){
var numP1 = parseInt(num / 10);
var numP2 = num % 10;
this.DrawNumber(startX,startY,numP1);
this.DrawNumber(startX + intervalWidth + unitWidth,startY,numP2);
}

else{
this.DrawNumber(startX,startY,0);
this.DrawNumber(startX + intervalWidth + unitWidth,startY,num);
}

},

DrawNumber : function(startX,startY,num){
switch(num){
case 1:
this.drawingHelper.drawLine(startX + unitWidth / 2,startY,startX + unitWidth / 2,startY + 2 * unitWidth + lineWidth,numColor);
	break;
case 2:
this.drawingHelper.drawLine(startX,startY,startX + unitWidth,startY,numColor);
this.drawingHelper.drawLine(startX + unitWidth,startY,startX + unitWidth,startY + unitWidth,numColor);
this.drawingHelper.drawLine(startX,startY + unitWidth,startX + unitWidth,startY + unitWidth,numColor);
this.drawingHelper.drawLine(startX,startY + unitWidth,startX,startY + 2 * unitWidth,numColor);
this.drawingHelper.drawLine(startX,startY + 2 * unitWidth,startX + unitWidth,startY + 2 * unitWidth,numColor);
	break;
case 3:
this.drawingHelper.drawLine(startX,startY,startX + unitWidth,startY,numColor);
this.drawingHelper.drawLine(startX + unitWidth,startY,startX + unitWidth,startY + unitWidth,numColor);
this.drawingHelper.drawLine(startX,startY + unitWidth,startX + unitWidth,startY + unitWidth,numColor);
this.drawingHelper.drawLine(startX + unitWidth,startY + unitWidth,startX + unitWidth,startY + 2 * unitWidth,numColor);
this.drawingHelper.drawLine(startX,startY + 2 * unitWidth,startX + unitWidth,startY + 2 * unitWidth,numColor);
	break;
case 4:
this.drawingHelper.drawLine(startX,startY,startX ,startY + unitWidth,numColor);
this.drawingHelper.drawLine(startX ,startY + unitWidth,startX + unitWidth,startY + unitWidth,numColor);
this.drawingHelper.drawLine(startX + unitWidth / 2,startY ,startX + unitWidth / 2,startY + 2 * unitWidth + lineWidth,numColor);
	break;
case 5:
this.drawingHelper.drawLine(startX,startY,startX + unitWidth,startY,numColor);
this.drawingHelper.drawLine(startX ,startY,startX,startY + unitWidth,numColor);
this.drawingHelper.drawLine(startX,startY + unitWidth,startX + unitWidth,startY + unitWidth,numColor);
this.drawingHelper.drawLine(startX + unitWidth,startY + unitWidth,startX + unitWidth,startY + 2 * unitWidth,numColor);
this.drawingHelper.drawLine(startX,startY + 2 * unitWidth,startX + unitWidth,startY + 2 * unitWidth,numColor);
	break;
case 6:
this.drawingHelper.drawLine(startX ,startY,startX,startY + 2 * unitWidth,numColor);
this.drawingHelper.drawLine(startX,startY + unitWidth,startX + unitWidth,startY + unitWidth,numColor);
this.drawingHelper.drawLine(startX + unitWidth,startY + unitWidth,startX + unitWidth,startY + 2 * unitWidth,numColor);
this.drawingHelper.drawLine(startX,startY + 2 * unitWidth,startX + unitWidth,startY + 2 * unitWidth,numColor);
	break;
case 7:
this.drawingHelper.drawLine(startX,startY,startX + unitWidth,startY,numColor);
this.drawingHelper.drawLine(startX + unitWidth,startY,startX + unitWidth,startY + 2 * unitWidth + lineWidth,numColor);
	break;
case 8:
this.drawingHelper.drawLine(startX,startY,startX + unitWidth,startY,numColor);
this.drawingHelper.drawLine(startX ,startY,startX ,startY + 2 * unitWidth,numColor);
this.drawingHelper.drawLine(startX + unitWidth,startY ,startX + unitWidth,startY + 2 * unitWidth,numColor);
this.drawingHelper.drawLine(startX,startY + unitWidth,startX + unitWidth,startY + unitWidth,numColor);
this.drawingHelper.drawLine(startX,startY + 2 * unitWidth,startX + unitWidth,startY + 2 * unitWidth,numColor);
	break;
case 9:
this.drawingHelper.drawLine(startX,startY,startX + unitWidth,startY,numColor);
this.drawingHelper.drawLine(startX ,startY,startX ,startY + unitWidth,numColor);
this.drawingHelper.drawLine(startX,startY + unitWidth,startX + unitWidth,startY + unitWidth,numColor);
this.drawingHelper.drawLine(startX + unitWidth,startY ,startX + unitWidth,startY + 2 * unitWidth + lineWidth,numColor);
	break;
case 0:
this.drawingHelper.drawLine(startX,startY,startX + unitWidth,startY,numColor);
this.drawingHelper.drawLine(startX ,startY,startX ,startY + 2 * unitWidth,numColor);
this.drawingHelper.drawLine(startX + unitWidth,startY ,startX + unitWidth,startY + 2 * unitWidth,numColor);
this.drawingHelper.drawLine(startX,startY + 2 * unitWidth,startX + unitWidth,startY + 2 * unitWidth,numColor);
	break;
}

}


}