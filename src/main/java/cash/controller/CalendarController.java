package cash.controller;

import java.io.IOException;
import java.util.Calendar;
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

@WebServlet("/calendar")
public class CalendarController extends HttpServlet {
       
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // session 검증 코드
      HttpSession session = request.getSession();
      Member member = (Member)session.getAttribute("loginMember"); // session안에 로그인 정보가 필요
      if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
      }
      String memberId = member.getMemberId();
      // view에 넘겨줄 달력정보(달력모델값)
      Calendar firstDay = Calendar.getInstance(); // 오늘 날짜
      
      // 오늘날짜(기본값) 년,월,일의 기본값은 이번달1일
      int targetYear = firstDay.get(Calendar.YEAR);
      int targetMonth = firstDay.get(Calendar.MONTH);
      firstDay.set(Calendar.DATE, 1);
      
      // 출력하고자하는 년도와 월이 매개값으로 넘어 왔다면 아래 사용 
      if(request.getParameter("targetYear") != null
         || request.getParameter("targetMonth") != null) {
         targetYear = Integer.parseInt(request.getParameter("targetYear"));
         targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
         firstDay.set(Calendar.YEAR, targetYear);
         // API를 통해 월이12가 들어오면 13월로 안넘어가지고 자동으로1월이 되고 년은 내년으로 넘어간다.
         // 또한 -1이 입력되면 -1월이 아닌 작년 12월오 되고 년은 작년으로 넘어간다.
         firstDay.set(Calendar.MONTH, targetMonth);
         
         targetYear = firstDay.get(Calendar.YEAR);
         targetMonth = firstDay.get(Calendar.MONTH);
      }
      
      // 달력출력시 시작공백 
      int beginBlank = firstDay.get(Calendar.DAY_OF_WEEK)-1; // 1일날짜의 요일(일1, 월2... 토6) -1 
      System.out.println(beginBlank + "<--- beginBlank(1일의 요일)");
      
      // 출력되는 월의 마지막날짜
      int lastDate = firstDay.getActualMaximum(Calendar.DATE);
      System.out.println(lastDate + "lastDate");

      // 달력출력시 마지막 날짜 출력후 공백 개수 -> 전체 출력 셀(totalCell)의 개수가 7로 나누어떨어졌을때 나머지가 공백수
      int endBlank = 0;
      if(((beginBlank+lastDate)%7) != 0) {
         endBlank = 7 - ((beginBlank+lastDate)%7);
      }
      // 달력 뒤에 공백 개수
      System.out.println(endBlank + "endBlank");
      // 전체행
      int totalCell = beginBlank+lastDate+endBlank;
      System.out.println(totalCell + "totalCell");
      
      // 모델을 호출(DAO 타겟 월의 수입/지출 데이터)
      List<Cashbook> list = new CashbookDao().selectCashbookListByMonth(memberId, targetYear, targetMonth+1);
      
      
      
      // 뷰에 값넘기기(request 속성)
      request.setAttribute("targetYear", targetYear);
      request.setAttribute("targetMonth", targetMonth);
      request.setAttribute("lastDate", lastDate);
      request.setAttribute("totalCell", totalCell);
      request.setAttribute("beginBlank", beginBlank);
      
      request.setAttribute("list", list);
      
      
      // 오늘 날짜에 색상표시를 위해 현재날짜 데이터 보냄
      Calendar today = Calendar.getInstance();
      int currentYear = today.get(Calendar.YEAR);
      int currentMonth = today.get(Calendar.MONTH);
      int currentDate = today.get(Calendar.DATE);
      
      // 현재날짜 보내기
      request.setAttribute("currentYear", currentYear);
      request.setAttribute("currentMonth", currentMonth);
      request.setAttribute("currentDate", currentDate);
      
      // 달력을 출력하는 뷰
      request.getRequestDispatcher("/WEB-INF/view/calendar.jsp").forward(request, response);
      
   }
}