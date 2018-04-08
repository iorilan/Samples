////////////////////////
////变量、配置参数//////
////////////////////////
var screenWidth = 1200;
var screenHeight = 450;
var timer;////定时器
var sleepTime = 200;////休眠时间
var level=0;////级别
var score=0;////分数
var unitWidth = 30;////人物宽度
var unitHeight = 50;////人物高度
var moveUnit = 5;////移动单元

var clickX = -1;////鼠标单击的横坐标
var clickY = -1;////鼠标单击的纵坐标

var currentSenario = 1;////记录当前的场景
///////////////////////////
////////当前人物//////////
/////////////////////////////
var currentImgSource ;
var currentPerson = new Object();////当前玩家
var direction = "right";////站立方向
var isMouseMoving = false;////鼠标控制移动
var isKeyBoardMoving = false;////键盘控制移动
var hMoveToggle = "hMove1";
var vMoveToggle = "vMove1";

//////当前人物站立图片上截取位置
var personImgStandLeft = new Object();
var personImgStandRight = new Object();
var personImgStandUp = new Object();
var personImgStandDown = new Object();
//////////////////////

////当前人物移动图片截取位置
var personImgMoveLeft1 = new Object();
var personImgMoveLeft2 = new Object();
var personImgMoveRight1 = new Object();
var personImgMoveRight2 = new Object();
var personImgMoveUp1 = new Object();
var personImgMoveUp2 = new Object();
var personImgMoveDown1 = new Object();
var personImgMoveDown2 = new Object();

//////////////////////////

////////////////////////////
//////场景//////////////////
///////////////////////////

var objs = new Array();////场景中的物体

//////////////////////////////
////图片资源//////////////////////
//////////////////////////////
var wushiImg = new Image();
wushiImg.src = "images/zhanshi/wushi2.png";
