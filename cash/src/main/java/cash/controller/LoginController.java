package cash.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.MemberDao;
import cash.vo.Member;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	// GET 값을 받아올 때는 해당 코드 실행
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// 로그인 된 사람이 로그인을 할려하면 cashbook으로 이동
		if(session.getAttribute("loginMember") != null) {
			response.sendRedirect(request.getContextPath()+"/cashbook");
			return;
		}
		request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request,response);
	}
	// POST값을 받아올 때는 해당 코드 실행
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		
		//요청값을 분석해서 요청값을 하나로 만드는 행위
		Member member = new Member(memberId,memberPw,null,null);
		
		MemberDao memberdao = new MemberDao();
		Member loginMember = memberdao.selectMemberById(member);
		
		// null 로그인 실패
		if(loginMember == null) {
			System.out.println("로그인실패");
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		
		// 로그인 성공시: 세션 사용
		HttpSession session = request.getSession();
		session.setAttribute("loginMember", loginMember);
		System.out.println("로그인성공");
		response.sendRedirect(request.getContextPath()+"/cashbook");
	}

}
