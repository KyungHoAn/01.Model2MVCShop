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
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		String tranCode = request.getParameter("tranCode");
		PurchaseVO purchaseVO = new PurchaseVO();
		purchaseVO.setTranNo(prodNo);
		purchaseVO.setTranCode(tranCode);
		PurchaseService service = new PurchaseServiceImpl();
		service.updateTranCode(purchaseVO);
		
		System.out.println("list로 이동");
		//if adming이면 produvt user면 purchase
		return "/listProduct.do?menu=manage";
	}
}