package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.service.PurchaseService;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class GetPurchaseAction extends Action{
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception{		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseService service = new PurchaseServiceImpl();
		System.out.println("2");
		System.out.println(tranNo);
		
		
		PurchaseVO purchaseVO = service.getPurchase(tranNo);
		
		
		System.out.println("3");
		request.setAttribute("purchaseVO", purchaseVO);
		System.out.println("4");
		System.out.println("getPurchase.jsp·Î Navigation");
		System.out.println("========================");
		return "forward:/purchase/getPurchase.jsp";
	}
}
