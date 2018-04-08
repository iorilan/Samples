/*
ªÊª≠π§æﬂ¿‡
*/


function drawingHelper(context){
this.context = context;
}

drawingHelper.prototype = {

////ª≠Õº∆¨
drawMarryImg : function(imgPosition,marry){
this.context.drawImage(marryImg,imgPosition.x,imgPosition.y,unitWidth,unitHeight,marry.x, marry.y, unitWidth, unitHeight);
},
drawBgImg : function(){
this.context.drawImage(screenImg,0,0,screenWidth,screenHeight);
},
drawEnemyImg : function(){
for(var i = 0;i < enemies.length;i ++){
this.context.drawImage(enemyImg,enemies[i].x,enemies[i].y,enemyWidth,enemyHeight);
}

},
clearRect : function(left,right,width,height){
this.context.clearRect(left,right,width,height);
}

}


