<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>sessionTest.jsp</title>
</head>
<body>
  <h3>세션 session</h3>
<%
/*
  [session 내부객체]
  - HttpSession session
  - 요청한 사용자에게 개별적으로 접근
  - 선언된 세션변수는 전역적 성격으로 유지된다
  - 일정시간동안 이벤트가 발생되지 않으면 자동 삭제
*/
  //out.print("현재 세션 유지 시간:");
  //out.print(session.getMaxInactiveInterval());
  //out.print("초(30분)");
  //out.print("<hr>");
//--------------------------------------------
  //세션 유지 시간 변경
  //1)
  /*session.setMaxInactiveInterval(60*10);
  out.print("변경된 세션 유지 시간:");
  out.print(session.getMaxInactiveInterval());
  out.print("초(10분)");
  out.print("<hr>");*/
  
  //2)/WEB-INF/web.xml
  // 배치관리자
  // 세션시간설정, 환경설정등 지정
  // web.xml이 수정되면 반드시 서버를 재시작할것!!
  /*
  <!-- 세션 유지 시간 설정(20분) -->
  <session-config>
  	<session-timeout>20</session-timeout>
  </session-config>
  */
//-----------------------------------------
  //세션변수
  //->별도의 자료형이 없다
  //->myweb프로젝트의 모든 페이지에서 공유되는 전역변수

  //세션변수 선언
  //session.setAttribute("변수명", 값)
  session.setAttribute("s_id", "soldesk");
  session.setAttribute("s_pw", "12341234");

  //세션변수값 가져오기
  Object obj=session.getAttribute("s_id");
  String s_id=(String)obj;
  String s_pw=(String)session.getAttribute("s_pw");

  out.print("세션변수 s_id :" + s_id + "<hr>");
  out.print("세션변수 s_pw :" + s_pw + "<hr>");
  
  //세션변수 강제 삭제(로그아웃) -> null값
  session.removeAttribute("s_id");
  session.removeAttribute("s_pw");

  out.print("세션변수 삭제후<hr>");
  out.print("세션변수 s_id :" + session.getAttribute("s_id"));
  out.print("<hr>");
  out.print("세션변수 s_pw :" + session.getAttribute("s_pw"));
  out.print("<hr>");
  
  //세션영역에 있는 모든값 전부 강제 삭제
  //session.invalidate();
  
  //세션객체에서 발급해 주는 아이디
  out.print("세션 임시 아이디 : ");
  out.print(session.getId());
  out.print("<hr>");
//------------------------------------------  
/*
  [application 내부객체]
  - ServletContext application
  - 서버에 대한 정보를 관리하는 객체
  - 사용자 모두가 공유하는 전역적 성격의 객체
*/
  // /bbs폴더의 실제 물리적 경로
  // 실제경로 d:\java0214\workspace\myweb\WebContent\bbs
  // 웹경로   http://172.16.82.100:9090/myweb/bbs
  out.print(application.getRealPath("/bbs"));
  out.print("<hr>");

  out.print(request.getRealPath("/bbs"));
  out.print("<hr>");
  
  //application 내장변수
  application.setAttribute("uname", "손흥민");
  out.print(application.getAttribute("uname"));
//---------------------------------------------
  //[response 내부객체]
  //->요청한 사용자에게 응답할때
  
  //페이지 이동
  //response.sendRedirect("파일명")

  //요청한 사용자에게 응답 메세지 전송
  //AJAX에서 많이 사용
  //PrintWriter write=response.getWriter();
%>  
  
  
  
  
  
  
  
  
  
</body>
</html>