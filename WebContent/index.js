/**
 * 格式化日期
 */
function myformatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}

/**
 * 解析日期
 */
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

/**
 * 页面初始化
 */
$(document).ready(function(){	
	$("#WinAdd").window("close");//关闭新增窗体
	$("#WinEdit").window("close");//关闭修改窗体
	
	$("#Search_specialty").combobox({url:'getSearch_specialty'});
	$("#dgStudent").datagrid({url:"getStudentInfo"});
	$("#Add_specialty").combobox({url:'getSpecialty'});
	$("#Edit_specialty").combobox({url:'getSpecialty'});
});

/**
 * 单击dg中的行，将选择的学生ID保存在隐藏文本框中供以后使用
 */
function Student_onClickRow(rowIndex,row){
	var stu_id = row.stu_id;
	$("#SelectId").val(stu_id);
}
var count = 0;
/**
 * 查询
 */
function fuzzyQuery(){
	var specialty_id = $("#Search_specialty").combobox("getValue");
	var SearchInputNum = $("#SearchInputNum").val();
	var SearchInputChar = $("#SearchInputChar").val();
	var url;
	if(specialty_id == undefined || specialty_id == ""){
		specialty_id = 0;
	}
	url = "fuzzyQuery?specialty_id="+specialty_id+"&SearchInputNum="+SearchInputNum+"&SearchInputChar="+SearchInputChar;
	$("#dgStudent").datagrid({url:url});
}

/**
 * 新增学生信息
 */
function add(){
	$("#Add_stu_id").val("");
	$("#Add_specialty").combobox("setValue",1);
	$("#Add_name").val("");
	$("#Add_sex").val(0);
	$("#Add_age").val("46");
	$("#Add_birthday").datebox("setValue","1970-1-1");
	$("#Add_info").val("");
	$("#WinAdd").window("open");
}

/**
 * 修改学生信息
 */
function edit(){
	var stu_id = $("#SelectId").val();
	if(stu_id == undefined || stu_id == ""){
		alert("请选择要修改的学生！");
		return;
	}else{
		var row = $("#dgStudent").datagrid("getSelected");
		$("#Edit_stu_id").val(row.stu_id);
		$("#Edit_specialty").combobox("setValue",row.specialty_id);
		$("#Edit_name").val(row.name);
		$("#Edit_sex").val(row.sex=="男"?0:1);
		$("#Edit_age").val(row.age);
		$("#Edit_birthday").datebox("setValue",row.birthday);
		$("#Edit_info").val(row.info);
		$("#WinEdit").window("open");
	}
}

/**
 * 删除学生信息
 */
function dele(){
	var stu_id = $("#SelectId").val();
	if(stu_id == undefined || stu_id == ""){
		alert("请选择要删除的学生！");
		return;
	}else{
		if(confirm("确认要删除该学生吗？")){
			$.ajax({
				url:"deleteStudent", 
				dataType:"json",
				async:true,
				data:{"stu_id":stu_id},
				type:"POST",
				success:function(result){
					if(result=="OK"){
						alert("删除成功！");
						$("#dgStudent").datagrid("reload");
					}
				}
			});
		}
	}
}

/**
 * 新增页面修改日期
 */
function WinAddonSelect(date){
	var age = getAge( myformatter(date) );
	$("#Add_age").val(age);
}

/**
 * 新增 保存
 */
function add_save(){
	var specialty_id = $("#Add_specialty").combobox("getValue");
	var name = $("#Add_name").val();
	var sex = $("#Add_sex").val();
	var age = $("#Add_age").val();
	var birthday = $("#Add_birthday").datebox("getValue");
	var info = $("#Add_info").val();
	if(specialty_id == "" || name == "" || sex == "" || age == "" || birthday == ""){
		alert("请补全相应信息！")
		return;
	}else if(isNaN(age)){
		alert("年龄必须为数字！")
		return;
	}else{
		var stu_info = specialty_id + "," + name + "," + sex + "," + age + "," + birthday + "," +  info;
		$.ajax({
			url:"AddStudent",
			dataType:"json",
			async:true, //异步提交
			data:{"stu_info":stu_info},
			type:"POST",
			success:function(result){
				if(result=="OK"){
					alert("新增成功！");
					$("#dgStudent").datagrid("reload");
					$("#WinAdd").window("close");//关闭新增窗体
				}
			}			
		});
	}
}

/**
 * 修改界面保存日期
 */
function WinEditonSelect(date){
	var age = getAge( myformatter(date) );
	$("#Edit_age").val(age);
}

/**
 * 编辑 保存
 */
function edit_save(){
	var stu_id = $("#Edit_stu_id").val();
	var specialty_id = $("#Edit_specialty").combobox("getValue");
	var name = $("#Edit_name").val();
	var sex = $("#Edit_sex").val();
	var age = $("#Edit_age").val();
	var birthday = $("#Edit_birthday").datebox("getValue");
	var info = $("#Edit_info").val();
	if(specialty_id == "" || name == "" || sex == "" || age == "" || birthday == ""){
		alert("请补全相应信息！")
		return;
	}else if(isNaN(age)){
		alert("年龄必须为数字！")
		return;
	}else{
		var stu_info = stu_id + "," + specialty_id + "," + name + "," + sex + "," + age + "," + birthday + "," +  info;
		$.ajax({
			url:"EditStudent",
			dataType:"json",
			async:true,
			data:{"stu_info":stu_info},
			type:"POST",
			success:function(result){
				if(result=="OK"){
					alert("修改成功！");
					$("#dgStudent").datagrid("reload");
					$("#WinEdit").window("close");//关闭修改窗体
				}
			}
		});
	}	
}

/**
 * 返回
 */
function back(){
	$("#WinAdd").window("close");//关闭新增窗体
	$("#WinEdit").window("close");//关闭修改窗体
}

/**
 * 根据出生日期算出年龄 
 */
function getAge(Birthday){         
    var returnAge;  
    var BirthdayArr=Birthday.split("-");  
    var birthYear = BirthdayArr[0];  
    var birthMonth = BirthdayArr[1];  
    var birthDay = BirthdayArr[2];  
      
    d = new Date();  
    var nowYear = d.getFullYear();  
    var nowMonth = d.getMonth() + 1;  
    var nowDay = d.getDate();  
      
    if(nowYear == birthYear){  
        returnAge = 0;//同年 则为0岁  
    }else{  
        var ageDiff = nowYear - birthYear ; //年之差  
        if(ageDiff > 0){  
            if(nowMonth == birthMonth) {  
                var dayDiff = nowDay - birthDay;//日之差  
                if(dayDiff < 0){  
                    returnAge = ageDiff - 1;  
                }else{  
                    returnAge = ageDiff ;  
                }  
            }else{  
                var monthDiff = nowMonth - birthMonth;//月之差  
                if(monthDiff < 0){  
                    returnAge = ageDiff - 1;  
                }else{  
                    returnAge = ageDiff ;  
                }  
            }  
        }else{  
            returnAge = -1;//返回-1 表示出生日期输入错误 晚于今天  
        }  
    }      
    return returnAge;//返回周岁年龄  
      
}  