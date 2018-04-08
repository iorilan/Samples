/*
µ÷ÊÔÆ÷Àà
*/

function Debuger(clientId){
this.clientId = clientId;
}

Debuger.prototype = {

DebugTxt : function(value){

$(this.clientId).innerHTML = value;
}

}

