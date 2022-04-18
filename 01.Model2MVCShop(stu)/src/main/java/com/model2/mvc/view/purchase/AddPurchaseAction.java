package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.user.ProductService;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.service.PurchaseService;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class AddPurchaseAction extends Action{
	public String execute(	HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("===================");
		System.out.println("test");
		
		PurchaseVO purchaseVO = new PurchaseVO();
		ProductVO productVO = new ProductVO();
		UserVO userVO = new UserVO();
		
		//userVO session 값 넣기
		userVO = (UserVO)request.getSession().getAttribute("user");
		productVO.setProdNo(Integer.parseInt(request.getParameter("prodNo")));
		
		System.out.println("====== userVO / ProductVO ======");
		System.out.println(userVO);
		System.out.println(productVO);
		
		purchaseVO.setPurchaseProd(productVO);
		purchaseVO.setBuyer(userVO);	
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purchaseVO.setDivyAddr(request.getParameter("receiverAddr"));
		purchaseVO.setDivyRequest(request.getParameter("receiverRequest"));
		purchaseVO.setDivyDate(request.getParameter("receiverDate"));
		purchaseVO.setTranCode("1");
		
		PurchaseService service = new PurchaseServiceImpl();
		service.addPurchase(purchaseVO);
		
		request.setAttribute("purchaseVO", purchaseVO);
		
		if(purchaseVO.getTranCode().equals("1")) {
			productVO.setProTranCode("1");
		}
//		ProductService service2 = new ProductServiceImpl();
//		service2.
		
		System.out.println("test1");
		return "forward:/purchase/addPurchase.jsp";
	}
}