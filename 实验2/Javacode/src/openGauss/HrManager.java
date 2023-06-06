package openGauss;

import java.sql.*;
import java.util.Scanner;

public class HrManager{
	// 人事经理类，实现以下功能:
	// 1 查看所有员工基本信息（选择按员工编号升序排列，或者按工资降序排列）；
	// 2 按员工编号查询员工基本信息；
	// 3 按员工姓名查询员工基本信息；
	// 4 统计各部门员工最高工资，最低工资以及平均工资；
	// 5 查询各部门基本信息；
	// 6 根据部门编号修改部门名称；
	// 7 查询各工作地点基本信息；
	// 8 增加新的工作地点；
	// 9 按员工编号查询员工工作信息，包括其历史工作信息，返回员工编号，职位编号和部门编号；
	Scanner sc = null;
	Connection conn = null;
	
	public HrManager() {
		this.sc = human_resources_management.sc;
		this.conn = human_resources_management.conn;
	}
	public void queryAll() {
		// 查看所有员工基本信息
		String sql = null;
		
		System.out.println("您好！\n输入1，可按员工编号升序查询\n输入2，可按薪资降序查询");
		
		int opt = sc.nextInt();
		switch(opt) { 
			case 1:
				sql = "SELECT * FROM staffs ORDER BY staff_id ASC";
				break;
			case 2:
				sql = "SELECT * FROM staffs ORDER BY salary DESC";
				break;
		}
		utils.queryBySql(sql);
		
	}
	
	public void queryById() {
		// 按员工编号查询员工基本信息
		System.out.println("请输入所要查询的员工编号:");
		int id = sc.nextInt();
		Human worker = new Human(id);
		worker.query();
		System.out.println("查询成功！\n");
	}
	
	public void queryByName() {
		// 按员工姓名查询员工基本信息（可能重名，所以要考虑多个）
		System.out.println("请输入所要查询的员工姓名(例如Yongding Tao):");
		sc.nextLine();
		String name = sc.nextLine();
		utils.queryByName(name);
	}
	
	public void querySalary() {
		// 统计各部门员工最高工资，最低工资以及平均工资；
		String sql = "SELECT section_id,"
				+ "MAX(salary) AS highest_salary,\n"
				+ "MIN(salary) AS lowest_salary,\n"
				+ "AVG(salary) AS average_salary\n"
				+ "FROM staffs\n"
				+ "GROUP BY section_id\n"
				+ "ORDER BY section_id ASC";
		utils.queryBySql(sql);
	}
	
	public void querySection() {
		// 查询各部门基本信息；
		String sql = "SELECT * FROM sections";
		utils.queryBySql(sql);
		
	}
	
	public void updateSection() {
		// 根据部门编号修改部门名称；
		System.out.println("请输入部门编号:");
		int section_id = sc.nextInt();
		System.out.println("当前部门名称为:");
		String sql = "SELECT section_name FROM sections WHERE section_id = " + section_id;
		utils.queryBySql(sql);
		System.out.println("请输入新的部门名称:");
		sc.nextLine();
		String section_name = sc.nextLine();
		sql = "UPDATE sections SET section_name='"+section_name+"' WHERE section_id="+section_id;
		utils.updateBySql(sql);
	}
	
	public void queryPlace() {
		// 查询各工作地点基本信息；
		String sql = "SELECT * FROM places";
		utils.queryBySql(sql);
	}
	
	public void addNewPlace() {
		System.out.println("您想要插入一个新地点，请根据引导完成数据读入");
		System.out.println("请输入部门编号:");
		int place_id = sc.nextInt();
		System.out.println("请输入部门所在地街道地址:");
		sc.nextLine();
		String street_address = sc.nextLine();
		System.out.println("请输入部门所在地邮政编码:");
		String postal_code = sc.nextLine();
		System.out.println("请输入部门所在城市:");
		String city = sc.nextLine();
		System.out.println("请输入部门所在省:");
		String state_province = sc.nextLine();
		System.out.println("请输入国家简写（如CN）");
		String state_id = sc.next();
		String sql = "INSERT INTO places (place_id, street_address, postal_code, city, state_province, state_id)"
				+ String.format("VALUES (%d, '%s', '%s', '%s', '%s', '%s')", place_id, street_address, postal_code, city, state_province, state_id);
		utils.updateBySql(sql);
//		
//		VALUES (1001, '123 Main St', '12345', 'New York', 'NY', 'US');

	}
	
	public void queryHistory() {
		// 查询工作历史信息 //test 101
		System.out.println("请输入所要查询的员工编号:");
		sc.nextLine();
		int staff_id = sc.nextInt();
		String sql = "SELECT * FROM employment_history WHERE staff_id="+staff_id;
		utils.queryBySql(sql);
		System.out.println("查询成功！\n");
	}
	
	public void choose(int opt) {
		switch(opt) {
			case 1:
				queryAll();
				break;
			case 2:
				queryById();
				break;
			case 3:
				queryByName();
				break;
			case 4:
				querySalary();
				break;
			case 5:
				querySection();
				break;
			case 6:
				updateSection();
				break;
			case 7:
				queryPlace();
				break;
			case 8:
				addNewPlace();
				break;
			case 9:
				queryHistory();
				break;
		}
	}
}