<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="ssi.jsp"%>
<%@ include file="../header.jsp"%>
<!-- 본문시작 bbsUpdateForm.jsp -->
<h3>* 글수정 *</h3>
<p>
   <a href="bbsForm.jsp">[글쓰기]</a>
   &nbsp;&nbsp;
   <a href="bbsList.jsp">[글목록]</a>
</p>
<%
String passwd =request.getParameter("passwd").trim();
int bbsno     =Integer.parseInt(request.getParameter("bbsno"));  
dto.setPasswd(passwd);
dto.setBbsno(bbsno);

dto=dao.updateform(dto);    
if(dto==null){
  out.println("<p>비밀번호가 일치하지 않습니다</p>");
  out.println("<p><a href='javascript:history.back()'>[다시시도]</a></p>");
}else{
%>
	<form name="bbsfrm"
	      method="post"
	      action="bbsUpdateProc.jsp"
	      onsubmit="return bbsCheck(this)">
	<input type="hidden" name="bbsno" value="<%=bbsno %>">
	<table class="table">
	<tr>
	  <th>작성자</th>
	  <td><input type="text" name="wname" value="<%=dto.getWname() %>" size="10" maxlength="20" required class="form-control"></td>
	</tr>
	<tr>
	  <th>제목</th>
	  <td><input type="text" name="subject" value="<%=dto.getSubject() %>" size="30" maxlength="100" required class="form-control"></td>
	</tr>
	<tr>
	  <th>내용</th>
	  <td><textarea rows="5" cols="30" name="content"  class="form-control"><%=dto.getContent() %></textarea> </td>
	</tr>
	<tr>
	  <th>비밀번호</th>
	  <td><input type="password" name="passwd" value="<%=passwd %>" size="10" maxlength="10" required  class="form-control"></td>
	</tr>
	<tr>
	    <td colspan="2" align="center">
	      <input type="submit" value="수정" class="btn btn-primary">
	      <input type="reset"  value="취소"  class="btn btn-primary">
	    </td>
	</tr> 
	</table>
	</form>

<%
}//if end
%>
<!-- 본문끝 -->
<%@ include file="../footer.jsp"%>



