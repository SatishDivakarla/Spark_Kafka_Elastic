<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Mousemove Demo</title>
<style>


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
    #div1, #div2, #div3, #div4 {
            background: green;
    }
    html, body { height: 100%; padding: 0; margin: 0; }
    #div1 { width: 50%; height: 50%; float: left; }
    #div2 { width: 50%; height: 50%; float: right; }
    #div3 { width: 50%; height: 50%; float: left; }
    #div4 { width: 50%; height: 50%; float: right; }

</style>

<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="<c:url value='/resources/js/activityview.js' />"></script>

</head>

<body>
<div id="div1">
</div>
<div id="div2">
</div>
<div id="div3">
</div>
<div id="div4">
</div>

</body>
</html>