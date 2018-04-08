/*
敌人控制类
*/

function EnemyManager(context){
this.context = context;
this.dh = new drawingHelper(context);
}

EnemyManager.prototype = {
////初始化敌人
InitEnemy : function(){
////3个敌人作为测试
for(var i = 0;i < 3;i ++){
enemies[i] =new Object();
enemies[i].x = screenWidth - enemyWidth * i - GetRandom(enemyMoveUnit * 10);
enemies[i].y = screenHeight - earthHeight - enemyHeight;
}

},

////敌人移动的AI
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

////绘制敌人
this.dh.drawEnemyImg();

}


}