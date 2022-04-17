package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.service.PurchaseService;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class UpdatePurchaseViewAction extends Action {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println(tranNo);
		PurchaseService service = new PurchaseServiceImpl();
//		PurchaseVO purchaseVO = service.getPurchase(Integer.parseInt(request.getParameter("tranNo")));
		PurchaseVO purchaseVO = service.getPurchase(tranNo);
		
		request.setAttribute("purchasevO", purchaseVO);
		System.out.println(purchaseVO);
		System.out.println("====updatePurcahse.jsp·Î Navigation==========");
		return "forward:/purchase/updatePurchase.jsp";
	}
}
