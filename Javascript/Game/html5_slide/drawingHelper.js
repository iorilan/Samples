/*
�滭������
*/


function drawingHelper(context){
this.context = context;
}

drawingHelper.prototype = {

////��ͼƬ
drawImg : function(imgPosition,img){
this.context.drawImage(img,
	imgPosition.x,
	imgPosition.y,
	imgPosition.width,
	imgPosition.height,
	img.x,
	img.y, 
	imgPosition.width,
	imgPosition.height);
},

clearRect : function(left,right,width,height){
this.context.clearRect(left,right,width,height);
}

}


