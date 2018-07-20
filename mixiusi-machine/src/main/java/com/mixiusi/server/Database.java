package com.mixiusi.server;
//package com.mixiusi.Server;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.mixiusi.bean.coffee;
//import com.mixiusi.bean.coffeedosing;
//import com.mixiusi.bean.discount_info;
//import com.mixiusi.util.Enums.Login;
//
////mysql���ݿ�
//public class Database {
//	private Connection con;
//	private Statement stmt = null;
//	private static Database instance = new Database();
//
//	public static Database sharedInstance() {
//		return instance;
//	}
//
//	public Database() {
//		String driver = "com.mysql.jdbc.Driver";
//		String url = "jdbc:mysql://47.98.164.44:3306/coffeemachine?useUnicode=true&characterEncoding=UTF-8&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai";
//		String user = "root";
//		String password = "root";
//		try {
//			Class.forName(driver);
//
//			con = DriverManager.getConnection(url, user, password);
//
//			if (!con.isClosed())
//				System.out.println("Succeeded connecting to the Database!");
//
//			stmt = con.createStatement();
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("fail to connect to the Database");
//		}
//	}
//
//	// ��������
//	public int insertmaterial(String vendername) {
//		int i = 0;
//		String sql = "insert into material (VenderName,water,cupnum,milk,sugar,chocolate,milktea,coffeebean) values(?,?,?,?,?,?,?,?)";
//		PreparedStatement pstmt;
//		double water = 0;
//		double cupnum = 0;
//		double milk = 0;
//		double sugar = 0;
//		double chocolate = 0;
//		double milktea = 0;
//		double coffeebean = 0;
//
//		try {
//			pstmt = (PreparedStatement) con.prepareStatement(sql);
//			pstmt.setString(1, vendername);
//			pstmt.setDouble(2, water);
//			pstmt.setDouble(3, cupnum);
//			pstmt.setDouble(4, milk);
//			pstmt.setDouble(5, sugar);
//			pstmt.setDouble(6, chocolate);
//			pstmt.setDouble(7, milktea);
//			pstmt.setDouble(8, coffeebean);
//
//			i = pstmt.executeUpdate();
//			pstmt.close();
//			return 1;
//		} catch (SQLException e) {
//			return 0;
//		}
//
//	}
//
//	// ��ѯ����
//	public Login QueryUserInfo(String vendername, String Password) {
//		ResultSet rs;
//
//		String result = null;
//		try {
//			String sql = "select * from logininfo where VenderName = ?";
//			PreparedStatement preStmt = con.prepareStatement(sql);
//			preStmt.setString(1, vendername);
//			rs = preStmt.executeQuery();
//			int col = rs.getMetaData().getColumnCount();
//
//			while (rs.next()) {
//				result = rs.getString(2);
//			}
//			System.out.println(result);
//			if (result == null) {
//				return Login.USER_NOT_EXIST;
//			} else if (Password.equals(result)) {//MD5.md5(result)
//				return Login.LOGIN_SSUCCESS;
//			} else {
//				return Login.PASSWORD_ERROR;
//			}
//		} catch (SQLException e) {
//			return Login.LOGIN_EXCEPTION;
//		} finally {
//
//		}
//	}
//
//	// ��������
//	public int UpdateMaterial(String vendorname, String inventory) {
//		JSONArray array = JSON.parseArray(inventory);
//		double water = 0, cupnum = 0, milk = 0, sugar = 0, chocolate = 0, milktea = 0, coffeebean = 0;
//		for (int i = 0; i < array.size(); i++) {
//			JSONObject jsonObj = array.getJSONObject(i);
//			int id = jsonObj.getInteger("id");
//
//			double value = jsonObj.getDouble("value");
//			if (id == 1)
//				water = value;
//			else if (id == 2)
//				cupnum = value;
//			else if (id == 3)
//				milk = value;
//			else if (id == 4)
//				sugar = value;
//			else if (id == 5)
//				chocolate = value;
//			else if (id == 6)
//				milktea = value;
//			else if (id == 7)
//				coffeebean = value;
//		}
//		int i = 0;
//		String sql = "update material set water='" + water + "',cupnum = '" + cupnum + "',milk = '" + milk
//				+ "',sugar = '" + sugar + "',chocolate = '" + chocolate + "',milktea = '" + milktea + "',coffeebean = '"
//				+ coffeebean + "'where VenderName='" + vendorname + "'";
//		PreparedStatement pstmt;
//		try {
//			pstmt = (PreparedStatement) con.prepareStatement(sql);
//			i = pstmt.executeUpdate();
//
//			pstmt.close();
//			return 1;
//		} catch (SQLException e) {
//			return 0;
//		}
//
//	}
//
//	// ��������
//	public int AddMaterial(String vendorname, String inventory) {
//		JSONArray array = JSON.parseArray(inventory);
//		double water = 0, cupnum = 0, milk = 0, sugar = 0, chocolate = 0, milktea = 0, coffeebean = 0;
//		for (int i = 0; i < array.size(); i++) {
//			JSONObject jsonObj = array.getJSONObject(i);
//			int id = jsonObj.getInteger("id");
//
//			double value = jsonObj.getDouble("total_value");
//			if (id == 1)
//				water = value;
//			else if (id == 2)
//				cupnum = value;
//			else if (id == 3)
//				milk = value;
//			else if (id == 4)
//				sugar = value;
//			else if (id == 5)
//				chocolate = value;
//			else if (id == 6)
//				milktea = value;
//			else if (id == 7)
//				coffeebean = value;
//		}
//		int i = 0;
//		String sql = "update material set water='" + water + "',cupnum = '" + cupnum + "',milk = '" + milk
//				+ "',sugar = '" + sugar + "',chocolate = '" + chocolate + "',milktea = '" + milktea + "',coffeebean = '"
//				+ coffeebean + "'where VenderName='" + vendorname + "'";
//		PreparedStatement pstmt;
//		try {
//			pstmt = (PreparedStatement) con.prepareStatement(sql);
//			i = pstmt.executeUpdate();
//
//			pstmt.close();
//			return 1;
//		} catch (SQLException e) {
//			return 0;
//		}
//
//	}
//
//	// ��ѯ������Ϣ
//	public ArrayList<coffee> Querycoffee() {
//		ResultSet rs;
//		coffeedosing info = new coffeedosing();
//		ArrayList<coffee> coffeelist = new ArrayList<coffee>();
//		String result = null;
//		try {
//			String sql = "select *  from coffeeinfo ";
//			PreparedStatement preStmt = con.prepareStatement(sql);
//			rs = preStmt.executeQuery();
//
//			while (rs.next()) {
//				coffee coffeeinfo = new coffee();
//				coffeeinfo.coffeeId = rs.getInt("coffee_id");
//				coffeeinfo.coffeeTitle = rs.getString("coffee_title");
//				coffeeinfo.price = rs.getDouble("price");
//				coffeeinfo.discount = rs.getDouble("discount");
//				coffeeinfo.imgurl = rs.getString("imgurl");
//				coffeeinfo.soldnum = rs.getInt("soldnum");
//				coffeeinfo.is_hot = rs.getBoolean("is_hot");
//				coffeeinfo.is_new = rs.getBoolean("is_new");
////				coffeeinfo.dosings = info.checkdosing(coffeeinfo.coffeeId);
//				coffeelist.add(coffeeinfo);
//			}
//			return coffeelist;
//
//		} catch (SQLException e) {
//			System.out.println("coffeeinfoexcep");
//			return coffeelist;
//		}
//	}
//
//	// �洢����״��
//	public int insertstatus(String vendername, long times, String status) {
//		int i = 0;
//		String sql = "insert into machine_statu (VenderName,timestamps,statujson) values(?,?,?)";
//		PreparedStatement pstmt;
//
//		try {
//			pstmt = (PreparedStatement) con.prepareStatement(sql);
//			pstmt.setString(1, vendername);
//			pstmt.setLong(2, times);
//			pstmt.setString(3, status);
//
//			i = pstmt.executeUpdate();
//			pstmt.close();
//			return 1;
//		} catch (SQLException e) {
//			return 0;
//		}
//
//	}
//
//	public int insertinfo(String vendername, long times, String info) {
//		int i = 0;
//		String sql = "insert into machine_info (VenderName,timestamps,machine_info) values(?,?,?)";
//		PreparedStatement pstmt;
//
//		try {
//			pstmt = (PreparedStatement) con.prepareStatement(sql);
//			pstmt.setString(1, vendername);
//			pstmt.setLong(2, times);
//			pstmt.setString(3, info);
//
//			i = pstmt.executeUpdate();
//			pstmt.close();
//			return 1;
//		} catch (SQLException e) {
//			return 0;
//		}
//
//	}
//
//	// ��ѯ������Ϣ
//	public discount_info Querydiscount(String vendername) {
//		ResultSet rs;
//		discount_info result = new discount_info();
//
//		try {
//			String sql = "select *  from discount_info where VenderName = ?";
//			PreparedStatement preStmt = con.prepareStatement(sql);
//			preStmt.setString(1, vendername);
//			rs = preStmt.executeQuery();
//
//			while (rs.next()) {
//				result.discount = rs.getString("discount");
//				result.reduction = rs.getString("reduction");
//			}
//
//			return result;
//		} catch (SQLException e) {
//			return result;
//		}
//	}
//
//	// ��ѯ���ȼ۸�
//	public double Queryprice(int coffeeid) {
//		ResultSet rs;
//		double price = 0.0;
//
//		try {
//			String sql = "select price  from coffeeinfo where coffee_id = ?";
//			PreparedStatement preStmt = con.prepareStatement(sql);
//			preStmt.setInt(1, coffeeid);
//			rs = preStmt.executeQuery();
//
//			while (rs.next()) {
//				price = rs.getDouble("price");
//			}
//
//			return price;
//		} catch (SQLException e) {
//			return price;
//		}
//	}
//
//	// ���붩��
//	public int insertindent(String indent_id, String coffeeindent, double price_ori, double price, int pay_method,
//			int pay_status) {
//		int i = 0;
//		String sql = "insert into payindent (indent_id,coffeeindent,price_ori,price,pay_method,pay_status) values(?,?,?,?,?,?)";
//		PreparedStatement pstmt;
//
//		try {
//			pstmt = (PreparedStatement) con.prepareStatement(sql);
//			pstmt.setString(1, indent_id);
//			pstmt.setString(2, coffeeindent);
//			pstmt.setDouble(3, price_ori);
//			pstmt.setDouble(4, price);
//			pstmt.setInt(5, pay_method);
//			pstmt.setInt(6, pay_status);
//
//			i = pstmt.executeUpdate();
//			pstmt.close();
//			return 1;
//		} catch (SQLException e) {
//			return 0;
//		}
//	}
//
//	// ɾ������
//	public int deleteindent(String indent_id) {
//		int i = 0;
//		String sql = "delete from payindent where indent_id='" + indent_id + "'";
//		PreparedStatement pstmt;
//		try {
//			pstmt = (PreparedStatement) con.prepareStatement(sql);
//			i = pstmt.executeUpdate();
//
//			pstmt.close();
//
//		} catch (SQLException e) {
//
//		}
//		return i;
//	}
//
//	// ��ѯ����֧�����
//	public int Querystatus(String indent_id) {
//		ResultSet rs;
//		int status = 0;
//
//		try {
//			String sql = "select pay_status  from payindent where indent_id = ?";
//			PreparedStatement preStmt = con.prepareStatement(sql);
//			preStmt.setString(1, indent_id);
//			rs = preStmt.executeQuery();
//
//			while (rs.next()) {
//				status = rs.getInt("pay_status");
//			}
//
//			return status;
//		} catch (SQLException e) {
//			return status;
//		}
//	}
//
//	// ������˶���
//	public int insertroll(String indent_id, double price, String reason) {
//		int i = 0;
//		String sql = "insert into rollbackindent (indent_id,price,reason) values(?,?,?)";
//		PreparedStatement pstmt;
//
//		try {
//			pstmt = (PreparedStatement) con.prepareStatement(sql);
//			pstmt.setString(1, indent_id);
//			pstmt.setDouble(2, price);
//			pstmt.setString(3, reason);
//
//			i = pstmt.executeUpdate();
//			pstmt.close();
//			return 1;
//		} catch (SQLException e) {
//			return 0;
//		}
//	}
//
//	// ���¶���֧�����
//	public int updateindentstatu(String indent_id) {
//		int i = 0;
//		String sql = "update payindent set pay_status = 1 where indent_id ='" + indent_id + "'";
//		PreparedStatement pstmt;
//		try {
//			pstmt = (PreparedStatement) con.prepareStatement(sql);
//			i = pstmt.executeUpdate();
//
//			pstmt.close();
//			return 1;
//		} catch (SQLException e) {
//			return 0;
//		}
//	}
//
//	// ��ѯ����֧����ʽ
//	public int querypaymethod(String indent_id) {
//		ResultSet rs;
//		int pay_method = 0;
//
//		try {
//			String sql = "select pay_method  from payindent where indent_id = ?";
//			PreparedStatement preStmt = con.prepareStatement(sql);
//			preStmt.setString(1, indent_id);
//			rs = preStmt.executeQuery();
//
//			while (rs.next()) {
//				pay_method = rs.getInt("pay_method");
//			}
//
//			return pay_method;
//		} catch (SQLException e) {
//			return pay_method;
//		}
//	}
//
//}
