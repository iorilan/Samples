function focusHere(context,left,top,color){
context.fillStyle=color;
context.beginPath();
////Ô²ÐÄºá×ø±ê¡¢×Ý×ø±ê¡¢°ë¾¶
context.arc(left + unitWidth / 2 ,top ,5,0,Math.PI * 2,true);
context.closePath();
context.fill();
}