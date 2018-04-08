//游戏前过场效果

var Before = {
	init:function(){
		var playerHTML = [];
		for(var i=0;i<conf.playerPicNum;i++){
			if(i ==conf.playerSelected )
				playerHTML.push('<img src="../res/player/'+i+'.jpg" class="h">');
			else
				playerHTML.push('<img src="../res/player/'+i+'.jpg" >');
		}
		var maskdiv = document.getElementById("mask");
		maskdiv.style.display="block";
		maskdiv.innerHTML = playerHTML.join("")+"<div>Press <strong>Enter</strong> to start</div>"
	},
	
	
	//人物角色选择
	playerSelect:function (){
		
		var players = document.querySelectorAll("#mask>img");
		
		players[conf.playerSelected].className = "";
		
		if(KeyState[Key.A]){
			conf.playerSelected>0 && conf.playerSelected--;
				
		}else if(KeyState[Key.D]){
			(conf.playerSelected < conf.playerPicNum-1) && conf.playerSelected++;
					
		}
		
		players[conf.playerSelected].className = "h";
		
	}
	
}