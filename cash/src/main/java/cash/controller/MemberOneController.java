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

@WebServlet("/memberOne")
public class MemberOneController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 유효성 검사
		HttpSession session = request.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		// 모델 값 구하기 (dao 메서드를 호출)
		MemberDao memberdao = new MemberDao();
		Member member = memberdao.selectMemberOne(loginMember.getMemberId());
		
		// member 출력하는(포워딩대상) memberOne.jsp에도 공유되어야 한다.
		// request가 공유되니까 request안에 넣어서 공유
		request.setAttribute("member", member);
		
		// memberOne.jsp 포워딩
		request.getRequestDispatcher("/WEB-INF/view/memberOne.jsp").forward(request, response);
	}
}
