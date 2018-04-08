/*
global params
*/

var screenWidth = 800;////屏幕宽度
var screenHeight = 500;////屏幕高度
var unitSize = 20;////单元格大小
var initSize = 3;////初始长度
var screenLeft = 0;////屏幕横起始坐标
var screenTop = 0;////屏幕纵起始坐标
var snakeArr = new Array();////贪吃蛇数组
var food;////食物
var snakeColor = "#009999";////蛇身颜色
var direction;////蛇的方向


var screenColor = "#99CCFF";////屏幕颜色
var lineColor = "#ffffff";////线条颜色（方格）
var lineWidth = 3;////线条宽度
var foodColor = "#FFCC00"////食物颜色

var fontColor = "#996600";////分数颜色
var fontSize = 300;////分数字体大小


var timer;////定时器
var sleepTime = 200;////休眠时间
var level=0;////级别
var score=0;////分数
var currentPlayer;////当前玩家

var gameStatus = 0;////0:未开始 1:运行 2:暂停 3:已结束