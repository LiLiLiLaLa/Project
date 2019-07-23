<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>��缁╃�璁�</title>
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/js/validateExtends.js"></script>
	<script type="text/javascript">
	$(function() {	
		
		//������杞藉������璇剧�淇℃��
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
		
	  
	  //涓���妗����ㄥ���
	  	$("#courseList").combobox({
	  		width: "200",
	  		height: "30",
	  		valueField: "id",
	  		textField: "name",
	  		multiple: false, //涓���澶���
	  		editable: false, //涓���缂�杈�
	  		method: "post",
	  	});
	  	
	    //��绱㈡��������浜�浠�
	  	$(".search-score-btn").click(function(){
	  		var searchKey = $(this).attr('key');
	  		var courseId = $("#courseList").combobox('getValue');
	  		if(courseId == null || courseId == ''){
	  			$.messager.alert("娑�������","璇烽���╄�剧�!","info");
	  			return;
	  		}
	  		$.ajax({
	  			url:'ScoreServlet?method=getStatsList&courseid='+courseId+"&searchType="+searchKey,
	  			dataType:'json',
	  			success:function(rst){
	  				if(rst.type == "suceess"){
	  					var option;
	  					if(searchKey == 'range'){
	  						option = {
		  			  	            title: {
		  			  	                text: '璇剧�锛�'+rst.courseName
		  			  	            },
		  			  	            tooltip: {
		  			  	                trigger: 'axis',
		  			  	                axisPointer: {
		  			  	                    type: 'cross',
		  			  	                    crossStyle: {
		  			  	                        color: '#999'
		  			  	                    }
		  			  	                }
		  			  	            },
		  			  	        legend: {
		  			  	        		data:['��缁╁�洪�村��甯�']
		  			  	    		},
		  			  	            xAxis: {
		  			  	                data: rst.rangeList
		  			  	            },
		  			  	            yAxis: {type: 'value'},
		  			  	            series: [{
		  			  	                name: '��缁╁�洪�村��甯�',
		  			  	                type: 'bar',
		  			  	                data: rst.numberList
		  			  	            }]
		  			  	        };
	  					}else{
	  						option = {
		  			  	            title: {
		  			  	                text: '璇剧�锛�'+rst.courseName
		  			  	            },
		  			  	            tooltip: {
		  			  	                trigger: 'axis',
		  			  	                axisPointer: {
		  			  	                    type: 'cross',
		  			  	                    crossStyle: {
		  			  	                        color: '#999'
		  			  	                    }
		  			  	                }
		  			  	            },
		  			  	        legend: {
		  			  	        		data:['��缁╁��甯�']
		  			  	    		},
		  			  	            xAxis: {
		  			  	                data: rst.avgList
		  			  	            },
		  			  	            yAxis: {type: 'value'},
		  			  	            series: [{
		  			  	                name: '��缁╁��甯�',
		  			  	                type: 'bar',
		  			  	                data: rst.scoreList
		  			  	            }]
		  			  	        };
	  					}
	  					showCharts(option);
	  				}else{
	  					$.messager.alert("娑�������","�峰���版���洪��!","info");
	  				}
	  			}
	  		})
	  		
	  	});
	    
	});
	</script>
</head>
<body style="padding:0px;">
	<div class="panel-header"><div class="panel-title panel-with-icon">��缁╀俊��缁�璁�</div><div class="panel-icon icon-more"></div><div class="panel-tool"></div></div>
	<!-- 宸ュ�锋�� -->
	<div id="toolbar" class="datagrid-toolbar">
		<div style="margin-top: 3px;">
			璇剧�锛�<input id="courseList" class="easyui-textbox" name="courseList" />
			<a href="javascript:;" class="easyui-linkbutton search-score-btn" key="range" data-options="iconCls:'icon-sum',plain:true">�洪�寸�璁″��</a>
			<a href="javascript:;" class="easyui-linkbutton search-score-btn" key="avg" data-options="iconCls:'icon-sum',plain:true">骞冲��缁�璁″��</a>
		</div>
	</div>
	<div id="charts-div" style="width:100%;height:500px;"></div>
</body>
<script type="text/javascript" src="easyui/js/echarts.common.min.js"></script>
<script type="text/javascript">
        // �轰���澶�濂界��dom锛���濮���echarts瀹�渚�
        var myChart = echarts.init(document.getElementById('charts-div'));

        function showCharts(option){
        	// 浣跨�ㄥ����瀹�����缃�椤瑰���版���剧ず�捐〃��
            myChart.setOption(option);
        }
        // ��瀹��捐〃����缃�椤瑰���版��
        

    </script>
</html>