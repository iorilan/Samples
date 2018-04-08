function Sound(cfg){
	for (var attr in cfg){
		this[attr]=cfg[attr];
	}
}

Sound.prototype={

	init : function(){
		this.audio=document.createElement("audio");
		this.audio.autoplay = this.autoplay ||  false;
		this.audio.controls = false;
		this.audio.loop = this.loop || false;
		this.audio.preload = this.preload || false;
		this.audio.src = this.src;
		//console.dir(this.audio)
		document.body.appendChild(this.audio);
		//this.initEvent();
	},
	run : function(){
		this.init();
		this.audio.play();
	}
};