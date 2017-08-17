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

out.println("Java 虚拟机试图使用的最大内存量(当前JVM的最大可用内存)maxMemory(): " + max + "MB<br/>");
out.println("Java 虚拟机中的内存总量(当前JVM占用的内存总数)totalMemory(): " + total + "MB<br/>");
out.println("Java 虚拟机中的空闲内存量(当前JVM空闲内存)freeMemory(): " + free + "MB<br/>");
out.println("因为JVM只有在需要内存时才占用物理内存使用，所以freeMemory()的值一般情况下都很小，<br/>" +"而JVM实际可用内存并不等于freeMemory()，而应该等于 maxMemory()-totalMemory()+freeMemory()。<br/>");
out.println("JVM实际可用内存: " + (max - total + free) + "MB<br/>");
out.println("<br/>");
out.println("<br/>数据库连接池信息：");
out.println("<br/>初始化连接数:" + dataSource.getInitialSize());
out.println("<br/>最大连接数:" + dataSource.getMaxActive());
out.println("<br/>最小空闲连接数:" + dataSource.getMinIdle());
out.println("<br/>最大空闲连接数:" + dataSource.getMaxIdle());
out.println("<br/>活动连接数:" + dataSource.getActiveCount());
/* out.println("<br/>空闲连接数:" + dataSource.getMaxIdle()); */
out.println("<br/>超时等待时间(毫秒):" + dataSource.getMaxWait());
out.println("<br/>是否自动回收超时连接:" + dataSource.getRemoveAbandonedCount());
out.println("<br/>超时时间(秒):" + dataSource.getRemoveAbandonedTimeout());
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