////////////////////////
////���������ò���//////
////////////////////////
var screenWidth = 1200;
var screenHeight = 450;
var timer;////��ʱ��
var sleepTime = 200;////����ʱ��
var level=0;////����
var score=0;////����
var unitWidth = 30;////������
var unitHeight = 50;////����߶�
var moveUnit = 5;////�ƶ���Ԫ

var clickX = -1;////��굥���ĺ�����
var clickY = -1;////��굥����������

var currentSenario = 1;////��¼��ǰ�ĳ���
///////////////////////////
////////��ǰ����//////////
/////////////////////////////
var currentImgSource ;
var currentPerson = new Object();////��ǰ���
var direction = "right";////վ������
var isMouseMoving = false;////�������ƶ�
var isKeyBoardMoving = false;////���̿����ƶ�
var hMoveToggle = "hMove1";
var vMoveToggle = "vMove1";

//////��ǰ����վ��ͼƬ�Ͻ�ȡλ��
var personImgStandLeft = new Object();
var personImgStandRight = new Object();
var personImgStandUp = new Object();
var personImgStandDown = new Object();
//////////////////////

////��ǰ�����ƶ�ͼƬ��ȡλ��
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
//////����//////////////////
///////////////////////////

var objs = new Array();////�����е�����

//////////////////////////////
////ͼƬ��Դ//////////////////////
//////////////////////////////
var wushiImg = new Image();
wushiImg.src = "images/zhanshi/wushi2.png";
