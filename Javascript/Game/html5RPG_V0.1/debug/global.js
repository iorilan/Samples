function focusHere(context,left,top,color){
context.fillStyle=color;
context.beginPath();
////Բ�ĺ����ꡢ�����ꡢ�뾶
context.arc(left + unitWidth / 2 ,top ,5,0,Math.PI * 2,true);
context.closePath();
context.fill();
}