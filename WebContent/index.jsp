<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>学生信息管理</title>
	
	<!-- 	加载jQuery库 -->
	<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
	<link rel="stylesheet" href="jquery-easyui-1.3.3/themes/default/easyui.css" type="text/css" />
	<link rel="stylesheet" href="jquery-easyui-1.3.3/themes/icon.css" type="text/css" />
	
	<!-- 	加载自定义库 -->
	<script type="text/javascript" src="index.js" ></script>
	<link rel="stylesheet" href="index.css" type="text/css" />

</head>

<body>

	<input type="hidden" id="SelectId" value="" />

	<h1>学生信息</h1>

	<div id="tb">
		班级：<input class="easyui-combobox" id="Search_specialty" data-options="valueField:'specialty_id',textField:'specialty_name'">
		学号：<input id="SearchInputNum" type="text" style="width:90px">
		姓名、个人简介：<input id="SearchInputChar" type="text" style="width:90px">
		<a class="easyui-linkbutton" icon="icon-search" onClick="fuzzyQuery()">查询</a>
 		<a class="easyui-linkbutton" icon="icon-add" OnClick="add()">新增</a>
 		<a class="easyui-linkbutton" icon="icon-edit" OnClick="edit()">修改</a>
 		<a class="easyui-linkbutton" icon="icon-remove" OnClick="dele()">删除</a>
	</div>
	<div class="dg_margin">
		<table id="dgStudent" class="easyui-datagrid" title="学生明细"
			data-options="
						toolbar:'#tb',
						striped:true, /*交替显示行背景 */
						rownumbers: true, /*显示行数 */
						singleSelect:true,
						scrolling:false,
						fit:true,
	 					idField: 'stu_id',
	 					onClickRow: Student_onClickRow
					">
			<thead>
				<tr>	
					<th data-options="field:'stu_id',width:80">学号</th>
					<th data-options="field:'specialty_id',hidden:true">班级号</th>
					<th data-options="field:'specialty_name',width:150">班级</th>
					<th data-options="field:'name',width:80">姓名</th>
					<th data-options="field:'sex',width:80">性别</th>
					<th data-options="field:'age',width:80">年龄</th>
					<th data-options="field:'birthday',width:80">生日</th>
					<th data-options="field:'info',width:400">个人简介</th>
				</tr>
			</thead>
		</table>	
	</div>
	
 	<div id="WinAdd" class="easyui-window" title="新增学生信息" style="width:650px;height:400px;" 
   		data-options="resizable:false,collapsible:false,minimizable:false,maximizable:false,modal:true">
   		<table class="window_table">
   			<tr>
   				<td class="tdwidth">学号：</td><td><input id="Add_stu_id" type="text" disabled="disabled"></td>
   				<td class="tdwidth">班级：</td><td><input class="easyui-combobox" id="Add_specialty" data-options="valueField:'specialty_id',textField:'specialty_name'">
   			</tr>
   			
   			<tr>
   				<td class="tdwidth">姓名：</td><td><input id="Add_name" type="text"></td>
   				<td class="tdwidth">性别：</td>
   				<td>
   					<select class="easyui-databox" id="Add_sex">
   						<option value="0">男</option>
   						<option value="1">女</option>
   					</select>
   				</td>
   			</tr>
   			<tr>
   				<td class="tdwidth">生日：</td><td><input  class="easyui-datebox" id="Add_birthday" data-options="formatter:myformatter,parser:myparser,onSelect:WinAddonSelect" >&nbsp;&nbsp;</td>
   				<td class="tdwidth">年龄：</td><td><input id="Add_age" type="text"></td>
   			</tr>
   			
   			<tr>
   				<td colspan="3">个人简介：</td>
   			</tr>
   			<tr>
   				<td colspan="4">
					<textarea id="Add_info" class="textarea_css" rows="8"></textarea>
					<br><br/>
   				</td>
   			</tr>
   			<tr><td>&nbsp;</td></tr>
   			<tr>
   				<td colspan="2" style="text-align:right"><a class="easyui-linkbutton" icon="icon-add" onClick="add_save()">保 存</a></td>
   				<td colspan="2" ><a class="easyui-linkbutton" icon="icon-undo" onClick="back()">返  回</a></td>
   			</tr>
   		</table>
   	</div>
  	
 	<div id="WinEdit" class="easyui-window" title="修改学生信息" style="width:650px;height:400px;" 
   		data-options="resizable:false,collapsible:false,minimizable:false,maximizable:false,modal:true">
   		<table class="window_table">
   			<tr>
   				<td class="tdwidth">学号：</td><td><input id="Edit_stu_id" type="text" disabled="disabled"></td>
   				<td class="tdwidth">班级：</td><td><input class="easyui-combobox" id="Edit_specialty" data-options="valueField:'specialty_id',textField:'specialty_name'">
   			</tr>
   			
   			<tr>
   				<td class="tdwidth">姓名：</td><td><input id="Edit_name" type="text"></td>
   				<td class="tdwidth">性别：</td>
   				<td>
   					<select class="easyui-databox" id="Edit_sex">
   						<option value="0">男</option>
   						<option value="1">女</option>
   					</select>
   				</td>
   			</tr>
   			<tr>
   				<td class="tdwidth">生日：</td><td><input  class="easyui-datebox" id="Edit_birthday" data-options="formatter:myformatter,parser:myparser,onSelect:WinEditonSelect" >&nbsp;&nbsp;</td>
   				<td class="tdwidth">年龄：</td><td><input id="Edit_age" type="text"></td>
   			</tr>
   			
   			<tr>
   				<td colspan="3">个人简介：</td>
   			</tr>
   			<tr>
   				<td colspan="4">
					<textarea id="Edit_info" class="textarea_css" rows="8"></textarea>
					<br><br/>
   				</td>
   			</tr>
   			<tr><td>&nbsp;</td></tr>
   			<tr>
   				<td colspan="2" style="text-align:right"><a class="easyui-linkbutton" icon="icon-add" onClick="edit_save()">保 存</a></td>
   				<td colspan="2" ><a class="easyui-linkbutton" icon="icon-undo" onClick="back()">返  回</a></td>
   			</tr>
   		</table>
   	</div>
    
  </body>

</html>

