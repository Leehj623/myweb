<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="ssi.jsp"%>
<%@ include file="../header.jsp"%>
<!-- 본문시작 pdsRead.jsp -->
<h3>* 포토 갤러리 상세보기 *</h3>
<%
  int pdsno=Integer.parseInt(request.getParameter("pdsno"));
  dto=dao.read(pdsno);
  if(dto==null){
      out.print("관련 자료 없음");
  }else{
      dao.incrementCnt(pdsno); //조회수 증가
%>
	  <table class="table">
	  <tr>
	    <th>글제목</th>
	    <td><%=dto.getSubject()%></td>
	  </tr>
	  <tr>
	    <th>사진</th>
	    <td><img src="../storage/<%=dto.getFilename()%>"></td>
	  </tr>
	  <tr>
	    <th>파일크기</th>
	    <td><%=Utility.toUnitStr(dto.getFilesize())%></td>
	  </tr>
	  <tr>
	    <th>조회수</th>
	    <td><%=dto.getReadcnt()%></td>
	  </tr>
	  <tr>
	    <th>작성일</th>
	    <td><%=dto.getRegdate()%></td>
	  </tr>
	  </table> 
	  <br><!-- 로그인한 경우만 수정/삭제 가능 -->
<%
  	if(session.getAttribute("s_id")!=null &&
  	   session.getAttribute("s_passwd")!=null &&
  	   session.getAttribute("s_mlevel")!=null)
  	{
%>	  
	  <input type="button" value="수정" class="btn btn-primary" onclick="location.href='pdsUpdate.jsp?pdsno=<%=pdsno%>'">
	  <input type="button" value="삭제" class="btn btn-primary" onclick="location.href='pdsDel.jsp?pdsno=<%=pdsno%>'">	  	      
<%
  	}//if end
  }//if end
%>  
<!-- 본문끝 -->
<%@ include file="../footer.jsp"%>



