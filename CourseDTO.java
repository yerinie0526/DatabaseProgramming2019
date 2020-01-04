package dto;

public class CourseDTO {
	String courseId;
	String courseName;
	int classNum;
	int credit;
	int enroll;
	String classroom1;
	String classroom2;
	String dayWeek1;
	int time1;
	String dayWeek2;
	int time2;
	String pName;
	int total = 0;
	int order;

	
	public int getEnroll() {
		return enroll;
	}

	public void setEnroll(int enroll) {
		this.enroll = enroll;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getClassNum() {
		return classNum;
	}

	public void setClassNum(int classNum) {
		this.classNum = classNum;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public String getClassroom1() {
		return classroom1;
	}

	public void setClassroom1(String classroom1) {
		this.classroom1 = classroom1;
	}

	public String getClassroom2() {
		return classroom2;
	}

	public void setClassroom2(String classroom2) {
		this.classroom2 = classroom2;
	}

	public String getDayWeek1() {
		return dayWeek1;
	}

	public void setDayWeek1(String dayWeek1) {
		this.dayWeek1 = dayWeek1;
	}

	public int getTime1() {
		return time1;
	}

	public void setTime1(int time1) {
		this.time1 = time1;
	}

	public String getDayWeek2() {
		return dayWeek2;
	}

	public void setDayWeek2(String dayWeek2) {
		this.dayWeek2 = dayWeek2;
	}

	public int getTime2() {
		return time2;
	}

	public void setTime2(int time2) {
		this.time2 = time2;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

}