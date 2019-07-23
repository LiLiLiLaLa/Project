<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>���ゅ��琛�</title>
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
	        title:'���や俊����琛�', 
	        iconCls:'icon-more',//�炬�� 
	        border: true, 
	        collapsible: false,//������������ 
	        fit: true,//���ㄥぇ灏� 
	        method: "post",
	        url:"AttendanceServlet?method=AttendanceList&t="+new Date().getTime(),
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
 		       {field:'type',title:'绛惧�扮被��',width:200, sortable: false},
 		      {field:'date',title:'绛惧�版�ユ��',width:200, sortable: false}
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
		  		width: 80,
		  		height: 25,
		  		valueField: "id",
		  		textField: "name",
		  		multiple: false, //��澶���
		  		editable: false, //涓���缂�杈�
		  		method: "post",
		  		url: "StudentServlet?method=StudentList&t="+new Date().getTime()+"&from=combox",
		  		
		  	});
	  		$("#courseList").combobox({
		  		width: 80,
		  		height: 25,
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
	    
	    
	    //����
	    $("#delete").click(function(){
	    	var selectRow = $("#dataList").datagrid("getSelected");
        	if(selectRow == null){
            	$.messager.alert("娑�������", "璇烽���╂�版��杩�琛�����!", "warning");
            } else{
            	var id = selectRow.id;
            	$.messager.confirm("娑�������", "灏����や�璇剧��稿�崇�������版��锛�纭�璁ょ户缁�锛�", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "AttendanceServlet?method=DeleteAttendance",
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
	    	title: "娣诲�����や俊��",
	    	width: 450,
	    	height: 300,
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
								url: "AttendanceServlet?method=AddAttendance",
								data: $("#addForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("娑�������","��璇句俊��娣诲������!","info");
										//�抽��绐���
										$("#addDialog").dialog("close");
										//娓�绌哄��琛ㄦ�兼�版��
										$("#add_name").textbox('setValue', "");
										//�锋��
										$('#dataList').datagrid("reload");
									} else{
										$.messager.alert("娑�������",msg,"warning");
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
	  	$("#add_studentList, #add_courseList,#studentList,#courseList,#add_typeList").combobox({
	  		width: "200",
	  		height: "30",
	  		valueField: "id",
	  		textField: "name",
	  		multiple: false, //涓���澶���
	  		editable: false, //涓���缂�杈�
	  		method: "post",
	  	});
	  	//娣诲��淇℃����甯����╂�
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
	  	var typeData = [{id:"涓���",text:"涓���"},{id:"涓���",text:"涓���"}];
	  	$("#add_typeList").combobox({
	  		data:typeData,
	  		valueField: 'id',
	  		textField: 'text',
	  		onLoadSuccess: function(){
				//榛�璁ら���╃��涓��℃�版��
				var data = $(this).combobox("getData");
				$(this).combobox("setValue", data[0].id);
	  		}
	  	});
	  	
	  	$("#typeList").combobox({
	  		data:typeData,
	  		valueField: 'id',
	  		textField: 'text',
	  		width: "80",
	  		height: "25",
	  		onLoadSuccess: function(){
				//榛�璁ら���╃��涓��℃�版��
				//var data = $(this).combobox("getData");
				//$(this).combobox("setValue", data[0].id);
	  		}
	  	});
	  	
	  	
	    //��绱㈡��������浜�浠�
	  	$("#search-btn").click(function(){
	  		$('#dataList').datagrid('load',{
	  			studentid: $("#studentList").combobox('getValue') == '' ? 0 : $("#studentList").combobox('getValue'),
	  			courseid: $("#courseList").combobox('getValue') == '' ? 0 : $("#courseList").combobox('getValue'),
				type: $("#typeList").combobox('getValue') == '' ? '' : $("#typeList").combobox('getValue'),
				date:$("#date").datebox('getValue')
	  		});
	  	});
	    
	    $("#clear-btn").click(function(){
	    	$('#dataList').datagrid("reload",{});
	    	$("#studentList").combobox('clear');
	    	$("#courseList").combobox('clear');
	    });
	    
	    
	});
	</script>
	<script type="text/javascript">
		function myformatter(date){
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
		}
		function myparser(s){
			if (!s) return new Date();
			var ss = (s.split('-'));
			var y = parseInt(ss[0],10);
			var m = parseInt(ss[1],10);
			var d = parseInt(ss[2],10);
			if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
				return new Date(y,m-1,d);
			} else {
				return new Date();
			}
		}
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
		<div style="float: left; margin-right: 10px;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">����</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="margin-top: 3px;">
			瀛���锛�<input id="studentList" class="easyui-textbox" name="studentList" />
			璇剧�锛�<input id="courseList" class="easyui-textbox" name="courseList" />
			绫诲��锛�<input id="typeList" class="easyui-textbox" name="typeList" />
			�堕�达�<input id="date" data-options="formatter:myformatter,parser:myparser" class="easyui-datebox" name="date" />
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
	    				<input id="add_studentList" style="width: 200px; height: 30px;" class="easyui-textbox" name="studentid" data-options="required:true, missingMessage:'璇烽���╁����'" />
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
	    			<td style="width:40px">绫诲��:</td>
	    			<td colspan="3">
	    				<input id="add_typeList" style="width: 200px; height: 30px;" class="easyui-textbox" name="type" data-options="required:true, missingMessage:'璇烽���╃被��'" />
	    			</td>
	    			<td style="width:80px"></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
</body>
</html>