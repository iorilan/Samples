function drawingHelper(context){
this.context = context;
}

drawingHelper.prototype = {
drawLine : function(sX,sY,eX,eY,color){

this.context.beginPath();
this.context.fillStyle = color;
this.context.strokeStyle = "#212121";
this.context.moveTo(sX,sY);
this.context.lineTo(eX,eY);

if(sX == eX){
this.context.lineTo(eX + lineWidth,eY);
this.context.lineTo(sX + lineWidth,sY);
}

else if(sY == eY){
this.context.lineTo(eX,eY + lineWidth);
this.context.lineTo(sX,sY + lineWidth);
}

this.context.closePath();
this.context.fill();
this.context.stroke();
},

clear : function(startX,startY,width,height){
this.context.clearRect(startX,startY,width,height);
}


}