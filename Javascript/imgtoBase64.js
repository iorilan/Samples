		function convertImgToBase64(url, callback, outputFormat){
    var img = new Image();
    img.crossOrigin = 'Anonymous';
    img.onload = function(){
        var canvas = document.createElement('CANVAS');
        var ctx = canvas.getContext('2d');
        canvas.height = this.height;
        canvas.width = this.width;
        ctx.drawImage(this,0,0);
        var dataURL = canvas.toDataURL(outputFormat || 'image/png');
        dataURL = dataURL.replace(/^data:image\/(png|jpg|jpeg);base64,/, "");
            //dataURL.substr(dataURL.indexOf("base64,") + 7, dataURL.length);
        callback(dataURL);
        canvas = null;
    };
    img.src = url;
}

convertImgToBase64(imgQrCode.src, function(base64Img){
        console.log('IMAGE:',base64Img);
    });
