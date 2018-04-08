var conf = {
	width:600,
	height:400,
	playerSelected:0,
	playerPicNum:2
}
// 加载图片
function loadImage(srcList,callback){
	var imgs={};
	var totalCount=srcList.length;
	var loadedCount=0;
	for (var i=0;i<totalCount;i++){
		var img=srcList[i];
		var image=imgs[img.id]=new Image();		
		image.src=img.url;
		image.onload=function(event){
			loadedCount++;
		}		
	}
	if (typeof callback=="function"){
		var Me=this;
		function check(){
			if (loadedCount>=totalCount){
				callback.apply(Me,arguments);
			}else{		
				setTimeout(check,100);
			}	
		}
		check();
	}
	return imgs;
}

//取得闭区间那的随机整数
function genRandom(lower, higher) {
	lower = lower || 0;
	higher = higher || 9999;
	return Math.floor( (higher - lower + 1) * Math.random() ) + lower;
}

//定义全局对象
var ImgCache=null;

//用来记录按键状态
var KeyState={};


//定义游戏所用 按键的keyCode的常量
var Key={
	A : 65,
	W : 87,
	D : 68,
	EN:13
}

var game;


// Demo的启动函数
function startDemo(){

	game = new Game({
		FPS : 30,
		width : conf.width,
		height :  conf.height,
		sprites : [ ]
	
	});


	//加入马里奥
	game.sprites.push(createPlayer());

	//加入五个敌人
	for(var i=0;i<5;i++){
		game.sprites.push(createEnemy());
	}

	game.init();
	game.start();

}


function initEvent(){
		//监听整个document的keydown,keyup事件, 为了保证能够监听到, 监听方式使用Capture

		document.addEventListener("keydown",function(evt){
			//console.log(evt.keyCode)
			//按下某按键, 该键状态为true
			KeyState[evt.keyCode]=true;
		},true);
		document.addEventListener("keyup",function(evt){
			//放开下某按键, 该键状态为true
			KeyState[evt.keyCode]=false;
		},true);
	}


//预载图片
function goGame(){
	document.getElementById("mask").style.display="none";
	
	ImgCache=loadImage( [
					{ 	id : "player",
						url : "../res/player/"+conf.playerSelected+".png?"+(new Date()).getTime()
					},
					{ 	id : "enemy",
						url : "../res/enemy.png"
					},
					{ 	id : "bg",
						url : "../res/bg.png"
					}
				],
				startDemo );
	
}


window.addEventListener('load',function(){
	
	Before.init();
	initEvent();
	
	var timer = setInterval(function(){
			//懒，利用KeyState逻辑
			
			//角色状态管理
			if(KeyState[Key.A] || KeyState[Key.D]){
				Before.playerSelect();
			}
			
			//开始游戏
			if(KeyState[Key.EN]){
				clearInterval(timer);
				goGame();
				
			}
			//console.log(1)
		},10)
	
	},false);