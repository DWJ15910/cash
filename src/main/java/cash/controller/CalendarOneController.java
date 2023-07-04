package cash.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.CashbookDao;
import cash.vo.Cashbook;
import cash.vo.Member;

@WebServlet("/calendarOne")
public class CalendarOneController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// session 검증 코드
		HttpSession session = request.getSession();
		
		
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		Member member = (Member)session.getAttribute("loginMember"); // session안에 로그인 정보가 필요
		String memberId = member.getMemberId();
		int targetYear = Integer.parseInt(request.getParameter("targetYear"));
		int targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
		int targetDate = Integer.parseInt(request.getParameter("targetDate"));
		
		request.setAttribute("targetYear",targetYear);
		request.setAttribute("targetMonth",targetMonth);
		request.setAttribute("targetDate",targetDate);
	
		List<Cashbook> list = new CashbookDao().selectCashbookListByDate(memberId, targetYear, targetMonth+1,targetDate);
		
		request.setAttribute("list", list);
		
		// 달력을 출력하는 뷰
		request.getRequestDispatcher("/WEB-INF/view/calendarOne.jsp").forward(request, response);
	}
}
