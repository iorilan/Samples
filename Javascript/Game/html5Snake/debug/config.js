/*
global params
*/

var screenWidth = 800;////��Ļ���
var screenHeight = 500;////��Ļ�߶�
var unitSize = 20;////��Ԫ���С
var initSize = 3;////��ʼ����
var screenLeft = 0;////��Ļ����ʼ����
var screenTop = 0;////��Ļ����ʼ����
var snakeArr = new Array();////̰��������
var food;////ʳ��
var snakeColor = "#009999";////������ɫ
var direction;////�ߵķ���


var screenColor = "#99CCFF";////��Ļ��ɫ
var lineColor = "#ffffff";////������ɫ������
var lineWidth = 3;////�������
var foodColor = "#FFCC00"////ʳ����ɫ

var fontColor = "#996600";////������ɫ
var fontSize = 300;////���������С


var timer;////��ʱ��
var sleepTime = 200;////����ʱ��
var level=0;////����
var score=0;////����
var currentPlayer;////��ǰ���

var gameStatus = 0;////0:δ��ʼ 1:���� 2:��ͣ 3:�ѽ���