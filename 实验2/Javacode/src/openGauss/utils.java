package openGauss;

import java.sql.*;
import java.util.Scanner;

public class utils {
	static public Scanner sc = human_resources_management.sc;
	static public Connection conn = human_resources_management.conn;
	
    public static void printCurrentRowData(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            Object columnValue = resultSet.getObject(i);

            System.out.println(columnName + ": " + columnValue);
        }
    }
    
    public static void printQueryResults(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        // 打印表头
        for (int i = 1; i <= columnCount; i++) {
            //System.out.print(metaData.getColumnName(i) + "\t");
        	System.out.format("%-20s", metaData.getColumnName(i));
        }
        System.out.println();

        // 打印分隔线
        for (int i = 1; i <= columnCount; i++) {
            System.out.print("--------------------");
        }
        System.out.println();

        // 打印数据行
        for (int i = 1; i <= columnCount; i++) {
            //System.out.print(resultSet.getString(i) + "\t");
        	System.out.format("%-20s", resultSet.getString(i));
        }
        System.out.println();
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                //System.out.print(resultSet.getString(i) + "\t");
            	System.out.format("%-20s", resultSet.getString(i));
            }
            System.out.println();
        }
    }
    
    public static void queryByName(String fullName) {
    	String[] names = fullName.split(" ");
    	if(names.length != 2) {
    		System.out.println("输入错误！");
    		return;
    	}
    	String sql = "SELECT staff_id FROM staffs WHERE first_name=\'"+names[0]+"\'AND last_name=\'"+names[1]+"\'";
    	try {
        	Statement stmt = conn.createStatement();      
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()) {
            	int staff_id = rs.getInt("staff_id");
            	Human worker = new Human(staff_id);
            	worker.query();
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void queryByName(String fullName, int section_id) {
    	String[] names = fullName.split(" ");
    	if(names.length != 2) {
    		System.out.println("输入错误！");
    		return;
    	}
    	String sql = "SELECT staff_id FROM staffs WHERE first_name=\'"+names[0]+"\'AND last_name=\'"+names[1]+"\'";
    	try {
        	Statement stmt = conn.createStatement();      
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()) {
            	int staff_id = rs.getInt("staff_id");
            	Human worker = new Human(staff_id);
            	if(worker.querySectionId() == section_id) {//是自己部门的人
            		worker.query();
            	}
            	else continue;
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void queryBySql(String sql) {
    	try {
        	Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
            	printQueryResults(rs);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateBySql(String sql) {
    	try {
        	Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            
            System.out.println("更新成功！");
            stmt.close();
        } catch (SQLException e) {
        	System.out.println("更新失败！");
            e.printStackTrace();
        }
    }
    
    public static boolean checkpassword(String correct) {
    	String password = sc.next();
    	int cnt = 0;
    	while(password.equals(correct) == false) {
    		System.out.println("登入失败，请重新输入！");
    		cnt++;
    		if(cnt == 5) {
    			System.out.println("连续5次错误，系统退出！");
    			return false;
    		}
    		password = sc.next();
    	}
    	System.out.println("登入成功!");
    	return true;
    }
}

