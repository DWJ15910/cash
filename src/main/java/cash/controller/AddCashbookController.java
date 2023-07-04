package cash.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.CashbookDao;
import cash.model.HashtagDao;
import cash.vo.Cashbook;
import cash.vo.Hashtag;
import cash.vo.Member;

@WebServlet("/addCashbook")
public class AddCashbookController extends HttpServlet {
	// 입력 폼
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 유효성 검사
		HttpSession session = request.getSession();
		if (session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		// request 매개값
		int targetYear = Integer.parseInt(request.getParameter("targetYear"));
		int targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
		int targetDate = Integer.parseInt(request.getParameter("targetDate"));
		
		// 나머지 데이터는 입력폼에서 사용자가 입력
		request.setAttribute("targetYear",targetYear);
		request.setAttribute("targetMonth",targetMonth);
		request.setAttribute("targetDate",targetDate);
		
		// jsp페이지로 포워드(디스 패치)
		request.getRequestDispatcher("/WEB-INF/view/addCashbook.jsp").forward(request,response);
	}
	// 입력 액션
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		// request 매개값
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("loginMember");
		
		CashbookDao cashbookDao = new CashbookDao();
		
		// request.getParameter()
		String category = request.getParameter("category");
		String memo = request.getParameter("memo");
		int price = Integer.parseInt(request.getParameter("price"));
		String memberId = member.getMemberId();
		String cashbookDate = request.getParameter("date");
		System.out.println(category);
		System.out.println(memo);
		System.out.println(price);
		System.out.println(memberId);
		System.out.println(cashbookDate);
		
		Cashbook cashbook = new Cashbook();
		cashbook.setCategory(category);
		cashbook.setMemo(memo);
		cashbook.setPrice(price);
		cashbook.setMemberId(memberId);
		cashbook.setCashbookDate(cashbookDate);
		
		int cashbookNo = cashbookDao.insertCashbook(cashbook); // 키 값 반환
		if(cashbookNo == 0) {
			System.out.println("입력실패");
			response.sendRedirect(request.getContextPath()+"/addCashbook");
			return;
		}
		
		// 입력성공시 -> 해시태그가 있다면 해시태그를 추출 - > 해시태그 입력
		// 해시태그 추출 알고리즘
		HashtagDao hashtagDao = new HashtagDao();
		int hashtagRow = 0;
		
		String memo1 = cashbook.getMemo();
		
		String memo2 = memo1.replace("#"," #"); //#구디#아카데미 -> #구디 #아카데미
		for(String ht : memo2.split(" ")) {
			String ht2 = ht.replace("#","");
			if(ht2.length() > 0) {
				Hashtag hashtag = new Hashtag();
				hashtag.setCashbookNo(cashbookNo);
				hashtag.setWord(ht2);
				hashtagRow = hashtagDao.insertHashtag(hashtag);
			}
		}
		if(hashtagRow>0) {
			System.out.println("해쉬테그입력성공");
			response.sendRedirect(request.getContextPath()+"/calendar");
			return;
		}
		// redirect -> cashbookListController
	}

}
