package chau.nguyen.calendar;

public class VNMDate {
	private int dayOfMonth;
	private int month;
	private int year;
	private int hourOfDay;
	private int minute;	
	
	public VNMDate() {		
	}
	public VNMDate(int dayOfMonth, int month, int year, int hourOfDay, int minute) {
		super();
		this.dayOfMonth = dayOfMonth;
		this.month = month;
		this.year = year;
		this.hourOfDay = hourOfDay;
		this.minute = minute;
	}
	public int getDayOfMonth() {
		return dayOfMonth;
	}
	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}
	/**
	 * 1 - 12
	 * @return
	 */
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getHourOfDay() {
		return hourOfDay;
	}
	public void setHourOfDay(int hourOfDay) {
		this.hourOfDay = hourOfDay;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	@Override
	public String toString() {
		return this.dayOfMonth + "/" + this.month + "/" + this.year;
	}
}
