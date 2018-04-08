function GetPlayerName(){var name=getV("userName");return name!=null?name:"new player"}
function IncreaseScore(context){score+=10;DrawScore(context,score)}
function ScorePlayerName(name){addKV("userName",name)}