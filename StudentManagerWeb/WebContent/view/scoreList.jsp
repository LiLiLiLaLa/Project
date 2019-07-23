<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>��缁╁��琛�</title>
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
	        title:'��缁╀俊����琛�', 
	        iconCls:'icon-more',//�炬�� 
	        border: true, 
	        collapsible: false,//������������ 
	        fit: true,//���ㄥぇ灏� 
	        method: "post",
	        url:"ScoreServlet?method=ScoreList&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect: true,//�������� 
	        pagination: true,//��椤垫�т欢 
	        rownumbers: true,//琛��� 
	        sortName:'id',
	        sortOrder:'DESC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:50},    
 		        {field:'studentId',title:'瀛���',width:200,
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
 		       	{field:'courseId',title:'璇剧�',width:200,
 		        	formatter: function(value,row,index){
 						if (row.courseId){
 							var courseList = $("#courseList").combobox("getData");
 							for(var i=0;i<courseList.length;i++ ){
 								//console.log(clazzList[i]);
 								if(row.courseId == courseList[i].id)return courseList[i].name;
 							}
 							return row.courseId;
 						} else {
 							return 'not found';
 						}
 					}		
 		       	},
 		       {field:'score',title:'��缁�',width:200},
 		       {field:'remark',title:'澶�娉�',width:200}
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
		//������杞藉������璇剧�淇℃��
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
		  		
		  	});
	  		$("#courseList").combobox({
		  		width: "150",
		  		height: "25",
		  		valueField: "id",
		  		textField: "name",
		  		multiple: false, //��澶���
		  		editable: false, //涓���缂�杈�
		  		method: "post",
		  		url: "CourseServlet?method=CourseList&t="+new Date().getTime()+"&from=combox",
		  		
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
	    
	    //璁剧疆瀵煎�ュ伐�风被����
	    $("#import").click(function(){
	    	$("#importDialog").dialog("open");
	    });
	    
	  //璁剧疆缂�杈�����
	    $("#edit").click(function(){
	    	table = $("#editTable");
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	if(selectRows.length != 1){
            	$.messager.alert("娑�������", "璇烽���╀��℃�版��杩�琛���浣�!", "warning");
            } else{
            	$("#edit_id").val(selectRows[0].id);
		    	$("#editDialog").dialog("open");
            }
	    });
	    
	    
	    //����
	    $("#delete").click(function(){
	    	var selectRow = $("#dataList").datagrid("getSelected");
        	if(selectRow == null){
            	$.messager.alert("娑�������", "璇烽���╂�版��杩�琛�����!", "warning");
            } else{
            	var id = selectRow.id;
            	$.messager.confirm("娑�������", "纭�瀹����ゆ��缁╀�锛�纭�璁ょ户缁�锛�", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "ScoreServlet?method=DeleteScore",
							data: {id: id},
							success: function(msg){
								if(msg == "success"){
									$.messager.alert("娑�������","���ゆ����!","info");
									//�锋�拌〃��
									$("#dataList").datagrid("reload");
								}else if(msg == "not found"){
									$.messager.alert("娑�������","涓�瀛��ㄨ�ラ��璇捐�板�!","info");
								}else{
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
	    	title: "娣诲����缁╀俊��",
	    	width: 450,
	    	height: 450,
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
								url: "ScoreServlet?method=AddScore",
								data: $("#addForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("娑�������","��璇句俊��娣诲������!","info");
										//�抽��绐���
										$("#addDialog").dialog("close");
										//娓�绌哄��琛ㄦ�兼�版��
										$("#add_remark").textbox('setValue', "");
										//�锋��
										$('#dataList').datagrid("reload");
									} else if(msg == "added"){
										$.messager.alert("娑�������","璇ヨ�剧���缁╁凡缁�褰��ワ�涓�����褰���!","warning");
										return;
									} else{
										$.messager.alert("娑�������","绯荤����ㄥ�洪��锛�璇疯��绯荤�＄����!","warning");
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
						$("#add_remark").textbox('setValue', "");
					}
				},
			]
	    });
	  	
	  //璁剧疆淇��圭����
	    $("#editDialog").dialog({
	    	title: "淇��规��缁╀俊��",
	    	width: 450,
	    	height: 450,
	    	iconCls: "icon-edit",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'淇���',
					plain: true,
					iconCls:'icon-book-edit',
					handler:function(){
						var validate = $("#editForm").form("validate");
						if(!validate){
							$.messager.alert("娑�������","璇锋��ヤ�杈��ョ���版��!","warning");
							return;
						} else{
							$.ajax({
								type: "post",
								url: "ScoreServlet?method=EditScore",
								data: $("#editForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("娑�������","��璇句俊��娣诲������!","info");
										//�抽��绐���
										$("#editDialog").dialog("close");
										//娓�绌哄��琛ㄦ�兼�版��
										$("#edit_remark").textbox('setValue', "");
										//�锋��
										$('#dataList').datagrid("reload");
									} else if(msg == "added"){
										$.messager.alert("娑�������","璇ヨ�剧���缁╁凡缁�褰��ワ�涓�����褰���!","warning");
										return;
									} else{
										$.messager.alert("娑�������","绯荤����ㄥ�洪��锛�璇疯��绯荤�＄����!","warning");
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
						$("#edit_remark").textbox('setValue', "");
					}
				},
			],
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//璁剧疆��
				$("#edit_studentList").combobox('setValue', selectRow.studentId);
				$("#edit_score").numberbox('setValue', selectRow.score);
				$("#edit_remark").textbox('setValue', selectRow.remark);
				setTimeout(function(){
					$("#edit_courseList").combobox('setValue', selectRow.courseId);
				}, 100);
				
			}
	    });
	  	
	  //璁剧疆瀵煎�ョ����
	    $("#importDialog").dialog({
	    	title: "瀵煎�ユ��缁╀俊��",
	    	width: 450,
	    	height: 150,
	    	iconCls: "icon-add",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'纭�璁ゅ�煎��',
					plain: true,
					iconCls:'icon-book-add',
					handler:function(){
						var validate = $("#importForm").form("validate");
						if(!validate){
							$.messager.alert("娑�������","璇烽���╂��浠�!","warning");
							return;
						} else{
							importScore();
							$("#importDialog").dialog("close");
						}
					}
				}
			]
	    });
	  
	  //涓���妗����ㄥ���
	  	$("#add_studentList, #add_courseList,#studentList,#courseList,#edit_studentList, #edit_courseList").combobox({
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
				getStudentSelectedCourseList(data[0].id);
	  		},
	  		onChange:function(id,o){
	  			getStudentSelectedCourseList(id);
	  		}
	  	});
	  	
	  //娣诲��淇℃��璇剧����╂�
	    
	    function getStudentSelectedCourseList(studentId){
		  	//娣诲��淇℃��璇剧����╂�
			    $("#add_courseList").combobox({
			  		url: "AttendanceServlet?method=getStudentSelectedCourseList&t="+new Date().getTime()+"&student_id="+studentId,
			  		onLoadSuccess: function(){
						//榛�璁ら���╃��涓��℃�版��
						var data = $(this).combobox("getData");
						$(this).combobox("setValue", data[0].id);
			  		}
			  	});
		  	}	
	  
	  //淇��逛俊��瀛������╂�
	    $("#edit_studentList").combobox({
	  		url: "StudentServlet?method=StudentList&t="+new Date().getTime()+"&from=combox",
	  		onLoadSuccess: function(){
				//榛�璁ら���╃��涓��℃�版��
				var data = $(this).combobox("getData");
				$(this).combobox("setValue", data[0].id);
				getEditStudentSelectedCourseList(data[0].id);
	  		},
	  		onChange:function(id,o){
	  			getEditStudentSelectedCourseList(id);
	  		}
	  	});
	    function getEditStudentSelectedCourseList(studentId){
		  	//淇��逛俊��璇剧����╂�
			    $("#edit_courseList").combobox({
			  		url: "AttendanceServlet?method=getStudentSelectedCourseList&t="+new Date().getTime()+"&student_id="+studentId,
			  		onLoadSuccess: function(){
						//榛�璁ら���╃��涓��℃�版��
						var data = $(this).combobox("getData");
						$(this).combobox("setValue", data[0].id);
			  		}
			  	});
		  	}
	  
	    //��绱㈡��������浜�浠�
	  	$("#search-btn").click(function(){
	  		$('#dataList').datagrid('load',{
	  			studentid: $("#studentList").combobox('getValue') == '' ? 0 : $("#studentList").combobox('getValue'),
	  			courseid: $("#courseList").combobox('getValue') == '' ? 0 : $("#courseList").combobox('getValue')
	  		});
	  	});
	    
	  //瀵煎�烘��������浜�浠�
	  	$("#export").click(function(){
	  		studentid = $("#studentList").combobox('getValue') == '' ? 0 : $("#studentList").combobox('getValue');
	  		courseid = $("#courseList").combobox('getValue') == '' ? 0 : $("#courseList").combobox('getValue');
	  		url = 'ScoreServlet?method=ExportScoreList&studentid='+studentid+"&courseid="+courseid;
	  		window.location.href = url;
	  	});
	    
	    //娓�绌烘��绱㈡�′欢
	  	$("#clear-btn").click(function(){
	    	$('#dataList').datagrid("reload",{});
	    	$("#studentList").combobox('clear');
	    	$("#courseList").combobox('clear');
	    });
	    
	  	function importScore(){
			$("#importForm").submit();
			$.messager.progress({text:'姝ｅ�ㄤ�浼�瀵煎�ヤ腑...'});
			var interval = setInterval(function(){
				var message =  $(window.frames["import_target"].document).find("#message").text();
				if(message != null && message != ''){
					$.messager.progress('close');
					$.messager.alert("娑�������",message,"info");
					$('#dataList').datagrid("reload");
					clearInterval(interval);
				}
			}, 1000)
		}
	});
	</script>
