<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="ssi.jsp"%>
<%@ include file="../header.jsp"%>
<!-- 본문시작 loginProc.jsp-->
<h3>* 회원가입 결과 *</h3>
<% 
String id    =request.getParameter("id").trim();
String passwd=request.getParameter("passwd").trim();
String mname=request.getParameter("mname").trim();
String tel=request.getParameter("tel").trim();
String email=request.getParameter("email").trim();
String zipcode=request.getParameter("zipcode").trim();
String address1=request.getParameter("address1").trim();
String address2=request.getParameter("address2").trim();
String job=request.getParameter("job").trim();
String mlevel=request.getParameter("mlevel").trim();
String mdate=request.getParameter("mdate").trim();

dto.setId(id);
dto.setPasswd(passwd);
dto.setMname(mname);
dto.setTel(tel);
dto.setEmail(email);
dto.setZipcode(zipcode);
dto.setAddress1(address1);
dto.setAddress2(address2);
dto.setJob(job);
dto.setMlevel(mlevel);
dto.setMdate(mdate);



String mlevel=dao.memberProc(dto);

if(mlevel==null){
  out.println("<p>아이디/비번 다시 한번 확인해주세요!!</p>");
  out.println("<p><a href='javascript:history.back()'>[다시시도]</a></p>");
}else{
  //로그인 성공
  //out.println(mlevel);
  //다른 페이지에서 로그인상태를 공유할 수 있도록
  session.setAttribute("s_id", id);
  session.setAttribute("s_passwd",passwd);
  session.setAttribute("s_mlevel",mlevel);


  //첫페이지로 이동
  String root=Utility.getRoot();// /myweb
  response.sendRedirect(root+"/loginForm.jsp");

 %>



<!-- 본문끝 -->
<%@ include file="../footer.jsp"%>