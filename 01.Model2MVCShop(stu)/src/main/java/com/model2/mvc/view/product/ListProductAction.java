package com.model2.mvc.view.product;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.user.ProductService;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.service.PurchaseService;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class ListProductAction extends Action{
	public String execute(	HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
//		String menu = request.getParameter("menu");
		
		SearchVO searchVO = new SearchVO();
		ProductVO productVO = new ProductVO();
		
		System.out.println("==================");
		System.out.println(productVO.getProTranCode());
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
		System.out.println("=============ListProductAction");
		System.out.println(map);
		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);
		
		
//		if(menu.equals(request.getParameter("manage"))) {
//			
//		}
		String menu = request.getParameter("menu");
		
		return "forward:/product/listProduct.jsp";
	}
}
