package net.bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import net.utility.DBClose;
import net.utility.DBOpen;

public class BbsDAO { //DB 관련 작업
  private DBOpen dbopen=null;  
  private Connection con=null;
  private PreparedStatement pstmt=null;
  private ResultSet rs=null;
  private StringBuilder sql=null; 
  
  public BbsDAO() {
    dbopen=new DBOpen();
  }
  
  
  public int insert(BbsDTO dto) {
    int cnt=0;
    try {
      con=dbopen.getConnection();
      
      sql=new StringBuilder();
      sql.append(" INSERT INTO tb_bbs (bbsno, wname, subject, content, passwd, ip, grpno) ");
      sql.append(" VALUES ( ");
      sql.append("          (SELECT NVL(MAX(bbsno),0)+1 FROM tb_bbs) ");
      sql.append("         ,?,?,?,?,? ");
      sql.append("         ,(SELECT NVL(MAX(bbsno),0)+1 FROM tb_bbs) ");
      sql.append("        )  ");

      pstmt=con.prepareStatement(sql.toString());
      pstmt.setString(1, dto.getWname());
      pstmt.setString(2, dto.getSubject());
      pstmt.setString(3, dto.getContent());
      pstmt.setString(4, dto.getPasswd());
      pstmt.setString(5, dto.getIp());
      
      cnt=pstmt.executeUpdate();
      
      
    }catch (Exception e) {
      System.out.println("추가실패:"+e);
    }finally {
      DBClose.close(con, pstmt);
    }//try end
    return cnt;
  }//insert() end
  
  
  public ArrayList<BbsDTO> list(){
    ArrayList<BbsDTO> list=null;
    try {
      con=dbopen.getConnection();
      sql=new StringBuilder();
      sql.append(" SELECT bbsno, wname, subject, readcnt, indent, regdt");
      sql.append(" FROM tb_bbs");
      sql.append(" ORDER BY grpno DESC, ansnum ASC");
      
      pstmt=con.prepareStatement(sql.toString());
      rs=pstmt.executeQuery();
      if(rs.next()){
        list=new ArrayList<>();
        do {
          BbsDTO dto=new BbsDTO();
          dto.setBbsno(rs.getInt("bbsno"));
          dto.setWname(rs.getString("wname"));
          dto.setSubject(rs.getString("subject"));
          dto.setReadcnt(rs.getInt("readcnt"));
          dto.setIndent(rs.getInt("indent"));
          dto.setRegdt(rs.getString("regdt"));
          list.add(dto);
        }while(rs.next());        
      }else {
        list=null;
      }//if end
      
    }catch (Exception e) {
      System.out.println("목록실패:"+e);
    }finally {
      DBClose.close(con, pstmt, rs);
    }//try end
    return list;
  }//list() end
  
  
  public void incrementCnt(int bbsno){
    try{
      con=dbopen.getConnection();
      sql=new StringBuilder();
      sql.append(" UPDATE tb_bbs ");
      sql.append(" SET readcnt=readcnt+1 ");
      sql.append(" WHERE bbsno=? ");
      pstmt=con.prepareStatement(sql.toString());
      pstmt.setInt(1, bbsno);
      pstmt.executeUpdate();
    }catch(Exception e){
      System.out.println("조회수 증가 실패:"+e);
    }finally {
      DBClose.close(con, pstmt);
    }//try end 
  }//incrementCnt() end
  
  
  public BbsDTO read(int bbsno) {
    BbsDTO dto=null;
    try {
      con=dbopen.getConnection();
      
      sql=new StringBuilder();
      sql.append(" SELECT bbsno, wname, subject, content, readcnt, grpno, ip, regdt");
      sql.append(" FROM tb_bbs ");
      sql.append(" WHERE bbsno=? ");
      
      pstmt=con.prepareStatement(sql.toString());
      pstmt.setInt(1, bbsno);
      rs=pstmt.executeQuery();
      if(rs.next()){
          dto=new BbsDTO();
          dto.setBbsno(rs.getInt("bbsno"));
          dto.setWname(rs.getString("wname"));
          dto.setSubject(rs.getString("subject"));
          dto.setContent(rs.getString("content"));
          dto.setReadcnt(rs.getInt("readcnt"));
          dto.setGrpno(rs.getInt("grpno"));
          dto.setRegdt(rs.getString("regdt"));
          dto.setIp(rs.getString("ip"));
      }else{
          dto=null;
      }//if end
      
    }catch (Exception e) {
      System.out.println("상세보기실패:"+e);
    }finally {
      DBClose.close(con, pstmt, rs);
    }//try end    
    return dto;      
  }//read() end
  
  
  public int delete(BbsDTO dto){
    int cnt=0;
    try{
      con=dbopen.getConnection();
      sql=new StringBuilder();
      sql.append(" DELETE FROM tb_bbs ");
      sql.append(" WHERE bbsno=? AND passwd=? ");
      pstmt=con.prepareStatement(sql.toString());
      pstmt.setInt(1, dto.getBbsno());
      pstmt.setString(2, dto.getPasswd());
      cnt=pstmt.executeUpdate();
    }catch(Exception e){
      System.out.println("삭제 실패:"+e);
    }finally {
      DBClose.close(con, pstmt);
    }//try end
    return cnt;
  }//delete() end
  
  
  public BbsDTO updateform(BbsDTO dto){
    try {    
      con=dbopen.getConnection();
      sql=new StringBuilder();
      sql.append(" SELECT wname, subject, content, passwd ");
      sql.append(" FROM tb_bbs ");
      sql.append(" WHERE bbsno=? AND passwd=?");
      pstmt=con.prepareStatement(sql.toString());
      pstmt.setInt(1,dto.getBbsno());
      pstmt.setString(2, dto.getPasswd());
      rs=pstmt.executeQuery();
      if(rs.next()){
         dto=new BbsDTO();
         dto.setWname(rs.getString("wname"));
         dto.setSubject(rs.getString("subject"));
         dto.setContent(rs.getString("content"));
         dto.setPasswd(rs.getString("passwd"));
      }else{
         dto=null;
      }//if end
      
    }catch(Exception e) {
      System.out.println("수정 조회 실패 : " + e);
    }finally {
      DBClose.close(con, pstmt);
    }//try end  
    return dto;
  }//updateform() end
  
  
  public int updateproc(BbsDTO dto){
    int cnt=0;
    try {    
      con=dbopen.getConnection();
      sql=new StringBuilder();
      sql.append(" UPDATE tb_bbs ");
      sql.append(" SET wname=? ");
      sql.append(" ,subject=? ");
      sql.append(" ,content=? ");
      sql.append(" ,passwd=? ");
      sql.append(" ,ip=? ");
      sql.append(" WHERE bbsno=? ");
      pstmt=con.prepareStatement(sql.toString());
      pstmt.setString(1, dto.getWname());
      pstmt.setString(2, dto.getSubject());
      pstmt.setString(3, dto.getContent());
      pstmt.setString(4, dto.getPasswd());
      pstmt.setString(5, dto.getIp());
      pstmt.setInt(6, dto.getBbsno());
      cnt=pstmt.executeUpdate();

    }catch(Exception e) {
      System.out.println("수정 실패 : " + e);
    }finally {
      DBClose.close(con, pstmt);
    }//try end    
    return cnt;
  }//updateProc() end
  
  
  public int reply(BbsDTO dto) {
    int cnt=0;
    try {
      con=dbopen.getConnection();
      sql=new StringBuilder();
      
      //1)부모글정보 가져오기(select문)      
      int grpno=0;  //그룹번호
      int indent=0; //들여쓰기
      int ansnum=0; //글순서
      sql.append(" SELECT grpno, indent, ansnum ");
      sql.append(" FROM tb_bbs ");
      sql.append(" WHERE bbsno=? ");
      pstmt=con.prepareStatement(sql.toString());
      pstmt.setInt(1, dto.getBbsno());
      rs=pstmt.executeQuery();
      if(rs.next()) {
        //그룹번호 : 부모그룹번호 그대로 가져오기
        grpno=rs.getInt("grpno");
        //들여쓰기 : 부모들여쓰기 + 1
        indent=rs.getInt("indent")+1;
        //글순서 : 부모글순서 + 1
        ansnum=rs.getInt("ansnum")+1;
      }//if end
      
      
      //2)글순서 재조정하기(update문)
      //  1)에서 사용했던 sql값 지우기
      sql.delete(0, sql.length());
      sql.append(" UPDATE tb_bbs ");
      sql.append(" SET ansnum=ansnum+1 ");
      sql.append(" WHERE grpno=? AND ansnum>=?");
      pstmt=con.prepareStatement(sql.toString());
      pstmt.setInt(1, grpno);
      pstmt.setInt(2, ansnum);
      pstmt.executeUpdate();
      
      
      //3)답변글 추가하기(insert문)
      sql.delete(0, sql.length());
      sql.append(" INSERT INTO tb_bbs (bbsno, wname, subject, content, passwd, ip, grpno, indent, ansnum) ");
      sql.append(" VALUES( (SELECT NVL(MAX(bbsno),0)+1 FROM tb_bbs) ");
      sql.append(" ,?,?,?,?,?,?,?,? ) ");
      
      pstmt=con.prepareStatement(sql.toString());
      pstmt.setString(1, dto.getWname());
      pstmt.setString(2, dto.getSubject());
      pstmt.setString(3, dto.getContent());
      pstmt.setString(4, dto.getPasswd());
      pstmt.setString(5, dto.getIp());
      pstmt.setInt(6, grpno);  
      pstmt.setInt(7, indent);
      pstmt.setInt(8, ansnum);
      
      cnt=pstmt.executeUpdate();
      
    }catch(Exception e) {
      System.out.println("답변 실패 : " + e);
    }finally {
      DBClose.close(con, pstmt, rs);
    }//try end    
    return cnt;
  }//reply() end
  
  
  public int count(String col, String word) {
    int cnt=0;
    try {
      con=dbopen.getConnection();
      sql=new StringBuilder();
      sql.append(" SELECT COUNT(*) as cnt ");
      sql.append(" FROM tb_bbs ");
      
      if(word.trim().length()>=1) {//검색어가 있다면?
        String search="";
        if(col.equals("wname")){
          search+=" WHERE wname LIKE '%" + word + "%' ";
        }else if(col.equals("subject")){
          search+=" WHERE subject LIKE '%" + word + "%' ";
        }else if(col.equals("content")){
          search+=" WHERE content LIKE '%" + word + "%' ";
        }else if(col.equals("subject_content")){
          search+=" WHERE subject LIKE '%" + word + "%' ";
          search+=" OR content LIKE '%" + word + "%' ";
        }//if end
        
        sql.append(search);
      }//if end      
      
      pstmt=con.prepareStatement(sql.toString());
      rs=pstmt.executeQuery();
      if(rs.next()){
          cnt=rs.getInt("cnt");
      }//if end      
      
    }catch(Exception e) {
      System.out.println("행갯수 실패 : " + e);
    }finally {
      DBClose.close(con, pstmt, rs);
    }//try end
    return cnt;
  }//count() end

  
  public ArrayList<BbsDTO> list2(String col, String word){
    ArrayList<BbsDTO> list=null;
    try {
      con=dbopen.getConnection();
      sql=new StringBuilder();
      sql.append(" SELECT bbsno, wname, subject, readcnt, indent, regdt");
      sql.append(" FROM tb_bbs");
      
      if(word.trim().length()>=1) {//검색어가 있다면?
        String search="";
        if(col.equals("wname")){
          search+=" WHERE wname LIKE '%" + word + "%' ";
        }else if(col.equals("subject")){
          search+=" WHERE subject LIKE '%" + word + "%' ";
        }else if(col.equals("content")){
          search+=" WHERE content LIKE '%" + word + "%' ";
        }else if(col.equals("subject_content")){
          search+=" WHERE subject LIKE '%" + word + "%' ";
          search+=" OR content LIKE '%" + word + "%' ";
        }//if end
        
        sql.append(search);
      }//if end      
      
      sql.append(" ORDER BY grpno DESC, ansnum ASC");
      
      pstmt=con.prepareStatement(sql.toString());
      rs=pstmt.executeQuery();
      if(rs.next()){
        list=new ArrayList<>();
        do {
          BbsDTO dto=new BbsDTO();
          dto.setBbsno(rs.getInt("bbsno"));
          dto.setWname(rs.getString("wname"));
          dto.setSubject(rs.getString("subject"));
          dto.setReadcnt(rs.getInt("readcnt"));
          dto.setIndent(rs.getInt("indent"));
          dto.setRegdt(rs.getString("regdt"));
          list.add(dto);
        }while(rs.next());        
      }else {
        list=null;
      }//if end
      
    }catch (Exception e) {
      System.out.println("검색목록실패:"+e);
    }finally {
      DBClose.close(con, pstmt, rs);
    }//try end
    return list;
  }//list2() end

  
  public ArrayList<BbsDTO> list3(String col, String word, int nowPage, int recordPerPage){
    ArrayList<BbsDTO> list=null;
    
    // 페이지당 출력할 레코드 갯수 (10개를 기준)
    // 1 page : WHERE r>=1 AND r<=10
    // 2 page : WHERE r>=11 AND r<=20
    // 3 page : WHERE r>=21 AND r<=30
    int startRow = ((nowPage-1) * recordPerPage) + 1 ;
    int endRow   = nowPage * recordPerPage;
    
    try{
      con=dbopen.getConnection();
      sql=new StringBuilder();
      
      word = word.trim(); //검색어의 좌우 공백 제거
      
      if(word.length()==0) { //검색을 하지 않는 경우
        sql.append(" SELECT bbsno,subject,wname,readcnt,indent,regdt, r");
        sql.append(" FROM( SELECT bbsno,subject,wname,readcnt,indent,regdt, rownum as r");
        sql.append("       FROM ( SELECT bbsno,subject,wname,readcnt,indent,regdt");
        sql.append("              FROM tb_bbs");
        sql.append("              ORDER BY grpno DESC, ansnum ASC");
        sql.append("           )");
        sql.append("     )");
        sql.append(" WHERE r>=" + startRow + " AND r<=" + endRow) ;
        
      } else {
        
        //검색을 하는 경우
        sql.append(" SELECT bbsno,subject,wname,readcnt,indent,regdt, r");
        sql.append(" FROM( SELECT bbsno,subject,wname,readcnt,indent,regdt, rownum as r");
        sql.append("       FROM ( SELECT bbsno,subject,wname,readcnt,indent,regdt");
        sql.append("              FROM tb_bbs");
        
        String search="";
        if(col.equals("wname")) {
          search += " WHERE wname LIKE '%" + word + "%'";
        } else if(col.equals("subject")) {
          search += " WHERE subject LIKE '%" + word + "%'";
        } else if(col.equals("content")) {
          search += " WHERE content LIKE '%" + word + "%'";
        } else if(col.equals("subject_content")) {
          search += " WHERE subject LIKE '%" + word + "%'";
          search += " OR content LIKE '%" + word + "%'";
        }
        
        sql.append(search);        
        
        sql.append("              ORDER BY grpno DESC, ansnum ASC");
        sql.append("           )");
        sql.append("     )");
        sql.append(" WHERE r>=" + startRow + " AND r<=" + endRow) ;
      }//if end
      
      pstmt=con.prepareStatement(sql.toString());
      rs=pstmt.executeQuery();
      if(rs.next()){
        list=new ArrayList<>();
        do{
          BbsDTO dto=new BbsDTO();
          dto.setBbsno(rs.getInt("bbsno"));
          dto.setSubject(rs.getString("subject"));
          dto.setWname(rs.getString("wname"));
          dto.setReadcnt(rs.getInt("readcnt"));
          dto.setRegdt(rs.getString("regdt"));
          dto.setIndent(rs.getInt("indent"));
          list.add(dto);
        }while(rs.next());
      }//if end
      
    }catch(Exception e) {
      System.out.println("목록 페이징 실패: "+e);
    }finally {
      DBClose.close(con, pstmt, rs);
    }   
    return list;
  }//list3() end  
  
}//class end




