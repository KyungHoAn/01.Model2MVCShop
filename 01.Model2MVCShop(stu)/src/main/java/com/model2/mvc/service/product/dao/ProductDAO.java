package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class ProductDAO {

	public ProductDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public void insertProduct(ProductVO productVO) throws Exception{
		Connection con = DBUtil.getConnection();
		
		String sql ="insert \r\n"
				+ "into product\r\n"
				+ "values(seq_product_prod_no.NEXTVAL,?,?,?,?,?,sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3,productVO.getManuDate().replace("-", ""));
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.executeUpdate();
		
		con.close();
	}
	
	public ProductVO findProduct(int prodNo) throws Exception{
		Connection con = DBUtil.getConnection();
		
		String sql = "select*from product where prod_no=?";		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		
		ResultSet rs = stmt.executeQuery();
		
		ProductVO productVO = null;
		while(rs.next()) {
			productVO = new ProductVO();
			productVO.setProdNo(rs.getInt("prod_no"));
			productVO.setProdName(rs.getString("prod_name"));
			productVO.setProdDetail(rs.getString("prod_detail"));
			productVO.setManuDate(rs.getString("manufacture_day"));
			productVO.setPrice(rs.getInt("price"));
			productVO.setFileName(rs.getString("image_file"));
			productVO.setRegDate(rs.getDate("reg_date"));
		}
		con.close(); 	
		return productVO;
	}
	public HashMap<String,Object> getProductList(SearchVO searchVO) throws Exception{
		Connection con = DBUtil.getConnection();
		String sql = "SELECT p.*,t.*, NVL(t.tran_status_code,0) NTSC from PRODUCT p, transaction t where p.prod_no = t.prod_no(+) ";
		if(searchVO.getSearchCondition()!=null) {
			if(searchVO.getSearchCondition().equals("0")) {
				sql+= " AND p.PROD_NO='" + searchVO.getSearchKeyword()
				+ "'";
			} else if(searchVO.getSearchCondition().equals("1")) {
				sql+=" AND p.PROD_NAME='" + searchVO.getSearchKeyword()
				+ "'";
			}
		}		
		sql+=" order by p.prod_no";
		PreparedStatement stmt = con.prepareStatement(
				sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery();
		
		rs.last();
		int total =rs.getRow();
		System.out.println("?????? ??:"+total);
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));
		
		rs.absolute(searchVO.getPage()*searchVO.getPageUnit()-searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():"+searchVO.getPage());
		System.out.println("searchVO.getPageUnit():"+searchVO.getPageUnit());
		
		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
//		ArrayList<PurchaseVO> list2 = new ArrayList<PurchaseVO>();
		if(total >0) {
			for(int i=0; i<searchVO.getPageUnit(); i++) {				
				ProductVO vo = new ProductVO();
				vo.setProdNo(rs.getInt("prod_no"));
				vo.setProdName(rs.getString("prod_name"));
				vo.setProdDetail(rs.getString("prod_detail"));
				vo.setManuDate(rs.getString("manufacture_day"));
				vo.setPrice(rs.getInt("price"));
				vo.setFileName(rs.getString("image_file"));
				vo.setRegDate(rs.getDate("reg_date"));
				vo.setProTranCode(rs.getString("NTSC"));
				
//				PurchaseVO vo2 = new PurchaseVO();
//				vo2.setTranNo(rs.getInt("tran_no"));
				list.add(vo);
				if(!rs.next())
					break;
			}
		}
//		while(rs.next()) {
//			PurchaseVO purchaseVO = new PurchaseVO();
//			purchaseVO.setTranNo(rs.getInt("tran_No"));
//		}
		System.out.println("list.size():"+list.size());
		map.put("list",list);
		System.out.println("map().size():"+map.size());
		con.close();
		return map;
	}
	
	public void updateProduct(ProductVO productVO) throws Exception{
		Connection con = DBUtil.getConnection();
		String sql ="update product set prod_name=?,prod_detail=?,manufacture_day=?, price =?, image_file=?\r\n"
				+ "where prod_no=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5,productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());

		System.out.println("sql update complete");
		stmt.executeUpdate();
		con.close();
	}
}
