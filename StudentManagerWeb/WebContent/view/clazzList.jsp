<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>��绾у��琛�</title>
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/js/validateExtends.js"></script>
	<script type="text/javascript">
	$(function() {	
		//datagrid��濮��� 
	    $('#dataList').datagrid({ 
	        title:'��绾у��琛�', 
	        iconCls:'icon-more',//�炬�� 
	        border: true, 
	        collapsible: false,//������������ 
	        fit: true,//���ㄥぇ灏� 
	        method: "post",
	        url:"ClazzServlet?method=getClazzList&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect: true,//�������� 
	        pagination: true,//��椤垫�т欢 
	        rownumbers: true,//琛��� 
	        sortName: 'id',
	        sortOrder: 'DESC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:50, sortable: true},    
 		        {field:'name',title:'��绾у��绉�',width:200},
 		        {field:'info',title:'��绾т�缁�',width:100, 
 		        },
	 		]], 
	        toolbar: "#toolbar"
	    }); 
	    //璁剧疆��椤垫�т欢 
	    var p = $('#dataList').datagrid('getPager'); 
	    $(p).pagination({ 
	        pageSize: 10,//姣�椤垫�剧ず��璁板��℃�帮�榛�璁や负10 
	        pageList: [10,20,30,50,100],//��浠ヨ�剧疆姣�椤佃�板��℃�扮����琛� 
	        beforePageText: '绗�',//椤垫�版����妗����剧ず��姹�瀛� 
	        afterPageText: '椤�    �� {pages} 椤�', 
	        displayMsg: '褰����剧ず {from} - {to} �¤�板�   �� {total} �¤�板�', 
	    });
	    //璁剧疆宸ュ�风被����
	    $("#add").click(function(){
	    	$("#addDialog").dialog("open");
	    });
	    //����
	    $("#delete").click(function(){
	    	var selectRow = $("#dataList").datagrid("getSelected");
	    	//console.log(selectRow);
        	if(selectRow == null){
            	$.messager.alert("娑�������", "璇烽���╂�版��杩�琛�����!", "warning");
            } else{
            	var clazzid = selectRow.id;
            	$.messager.confirm("娑�������", "灏����ょ��绾т俊��锛�濡�����绾т�瀛��ㄥ��������甯���涓��藉���わ�锛�纭�璁ょ户缁�锛�", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "ClazzServlet?method=DeleteClazz",
							data: {clazzid: clazzid},
							success: function(msg){
								if(msg == "success"){
									$.messager.alert("娑�������","���ゆ����!","info");
									//�锋�拌〃��
									$("#dataList").datagrid("reload");
								} else{
									$.messager.alert("娑�������","���ゅけ璐�!","warning");
									return;
								}
							}
						});
            		}
            	});
            }
	    });
	    
	    
	  	
	  	//璁剧疆娣诲����绾х����
	    $("#addDialog").dialog({
	    	title: "娣诲����绾�",
	    	width: 500,
	    	height: 400,
	    	iconCls: "icon-add",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'娣诲��',
					plain: true,
					iconCls:'icon-add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("娑�������","璇锋��ヤ�杈��ョ���版��!","warning");
							return;
						} else{
							//var gradeid = $("#add_gradeList").combobox("getValue");
							$.ajax({
								type: "post",
								url: "ClazzServlet?method=AddClazz",
								data: $("#addForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("娑�������","娣诲������!","info");
										//�抽��绐���
										$("#addDialog").dialog("close");
										//娓�绌哄��琛ㄦ�兼�版��
										$("#add_name").textbox('setValue', "");
										$("#info").val("");
										//���板�锋�伴〉�㈡�版��
							  			//$('#gradeList').combobox("setValue", gradeid);
							  			$('#dataList').datagrid("reload");
										
									} else{
										$.messager.alert("娑�������","娣诲��澶辫触!","warning");
										return;
									}
								}
							});
						}
					}
				},
				{
					text:'��缃�',
					plain: true,
					iconCls:'icon-reload',
					handler:function(){
						$("#add_name").textbox('setValue', "");
						//���板��杞藉勾绾�
						$("#info").val("");
					}
				},
			]
	    });
	  	
	  	
	  	//��绱㈡��������浜�浠�
	  	$("#search-btn").click(function(){
	  		$('#dataList').datagrid('load',{
	  			clazzName: $('#clazzName').val()
	  		});
	  	});
	  	
	  //淇��规��������浜�浠�
	  	$("#edit-btn").click(function(){
	  		var selectRow = $("#dataList").datagrid("getSelected");
	    	//console.log(selectRow);
        	if(selectRow == null){
            	$.messager.alert("娑�������", "璇烽���╂�版��杩�琛�淇���!", "warning");
            	return;
            }
        	$("#editDialog").dialog("open");
	  	});
	  
	  //璁剧疆缂�杈���绾х����
	    $("#editDialog").dialog({
	    	title: "缂�杈���绾�",
	    	width: 500,
	    	height: 400,
	    	iconCls: "icon-add",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'纭�瀹�淇���',
					plain: true,
					iconCls:'icon-add',
					handler:function(){
						var validate = $("#editForm").form("validate");
						if(!validate){
							$.messager.alert("娑�������","璇锋��ヤ�杈��ョ���版��!","warning");
							return;
						} else{
							//var gradeid = $("#add_gradeList").combobox("getValue");
							$.ajax({
								type: "post",
								url: "ClazzServlet?method=EditClazz",
								data: $("#editForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("娑�������","淇��规����!","info");
										//�抽��绐���
										$("#editDialog").dialog("close");
										//娓�绌哄��琛ㄦ�兼�版��
										$("#edit_name").textbox('setValue', "");
										$("#edit_info").val("");
										//���板�锋�伴〉�㈡�版��
							  			//$('#gradeList').combobox("setValue", gradeid);
							  			$('#dataList').datagrid("reload");
										
									} else{
										$.messager.alert("娑�������","淇��瑰け璐�!","warning");
										return;
									}
								}
							});
						}
					}
				},
				{
					text:'��缃�',
					plain: true,
					iconCls:'icon-reload',
					handler:function(){
						$("#edit_name").textbox('setValue', "");
						//���板��杞藉勾绾�
						$("#edit_info").val("");
					}
				},
			],
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//console.log(selectRow);
				//璁剧疆��
				$("#edit_name").textbox('setValue', selectRow.name);
				$("#edit_info").val(selectRow.info);
				$("#edit-id").val(selectRow.id);
			}
	    });
	  
	});
	</script>
