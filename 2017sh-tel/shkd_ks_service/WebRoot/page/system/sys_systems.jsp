<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt"  uri="/WEB-INF/fmt-rt.tld"%> 
<%@ taglib prefix="c"  uri="/WEB-INF/c.tld"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
    <title></title> 
    <link href="${ctx}/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" /> 
	<link href="${ctx}/css/public.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>   
    <script src="${ctx}/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script src="${ctx}/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="${ctx}/js/ajaxSession.js" type="text/javascript"></script>
    <script src="${ctx}/lib/ligerUI/js/plugins/ligerPanel.js" type="text/javascript"></script>
    <script type="text/javascript">
        var manager;
        $(function ()
        {
            var wid = $("body").width()/2-32;
            var hei = $("body").height()-40;
            $("#panel2").ligerPanel({
                title: '业务分类管理',
                width: wid,
                height : hei,
                frameName :'question',
                url : '${ctx}/page/system/sys_question.jsp'
            });
             $("#panel3").ligerPanel({
                title: '参数配置',
                width: wid,
                height : hei,
                frameName :'seting',
                url : '${ctx}/page/system/sys_seting.jsp'
            });
        }); 
        
        function deleteNode(){
           $.ligerDialog.success('删除成功');	
        }
        
        function getNode(){
           $.ligerDialog.warn('获取树异常');
        }
        
        function isused(){
          $.ligerDialog.error('该类型已被使用，无法删除', '提示',
										function(yes) {
											history.go(0);
										});
        }
        
        function delete1(){
         $.ligerDialog.error('请先删除该分类的子类别', '提示',
										function(yes) {
											history.go(0);
										});
        }
        
        function alertupdateFlag(updateFlag){
            $.ligerDialog.warn(result.flag);
        }
        
        
        function deletefail(){
         $.ligerDialog.error('删除失败','提示',function(yes){	
	     		 			history.go(0);
	     		 		});
        }
        
        function dodail(){
           $.ligerDialog.warn('操作异常');
        }
        
        function checknameisnull(){
           $.ligerDialog.warn("名称不能为空");
        }
        
        function checknamelength(){
           $.ligerDialog.warn("节点名称长度超出长度限制");
        }
        
        function insertsuccess(){
        	history.go(0);
        }
        
        function updatesuccess(){
           $.ligerDialog.success('修改成功');
        }
        
        
        function sure(type,e,treeNode){
		   $.ligerDialog.confirm('确认要新增吗？', function (yes){
                  if(yes){
                     if(type=="question"){
                         window.frames["question"].addNexquestiontDom(e,treeNode);
                     }else if(type=="seting"){
                         window.frames["seting"].addNextDom(e,treeNode);
                     }
                  }
           })
        }
    </script>

</head>
<body >

      <div style="width:100%;padding: 20px;">
            <div id="panel1-1" style="float:left"></div>
            <div id="panel1-2" style="float:left; margin-left:10px;"></div>
            <div id="panel2" style=" float:left;"> </div>
            <div id="panel3" style=" float:left; margin-left:20px;"> </div>
            <div class="l-clear"></div>
        </div>

</body>
</html>
