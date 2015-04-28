<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Mousemove Demo</title>
<style>
    div {
        position:fixed;
        padding:0;
        margin:0;
        width: 100%;
        height: 100%;
        background:rgba(16, 30, 45, 0.5);
        }

    p {
        margin: 0;
        margin-left: 10px;
        color: red;
        width: 220px;
        height: 120px;
        padding-top: 70px;
        float: left;
        font-size: 14px;
    }
    span {
        display: block;
    }
</style>

<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="<c:url value='/resources/js/welcome.js' />"></script>

</head>

<body>
<p>
<span>Move the mouse over the div.</span>
<span>&nbsp;</span>
</p>
<div id="testdiv"></div>
<script>

</script>

</body>
</html>