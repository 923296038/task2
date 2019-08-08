<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>


<html>
<body>
添加结果:true.<br>
刚添加的学生信息<br>
<json:object>
    <json:object name="student">
        <json:property name="name" value="${student.name}"/>
        <json:property name="student_id" value="${student.student_id}"/>
        <json:property name="qq" value="${student.qq}"/>
        <json:property name="type" value="${student.learn_type}"/>
        <json:property name="estimatedtime" value="${student.entrance_time}"/>
        <json:property name="school" value="${student.school}"/>
        <json:property name="manner" value="${student.senior}"/>
        <json:property name="daily" value="${student.daily_link}"/>
        <json:property name="wish" value="${student.wish}"/>
        <json:property name="counselor" value="${student.way}"/>
        <json:property name="create_at" value="${student.create_time}"/>
        <json:property name="update_at" value="${student.update_time}"/>
        <br>
    </json:object>
</json:object>
<a href="/Task/AllStudent">点击返回学生列表</a>
</body>
</html>