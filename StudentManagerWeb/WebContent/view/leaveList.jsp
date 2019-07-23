<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>璇峰����琛�</title>
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
	        title:'璇峰����琛�', 
	        iconCls:'icon-more',//�炬�� 
	        border: true, 
	        collapsible: false,//������������ 
	        fit: true,//���ㄥぇ灏� 
	        method: "post",
	        url:"LeaveServlet?method=LeaveList&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect: true,//�������� 
	        pagination: true,//��椤垫�т欢 
	        rownumbers: true,//琛��� 
	        sortName:'id',
	        sortOrder:'DESC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:50, sortable: true},    
 		       	{field:'studentID',title:'瀛���',width:200,
 		        	formatter: function(value,row,index){
 						if (row.studentId){
 							var studentList = $("#studentList").combobox("getData");
 							for(var i=0;i<studentList.length;i++ ){
 								//console.log(clazzList[i]);
 								if(row.studentId == studentList[i].id)return studentList[i].name;
 							}
 							return row.studentId;
 						} else {
 							return 'not found';
 						}
 					}	
 		       	},
 		       	{field:'info',title:'璇峰������',width:400},
 		        {field:'status',title:'�舵��',width:80,
 		       	formatter: function(value,row,index){
						switch(row.status){
							case 0:{
								return '绛�寰�瀹℃��';
							}
							case 1:{
								return '瀹℃�搁��杩�';
							}
							case -1:{
								return '瀹℃�镐���杩�';
							}
						}
					}	
 		        },
 		        {field:'remark',title:'�瑰���瀹�',width:400},
	 		]], 
	        toolbar: "#toolbar",
	        onBeforeLoad : function(){
	        	try{
	        		$("#studentList").combobox("getData")
	        	}catch(err){
	        		preLoadClazz();
	        	}
	        }
	    }); 
		//������杞藉����淇℃��
	    function preLoadClazz(){
	  		$("#studentList").combobox({
		  		width: "150",
		  		height: "25",
		  		valueField: "id",
		  		textField: "name",
		  		multiple: false, //��澶���
		  		editable: false, //涓���缂�杈�
		  		method: "post",
		  		url: "StudentServlet?method=StudentList&t="+new Date().getTime()+"&from=combox",
		  		onChange: function(newValue, oldValue){
		  			//��杞界��绾т���瀛���
		  			//$('#dataList').datagrid("options").queryParams = {clazzid: newValue};
		  			//$('#dataList').datagrid("reload");
		  		}
		  	});
	  	}
		
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
	    
	  //璁剧疆缂�杈�����
	    $("#edit").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	if(selectRows.length != 1){
            	$.messager.alert("娑�������", "璇烽���╀��℃�版��杩�琛���浣�!", "warning");
            } else{
            	if(selectRows[0].status != 0){
            		$.messager.alert("娑�������", "璇ヨ�峰��淇℃��宸茶�瀹℃�革�涓���璁镐慨��!", "warning");
            		return;
            	}
		    	$("#editDialog").dialog("open");
            }
	    });
	  
	  //璁剧疆瀹℃�告����
	    $("#check").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	if(selectRows.length != 1){
            	$.messager.alert("娑�������", "璇烽���╀��℃�版��杩�琛���浣�!", "warning");
            } else{
            	if(selectRows[0].status != 0){
            		$.messager.alert("娑�������", "璇ヨ�峰��淇℃��宸茶�瀹℃�革�璇峰�块��澶�瀹℃��!", "warning");
            		return;
            	}
		    	$("#checkDialog").dialog("open");
            }
	    });
	    
	  //缂�杈�璇峰��淇℃��
	  	$("#editDialog").dialog({
	  		title: "淇��硅�峰��淇℃��",
	    	width: 450,
	    	height: 350,
	    	iconCls: "icon-edit",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'��浜�',
					plain: true,
					iconCls:'icon-add',
					handler:function(){
						var validate = $("#editForm").form("validate");
						if(!validate){
							$.messager.alert("娑�������","璇锋��ヤ�杈��ョ���版��!","warning");
							return;
						} else{
							var studentid = $("#edit_studentList").combobox("getValue");
							var id = $("#dataList").datagrid("getSelected").id;
							var info = $("#edit_info").textbox("getValue");
							var data = {id:id, studentid:studentid, info:info};
							
							$.ajax({
								type: "post",
								url: "LeaveServlet?method=EditLeave",
								data: data,
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("娑�������","淇��规����!","info");
										//�抽��绐���
										$("#editDialog").dialog("close");
										//娓�绌哄��琛ㄦ�兼�版��
										$("#edit_info").textbox('setValue',"");
										
										//���板�锋�伴〉�㈡�版��
							  			$('#dataList').datagrid("reload");
							  			$('#dataList').datagrid("uncheckAll");
										
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
						$("#edit_info").val("");
					}
				},
			],
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//璁剧疆��
				$("#edit_info").textbox('setValue',selectRow.info);
				//$("#edit-id").val(selectRow.id);
				var studentId = selectRow.studentId;
				setTimeout(function(){
					$("#edit_studentList").combobox('setValue', studentId);
				}, 100);
			},
			onClose: function(){
				$("#edit_info").val("");
				//$("#edit-id").val('');
			}
	    });
	  
	  
	  //瀹℃�歌�峰��淇℃��
	  	$("#checkDialog").dialog({
	  		title: "瀹℃�歌�峰��淇℃��",
	    	width: 450,
	    	height: 350,
	    	iconCls: "icon-edit",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'��浜�',
					plain: true,
					iconCls:'icon-add',
					handler:function(){
						var validate = $("#checkForm").form("validate");
						if(!validate){
							$.messager.alert("娑�������","璇锋��ヤ�杈��ョ���版��!","warning");
							return;
						} else{
							
							var studentid = $("#dataList").datagrid("getSelected").studentId;
							var id = $("#dataList").datagrid("getSelected").id;
							var info = $("#dataList").datagrid("getSelected").info;
							var remark = $("#check_remark").textbox("getValue");
							var status = $("#check_statusList").combobox("getValue");
							var data = {id:id, studentid:studentid, info:info,remark:remark,status:status};
							
							$.ajax({
								type: "post",
								url: "LeaveServlet?method=CheckLeave",
								data: data,
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("娑�������","瀹℃�告����!","info");
										//�抽��绐���
										$("#checkDialog").dialog("close");
										//娓�绌哄��琛ㄦ�兼�版��
										$("#check_remark").textbox('setValue',"");
										
										//���板�锋�伴〉�㈡�版��
							  			$('#dataList').datagrid("reload");
							  			$('#dataList').datagrid("uncheckAll");
										
									} else{
										$.messager.alert("娑�������","瀹℃�稿け璐�!","warning");
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
						$("#check_remark").val("");
						$("#check_statusList").combox('clear');
					}
				},
			],
			onBeforeOpen: function(){
				/*
				var selectRow = $("#dataList").datagrid("getSelected");
				//璁剧疆��
				$("#edit_info").textbox('setValue',selectRow.info);
				//$("#edit-id").val(selectRow.id);
				var studentId = selectRow.studentId;
				setTimeout(function(){
					$("#edit_studentList").combobox('setValue', studentId);
				}, 100);*/
			},
			onClose: function(){
				$("#edit_info").val("");
				//$("#edit-id").val('');
			}
	    });
	    
	    //����
	    $("#delete").click(function(){
	    	var selectRow = $("#dataList").datagrid("getSelected");
        	if(selectRow == null){
            	$.messager.alert("娑�������", "璇烽���╂�版��杩�琛�����!", "warning");
            } else{
            	$.messager.confirm("娑�������", "纭�璁ゅ���ゅ��锛�纭�璁ょ户缁�锛�", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "LeaveServlet?method=DeleteLeave",
							data: {id: selectRow.id},
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
	  	
	  	//璁剧疆娣诲��绐���
	    $("#addDialog").dialog({
	    	title: "娣诲��璇峰����",
	    	width: 450,
	    	height: 350,
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
					iconCls:'icon-book-add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("娑�������","璇锋��ヤ�杈��ョ���版��!","warning");
							return;
						} else{
							$.ajax({
								type: "post",
								url: "LeaveServlet?method=AddLeave",
								data: $("#addForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("娑�������","娣诲������!","info");
										//�抽��绐���
										$("#addDialog").dialog("close");
										//娓�绌哄��琛ㄦ�兼�版��
										//$("#add_name").textbox('setValue', "");
										//�锋��
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
					iconCls:'icon-book-reset',
					handler:function(){
						$("#add_name").textbox('setValue', "");
					}
				},
			]
	    });
	  	
	  //涓���妗����ㄥ���
	  	$("#add_studentList, #edit_studentList,#studentList").combobox({
	  		width: "200",
	  		height: "30",
	  		valueField: "id",
	  		textField: "name",
	  		multiple: false, //涓���澶���
	  		editable: false, //涓���缂�杈�
	  		method: "post",
	  	});
	  	//娣诲��淇℃��瀛������╂�
	    $("#add_studentList").combobox({
	  		url: "StudentServlet?method=StudentList&t="+new Date().getTime()+"&from=combox",
	  		onLoadSuccess: function(){
				//榛�璁ら���╃��涓��℃�版��
				var data = $(this).combobox("getData");
				$(this).combobox("setValue", data[0].id);
	  		}
	  	});
	  //缂�杈�淇℃��瀛������╂�
	    $("#edit_studentList").combobox({
	  		url: "StudentServlet?method=StudentList&t="+new Date().getTime()+"&from=combox",
	  		onLoadSuccess: function(){
				//榛�璁ら���╃��涓��℃�版��
				var data = $(this).combobox("getData");
				$(this).combobox("setValue", data[0].id);
	  		}
	  	});
	  	
	    //��绱㈡��������浜�浠�
	  	$("#search-btn").click(function(){
	  		$('#dataList').datagrid('load',{
	  			studentid: $("#studentList").combobox('getValue') == '' ? 0 : $("#studentList").combobox('getValue')
	  		});
	  	});
	    
	    //娓�绌烘��绱㈡�′欢
	  	$("#clear-btn").click(function(){
	    	$('#dataList').datagrid("reload",{});
	    	$("#studentList").combobox('clear');
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
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<c:if test="${userType == 2}">
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">淇���</a></div>
		</c:if>
		<c:if test="${userType == 1 || userType == 3}">
		<div style="float: left;"><a id="check" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">瀹℃��</a></div>
		</c:if>
		<div style="float: left; margin-right: 10px;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">����</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="margin-top: 3px;">
			瀛���锛�<input id="studentList" class="easyui-textbox" name="studentid" />
			<a id="search-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">��绱�</a>
			<a id="clear-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">娓�绌烘��绱�</a>
		</div>
	</div>
	
	<!-- 娣诲���版��绐��� -->
	<div id="addDialog" style="padding: 10px">  
    	<form id="addForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td style="width:60px">瀛���:</td>
	    			<td colspan="3">
	    				<input id="add_studentList" style="width: 300px; height: 30px;" class="easyui-textbox" name="studentid" data-options="required:true, missingMessage:'璇烽���╁����淇℃��'" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>璇峰������:</td>
	    			<td>
	    				<textarea id="info" name="info" style="width: 300px; height: 160px;" class="easyui-textbox" data-options="multiline:true,required:true, missingMessage:'璇峰������涓��戒负绌�'" ></textarea>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	<!-- 缂�杈��版��绐��� -->
	<div id="editDialog" style="padding: 10px">  
    	<form id="editForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td style="width:60px">瀛���:</td>
	    			<td colspan="3">
	    				<input id="edit_studentList" style="width: 300px; height: 30px;" class="easyui-textbox" name="studentid" data-options="required:true, missingMessage:'璇烽���╁����淇℃��'" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>璇峰������:</td>
	    			<td>
	    				<textarea id="edit_info" name="info" style="width: 300px; height: 160px;" class="easyui-textbox" data-options="multiline:true,required:true, missingMessage:'璇峰������涓��戒负绌�'" ></textarea>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	<!-- 瀹℃�告�版��绐��� -->
	<div id="checkDialog" style="padding: 10px">  
    	<form id="editForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td style="width:60px">瀛���:</td>
	    			<td colspan="3">
	    				<select id="check_statusList" style="width: 300px; height: 30px;" class="easyui-combobox" name="status" data-options="required:true, missingMessage:'璇烽���╃�舵��'" >
	    					<option value="1">瀹℃�搁��杩�</option>
	    					<option value="-1">瀹℃�镐���杩�</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>�瑰���瀹�:</td>
	    			<td>
	    				<textarea id="check_remark" name="remark" style="width: 300px; height: 160px;" class="easyui-textbox" data-options="multiline:true" ></textarea>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
</body>
</html>