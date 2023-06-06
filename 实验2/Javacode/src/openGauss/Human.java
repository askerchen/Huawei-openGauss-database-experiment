package openGauss;

import java.sql.*;
import java.util.Scanner;

public class Human {
	// 普通员工类，实现以下功能:
	// 1 在员工主页面，可以选择查看员工自己基本信息
	// 2 在员工主页面，修改员工自己的电话号码
	protected int staff_id;
	public Connection conn = null;
	public Scanner sc = null;
	public Human(int staff_id) {
		this.sc = human_resources_management.sc;
		this.conn = human_resources_management.conn;
		this.staff_id = staff_id;
	}
	
	public void query() {
		//System.out.println("您的基本信息如下:");
		String sql = "SELECT * FROM staffs WHERE staff_id=" + staff_id;
		utils.queryBySql(sql);
	}
	
	public int querySectionId() {
		String sql;
		sql = "SELECT section_id FROM staffs WHERE staff_id=" + staff_id;
		int section_id = -1;
        try {
        	Statement stmt = conn.createStatement();       
            ResultSet rs = stmt.executeQuery(sql);
            
            if(rs.next()) {
            	section_id = rs.getInt("section_id");
            	return section_id;
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return section_id;
	}

	
	public String queryPassword() {
		String sql;
		sql = "SELECT password FROM staffs WHERE staff_id=" + staff_id;
		String password = null;
        try {
        	Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(sql);
            
            if(rs.next()) {
            	password = rs.getString("password");
            	return password;
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
	}
	
	public void updatePhoneNumber() {
		System.out.println("请输入新的电话号码(请按照xxx.xxx.xxxx的格式):");
		String phone_number = sc.next();	
		String sql = "UPDATE staffs SET phone_number = '"+ phone_number +"'WHERE staff_id=" + staff_id;
		utils.updateBySql(sql);
	}
	
	public void choose(int opt) {
		switch(opt) {
			case 1:
				query();
				break;
			case 2:
				updatePhoneNumber();
				break;
		}
	}
}
