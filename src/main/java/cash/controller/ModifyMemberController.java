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

@WebServlet("/modifyMember")
public class ModifyMemberController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		request.getRequestDispatcher("/WEB-INF/view/modifyMember.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		String beforePw = request.getParameter("beforePw");
		String chPw1 = request.getParameter("chPw1");
		String chPw2 = request.getParameter("chPw2");
		
		MemberDao memberdao = new MemberDao();
		Member memberId = memberdao.selectMemberOne(loginMember.getMemberId());
		
		int row = memberdao.modifyMember(memberId,beforePw,chPw1,chPw2);
		
		if(row==0) { // 비밀번호변경 실패시
			System.out.println("비밀번호변경 실패");
			response.sendRedirect(request.getContextPath()+"/modifyMember");
		} else if(row==1) { // 비밀번호변경 성공시
			// 비밀번호 변경시 재로그인 필요
			System.out.println("비밀번호변경 성공");
			session.invalidate();
			response.sendRedirect(request.getContextPath()+"/login");
		} else {
			System.out.println("modify member error");
		}
	}

}
