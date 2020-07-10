package net.pds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import net.utility.DBClose;
import net.utility.DBOpen;
import net.utility.Utility;

public class PdsDAO {
  private DBOpen dbopen=null;  
  private Connection con=null;
  private PreparedStatement pstmt=null;
  private ResultSet rs=null;
  private StringBuilder sql=null;
  
  public PdsDAO() {
    dbopen=new DBOpen();
  }

  public int insert(PdsDTO dto) {
    int cnt=0;
    try {
      con=dbopen.getConnection();
      sql=new StringBuilder();
      sql.append(" INSERT INTO tb_pds(pdsno,wname,subject,passwd,filename,filesize,regdate) ");
      sql.append(" VALUES ((SELECT NVL(MAX(pdsno),0)+1 FROM tb_pds) ");
      sql.append("         , ?, ?, ?, ?, ?, sysdate) ");

      pstmt=con.prepareStatement(sql.toString());
      pstmt.setString(1, dto.getWname());
      pstmt.setString(2, dto.getSubject());
      pstmt.setString(3, dto.getPasswd());
      pstmt.setString(4, dto.getFilename());
      pstmt.setLong(5, dto.getFilesize());
      
      cnt=pstmt.executeUpdate();
    }catch (Exception e) {
      System.out.println("추가실패:"+e);
    }finally {
      DBClose.close(con, pstmt);
    }//try end
    return cnt;
  }//insert() end
  
  
  public synchronized ArrayList<PdsDTO> list(){
    ArrayList<PdsDTO> list=null;
    try {
      con=dbopen.getConnection();
      StringBuilder sql=new StringBuilder();
      sql.append(" SELECT pdsno, wname, subject, readcnt, regdate, filename ");
      sql.append(" FROM tb_pds ");
      sql.append(" ORDER BY regdate DESC ");
      pstmt=con.prepareStatement(sql.toString());
      rs=pstmt.executeQuery();
      if(rs.next()){
          list=new ArrayList<>();
          do {
            PdsDTO dto=new PdsDTO();
            dto.setPdsno(rs.getInt("pdsno"));
            dto.setWname(rs.getString("wname"));
            dto.setSubject(rs.getString("subject"));
            dto.setReadcnt(rs.getInt("readcnt"));
            dto.setFilename(rs.getString("filename"));
            dto.setRegdate(rs.getString("regdate"));
            list.add(dto);
          }while(rs.next());
      }else{
          list=null;
      }//if end
      
    }catch (Exception e) {
      System.out.println("목록실패:"+e);
    }finally {
      DBClose.close(con, pstmt, rs);
    }//try end
    return list;
  }//list() end
  
  
  public PdsDTO read(int pdsno){
    PdsDTO dto=null;
    try{
        con=dbopen.getConnection();
        sql=new StringBuilder();
        sql.append(" SELECT wname, subject, readcnt, regdate, filename, filesize");
        sql.append(" FROM tb_pds ");
        sql.append(" WHERE pdsno=?");
        pstmt=con.prepareStatement(sql.toString());
        pstmt.setInt(1, pdsno);
        ResultSet rs=pstmt.executeQuery();
        if(rs.next()){
            dto=new PdsDTO();                
            dto.setWname(rs.getString("wname"));
            dto.setSubject(rs.getString("subject"));
            dto.setReadcnt(rs.getInt("readcnt"));
            dto.setRegdate(rs.getString("regdate"));
            dto.setFilename(rs.getString("filename"));
            dto.setFilesize(rs.getLong("filesize"));
        }else{
            dto=null;
        }//if end
    }catch(Exception e){
        System.out.println("상세보기실패:"+e);
    }finally {
      DBClose.close(con, pstmt, rs);
    }//try end
    return dto;
  }//read() end


  public void incrementCnt(int pdsno){
    try{
        con=dbopen.getConnection();
        sql=new StringBuilder();
        sql.append(" UPDATE tb_pds ");
        sql.append(" SET readcnt=readcnt+1 ");
        sql.append(" WHERE pdsno=? ");
        pstmt=con.prepareStatement(sql.toString());
        pstmt.setInt(1, pdsno);
        pstmt.executeUpdate();
    }catch(Exception e){
        System.out.println("조회수 증가 실패:"+e);
    }finally {
      DBClose.close(con, pstmt);
    }//try end
  }//incrementCnt() end  
  

  public int delete(int pdsno, String passwd, String saveDir) {
    int cnt=0;
    try {
      //행삭제 하기전 삭제하고자 하는 파일명 가져온다
      String filename="";
      PdsDTO oldDTO=read(pdsno);
      if(oldDTO!=null) {
        filename=oldDTO.getFilename();
      }//if end
      
      con=dbopen.getConnection();
      sql=new StringBuilder();
      sql.append(" DELETE FROM tb_pds ");            
      sql.append(" WHERE pdsno=? AND passwd=? ");
      pstmt=con.prepareStatement(sql.toString());
      pstmt.setInt(1, pdsno);
      pstmt.setString(2, passwd);
      cnt=pstmt.executeUpdate();
      if(cnt==1) {//테이블에서 행삭제가 성공했으므로
                  //첨부했던 파일도 삭제
        Utility.deleteFile(saveDir, filename);
      }//if end
      
    }catch(Exception e){
      System.out.println("삭제실패:"+e);
    }finally {
      DBClose.close(con, pstmt);
    }//try end
    return cnt;
  }//delete() end
  
  
  
  
  
  
}//class end
