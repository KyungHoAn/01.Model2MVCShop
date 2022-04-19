package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.dao.UserDAO;
import com.model2.mvc.service.user.vo.UserVO;

public class PurchaseDAO {

	public PurchaseDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public void insertPurchase(PurchaseVO purchaseVO) throws Exception{
		System.out.println("============================");
		System.out.println("purchaseDao DB 부분");
		Connection con = DBUtil.getConnection();
		
		System.out.println(purchaseVO.getPurchaseProd());
		System.out.println(purchaseVO.getBuyer());
		
		String sql ="insert\r\n"
				+ " into transaction\r\n"
				+ " values (seq_transaction_tran_no.NEXTVAL,?,?,?,?,?,?,?,?,sysdate,?)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		stmt.setString(2, purchaseVO.getBuyer().getUserId());
		stmt.setString(3, purchaseVO.getPaymentOption());
		stmt.setString(4, purchaseVO.getReceiverName());
		stmt.setString(5, purchaseVO.getReceiverPhone());
		stmt.setString(6, purchaseVO.getDivyAddr());
		stmt.setString(7, purchaseVO.getDivyRequest());
		stmt.setString(8, purchaseVO.getTranCode());
		stmt.setString(9, purchaseVO.getDivyDate());
		stmt.executeUpdate();
		
		System.out.println("purchase insert 완료");
		System.out.println("=======================");
		
		con.close();
	}

	public PurchaseVO findPurchase(int tranNo) throws Exception{
		Connection con = DBUtil.getConnection();
	
		String sql = "SELECT * FROM transaction WHERE tran_no = ?";
//		String sql = "SELECT * \r\n"
//				+ " FROM transaction t, product p, users u\r\n"
//				+ " WHERE t.prod_no = p.prod_no AND t.buyer_id = u.user_id and tran_no = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1,tranNo);
		
		ResultSet rs = stmt.executeQuery();
		PurchaseVO pvo = new PurchaseVO();
		
		while(rs.next()) {
//			ProductVO vo = new ProductVO();
//			vo.setProdNo(rs.getInt("prod_no"));
//			
//			UserVO uvo = new UserVO();
//			uvo.setUserId(rs.getString("user_id"));
//			
//			pvo.setPurchaseProd(vo);
//			pvo.setBuyer(uvo);
			pvo.setPurchaseProd(new ProductDAO().findProduct(rs.getInt("prod_no")));
			pvo.setBuyer(new UserDAO().findUser(rs.getString("buyer_id")));
			pvo.setPaymentOption(rs.getString("payment_option"));
			pvo.setReceiverName(rs.getString("receiver_name"));
			pvo.setReceiverPhone(rs.getString("receiver_phone"));
			pvo.setDivyAddr(rs.getString("demailaddr"));
			pvo.setDivyRequest(rs.getString("dlvy_Request"));
			pvo.setOrderDate(rs.getDate("order_data"));
			pvo.setDivyDate(rs.getString("dlvy_date"));
			pvo.setTranNo(rs.getInt("tran_no"));	
						
		}
		con.close();
		return pvo;
	}
	
	public HashMap<String, Object> getPurchaseList(SearchVO searchVO, String buyerId) throws Exception{
		Connection con = DBUtil.getConnection();
//		String sql = "SELECT*FROM transaction where buyer_id = ?";
		String sql = "SELECT * \r\n"
				+ " FROM transaction t, product p, users u\r\n"
				+ " WHERE t.prod_no = p.prod_no AND t.buyer_id = u.user_id and buyer_id = ?";
		PreparedStatement stmt = con.prepareStatement(
				sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		
		stmt.setString(1, buyerId);
		ResultSet rs = stmt.executeQuery();
		rs.last();
		int total = rs.getRow();
		System.out.println("total:"+total);
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));
		
		rs.absolute(searchVO.getPage()*searchVO.getPageUnit()-searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():"+searchVO.getPage());
		System.out.println("searchVO.getPageUnit():"+searchVO.getPageUnit());
		
		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
		if(total>0) {
			for(int i=0; i<searchVO.getPageUnit(); i++) {
				ProductVO vo = new ProductVO();
				vo.setProdNo(rs.getInt("prod_no"));
				
				UserVO uvo = new UserVO();
				uvo.setUserId(rs.getString("user_id"));				
				
				PurchaseVO pvo = new PurchaseVO();
				pvo.setPurchaseProd(vo);
				pvo.setBuyer(uvo);
				pvo.setReceiverName(rs.getString("receiver_name"));
				pvo.setReceiverPhone(rs.getString("receiver_phone"));
				pvo.setTranNo(rs.getInt("tran_no"));
				pvo.setTranCode(rs.getString("tran_status_code").trim());
				list.add(pvo);
				if(!rs.next())
					break;
			}
		}
		System.out.println("=================");
		System.out.println(list);
		System.out.println("=================");
		
		System.out.println("list.size()"+list.size());
		map.put("list",list);
		System.out.println("map().size():"+map.size());
		con.close();
		return map;
	}
	
	public void updatePurchase(PurchaseVO purchaseVO) throws Exception{
		System.out.println("=============updatePurchaseDAO 부분===========");
		
		Connection con = DBUtil.getConnection();
		String sql ="UPDATE transaction\r\n"
				+ "SET payment_option=?,receiver_name=?,receiver_phone=?, demailaddr=?,dlvy_request=?,dlvy_date=?\r\n"
				+ "where tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchaseVO.getPaymentOption());
		stmt.setString(2, purchaseVO.getReceiverName());
		stmt.setString(3, purchaseVO.getReceiverPhone());
		stmt.setString(4, purchaseVO.getDivyAddr());
		stmt.setString(5, purchaseVO.getDivyRequest());
		stmt.setString(6, purchaseVO.getDivyDate());
		stmt.setInt(7, purchaseVO.getTranNo());
		
		stmt.executeUpdate();
		System.out.println("update 완료");
		con.close();		
	}
	
	public void updateTranCode(PurchaseVO purchaseVO) throws Exception{
		Connection con = DBUtil.getConnection();
		String sql ="UPDATE transaction\r\n"
				+ "SET tran_status_code=?\r\n"
				+ "WHERE prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchaseVO.getTranCode());
		stmt.setInt(2, purchaseVO.getTranNo());
		
		stmt.executeUpdate();
		System.out.println("updatetrancode 완료");
		con.close();
	}
}
