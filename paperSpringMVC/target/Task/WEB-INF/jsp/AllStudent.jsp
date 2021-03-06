<%--
  Created by IntelliJ IDEA.
  User: Mr_Wang
  Date: 2018/9/25
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  import="java.util.*" %>
<%@ page isELIgnored="false" %>
<% String path=request.getContextPath();
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="/tags" prefix="date"%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>学生列表</title>
</head>
<body>
<h2><a href="<%=basePath%>/toStudentMore">添加用户</a> </h2>
<c:if test="${allErrors != null }">
    <c:forEach items="${allErrors }" var="error">
        <font color="red" >${ error.defaultMessage}</font>
    </c:forEach>
</c:if>
<%--${pageContext.request.contextPath}是绝对路径--%>
<form action="<%=basePath%>/StudentByName" method="GET">
    <label for="name">姓名：</label>
    <input id="name" name="name">
    <input type="submit" value="查询"/>
</form>
<table width="100%" border="1">
 <tbody>
  <tr>
      <th>姓名</th>
      <th>学号</th>
      <th>QQ</th>
      <th>学习类型</th>
      <th>入学时间</th>
      <th>毕业院校</th>
      <th>日报链接</th>
      <th>愿望</th>
      <th>师兄</th>
      <th>方式</th>
      <th>表格创建时间</th>
      <th>表格更新时间</th>
  </tr>
  <%--传入数据--%>
 <c:forEach items="${pageInfo.list}" var="student" >
     <tr>
         <td>${student.name}</td>
         <td>${student.student_id}</td>
         <td>${student.qq}</td>
         <td>${student.learn_type}</td>
         <td><date:date value ="${student.entrance_time} "/></td>
         <td>${student.school}</td>
         <td>${student.daily_link}</td>
         <td>${student.wish}</td>
         <td>${student.senior}</td>
         <td>${student.way}</td>
         <td><date:date value ="${student.create_time} "/></td>
         <td><date:date value ="${student.update_time} "/></td>

             <%--点击编辑时,会将这一条数据的id信息传入控制层的toUpdate方法
             toUpdate方法中,会按这个id去查询,返回这条数据信息--%>

             <%--         修改了路径.改成了<%=basePath%>/>--%>
         <td><a href="<%=basePath%>/toStudentInfo/${student.id}">编辑</a></td>
         <td><form action="<%=basePath%>/StudentLesser/${student.id}" method="post">
<%--         <td><form action="${pageContext.request.contextPath}/deleteStudent/{id}" method="post">--%>
             <input type="hidden" name="_method" value="DELETE">
             <input TYPE="submit" value="删除"></form>
         </td>
     </tr>
 </c:forEach>
 </tbody>
</table>
<center>
<p>当前 ${pageInfo.pageNum }页,总${pageInfo.pages }
    页,总 ${pageInfo.total } 条记录</div></p>
<a href="/Task/AllStudent?pageNo=${pageInfo.firstPage}">1</a>

<a href="/Task/AllStudent?pageNo=${pageInfo.firstPage+1}">2</a>

<c:if test="${pageInfo.hasPreviousPage }">
    <a href="/Task/AllStudent?pageNo=${pageInfo.pageNum-1}">上一页</a>
</c:if>
<c:if test="${pageInfo.hasNextPage }">
    <a href="/Task/AllStudent?pageNo=${pageInfo.pageNum+1}">下一页</a>
</c:if>
<a href="/Task/AllStudent?pageNo=${pageInfo.lastPage}">${pageInfo.lastPage}</a>


</center>
</body>
</html>
