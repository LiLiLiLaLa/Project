<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>璇剧���琛�</title>
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
	        title:'璇剧���琛�', 
	        iconCls:'icon-more',//�炬�� 
	        border: true, 
	        collapsible: false,//������������ 
	        fit: true,//���ㄥぇ灏� 
	        method: "post",
	        url:"CourseServlet?method=CourseList&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect: false,//�������� 
	        pagination: true,//��椤垫�т欢 
	        rownumbers: true,//琛��� 
	        sortName:'id',
	        sortOrder:'DESC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:50, sortable: true},    
 		        {field:'name',title:'璇剧���绉�',width:200},
 		       	{field:'teacherId',title:'��璇捐��甯�',width:200,
 		        	formatter: function(value,row,index){
 						if (row.teacherId){
 							var teacherList = $("#teacherList").combobox("getData");
 							for(var i=0;i<teacherList.length;i++ ){
 								//console.log(clazzList[i]);
 								if(row.teacherId == teacherList[i].id)return teacherList[i].name;
 							}
 							return row.clazzId;
 						} else {
 							return 'not found';
 						}
 					}	
 		       	},
 		       	{field:'courseDate',title:'涓�璇炬�堕��',width:200},
 		        {field:'selectedNum',title:'宸查��浜烘��',width:200},
 		        {field:'maxNum',title:'��澶у����浜烘��',width:200},
	 		]], 
	        toolbar: "#toolbar",
	        onBeforeLoad : function(){
	        	try{
	        		$("#teacherList").combobox("getData")
	        	}catch(err){
	        		preLoadClazz();
	        	}
	        }
	    }); 
		//������杞芥��甯�淇℃��
	    function preLoadClazz(){
	  		$("#teacherList").combobox({
		  		width: "150",
		  		height: "25",
		  		valueField: "id",
		  		textField: "name",
		  		multiple: false, //��澶���
		  		editable: false, //涓���缂�杈�
		  		method: "post",
		  		url: "TeacherServlet?method=TeacherList&t="+new Date().getTime()+"&from=combox",
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
	    	table = $("#editTable");
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	if(selectRows.length != 1){
            	$.messager.alert("娑�������", "璇烽���╀��℃�版��杩�琛���浣�!", "warning");
            } else{
		    	$("#editDialog").dialog("open");
            }
	    });
	    
	  //缂�杈�璇剧�淇℃��
	  	$("#editDialog").dialog({
	  		title: "淇��硅�剧�淇℃��",
	    	width: 450,
	    	height: 400,
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
					iconCls:'icon-user_add',
					handler:function(){
						var validate = $("#editForm").form("validate");
						if(!validate){
							$.messager.alert("娑�������","璇锋��ヤ�杈��ョ���版��!","warning");
							return;
						} else{
							var teacherid = $("#edit_teacherList").combobox("getValue");
							var id = $("#dataList").datagrid("getSelected").id;
							var name = $("#edit_name").textbox("getText");
							var courseDate = $("#edit_course_date").textbox("getText");
							var maxNum = $("#edit_max_num").numberbox("getValue");
							var info = $("#edit_info").val();
							var data = {id:id, teacherid:teacherid, name:name,courseDate:courseDate,info:info,maxnum:maxNum};
							
							$.ajax({
								type: "post",
								url: "CourseServlet?method=EditCourse",
								data: data,
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("娑�������","淇��规����!","info");
										//�抽��绐���
										$("#editDialog").dialog("close");
										//娓�绌哄��琛ㄦ�兼�版��
										$("#edit_name").textbox('setValue', "");
										$("#edit_course_date").textbox('setValue', "");
										$("#edit_info").val("");
										
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
						$("#edit_name").textbox('setValue', "");
						$("#edit_phone").textbox('setValue', "");
						$("#edit_qq").textbox('setValue', "");
						
						$(table).find(".chooseTr").remove();
						
					}
				},
			],
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//璁剧疆��
				$("#edit_name").textbox('setValue', selectRow.name);
				$("#edit_course_date").textbox('setValue', selectRow.courseDate);
				$("#edit_max_num").numberbox('setValue', selectRow.maxNum);
				$("#edit_info").val(selectRow.info);
				//$("#edit-id").val(selectRow.id);
				var teacherId = selectRow.teacherId;
				setTimeout(function(){
					$("#edit_teacherList").combobox('setValue', teacherId);
				}, 100);
			},
			onClose: function(){
				$("#edit_name").textbox('setValue', "");
				$("#edit_course_date").textbox('setValue', "");
				$("#edit_info").val("");
				//$("#edit-id").val('');
			}
	    });
	    
	    //����
	    $("#delete").click(function(){
	    	var selectRow = $("#dataList").datagrid("getSelections");
        	if(selectRow == null){
            	$.messager.alert("娑�������", "璇烽���╂�版��杩�琛�����!", "warning");
            } else{
            	var ids = [];
            	$(selectRow).each(function(i, row){
            		ids[i] = row.id;
            	});
            	$.messager.confirm("娑�������", "灏����や�璇剧��稿�崇�������版��锛�纭�璁ょ户缁�锛�", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "CourseServlet?method=DeleteCourse",
							data: {ids: ids},
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
	    	title: "娣诲��璇剧�",
	    	width: 450,
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
					iconCls:'icon-book-add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("娑�������","璇锋��ヤ�杈��ョ���版��!","warning");
							return;
						} else{
							$.ajax({
								type: "post",
								url: "CourseServlet?method=AddCourse",
								data: $("#addForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("娑�������","娣诲������!","info");
										//�抽��绐���
										$("#addDialog").dialog("close");
										//娓�绌哄��琛ㄦ�兼�版��
										$("#add_name").textbox('setValue', "");
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
	  	$("#add_teacherList, #edit_teacherList,#teacherList").combobox({
	  		width: "200",
	  		height: "30",
	  		valueField: "id",
	  		textField: "name",
	  		multiple: false, //涓���澶���
	  		editable: false, //涓���缂�杈�
	  		method: "post",
	  	});
	  	//娣诲��淇℃����甯����╂�
	    $("#add_teacherList").combobox({
	  		url: "TeacherServlet?method=TeacherList&t="+new Date().getTime()+"&from=combox",
	  		onLoadSuccess: function(){
				//榛�璁ら���╃��涓��℃�版��
				var data = $(this).combobox("getData");
				$(this).combobox("setValue", data[0].id);
	  		}
	  	});
	  //缂�杈�淇℃����甯����╂�
	    $("#edit_teacherList").combobox({
	  		url: "TeacherServlet?method=TeacherList&t="+new Date().getTime()+"&from=combox",
	  		onLoadSuccess: function(){
				//榛�璁ら���╃��涓��℃�版��
				var data = $(this).combobox("getData");
				$(this).combobox("setValue", data[0].id);
	  		}
	  	});
	  	
	    //��绱㈡��������浜�浠�
	  	$("#search-btn").click(function(){
	  		$('#dataList').datagrid('load',{
	  			name: $('#courseName').val(),
	  			teacherid: $("#teacherList").combobox('getValue') == '' ? 0 : $("#teacherList").combobox('getValue')
	  		});
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
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">淇���</a></div>
		<div style="float: left; margin-right: 10px;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">����</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="margin-top: 3px;">
			璇剧���绉帮�<input id="courseName" class="easyui-textbox" name="clazzName" />
			��璇捐��甯�锛�<input id="teacherList" class="easyui-textbox" name="clazz" />
			<a id="search-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">��绱�</a>
		</div>
	</div>
	
	<!-- 娣诲���版��绐��� -->
	<div id="addDialog" style="padding: 10px">  
    	<form id="addForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>璇剧���绉�:</td>
	    			<td><input id="add_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'涓��戒负绌�'" /></td>
	    		</tr>
	    		<tr>
	    			<td style="width:40px">��璇捐��甯�:</td>
	    			<td colspan="3">
	    				<input id="add_teacherList" style="width: 200px; height: 30px;" class="easyui-textbox" name="teacherid" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr>
	    		<tr>
	    			<td>涓�璇炬�堕��:</td>
	    			<td><input id="add_course_date" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="course_date" data-options="required:true, missingMessage:'涓��戒负绌�'" /></td>
	    		</tr>
	    		<tr>
	    			<td>��澶�����浜烘��:</td>
	    			<td><input id="add_max_num" style="width: 200px; height: 30px;" class="easyui-numberbox" type="text" name="maxnum" data-options="min:0,precision:0,required:true, missingMessage:'涓��戒负绌�'" /></td>
	    		</tr>
	    		<tr>
	    			<td>璇剧�浠�缁�:</td>
	    			<td>
	    				<textarea id="info" name="info" style="width: 200px; height: 60px;" class="" ></textarea>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	<!-- 缂�杈��版��绐��� -->
	<div id="editDialog" style="padding: 10px">  
    	<form id="editForm" method="post">
    		<!-- <input type="hidden" name="id" id="edit-id"> -->
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>璇剧���绉�:</td>
	    			<td><input id="edit_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'涓��戒负绌�'" /></td>
	    		</tr>
	    		<tr>
	    			<td style="width:40px">��璇捐��甯�:</td>
	    			<td colspan="3">
	    				<input id="edit_teacherList" style="width: 200px; height: 30px;" class="easyui-textbox" name="teacherid" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr>
	    		<tr>
	    			<td>涓�璇炬�堕��:</td>
	    			<td><input id="edit_course_date" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="course_date" data-options="required:true, missingMessage:'涓��戒负绌�'" /></td>
	    		</tr>
	    		<tr>
	    			<td>��澶�����浜烘��:</td>
	    			<td><input id="edit_max_num" style="width: 200px; height: 30px;" class="easyui-numberbox" type="text" name="max_num" data-options="min:0,precision:0,required:true, missingMessage:'涓��戒负绌�'" /></td>
	    		</tr>
	    		<tr>
	    			<td>璇剧�浠�缁�:</td>
	    			<td>
	    				<textarea id="edit_info" name="info" style="width: 200px; height: 60px;" class="" ></textarea>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
</body>
</html>