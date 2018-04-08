
function Player(){

}

Player.prototype = {
GetPlayerName : function(){
	var name = getV("userName");//global function
return name != null ? name : "new player" ;
},

IncreaseScore : function(context){
score += 10;
},

StorePlayerName : function(name){
addKV("userName",name);//global function
}

}

