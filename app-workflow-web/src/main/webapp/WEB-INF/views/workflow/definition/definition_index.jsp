<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
  <script>
      function toDesigner(){
        window.location.href="<%=request.getContextPath()%>/workflow/definition/designer?modelId=1000";
      }
  </script>
</head>
<body>
  <button onclick="toDesigner()">toDesigner</button>
</body>
</html>
