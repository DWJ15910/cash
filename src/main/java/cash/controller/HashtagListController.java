package cash.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.CashbookDao;
import cash.model.HashtagDao;
import cash.vo.Cashbook;
import cash.vo.Member;

@WebServlet("/hashtagList")
public class HashtagListController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 유효성 검사
		HttpSession session = request.getSession();
		if (session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		Member member = (Member)session.getAttribute("loginMember"); // session안에 로그인 정보가 필요
		String memberId = member.getMemberId();
		int targetYear = Integer.parseInt(request.getParameter("targetYear"));
		int targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
		String word = request.getParameter("word");
		System.out.println(memberId);
		System.out.println(targetYear);
		System.out.println(targetMonth);
		System.out.println(word);
		
		
		List<Map<String,Object>> hashList = new ArrayList<Map<String, Object>>();
		HashtagDao hashtagDao = new HashtagDao();
	      
		hashList = hashtagDao.selectWordByCount(memberId, word,  targetYear,targetMonth+1);
		System.out.println(hashList);
		
		request.setAttribute("hashList", hashList);
		
		// jsp페이지로 포워드(디스 패치)
		request.getRequestDispatcher("/WEB-INF/view/hashtagList.jsp").forward(request,response);
	}
}