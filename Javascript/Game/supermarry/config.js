/*
系统参数类
*/

var timer;//定时器
var sleepTime = 100;//休眠时间

/*
背景大小
*/

var screenWidth = 800;
var screenHeight = 500;

/*
人物大小
*/
var unitWidth = 50;
var unitHeight = 60;

//////当前人物站立图片上截取位置
var personImgStandLeft = new Object();
personImgStandLeft.x = 0;
personImgStandLeft.y = unitHeight;

var personImgStandRight = new Object();
personImgStandRight.x = 0;
personImgStandRight.y = 0;
//////////////////////

////当前人物移动图片截取位置
var personImgMoveLeft1 = new Object();
personImgMoveLeft1.x = unitWidth ;
personImgMoveLeft1.y = unitHeight;

var personImgMoveLeft2 = new Object();
personImgMoveLeft2.x = 2 * unitWidth ;
personImgMoveLeft2.y = unitHeight;

var personImgMoveRight1 = new Object();
personImgMoveRight1.x = unitWidth ;
personImgMoveRight1.y = 0;

var personImgMoveRight2 = new Object();
personImgMoveRight2.x = 2 * unitWidth ;
personImgMoveRight2.y = 0;


/*
场景台阶高度
*/
var earthHeight = 70;

/*
敌人
*/
var enemies = new Array();
var enemyWidth = 30;
var enemyHeight = 30;
var enemyMoveUnit = 5;
/*
障碍物
*/
var currentStandObH = new Object();

/*
图片资源
*/

var marryImg = new Image();
marryImg.src = "res/marry.png";

var screenImg = new Image();
screenImg.src = "res/bg.png";

var enemyImg  = new Image();
enemyImg.src = "res/enemy.png";