</head>
<body>
	<!-- �版����琛� -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	    
	</table> 
	<!-- 宸ュ�锋�� -->
	<div id="toolbar">
		<c:if test="${userType != 2}">
		<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">娣诲��</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="import" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">瀵煎��</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="export" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">瀵煎��</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">淇���</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left; margin-right: 10px;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">����</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		</c:if>
		<div style="margin-top: 3px;">
			瀛���锛�<input id="studentList" class="easyui-textbox" name="studentList" />
			璇剧�锛�<input id="courseList" class="easyui-textbox" name="courseList" />
			<a id="search-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">��绱�</a>
			<a id="clear-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">娓�绌烘��绱�</a>
		</div>
	</div>
	
	<!-- 娣诲���版��绐��� -->
	<div id="addDialog" style="padding: 10px">  
    	<form id="addForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td style="width:40px">瀛���:</td>
	    			<td colspan="3">
	    				<input id="add_studentList" style="width: 200px; height: 30px;" class="easyui-textbox" name="studentid" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr>
	    		<tr>
	    			<td style="width:40px">璇剧�:</td>
	    			<td colspan="3">
	    				<input id="add_courseList" style="width: 200px; height: 30px;" class="easyui-textbox" name="courseid" data-options="required:true, missingMessage:'璇烽���╄�剧�'" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr>
	    		
	    		<tr>
	    			<td style="width:40px">��缁�:</td>
	    			<td colspan="3">
	    				<input id="add_score" style="width: 200px; height: 30px;" class="easyui-numberbox" data-options="required:true,min:0,precision:2, missingMessage:'璇峰～��姝ｇ‘����缁�'" name="score" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr>
	    		
	    		<tr>
	    			<td>澶�娉�:</td>
	    			<td>
	    				<textarea id="add_remark" name="remark" style="width: 300px; height: 160px;" class="easyui-textbox" data-options="multiline:true" ></textarea>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	<!-- 淇��规�版��绐��� -->
	<div id="editDialog" style="padding: 10px">  
    	<form id="editForm" method="post">
	    	
	    	<input type="hidden" id="edit_id" name="id">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td style="width:40px">瀛���:</td>
	    			<td colspan="3">
	    				<input id="edit_studentList" style="width: 200px; height: 30px;" class="easyui-textbox" name="studentid" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr>
	    		<tr>
	    			<td style="width:40px">璇剧�:</td>
	    			<td colspan="3">
	    				<input id="edit_courseList" style="width: 200px; height: 30px;" class="easyui-textbox" name="courseid" data-options="required:true, missingMessage:'璇烽���╄�剧�'" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr>
	    		
	    		<tr>
	    			<td style="width:40px">��缁�:</td>
	    			<td colspan="3">
	    				<input id="edit_score" style="width: 200px; height: 30px;" class="easyui-numberbox" data-options="required:true,min:0,precision:2, missingMessage:'璇峰～��姝ｇ‘����缁�'" name="score" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr>
	    		
	    		<tr>
	    			<td>澶�娉�:</td>
	    			<td>
	    				<textarea id="edit_remark" name="remark" style="width: 300px; height: 160px;" class="easyui-textbox" data-options="multiline:true" ></textarea>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	<!-- 瀵煎�ユ�版��绐��� -->
	<div id="importDialog" style="padding: 10px">  
    	<form id="importForm" method="post" enctype="multipart/form-data" action="ScoreServlet?method=ImportScore" target="import_target">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>璇烽���╂��浠�:</td>
	    			<td>
	    				<input class="easyui-filebox" name="importScore" data-options="required:true,min:0,precision:2, missingMessage:'璇烽���╂��浠�',prompt:'���╂��浠�'" style="width:200px;">
	    			</td>
	    		</tr>
	    		
	    	</table>
	    </form>
	</div>
<!-- ��浜よ〃��澶���iframe妗��� -->
	<iframe id="import_target" name="import_target"></iframe>	
</body>
</html>