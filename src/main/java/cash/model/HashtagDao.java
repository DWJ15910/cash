package cash.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cash.vo.Hashtag;

public class HashtagDao {
	// 이달의 해쉬태그 리스트 총 갯수
	public int selectWordByCountHashtagList(String memberId, String word, int targetYear, int targetMonth) {
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String driver = "org.mariadb.jdbc.Driver";
			String dburl = "jdbc:mariadb://127.0.0.1:3306/cash";
			String dbuser = "root";
			String dbpw = "java1234";
			Class.forName(driver);
			conn = DriverManager.getConnection(dburl,dbuser,dbpw);
			String sql = "SELECT count(*)"
					+ " FROM hashtag h INNER JOIN cashbook c ON h.cashbook_no=c.cashbook_no\r\n"
					+ " WHERE h.word = ?\r\n"
					+ " AND YEAR(c.cashbook_date)=?\r\n"
					+ " AND MONTH(c.cashbook_date)=?\r\n"
					+ " AND c.member_id = ?\r\n"
					+ " ORDER BY c.cashbook_date DESC";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, word);
			stmt.setInt(2, targetYear);
			stmt.setInt(3, targetMonth);
			stmt.setString(4, memberId);
			rs = stmt.executeQuery();
			if(rs.next()) {
				row = rs.getInt(1);
			}
			System.out.println(stmt + "stmt");
			System.out.println(rs + "<--rs");
		} catch (Exception e) {
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
		return row;
	}
	// 이달의 해쉬태그 리스트
	public List<Map<String,Object>> selectWordByCount(String memberId, String word, int targetYear, int targetMonth,int beginRow, int rowPerPage){
		List<Map<String,Object>> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String driver = "org.mariadb.jdbc.Driver";
			String dburl = "jdbc:mariadb://127.0.0.1:3306/cash";
			String dbuser = "root";
			String dbpw = "java1234";
			Class.forName(driver);
			conn = DriverManager.getConnection(dburl,dbuser,dbpw);
			String sql = "SELECT c.cashbook_date,c.category, c.price,c.memo,h.word\r\n"
					+ " FROM hashtag h INNER JOIN cashbook c ON h.cashbook_no=c.cashbook_no\r\n"
					+ " WHERE h.word = ?\r\n"
					+ " AND YEAR(c.cashbook_date)=?\r\n"
					+ " AND MONTH(c.cashbook_date)=?\r\n"
					+ " AND c.member_id = ?\r\n"
					+ " ORDER BY c.cashbook_date DESC"
					+ " LIMIT ?,?;";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, word);
			stmt.setInt(2, targetYear);
			stmt.setInt(3, targetMonth);
			stmt.setString(4, memberId);
			stmt.setInt(5, beginRow);
			stmt.setInt(6, rowPerPage);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("cashbookDate", rs.getString("c.cashbook_date"));
				m.put("category", rs.getString("c.category"));
				m.put("price", rs.getInt("c.price"));
				m.put("memo", rs.getString("c.memo"));
				m.put("word", rs.getString("h.word"));
				list.add(m);
			}
			System.out.println(stmt + "selectHashtagList DAO");
			System.out.println(rs + "<--rs");
			System.out.println(list + "list");
			

		} catch (Exception e) {
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
		
		return list;
	}
	//월별 해시태그 조회
	public List<Map<String,Object>> selectWordcountByMonth(String memberId,int targetYear,int targetMonth) {
		List<Map<String,Object>> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String driver = "org.mariadb.jdbc.Driver";
			String dburl = "jdbc:mariadb://127.0.0.1:3306/cash";
			String dbuser = "root";
			String dbpw = "java1234";
			Class.forName(driver);
			conn = DriverManager.getConnection(dburl,dbuser,dbpw);
			String sql = "SELECT word, count(*) cnt FROM hashtag h inner join cashbook c"
					+ " on h.cashbook_no = c.cashbook_no where year(c.cashbook_date) = ?"
					+ " and month(c.cashbook_date) = ? AND c.member_id = ? group by word order by count(*) desc";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, targetYear);
			stmt.setInt(2, targetMonth);
			stmt.setString(3, memberId);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("word", rs.getString("word"));
				m.put("cnt", rs.getString("cnt"));
				list.add(m);
			}
		} catch (Exception e) {
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
		
		return list;
	}
	// 해시태그 추가 메서드
	public int insertHashtag(Hashtag hashtag) {
		Connection conn = null;
		PreparedStatement stmt = null;
		int row = 0;
		
		try {
			String driver = "org.mariadb.jdbc.Driver";
			String dburl = "jdbc:mariadb://127.0.0.1:3306/cash";
			String dbuser = "root";
			String dbpw = "java1234";
			Class.forName(driver);
			conn = DriverManager.getConnection(dburl,dbuser,dbpw);
			String sql = "INSERT INTO hashtag(cashbook_no,word,updatedate,createdate)"
					+ " VALUES(?,?,NOW(),NOW())";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, hashtag.getCashbookNo());
			stmt.setString(2, hashtag.getWord());
			row = stmt.executeUpdate();
		} catch (Exception e) {
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
}
