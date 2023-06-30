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

@WebServlet("/addMember")
public class AddMemberController extends HttpServlet {
	// addMember.jsp 회원가입폼
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 유효검사 (null 일때)
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") != null) {
			response.sendRedirect(request.getContextPath()+"/cashbook");
			return;
		}
		// jsp페이지로 포워드(디스 패치)
		request.getRequestDispatcher("/WEB-INF/view/addMember.jsp").forward(request,response);
	}
	
	//회원 가입 액션
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 유효검사 (null 일때)
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") != null) {
			response.sendRedirect(request.getContextPath()+"/cashbook");
			return;
		}
		// request.getParameter()
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		Member member = new Member(memberId,memberPw,null,null);
		
		// 회원가입 DAO 호출
		MemberDao memberdao = new MemberDao();
		int row = memberdao.insertMember(member);
		
		if(row==0) { // 회워가입 실패시
			// addMember.jsp 뷰로 이동하는 controller를 리다이렉트
			System.out.println("회원가입 실패");
			response.sendRedirect(request.getContextPath()+"/addMember");
		} else if(row==1) { // 회원가입 성공시
			// login.jsp 뷰를 이동하는 controller를 리다이렉트
			System.out.println("회원가입 성공");
			response.sendRedirect(request.getContextPath()+"/login");
		} else {
			System.out.println("add member error");
		}
	}

}
