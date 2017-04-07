/** layer mobile demo */

!function(){

var demo = {};

//ɨ��ά��
window.erweima && (erweima.onclick = function(){
    layer.open({
        type: 1,
        content: '<img src="./layer-mobile.png">'
    })
});

//С��ţ��
demo.trys = function(index){
    
    //��Ϣ��
    if(index === 0){
        layer.open({
            content: '����',
            time: 2
        });
    } 
    
    //��ʾ��
    else if(index === 1){
        layer.open({
            title: '��ʾ',
            content: 'layer�ƶ����PC�治��ͬʱʹ����ͬһҳ�档'
        });
    } 
    
    //ѯ�ʿ�
    else if(index === 2){
        layer.open({
            title: '��ʾ',
            content: '��ȷ��Ҫˢ��һ�±�ҳ����',
            btn: ['��', '��Ҫ'],
            yes: function(index){
                location.reload();
                layer.close(index);
            }
        });
    } 
    
    //ҳ���
    else if(index === 3){
        var pagei = layer.open({
            type: 1,
            content: '<div style="padding:20px;">����һ��ҳ��㣨����type:1������������ⶨ����<p style="margin-top:10px;"><button class="closediy">�Զ���Ĺرհ�ť</button></p></div>',
            style: 'width:300px; height:200px;',
            success: function(olayer){
                olayer.getElementsByClassName('closediy')[0].onclick = function(){
                    layer.close(pagei)
                }
            }
        });
    }
    
    //������
    else if(index === 4){
        layer.open({
            type: 2,
            //shade: false,
            time: 10
            //content: '���ز����С�',
        });
    }
};

if(window.trys){
    var tryas = trys.getElementsByTagName('a');
    for(var i = 0, len = tryas.length; i < len; i++){
        (function(i){
            tryas[i].addEventListener('click', function(){
                demo.trys(i);
            });
        }(i));
    };
}

//չ����ʾ����
if(window.seecodes){
    var ishide, exttext = seecodes.innerHTML;
    seecodes.onclick = function(){
        if(!ishide){
            trycode.style.display='block';
            this.innerHTML = '����';
            ishide = true;
        } else {
            trycode.style.display='none';
            this.innerHTML = exttext;
            ishide = false;
        }
    }
}

if(window.downs){
    downs.onclick = function(){
        var xhr = new XMLHttpRequest();
        xhr.open('get', '/item/filedown.asp?id=3957', true)
        xhr.send(null);
    };
}


}();





