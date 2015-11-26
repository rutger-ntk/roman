<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
 "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Video upload and screenshot extraction tool</title>
  </head>
  <body>
    <h1>Video Upload & Screenshot Extraction Tool</h1><br/>
 	
 	<a href="screenshots.jsp">Screenshots</a><br/>
 		
    <br/><form method="post" action="FileUploadUtil" enctype="multipart/form-data">
		<h4>Upload a video file for processing:</h4>
        <input type="file" name="File" size="50" value="Upload File"/><br/><br/> 
        <input type="submit" value="Submit"/>
    </form><br/>	
  
    <c:if test="${requestScope.error == 'error' }">
      </br><c:out value="Choose a file to upload!"/>
    </c:if>
    
  </body>
</html> 