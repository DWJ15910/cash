package cash.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.mariadb.jdbc.export.Prepare;

import cash.vo.Cashbook;
import cash.vo.Member;

public class CashbookDao {
	
	// 반환 값 : cashbook_no 키값
	public int insertCashbook(Cashbook cashbook) {
		int cashbookNo = 0;
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
			String sql = "INSERT INTO cashbook(member_id, category, cashbook_date,price,memo,updatedate,createdate)"
					+ " VALUES(?,?,?,?,?,now(),now())";
			stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1,cashbook.getMemberId());
			stmt.setString(2,cashbook.getCategory());
			stmt.setString(3,cashbook.getCashbookDate());
			stmt.setInt(4,cashbook.getPrice());
			stmt.setString(5,cashbook.getMemo());
			int row = stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if(rs.next()) {
				cashbookNo = rs.getInt(1);
				System.out.println(cashbookNo);
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
		
		return cashbookNo;
	}
	// 월별 금액
	public List<Cashbook> selectCashbookListByMonth(String memberId, int targetYear, int targetMonth) {
		List<Cashbook> list = new ArrayList<Cashbook>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String driver = "org.mariadb.jdbc.Driver";
		String dburl = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbuser = "root";
		String dbpw = "java1234";
		String sql = "SELECT cashbook_no,category,price,cashbook_date cashbookDate FROM cashbook WHERE member_id=?"
				+ " AND YEAR(cashbook_date)=? AND MONTH(cashbook_date)=?"
				+ " ORDER BY cashbook_date";
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dburl,dbuser,dbpw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,memberId);
			stmt.setInt(2,targetYear);
			stmt.setInt(3,targetMonth);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Cashbook c = new Cashbook();
				c.setCashbookNo(rs.getInt("cashbook_no"));
				c.setCategory(rs.getString("category"));
				c.setPrice(rs.getInt("price"));
				c.setCashbookDate(rs.getString("cashbookDate"));
				list.add(c);
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
		return list;
	}
	// 금액 일자별 상세보기
	public List<Cashbook> selectCashbookListByDate(String memberId, int targetYear, int targetMonth,int targetDate) {
		List<Cashbook> list = new ArrayList<Cashbook>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String driver = "org.mariadb.jdbc.Driver";
		String dburl = "jdbc:mariadb://127.0.0.1:3306/cash";
		String dbuser = "root";
		String dbpw = "java1234";
		String sql = "SELECT member_id,category,cashbook_date,price,memo"
				+ " FROM cashbook"
				+ " WHERE member_id=?"
				+ " AND YEAR(cashbook_date)=?"
				+ " AND MONTH(cashbook_date)=?"
				+ " AND DAY(cashbook_date)=?"
				+ " ORDER BY cashbook_date";
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dburl,dbuser,dbpw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,memberId);
			stmt.setInt(2,targetYear);
			stmt.setInt(3,targetMonth);
			stmt.setInt(4,targetDate);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Cashbook c = new Cashbook();
				c.setMemberId(rs.getString("member_id"));
				c.setCategory(rs.getString("category"));
				c.setCashbookDate(rs.getString("cashbook_date"));
				c.setPrice(rs.getInt("price"));
				c.setMemo(rs.getString("memo"));
				list.add(c);
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
		return list;
	}
}
