
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
			<span>�� <%=pageBean.getTotalRecords() %> ��</span>
			<span>ÿҳ��ʾ <%=pageBean.getRowsPerPage() %> ��</span>
			

			<%	if(pageBean.getCurrentPage() <=1)
				{out.print(" <span>��ҳ</span> <span>��һҳ</span>");}
				else{
			%>
			<a href="javascript:gotoPage(1)">��ҳ</a>
			<a href="javascript:gotoPage(<%=pageBean.getCurrentPage()-1%>)">��һҳ</a>
			
			<%}%>
			 <span><%=pageBean.getCurrentPage()%> / <%=pageBean.getTotalPages()%></span>
			<%	if(pageBean.getCurrentPage()==pageBean.getTotalPages()||pageBean.getTotalPages()==0)
				{out.print(" <span>��һҳ</span> <span>βҳ</span>");}
				else{%>
			<a href="javascript:gotoPage(<%=pageBean.getCurrentPage()+1%>)">��һҳ</a>
			<a href="javascript:gotoPage(<%=pageBean.getTotalPages()%>)">βҳ</a>
			
			<%}%>
			<input type="hidden" name="currentPage" value="<%=pageBean.getCurrentPage()%>">	
			<input type="hidden" name="total" id="total" value="<%=pageBean.getTotalRecords() %>">	
			
		</td>
	</tr>
</table>
