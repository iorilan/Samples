/*
���˿�����
*/

function EnemyManager(context){
this.context = context;
this.dh = new drawingHelper(context);
}

EnemyManager.prototype = {
////��ʼ������
InitEnemy : function(){
////3��������Ϊ����
for(var i = 0;i < 3;i ++){
enemies[i] =new Object();
enemies[i].x = screenWidth - enemyWidth * i - GetRandom(enemyMoveUnit * 10);
enemies[i].y = screenHeight - earthHeight - enemyHeight;
}

},

////�����ƶ���AI
TraceEnemy : function(){
var len = enemies.length;
if(len ==0){
	return;
	}

for(var i = 0;i < len;i ++){
if(enemies[i].x <= 0){
enemies[i].x = screenWidth - enemyWidth + GetRandom(100 * enemyMoveUnit);
}
enemies[i].x -= enemyMoveUnit ;
}

////���Ƶ���
this.dh.drawEnemyImg();

}


}