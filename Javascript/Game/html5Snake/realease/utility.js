function $(clientId){return document.getElementById(clientId)}
function GetSelectObj(clientId){var obj=$(clientId),index=obj.selectedIndex;return obj.options[index]}
function DebugVar(param){$("debug").innerHTML=param}
function DebugTxt(text){$("debug").innerHTML=text}
function DrawRect(context,left,right,width,height,color){context.strokeStyle=color;context.strokeRect(left,right,width,height)}
function FillRect(context,left,right,width,height,color){context.fillStyle=color;context.fillRect(left,right,width,height)}
function DrawScore(context,txt){context.moveTo(0,0);context.clearRect(0,0,500,500);context.font='60px impact';context.fillStyle=fontColor;context.textAlign='center';txt="·ÖÊý:"+txt;context.fillText(txt,100,100,fontSize)}
function ScreenGradient(context){var grd=context.createLinearGradient(0,0,screenWidth,screenHeight);grd.addColorStop(0,"#FFCC00");grd.addColorStop(1,"#99FFFF");context.fillStyle=grd;context.fillRect(0,0,screenWidth,screenHeight)}
function GetRandom(n){return Math.floor(Math.random()*n+1)}
function addKV(k,v){localStorage.setItem(k,v)}
function getV(k){return localStorage.getItem(k)}
function getAllValueToStr(){var content="";for(var i=0;i<localStorage.length;i++)content+=localStorage.key(i)+" : "+localStorage.getItem(localStorage.key(i))+"<br />"}