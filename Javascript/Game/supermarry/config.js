/*
ϵͳ������
*/

var timer;//��ʱ��
var sleepTime = 100;//����ʱ��

/*
������С
*/

var screenWidth = 800;
var screenHeight = 500;

/*
�����С
*/
var unitWidth = 50;
var unitHeight = 60;

//////��ǰ����վ��ͼƬ�Ͻ�ȡλ��
var personImgStandLeft = new Object();
personImgStandLeft.x = 0;
personImgStandLeft.y = unitHeight;

var personImgStandRight = new Object();
personImgStandRight.x = 0;
personImgStandRight.y = 0;
//////////////////////

////��ǰ�����ƶ�ͼƬ��ȡλ��
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
����̨�׸߶�
*/
var earthHeight = 70;

/*
����
*/
var enemies = new Array();
var enemyWidth = 30;
var enemyHeight = 30;
var enemyMoveUnit = 5;
/*
�ϰ���
*/
var currentStandObH = new Object();

/*
ͼƬ��Դ
*/

var marryImg = new Image();
marryImg.src = "res/marry.png";

var screenImg = new Image();
screenImg.src = "res/bg.png";

var enemyImg  = new Image();
enemyImg.src = "res/enemy.png";

