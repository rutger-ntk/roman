<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Screenshots</title>
</head>
<body>
  <h1>Screenshots</h1>
  
  <a href="index.jsp">Home</a><br/>
  
  <c:set var="videoFile" value="${sessionScope.videoFile}"/>
 
  <br/><video width="400" controls align="middle">
    <source src='<c:url value="../${videoFile}"/>' type="video/mp4"/>
   	<source src="${videoFile}" type="video/webm"/>
   	<source src="${videoFile}" type="video/ogg"/>             
   	<p>This video format is not supported by your browser.</p>
  </video></br> 
  
  <br/>
  
  <table>
    <c:forEach var="screenshot" items="${sessionScope.screenshots}" varStatus="rowCounter">
      <c:if test="${rowCounter.count % 4 == 1}">
        <tr>
      </c:if>
      <td><img src="${screenshot}" width="200"/><br/>${screenshot}</td>      
    </c:forEach>
  </table>
  
</body>
</html>