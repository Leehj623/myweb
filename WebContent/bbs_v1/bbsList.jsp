<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="ssi.jsp"%>
<%@ include file="../header.jsp"%>
<!-- 본문시작 bbsList.jsp-->
<h3>* 게시판 목록*</h3>
<p><a href="bbsForm.jsp">[글쓰기]</a></p>
<table class="table table-hover">
<thead>
  <tr>
	<th>제목</th>
	<th>조회수</th>
	<th>작성자</th>
	<th>작성일</th>
  </tr>
</thead>
<tbody>
<%
ArrayList<BbsDTO> list=dao.list(); 
if(list==null){
  out.println("<tr>");
  out.println("  <td colspan='4'>");
  out.println("    <strong>관련자료 없음!!</strong>");
  out.println("  </td>");
  out.println("</tr>");
}else{
  
  //오늘 날짜를 문자열 "2020-04-13" 만들기
  String today=Utility.getDate();
  
  for(int i=0; i<list.size(); i++) {
    dto=list.get(i);   
%>
    <tr>
      <td style="text-align:left">
<%
        //답변 이미지 출력
        for(int n=1; n<=dto.getIndent(); n++){
          out.println("<img src='../images/reply.gif'>");
        }//for end
%>            
        <a href="bbsRead.jsp?bbsno=<%=dto.getBbsno()%>"><%=dto.getSubject()%></a>
<%
        //조회수가 10이상이면 hot이미지 출력
        if(dto.getReadcnt()>=10){
          out.println("<img src='../images/hot.gif'>");                  
        }//if end
        
        //오늘 작성한 글제목 뒤에 new이미지 출력
        //regdt에서 "년월일"만 자르기
        //->2020-04-13
        String regdt=dto.getRegdt().substring(0, 10);
        if(regdt.equals(today)){
          out.println("<img src='../images/new.gif'>");
        }//if end
%>      
      </td>
      <td><%=dto.getReadcnt()%></td>
      <td><%=dto.getWname()%></td>
      <td><%=dto.getRegdt().substring(0, 10)%></td>
    </tr>
<%          
  }//for end
}//if end
%> 
</tbody>
</table>
<!-- 본문끝 -->
<%@ include file="../footer.jsp"%>





