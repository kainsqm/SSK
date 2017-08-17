<%@page contentType="text/html" pageEncoding="GB2312"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">


<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.alibaba.druid.pool.DruidDataSource" %>
<%@page import="java.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JVM memory</title>
</head>
<body>
<%
double total = (Runtime.getRuntime().totalMemory()) / (1024.0 * 1024);
double max = (Runtime.getRuntime().maxMemory()) / (1024.0 * 1024);
double free = (Runtime.getRuntime().freeMemory()) / (1024.0 * 1024);
double realMeomry = max - total + free;
ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext

(this.getServletContext());
DruidDataSource dataSource = (DruidDataSource) context.getBean("DataSource");

out.println("Java �������ͼʹ�õ�����ڴ���(��ǰJVM���������ڴ�)maxMemory(): " + max + "MB<br/>");
out.println("Java ������е��ڴ�����(��ǰJVMռ�õ��ڴ�����)totalMemory(): " + total + "MB<br/>");
out.println("Java ������еĿ����ڴ���(��ǰJVM�����ڴ�)freeMemory(): " + free + "MB<br/>");
out.println("��ΪJVMֻ������Ҫ�ڴ�ʱ��ռ�������ڴ�ʹ�ã�����freeMemory()��ֵһ������¶���С��<br/>" +"��JVMʵ�ʿ����ڴ沢������freeMemory()����Ӧ�õ��� maxMemory()-totalMemory()+freeMemory()��<br/>");
out.println("JVMʵ�ʿ����ڴ�: " + (max - total + free) + "MB<br/>");
out.println("<br/>");
out.println("<br/>���ݿ����ӳ���Ϣ��");
out.println("<br/>��ʼ��������:" + dataSource.getInitialSize());
out.println("<br/>���������:" + dataSource.getMaxActive());
out.println("<br/>��С����������:" + dataSource.getMinIdle());
out.println("<br/>������������:" + dataSource.getMaxIdle());
out.println("<br/>�������:" + dataSource.getActiveCount());
/* out.println("<br/>����������:" + dataSource.getMaxIdle()); */
out.println("<br/>��ʱ�ȴ�ʱ��(����):" + dataSource.getMaxWait());
out.println("<br/>�Ƿ��Զ����ճ�ʱ����:" + dataSource.getRemoveAbandonedCount());
out.println("<br/>��ʱʱ��(��):" + dataSource.getRemoveAbandonedTimeout());
out.println("<div style='display:none'>teshu");
List retunList = new ArrayList();
    retunList.add(0,max);
    retunList.add(1,total);
    retunList.add(2,free);
    retunList.add(3,realMeomry); 
    retunList.add(4,dataSource.getInitialSize());
    retunList.add(5,dataSource.getMaxActive());
    retunList.add(6,dataSource.getMinIdle());
    retunList.add(7,dataSource.getMaxIdle());
    retunList.add(8,dataSource.getActiveCount());
   /*  retunList.add(9,dataSource.getMaxIdle()); */
    retunList.add(10,dataSource.getMaxWait());
    retunList.add(11,dataSource.getRemoveAbandonedCount());
    retunList.add(12,dataSource.getRemoveAbandonedTimeout()); 

 	out.println(retunList.get(0)+","+retunList.get(1)+"t"+retunList.get(2)+"f"+retunList.get(3)+"r"+retunList.get(4)+"getInitialSize"+
 	retunList.get(5)+"getMaxActive"+retunList.get(6)+"getMinIdle"+retunList.get(7)+"getMaxIdle"+retunList.get(8)+"getNumActive"+
 	retunList.get(9)+"getNumIdle"+retunList.get(10)+"getMaxWait"+retunList.get(11)+"getRemoveAbandoned"+retunList.get(12)+"</div>");
%>
</body>
</html>