<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <title>My Web</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
 <link href='http://fonts.googleapis.com/css?family=Love+Ya+Like+A+Sister' rel='stylesheet' type='text/css'>

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

  <!-- CSS, JS import -->
  <link rel="stylesheet" href="./css/mystyle.css">
  <script src="./js/myscript.js"></script>

  <style>
  .time{margin:auto 0; /*가운데 정렬*/
        background-color:white; 
	    width:100%; height:50px;
		color:orange;            
		font-weight:bold;
		font-size:20px;
		text-align:center;}
  </style>
  <script>
  function showtime(){
   var today=new Date();
   var str="";
   
   str += today.getFullYear() + ".";

   if(today.getMonth()+1<10){
     str += "0"; //글자갯수를 2자리로 맞추기 위해
   }
   str += (today.getMonth()+1) + ".";

   if(today.getDate()<10){
		str += "0";                      
   }//if end
   str += today.getDate() + ".";
  
   switch(today.getDay()) {
     case 0: str += " (일) "; break;
     case 1: str += " (월) "; break;
     case 2: str += " (화) "; break;
     case 3: str += " (수) "; break;
     case 4: str += " (목) "; break;
	 case 5: str += " (금) "; break;
	 case 6: str += " (토) "; break;
   }//switch end

    if(today.getHours()<12)  {
		  str += "AM ";
    } else {
		  str += "PM ";
    }//if end
	
	if(today.getHours()>=13)  { //시
		  str += (today.getHours()-12)+":";
	} else {
		  str += today.getHours()+":";
	}//if end

	if(today.getMinutes()<10) { //분
		  str += "0";
	} 		
	str += today.getMinutes()+":";

	if(today.getSeconds()<10) { //초
		  str += "0";
	} 
	str += today.getSeconds();

    document.getElementById("clock").innerHTML=str; 
    timer=setTimeout(showtime, 1000);
  }//showtime() end

  var timer;

  function killtime(){
     clearTimeout(timer); //시간해제
  }//killtime() end
 </script>
  
</head>

<body onload="showtime()" onunload="killtime()">
<!-- 메인카테고리 시작 -->
<nav class="navbar navbar-default">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="index.jsp">HOME</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav navbar-right">
        <li><a href="./bbs/bbsList.jsp">게시판</a></li>
        <li><a href="./notice/noticeList.jsp">공지사항</a></li>
        <li><a href="./member/loginForm.jsp">Login</a></li>
        <li><a href="./pds/pdsList.jsp">Gallery</a></li>
        <li><a href="./mail/mailForm.jsp">Mail</a></li>
        <li><a href="./bbs2/bbslist.do">게시판(MVC)</a></li>
        <li><a href="./member2/loginform.do">로그인(MVC)</a></li>
      </ul>
    </div>
  </div>
</nav>
<!-- 메인카테고리 끝 -->

<!-- container 시작 -->
<div class="container-fluid bg-1 text-center">
  <h3 class="margin">Welcome To MyWeb</h3>
  <img src="./images/hamster.jpg" class="img-responsive img-circle margin" style="display:inline" alt="햄스터" width="350" height="350">
</div>

<div class="container-fluid bg-3 text-center">    
  <div class="row">
    <div class="col-sm-12"><br><br>
         <!-- 디지털시계 -->
         <div id="clock" class="time"></div>
    </div>
  </div>
</div>
<!-- container 끝 -->

<!-- Footer -->
<footer class="container-fluid bg-4 text-center">
  Copyright &copy; 2020 MyWeb 
</footer>

</body>
</html>
