package com.model2.mvc.view.purchase;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.service.PurchaseService;
import com.model2.mvc.service.user.vo.UserVO;

public class ListPurchaseAction extends Action{
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		SearchVO searchVO = new SearchVO();
		
		int page =1;
		if(request.getParameter("page")!=null)
			page = Integer.parseInt(request.getParameter("page"));
		
		searchVO.setPage(page);
		searchVO.setSearchCondition(request.getParameter("searchCondition"));
		searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
		
		String pageunit = getServletContext().getInitParameter("pageSize");
		searchVO.setPageUnit(Integer.parseInt(pageunit));
		
		UserVO userVO = new UserVO();
		userVO = (UserVO)request.getSession().getAttribute("user");
			
		PurchaseService service = new PurchaseServiceImpl();
		HashMap<String,Object> map = service.getPurchaseList(searchVO, userVO.getUserId());
			
		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);
		System.out.println("===============");
		System.out.println(map);
		return "forward:/purchase/listPurchase.jsp";
	}
}
