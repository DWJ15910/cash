package cash.model;

import java.sql.*;

import cash.vo.Member;

public class MemberDao {
	// 1) 로그인 메서드
	public Member selectMemberById(Member paramMember) {
		Member returnMember = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT member_id memberId FROM member WHERE member_id = ? AND member_pw = password(?)";
		String driver = "org.mariadb.jdbc.Driver";
		String dburl = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbuser = "root";
		String dbpw = "java1234";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dburl,dbuser,dbpw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, paramMember.getMemberId());
			stmt.setString(2, paramMember.getMemberPw());
			rs = stmt.executeQuery();
			if(rs.next()) {
				returnMember = new Member();
				returnMember.setMemberId(rs.getString("memberId"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return returnMember;
	}
	
	// 회원가입 메서드
	public int insertMember(Member member) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		int row = 0;
		
		String sql = "INSERT INTO member VALUES(?,PASSWORD(?),now(),now())";
		String driver = "org.mariadb.jdbc.Driver";
		String dburl = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbuser = "root";
		String dbpw = "java1234";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dburl,dbuser,dbpw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,member.getMemberId());
			stmt.setString(2,member.getMemberPw());
			row = stmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return row;
	}
	
	// 회원 상세 정보
	public Member selectMemberOne(String memberId) {
		Member returnMember = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT member_id,member_pw FROM member WHERE member_id = ?";
		String driver = "org.mariadb.jdbc.Driver";
		String dburl = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbuser = "root";
		String dbpw = "java1234";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dburl,dbuser,dbpw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			rs = stmt.executeQuery();
			if(rs.next()) {
				returnMember = new Member();
				returnMember.setMemberId(rs.getString("member_id"));
				returnMember.setMemberPw(rs.getString("member_pw"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return returnMember;
	}
	
	// 회원탈퇴
	public int removeMember(Member member, String memberPw) {
		Connection conn = null;
		PreparedStatement stmt = null;
		int row = 0;
		
		String sql = "DELETE FROM member WHERE member_id = ? AND member_pw = PASSWORD(?)";
		String driver = "org.mariadb.jdbc.Driver";
		String dburl = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbuser = "root";
		String dbpw = "java1234";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dburl,dbuser,dbpw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,member.getMemberId());
			stmt.setString(2,memberPw);
			row = stmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return row;
	}
	
	// 회원 비밀번호변경
	public int modifyMember(Member memberId,String beforePw,String chPw1, String chPw2) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int cnt = 0;
		int row = 0;
		
		String sql = "SELECT count(*) FROM member WHERE member_id = ? AND member_pw = password(?)";
		String driver = "org.mariadb.jdbc.Driver";
		String dburl = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbuser = "root";
		String dbpw = "java1234";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dburl,dbuser,dbpw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,memberId.getMemberId());
			stmt.setString(2,beforePw);
			rs = stmt.executeQuery();
			if(rs.next()) {
				cnt = rs.getInt("count(*)");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		if(cnt>0 && chPw1.equals(chPw2)) {
			String modifySql = "UPDATE member SET member_pw=Password(?) WHERE member_id = ?";
			
			try {
				Class.forName(driver);
				conn = DriverManager.getConnection(dburl,dbuser,dbpw);
				stmt = conn.prepareStatement(modifySql);
				stmt.setString(1,chPw1);
				stmt.setString(2,memberId.getMemberId());
				row = stmt.executeUpdate();
			} catch(Exception e3) {
				e3.printStackTrace();
			} finally {
				try {
					stmt.close();
					conn.close();
				} catch(Exception e4) {
					e4.printStackTrace();
				}
			}
		}
		return row;
	}
}
