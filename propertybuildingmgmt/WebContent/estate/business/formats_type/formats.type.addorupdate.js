$(function(){
	function opensf(){
		window.parent.document.opensf();
	}
});

//保存
function saveBtn(){
	if($('#name').val() == ""){
		$('#errorTipn')[0].innerHTML = '类型名不能为空!';
		return;
	}
	var re = /^[\w\u4e00-\u9fa5]+$/gi;
	if (!re.test($('#name').val())) {  
		$('#errorTipn')[0].innerHTML = "类型名称中不能包含特殊字符"; 
		return;
	}  
	//清空错误提示
	$('#errorTipn')[0].innerHTML = '';
	$('#errorTipc')[0].innerHTML = '';
	$.messager.progress({
        text: '正在添加数据，请稍等...'
    });
	$('#dgForm').form('submit',{
		type:'post',
		url:'/propertybuildingmgmt/formatsType_addType.action',
		success:function(data){
			$.messager.progress('close');
			data = JSON.parse(data);
			if(!data.flagName){
				$('#errorTipn')[0].innerHTML = '类型'+data.name+'已存在!';
			}
			if(!data.flagColor){
				$('#errorTipc')[0].innerHTML = '颜色'+data.color+'已存在！';
			}
			if(data.flagAdd){
				$.messager.alert('提示','类型添加成功！','',function(r){
					window.parent.closeBtn();
					window.parent.datainit();
				});
				
			}
		},
		error:function(data){
			$.messager.progress('close');
		}
	});
}

//修改
function upDataBtn(){
	if($('#name').val() == ""){
		$('#errorTipn')[0].innerHTML = '类型名不能为空!';
		return;
	}
	var re = /^[\w\u4e00-\u9fa5]+$/gi;
	if (!re.test($('#name').val())) {  
		$('#errorTipn')[0].innerHTML = "类型名称中不能包含特殊字符"; 
		return;
	} 
	//清空错误提示
	$('#errorTipn')[0].innerHTML = '';
	$('#errorTipc')[0].innerHTML = '';
	$.messager.progress({
        text: '正在修改数据，请稍等...'
    });
	$('#dgForm').form('submit',{
		type:'post',
		url:'/propertybuildingmgmt/formatsType_editType.action',
		success:function(data){
			$.messager.progress('close');
			data = JSON.parse(data);
			if(!data.flagName){
				$('#errorTipn')[0].innerHTML = '类型'+data.name+'已存在!';
			}
			if(!data.flagColor){
				$('#errorTipc')[0].innerHTML = '颜色'+data.color+'已存在！';
			}
			if(data.flagEdit){
				$.messager.alert('提示','类型修改成功！','',function(r){
					window.parent.closeBtn();
					window.parent.datainit();
				});
				
			}
		},
		error:function(data){
			$.messager.progress('close');
		}
	});
}

//退出
function backBtn(){
	parent.closeBtn();
}