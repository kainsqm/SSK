
<%@ page language="java" contentType="text/html; charset=GBK"%>
<script type="text/javascript" src="../js/pageReturnOnePByCurrentP.js"></script>
<style>
  tr td a,tr td span{
    margin-left:10px;
   
  }
</style>
<jsp:useBean id="pageBean" class="cn.sh.ideal.util.SearchBean" scope="request"/>
<table border="0" cellpadding="0" cellspacing="0" align="center" style="margin:auto">
<%if(pageBean.getTotalRecords()==-1)pageBean.setTotalRecords(0); %>
	<tr>
		<td >
			<span>共 <%=pageBean.getTotalRecords() %> 条</span>
			<span>每页显示 <%=pageBean.getRowsPerPage() %> 条</span>
			

			<%	if(pageBean.getCurrentPage() <=1)
				{out.print(" <span>首页</span> <span>上一页</span>");}
				else{
			%>
			<a href="javascript:gotoPage(1)">首页</a>
			<a href="javascript:gotoPage(<%=pageBean.getCurrentPage()-1%>)">上一页</a>
			
			<%}%>
			 <span><%=pageBean.getCurrentPage()%> / <%=pageBean.getTotalPages()%></span>
			<%	if(pageBean.getCurrentPage()==pageBean.getTotalPages()||pageBean.getTotalPages()==0)
				{out.print(" <span>下一页</span> <span>尾页</span>");}
				else{%>
			<a href="javascript:gotoPage(<%=pageBean.getCurrentPage()+1%>)">下一页</a>
			<a href="javascript:gotoPage(<%=pageBean.getTotalPages()%>)">尾页</a>
			
			<%}%>
			<input type="hidden" name="currentPage" value="<%=pageBean.getCurrentPage()%>">	
			<input type="hidden" name="total" id="total" value="<%=pageBean.getTotalRecords() %>">	
			
		</td>
	</tr>
</table>
