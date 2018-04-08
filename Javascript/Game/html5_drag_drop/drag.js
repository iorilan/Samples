function test(){alert("test");}
 
 function handleDrop(e) {
					e.stopPropagation();
¡¡¡¡                e.preventDefault();
                    
                    var fileList  = e.dataTransfer.files,¡¡¡¡//»ñÈ¡ÍÏ×§ÎÄ¼þ
                        fileType = fileList[0].type,
¡¡¡¡¡¡¡¡                 oImg = document.createElement('img'),
¡¡¡¡¡¡¡¡                 reader = new FileReader();
                    
                    if (fileType.indexOf('image') == -1) {
                        alert('ÇëÍÏ×§Í¼Æ¬~');
                        return;
                    }

                    reader.onload = function(e) {
                        oImg.src = reader.result;
                        this.container.innerHTML = '';
                        this.container.appendChild(oImg);
                    }
                    reader.readAsDataURL(fileList[0])
}
