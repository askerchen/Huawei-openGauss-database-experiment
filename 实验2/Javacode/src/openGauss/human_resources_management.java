package openGauss;


import java.sql.*;
import java.util.Scanner;

public class human_resources_management {
	
	static String driver = "org.postgresql.Driver";
	// 服务器的ip和数据库的端口，需要根据自己设置
    static String sourceURL = "jdbc:postgresql://120.46.176.58:26000/human_resource";
  
    // 数据库的用户名与密码，需要根据自己的设置
    static final String username = "taoyongding";
    static final String passwd = "taoyongding@123";
    
    static Connection conn = null;
    static Scanner sc = new Scanner(System.in);
    
    static void menuWorker(int staff_id) {
    	System.out.println("您好！请输入您的password:");
    	Human worker = new Human(staff_id);
    	if(utils.checkpassword(worker.queryPassword())) {
    		while (true) {
        		System.out.println("请输入数字进入对应功能:\n"
        				+ "1 查看您的基本信息\n"
        				+ "2 修改您的电话号码\n"
        				+ "3 退出系统");
                int opt = sc.nextInt();

                if (opt == 3) {
                    System.out.println("感谢您的使用！");
                    break;
                }
                worker.choose(opt);
            }
    	}
    }
    
    static void menuManager(int staff_id) {
    	System.out.println("经理您好！请输入您的password:");
    	Manager manager = new Manager(staff_id);
    	if(utils.checkpassword(manager.queryPassword())) {
    		while (true) {
        		System.out.println("请输入数字进入对应功能:\n"
        				+ "1 查看本部门所有员工基本信息\n"
        				+ "2 按员工编号查询员工基本信息\n"
        				+ "3 按员工姓名查询员工基本信息\n"
        				+ "4 统计查询本部门员工最高工资，最低工资以及平均工资\n"
        				+ "5 退出系统");
                int opt = sc.nextInt();
                if (opt == 5) {
                    System.out.println("感谢您的使用！");
                    break;
                }
                manager.choose(opt);
            }
    	}
    }
    
    static void menuHrManager() {
    	System.out.println("人事经理您好！请输入您的password:");
    	if(utils.checkpassword("hr@001")) {
    		HrManager hrManager = new HrManager();
    		while (true) {
        		System.out.println("请输入数字进入对应功能:\n"
        				+ "1 查看所有员工基本信息（选择按员工编号升序排列，或者按工资降序排列）\n"
        				+ "2 按员工编号查询员工基本信息\n"
        				+ "3 按员工姓名查询员工基本信息\n"
        				+ "4 统计各部门员工最高工资，最低工资以及平均工资\n"
        				+ "5 查询各部门基本信息\n"
        				+ "6 根据部门编号修改部门名称\n"
        				+ "7 查询各工作地点基本信息\n"
        				+ "8 增加新的工作地点\n"
        				+ "9 按员工编号查询员工工作信息，包括其历史工作信息，返回员工编号，职位编号和部门编号\n"
        				+ "10 退出系统");
                int opt = sc.nextInt();
                if (opt == 10) {
                    System.out.println("感谢您的使用！");
                    break;
                }
                hrManager.choose(opt);
            }
    	}
    }

	public static void main(String[] args) {
        PreparedStatement stmt = null;
        try {
        	// 注册JDBC驱动
            Class.forName(driver).newInstance();

            // 打开链接
            conn = DriverManager.getConnection(sourceURL, username, passwd);
            System.out.println("连接数据库...");
            
            System.out.println("请输入您的staff_id:");
            //测试样例:员工 107 经理 100 hr hr001
            
            //Michael Hartstein
            String staff_id = sc.nextLine();
            if(staff_id.equals("hr001") == false) {
            	//用staff_id 查询对应的manager_id
            	int s_id = Integer.parseInt(staff_id);
            	String sql;
                sql = "SELECT * FROM staffs WHERE manager_id=?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, s_id);             
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {//能够查到，说明是某个部门的经理
                	menuManager(s_id);
                }
                else {//为普通员工
                	menuWorker(s_id);
                }
            }
            else {//为hr
            	menuHrManager();
            }
            sc.close();
        } catch (SQLException se) {
            // 处理JDBC异常
            se.printStackTrace();
        } catch (Exception e) {
            // 处理其他异常
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            	
            }// 什么都不做
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
}
