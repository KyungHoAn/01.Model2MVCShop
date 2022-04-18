package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.service.PurchaseService;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class UpdateTranCodeAction extends Action{
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("UpdateTranCodeAction");
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseVO purchaseVO = new PurchaseVO();
		purchaseVO.setTranNo(tranNo);
		
		PurchaseService service = new PurchaseServiceImpl();
		service.updateTranCode(purchaseVO);
		System.out.println("list�� �̵�");
		return "forward:/purchase/listPurchase.jsp";
	}
}
