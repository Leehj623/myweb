<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="ssi.jsp"%>
<%@ include file="../header.jsp"%>
<!-- 본문시작 pdsIns.jsp -->
<h3>* 사진 올리기 결과*</h3>
<%
try{
  //1)첨부 파일 저장하기-------------------------------------
  String saveDirectory=application.getRealPath("/storage");
  int maxPostSize=1024*1024*10;
  String encoding="UTF-8"; 
  MultipartRequest mr=new MultipartRequest(request
                                          ,saveDirectory
                                          ,maxPostSize
                                          ,encoding
                                          ,new DefaultFileRenamePolicy());

  //2)storage폴더에 저장된 파일명, 파일크기 가져오기--------------
  String item     ="";
  String fileName ="";   //파일명
  long filesize   =0;    //파일크기
  File file       =null;
  
  Enumeration files=mr.getFileNames();
  while(files.hasMoreElements()){
    item=(String)files.nextElement();
    fileName=mr.getFilesystemName(item);
    if(fileName!=null){
      file=mr.getFile(item);//실제파일가져오기
      if(file.exists()){//파일이 존재하는지?
        filesize=file.length();
      }//if end
    }//if end
  }//while end

  //3)tb_pds테이블에 추가하기--------------------------------
  String wname  =mr.getParameter("wname").trim();
  String subject=mr.getParameter("subject").trim();
  String passwd =mr.getParameter("passwd").trim();
  
  dto.setWname(wname);
  dto.setSubject(subject);
  dto.setPasswd(passwd);
  dto.setFilename(fileName);
  dto.setFilesize(filesize);

  int cnt=dao.insert(dto);
  if(cnt==0){
    out.println("<p>사진추가 실패했습니다</p>");
    out.println("<p><a href='javascript:history.back()'>[다시시도]</a></p>");
  }else{
    out.println("<script>");
    out.println("  alert('사진을 추가했습니다');");
    out.println("  location.href='pdsList.jsp'");
    out.println("</script>");
  }//if end
  
  
}catch(Exception e){
  out.print(e);
  out.print("<p>사진 올리기 실패!!</p>");
  out.print("<a href='javascript:history.back();'>[다시시도]</a>");
}//try end
%>
<!-- 본문끝 -->
<%@ include file="../footer.jsp"%>