</head>
<body>
	<!-- �版����琛� -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	</table> 
	
	<!-- 宸ュ�锋�� -->
	<div id="toolbar">
		<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">娣诲��</a></div>
		<div style="float: left; margin-right: 10px;"><a id="edit-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">淇���</a></div>
		<div style="float: left; margin-right: 10px;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">����</a></div>
		<div style="margin-top: 3px;">��绾у��绉帮�<input id="clazzName" class="easyui-textbox" name="clazzName" />
			<a id="search-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">��绱�</a>
		</div>
	</div>
	
	<!-- 娣诲��绐��� -->
	<div id="addDialog" style="padding: 10px">  
    	<form id="addForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>��绾у��绉�:</td>
	    			<td><input id="add_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name"  data-options="required:true, missingMessage:'涓��戒负绌�'" /></td>
	    		</tr>
	    		<tr>
	    			<td>��绾т�缁�:</td>
	    			<td>
	    				<textarea id="info" name="info" style="width: 200px; height: 60px;" class="" ></textarea>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	<!-- 缂�杈�绐��� -->
	<div id="editDialog" style="padding: 10px">  
    	<form id="editForm" method="post">
    	<input type="hidden" id="edit-id" name="id">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>��绾у��绉�:</td>
	    			<td><input id="edit_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name"  data-options="required:true, missingMessage:'涓��戒负绌�'" /></td>
	    		</tr>
	    		<tr>
	    			<td>��绾т�缁�:</td>
	    			<td>
	    				<textarea id="edit_info" name="info" style="width: 200px; height: 60px;" class="" ></textarea>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
</body>
</html>