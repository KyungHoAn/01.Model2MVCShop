package com.model2.mvc.view.product;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.user.ProductService;

public class ListProductAction extends Action{
	public String execute(	HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
//		String menu = request.getParameter("menu");
		
		SearchVO searchVO = new SearchVO();
		
		int page =1;
		if(request.getParameter("page")!=null)
			page = Integer.parseInt(request.getParameter("page"));
		
		searchVO.setPage(page);
		searchVO.setSearchCondition(request.getParameter("searchCondition"));
		searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
		
		String pageunit = getServletContext().getInitParameter("pageSize");
		searchVO.setPageUnit(Integer.parseInt(pageunit));
		
		ProductService service = new ProductServiceImpl();
		HashMap<String,Object> map = service.getProductList(searchVO);
		
		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);
		
//		if(menu.equals(request.getParameter("manage"))) {
//			
//		}
		String menu = request.getParameter("menu");
		
		return "forward:/product/listProduct.jsp";
	}
}
