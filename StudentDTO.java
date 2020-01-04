package dto;

public class StudentDTO {
	private int stu_id;
	private String name = null;
	private String dept = null;
	private int availCredit = 0;
	private int regiSeme = 0;
	
	public int getStu_id() {
		return stu_id;
	}
	public void setStu_id(int stu_id) {
		this.stu_id = stu_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public int getAvailCredit() {
		return availCredit;
	}
	public void setAvailCredit(int availCredit) {
		this.availCredit = availCredit;
	}
	public int getRegiSeme() {
		return regiSeme;
	}
	public void setRegiSeme(int regiSeme) {
		this.regiSeme = regiSeme;
	}

}
