<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%-- ssi.jsp 공통페이지 --%>

<%@ page import="java.io.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<%@ page import="net.utility.*"%>
<%@ page import="net.bbs.*"%>

<jsp:useBean id="dto" class="net.bbs.BbsDTO"></jsp:useBean>
<jsp:useBean id="dao" class="net.bbs.BbsDAO"></jsp:useBean>

<%request.setCharacterEncoding("UTF-8"); %> 

<%
//검색--------------------------------------
String col =request.getParameter("col"); 
String word=request.getParameter("word");

//String값이 null이면 공백문자열 반환
col = Utility.checkNull(col);
word= Utility.checkNull(word);

//현재페이지---------------------------------
int nowPage=1;
if(request.getParameter("nowPage")!=null){
    nowPage=Integer.parseInt(request.getParameter("nowPage"));
}//if end
%>





