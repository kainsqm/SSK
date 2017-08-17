<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>客调项目管理系统</title>
		<link href="<%=path%>/css/public.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=path%>/lib/ligerUI/skins/Gray/css/all.css"
			rel="stylesheet" type="text/css" />
		<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js"
			type="text/javascript"></script>
		<script src="<%=path%>/lib/ligerUI/js/core/base.js"
			type="text/javascript"></script>
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerGrid.js"
			type="text/javascript"></script>
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js"
			type="text/javascript"></script>
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerCheckBox.js"
			type="text/javascript"></script>
		<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js"
			type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
			<script src="<%=path%>/js/utils.js" type="text/javascript"></script>
		<style type="text/css">
div.title_div{border-radius: 5px; text-align:left; margin-top:5px; background-image:url(<%=path%>/img/login/title_bg.jpg);border: solid 1px #D7D7D7;
			 width:90%; height:30px; line-height:30px; vertical-align:middle; font-size:14px; font-family:"黑体";
		}
		td{line-height:16px !important;}
		input.input_text{border-radius:5px; width:130px; height:18px; line-height:18px; padding-left:3px;}
		input[type='button']{ border:#d3d3d3 1px solid; width:80px; height:25px; cursor:pointer;}
		input.date_text{border-radius:5px; width:140px; height:18px; line-height:18px;}
		
		table#search_table td{ height:30px; line-height:30px;}
		table#search_table td.td_lable{ text-align:right;}
		table#search_table td.td_value{ text-align:left;}
</style>
		<script type="text/javascript"> 
	    var maingrid_gd=null;
	    var curTime=null;
	    var today;
		$(function () {
		var d = new Date();	
			var year = d.getFullYear();
			var month = d.getMonth() + 1; // 记得当前月是要+1的
			if(month<10){
			month="0"+month;
			}
			today = year + "-" + month;
		
			$("#qctime").val(today);
             maingrid_gd= $("#agentscore").ligerGrid({
                columns: [                
				{ display: '序号', name: 'id', align: 'left', minWidth:130,isSort:false,
				totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return '<div>合计</div>';
                        },
                        align: 'left'
                    }},
				{ display: '姓名', name: 'name', align: 'left', minWidth:130,isSort:false},
                { display: '工号', name: 'smailworkid', align: 'left', minWidth:120,isSort:false},
				{ display: '时间', name: 'qctime', align: 'left', minWidth:150,isSort:false},
				{ display: '112', name: 'is112', align: 'left', minWidth:80,isSort:false,
				 totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: 'c网', name: 'iscw', align: 'left', minWidth:80,isSort:false,
				 totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '其他', name: 'ycl', align: 'left', minWidth:100,isSort:false,
				 totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '管控', name: 'gk', align: 'left', minWidth:160,isSort:false,
				 totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '联系信息输入错', name: 'lxcw', align: 'left', minWidth:100,isSort:false,
				 totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '错派/错约', name: 'cp', align: 'left', minWidth:100,isSort:false,
				 totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '漏派/漏约修', name: 'lp', align: 'left', minWidth:100,isSort:false,
				 totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '业务不熟处理不当', name: 'ywbs', align: 'left', minWidth:100,isSort:false,
				totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '有闻不录', name: 'ywbl', align: 'left', minWidth:100,isSort:false,
				totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
                { display: '号码未复述', name: 'wfl', align: 'left', minWidth:100,isSort:false,
                totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
                },
				{ display: '用语不规范', name: 'ywbgf', align: 'left', minWidth:100,isSort:false,
				totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '受理中态度生硬', name: 'tdsy', align: 'left', minWidth:100,isSort:false,
				totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '分类选择错误', name: 'flxzcw', align: 'left', minWidth:100,isSort:false,
				totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '违反现场管理制度', name: 'wfxc', align: 'left', minWidth:100,isSort:false,
				totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '表式（原始记录）填写不规范', name: 'bsbgf', align: 'left', minWidth:100,isSort:false,
				totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '受理/测试/结案代码错', name: 'jndmc', align: 'left', minWidth:100,isSort:false,
				totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '受理/抽单/派修超时', name: 'pdcs', align: 'left', minWidth:100,isSort:false,
				totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '操作不规范', name: 'czbgf', align: 'left', width:100,isSort:false,
				totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
                { display: '用心服务不够', name: 'yxbg', align: 'left', width:150,isSort:false,
                totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
                },
				{ display: '其它', name: 'qt', align: 'left', width:150,isSort:false,
				totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '用户投诉', name: 'yhts', align: 'left', minWidth:100,isSort:false,
				totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '用户表扬', name: 'yhby', align: 'left', minWidth:100,isSort:false,
				totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '备注信息错', name: 'bzxxc', align: 'left', minWidth:160,isSort:false,
				totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '129应答不规范', name: 'ydbgf', align: 'left', minWidth:160,isSort:false,
				totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '管控错派/错约', name: 'gkcp', align: 'left', minWidth:100,isSort:false,
				totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '管控分类选择错误', name: 'gkfl', align: 'left', minWidth:100,isSort:false,
				totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '漏管控/点评/关注', name: 'lgk', align: 'left', minWidth:100,isSort:false,
				totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '业务不熟管控错误', name: 'ywgkbs', align: 'left', minWidth:100,isSort:false,
				totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '质量得分', name: 'zldf', align: 'left', width:160,isSort:false,
				totalSummary:
	                    {
	                       // type: 'sum'
	                        render: function (suminf, column, cell)
	                        {
	                            return '<div>' + suminf.sum + '</div>';
	                        },
                            align: 'left'
	                    }
				},
				{ display: '合格率', name: 'qualified', align: 'left', width:160,isSort:false,
				  render : function(rowdata, rowindex, value) {
					var result="";
					 if (value == null||value ==""){ return '0%';
					 }else{
					  return value;
					 }
						return result;
					}	
				}	
                ], url:'<%=path%>/controller/qc/selectAgentscore.do',
                parms :[{ name:"qctime",value:$("#qctime").val()}],  
                height:'100%', width:"auto", pageSize:10 ,rownumbers :false,
                rowAttrRender: function(rowdata,rowindex){
	                if(rowdata.lxcw>=3||rowdata.cp>=3||rowdata.lp>=3||rowdata.ywbs>=3||rowdata.ywbl>=3||rowdata.wfl>=3||rowdata.ywbgf>=3||rowdata.tdsy>=3
		                ||rowdata.flxzcw>=3||rowdata.wfxc>=3||rowdata.bsbgf>=3||rowdata.jndmc>=3||rowdata.pdcs>=3||rowdata.czbgf>=3||rowdata.yxbg>=3||rowdata.yhts>=3
		                ||rowdata.bzxxc>=3||rowdata.ydbgf>=3||rowdata.gkcp>=3||rowdata.gkfl>=3||rowdata.lgk>=3||rowdata.ywgkbs>=3||rowdata.zldf<90||parseFloat(rowdata.qualified)<=parseFloat("83%") ){       
		              	return "style='color:red !important;' onclick='getinfo(\""+rowindex+"\")' ";
	                }
                } 
            });
            $("#pageloading").hide(); 
        });
        
        
        function getinfo(rowindex){
        	var uprow = maingrid_gd.getRow(rowindex);
     		window.location.href="<%=path%>/page/report/workAgentscoreInfo.jsp?workid="+uprow.smailworkid+"&qctime="+uprow.qctime;
        }
        
          function f_search(){
          var qctime=$("#qctime").val();
          if(qctime==null||qctime==""){
          $.ligerDialog.error("质检时间不能为空");
          return false;
          }
            var parmItems=[
				{ name:"smailworkid",value:$("#smailworkid").val()},
				{ name:"isTl9000",value:$("#isTl9000").val()},
				{ name:"qctime",value:$("#qctime").val()}
				];
			maingrid_gd.options.parms=parmItems;
			maingrid_gd.options.page=1;
			maingrid_gd.options.newPage=1;
            //grid.loadServerData(parmItems);
            maingrid_gd.options.data = $.extend(true, {}, parmItems);//必须这么写
		    maingrid_gd.loadData(f_getWhere());
        }
        function f_getWhere()
        {
            if (!maingrid_gd) return null;
            var clause = function (rowdata, rowindex)
            {
                var key = $("#txtKey").val();
                return rowdata.CustomerID.indexOf(key) > -1;
            };
            return clause; 
        }
        
        function exportExcel(){
        	var gridMgr = liger.get("agentscore");
			var data = gridMgr.getData();
			if (data.length == 0) { // 判断grid是否有数据
			$.ligerDialog.warn("没有需要导出的数据");
			return false;
			}
			var smailworkid = $("#smailworkid").val();
			var isTl9000 = $("#isTl9000").val();
			var qctime = $("#qctime").val();
			window.location.href="<%=path%>/controller/qc/exportExcel.do?smailworkid="+smailworkid+"&isTl9000="+isTl9000+"&qctime="+qctime;      
        }
        
       
		function chongzhi(){
		    $("#smailworkid").val("");
			$("#isTl9000").val("");
			$("#qctime").val(today);
		}
    </script>
	</head>
	<!-- 
<body style="margin:0 auto; background-color:#FFFFFF; padding:5px;">
 -->
	<body>
		<div style="">
			<div class="title_div"
				style="width: 100%; margin-top: 0px; display: none;">
				<label style="margin-left: 15px;">
					查询条件
				</label>
			</div>
			<div align="left"
				style="margin-top: 10px; margin-left: 5px; padding-left: 10px;">
				<table id="search_table" cellpadding="3" cellspacing="3"
					width="100%">
					<tr>
						<td class="td_lable">
							受理员：
						</td>
						<td class="td_value">
							<input type="text" id="smailworkid"  onkeyup="strlenLimit(this,20);"/>
						</td>
						<td>
							检查部门：&nbsp;&nbsp;
							<select id="isTl9000">
							<option value="">---请选择---</option>		
							<option value="1" >tl9000</option>
							<option value="2" >业务室</option>
							<option value="3" >受理中心</option>
							<option value="4" >质检小组</option>
							<option value="5" >全部</option>
							</select>
						</td>						
						<td class="td_lable">
							质检时间：
						</td>
						<td class="td_value">
							<input class="Wdate date_text" type="text"
								onfocus="WdatePicker({dateFmt:'yyyy-MM'})"
								id="qctime" />
						</td>			
					</tr>
					<tr>
						<td colspan="8" align="center">
							<input type="button" value="查询" onclick="f_search()" />
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="重置" onclick="chongzhi()" />
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" onclick="exportExcel()" value="导出" />
						</td>
					</tr>
				</table>
			</div>
			<div class="l-loading" style="display: block" id="pageloading"></div>
			<div id="agentscore" style="margin-top: 10px;"></div>
		</div>
	</body>
</html>
