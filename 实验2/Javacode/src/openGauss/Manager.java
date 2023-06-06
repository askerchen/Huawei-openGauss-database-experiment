package openGauss;

import java.util.Scanner;

public class Manager extends Human {
	// 部门经理类，实现以下功能:
	// 1 查看本部门所有员工基本信息（选择按员工编号升序排列，或者按工资降序排列）；
	// 2 按员工编号查询员工基本信息；
	// 3 按员工姓名查询员工基本信息；
	// 4 统计查询本部门员工最高工资，最低工资以及平均工资；
	
	Scanner sc = new Scanner(System.in);
	public Manager (int staff_id) {
		super(staff_id);
	}
	public void queryAll() {
		// 查看本部门所有员工基本信息
		String sql = null;
		
		System.out.println("您好！\n输入1，可按员工编号升序查询\n输入2，可按薪资降序查询");
		
		int opt = sc.nextInt();
		switch(opt) { 
			case 1:
				sql = "SELECT * FROM staffs WHERE section_id="+this.querySectionId()+" ORDER BY staff_id ASC";
			case 2:
				sql = "SELECT * FROM staffs WHERE section_id="+this.querySectionId()+" ORDER BY salary DESC";
			default:
				utils.queryBySql(sql);
				break;
		}
		
	}
	
	public void queryById() {
		// 按员工编号查询员工基本信息
		System.out.println("请输入所要查询的员工编号:");
		int id = sc.nextInt();
		Human worker = new Human(id);
		if(worker.querySectionId() != this.querySectionId()) {
			System.out.println("查询失败，请输入本部门员工的编号！\n");
		}
		else {
			worker.query();
			System.out.println("查询成功！\n");
		}
		
	}
	
	public void queryByName() {
		// 按员工姓名查询员工基本信息（可能重名，所以要考虑多个）
		System.out.println("请输入所要查询的员工姓名(例如Yongding Tao):");
		String name = sc.nextLine();
		utils.queryByName(name, this.querySectionId());
	}
	
	public void querySalary() {
		// 统计查询本部门员工最高工资，最低工资以及平均工资
		String sql = "SELECT MAX(salary) AS highest_salary,\n"
				+ "       MIN(salary) AS lowest_salary,\n"
				+ "       AVG(salary) AS average_salary\n"
				+ "FROM staffs\n"
				+ "WHERE section_id = " +this.querySectionId()+"\n";
		utils.queryBySql(sql);
	}
	
	@Override
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
		}
	}
}