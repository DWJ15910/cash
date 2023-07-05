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
		
		//페이징
		int currentPage = 1;
		// 페이지 값 받아오기, 유효성 검사
		if(request.getParameter("currentPage")!=null){
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		int rowPerPage = 3;
		int beginRow = (currentPage-1)*rowPerPage;
		
		// 디버깅
		System.out.println(memberId);
		System.out.println(targetYear);
		System.out.println(targetMonth);
		System.out.println(word);
		
		
		List<Map<String,Object>> hashList = new ArrayList<Map<String, Object>>();
		HashtagDao hashtagDao = new HashtagDao();
	      
		hashList = hashtagDao.selectWordByCount(memberId, word,  targetYear,targetMonth+1,beginRow,rowPerPage);
		// 해쉬태그 해당 달의 리스트 최종 개수
		int totalRow = hashtagDao.selectWordByCountHashtagList(memberId, word,  targetYear,targetMonth+1);
		System.out.println(hashList);
		
		int lastPage = totalRow/rowPerPage;
		if(totalRow%rowPerPage!=0){
			lastPage++;
		}
		//1~10에서 8번 눌러도 여전히 1~10나오도록 설정 변수 선언
		//첫번째 번호
		int startPage = ((currentPage-1)/10)*10+1;
		//페이지의 마지막 번호
		int endPage = Math.min(startPage+9,lastPage);
		
		request.setAttribute("hashList", hashList);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("lastPage", lastPage);
		request.setAttribute("endPage", endPage);
		
		// jsp페이지로 포워드(디스 패치)
		request.getRequestDispatcher("/WEB-INF/view/hashtagList.jsp").forward(request,response);
	}
}