<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../header.jsp"%>
<!-- 본문시작 pdsDel.jsp -->
<h3>* 사진삭제 *</h3>
<p><a href="pdsList.jsp">[사진목록]</a></p>
<form method="post"
      action="pdsDelProc.jsp"
      onsubmit="return pwCheck(this)">
<input type="hidden"
       name="pdsno"
       value="<%=request.getParameter("pdsno")%>">      
<table class="table">
<tr>
	<th>비밀번호</th>
	<td><input type="password" name="passwd" required></td>
</tr>
<tr>
	<td colspan="2">
	   <input type="submit" value="확인" class="btn btn-primary">
	</td>
</tr>
</table>
</form>
<!-- 본문끝 -->
<%@ include file="../footer.jsp"%>