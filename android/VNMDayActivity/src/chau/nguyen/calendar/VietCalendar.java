package chau.nguyen.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.util.Log;

/**
* @author Hồ Ngọc Đức
* 
*/
public class VietCalendar {
	public static final Locale vnLocale = new Locale("vi");
	private static final java.text.SimpleDateFormat vnDateFormat = new SimpleDateFormat("dd-MM-yyyy", vnLocale);;
    public static final double PI = Math.PI;
    public static final byte DAY = 0;
    public static final byte MONTH = 1;    
    public static final byte YEAR = 2;
    public static final byte LUNAR = 3;    
    public static final byte HOUR = 3;
    public static final byte TIET_KHI = 4;
    
    public static String[] CAN = new String[] {"Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ", "Canh", "Tân", "Nhâm", "Quý"};
    public static String[] CHI = new String[] {"Tý", "Sửu", "Dần", "Mẹo", "Thìn", "Tỵ", "Ngọ", "Mùi", "Thân", "Dậu", "Tuất", "Hợi"};
    public static String[] TIETKHI = new String[] {"Xuân phân", "Thanh minh", "C\u1ED1c v\u0169", "L\u1EADp h\u1EA1", "Ti\u1EC3u m\u00E3n", "Mang ch\u1EE7ng",
    		"H\u1EA1 ch\u00ED", "Ti\u1EC3u th\u1EED", "\u0110\u1EA1i th\u1EED", "L\u1EADp thu", "X\u1EED th\u1EED", "B\u1EA1ch l\u1ED9",
    		"Thu ph\u00E2n", "H\u00E0n l\u1ED9", "S\u01B0\u01A1ng gi\u00E1ng", "L\u1EADp \u0111\u00F4ng", "Ti\u1EC3u tuy\u1EBFt", "\u0110\u1EA1i tuy\u1EBFt",
    		"\u0110\u00F4ng ch\u00ED", "Ti\u1EC3u h\u00E0n", "\u0110\u1EA1i h\u00E0n", "L\u1EADp xu\u00E2n", "V\u0169 Th\u1EE7y", "Kinh tr\u1EADp"
    };    
    public static Holiday[] HOLIDAYS = null;
    
    private static String[] dayOfWeekInVietnamese = new String[] {"Chủ nhật", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy"};
	
    static {
    	Holiday h1 = new Holiday(1, 1, false, true, "Mồng một tết Âm lịch");
    	Holiday h2 = new Holiday(2, 1, false, true, "Mồng hai tết Âm lịch");
    	Holiday h3 = new Holiday(3, 1, false, true, "Mồng ba tết Âm lịch");
    	Holiday h4 = new Holiday(10, 3, false, true, "Giỗ tổ Hùng Vương");
    	Holiday h5 = new Holiday(1, 1, true, true, "Tết Dương lịch");
    	Holiday h6 = new Holiday(30, 4, true, true, "Ngày thống nhất đất nước");
    	Holiday h7 = new Holiday(1, 5, true, true, "Ngày Quốc tế lao động");
    	Holiday h8 = new Holiday(2, 9, true, true, "Ngày Quốc khánh");
    	Holiday h9 = new Holiday(14, 2, true, false, "Ngày lễ tình nhân");
    	Holiday h10 = new Holiday(8, 3, true, false, "Ngày quốc tế phụ nữ");
    	Holiday h11 = new Holiday(20, 10, true, false, "Ngày phụ nữ Việt Nam");
    	HOLIDAYS = new Holiday[] {h1, h2, h3, h4, h5, h6, h7, h8, h9, h10, h11};
    }
    
    /*
     * dd : 1 - 31
     * mm : 1 - 12
     * yy : 1800 -  
     */
    public static String[] getCanChiInfo(int lunarDay, int lunarMonth, int lunarYear, 
    		int solarDay, int solarMonth, int solarYear) {
		String[] results = new String[5];
		int jd = jdFromDate(solarDay, solarMonth, solarYear);
		results[YEAR] = CAN[(lunarYear + 6) % 10] + " " + CHI[(lunarYear + 8) % 12];
		results[MONTH] = CAN[(lunarYear * 12 + lunarMonth + 3) % 10] + " " + CHI[(lunarMonth + 1) % 12];
		results[DAY] = CAN[(jd + 9) % 10] + " " + CHI[(jd + 1) % 12];
		results[HOUR] = CAN[(jd - 1) * 2 % 10] + " " + CHI[0];
    	results[TIET_KHI] = "";//TIETKHI[getSolarTerm(jd + 1, 7.0)]; 
    	return results;
    }
    
    public static String formatVietnameseDate(Date date) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    	return VietCalendar.getDayOfWeekText(dayOfWeek) + ", " + vnDateFormat.format(cal.getTime());
    }
    
    public static String getDayOfWeekText(int dayOfWeek) {
    	return dayOfWeekInVietnamese[dayOfWeek - 1];
    }
    
    public static VNMDate addMonth(VNMDate date, int offset) {
    	VNMDate newDate = new VNMDate();
    	newDate.setDayOfMonth(date.getDayOfMonth());
    	int month = date.getMonth() + offset;
		newDate.setMonth(month % 12 > 0 ? month % 12 : 1);		
		newDate.setYear(date.getYear() + month / 12);
    	
    	newDate.setHourOfDay(date.getHourOfDay());
    	newDate.setMinute(date.getMinute());    	
    	return newDate;
    }
    
    public static VNMDate addYear(VNMDate date, int offset) {
    	VNMDate newDate = new VNMDate();
    	newDate.setDayOfMonth(date.getDayOfMonth());
    	newDate.setMonth(date.getMonth());
    	newDate.setYear(date.getYear() + offset);
    	newDate.setHourOfDay(date.getHourOfDay());
    	newDate.setMinute(date.getMinute());
    	return newDate;
    }
    
    /**
     *
     * @param dd
     * @param mm
     * @param yy
     * @return the number of days since 1 January 4713 BC (Julian calendar)
     */
    private static int jdFromDate(int dd, int mm, int yy) {
        int a = (14 - mm) / 12;
        int y = yy+4800-a;
        int m = mm+12*a-3;
        int jd = dd + (153*m+2)/5 + 365*y + y/4 - y/100 + y/400 - 32045;
        if (jd < 2299161) {
            jd = dd + (153*m+2)/5 + 365*y + y/4 - 32083;
        }
        //jd = jd - 1721425;
        return jd;
    }
    /**
     * http://www.tondering.dk/claus/calendar.html
     * Section: Is there a formula for calculating the Julian day number?
     * @param jd - the number of days since 1 January 4713 BC (Julian calendar)
     * @return
     */
    private static int[] jdToDate(int jd) {
        int a, b, c;
        if (jd > 2299160) { // After 5/10/1582, Gregorian calendar
            a = jd + 32044;
            b = (4*a+3)/146097;
            c = a - (b*146097)/4;
        } else {
            b = 0;
            c = jd + 32082;
        }
        int d = (4*c+3)/1461;
        int e = c - (1461*d)/4;
        int m = (5*e+2)/153;
        int day = e - (153*m+2)/5 + 1;
        int month = m + 3 - 12*(m/10);
        int year = b*100 + d - 4800 + m/10;
        return new int[]{day, month, year};
    }
    /**
     * Solar longitude in degrees
     * Algorithm from: Astronomical Algorithms, by Jean Meeus, 1998
     * @param jdn - number of days since noon UTC on 1 January 4713 BC
     * @return
     */
    private static double SunLongitude(double jdn) {
        //return CC2K.sunLongitude(jdn);
        return SunLongitudeAA98(jdn);
    }
    private static double SunLongitudeAA98(double jdn) {
        double T = (jdn - 2451545.0 ) / 36525; // Time in Julian centuries from 2000-01-01 12:00:00 GMT
        double T2 = T*T;
        double dr = PI/180; // degree to radian
        double M = 357.52910 + 35999.05030*T - 0.0001559*T2 - 0.00000048*T*T2; // mean anomaly, degree
        double L0 = 280.46645 + 36000.76983*T + 0.0003032*T2; // mean longitude, degree
        double DL = (1.914600 - 0.004817*T - 0.000014*T2)*Math.sin(dr*M);
        DL = DL + (0.019993 - 0.000101*T)*Math.sin(dr*2*M) + 0.000290*Math.sin(dr*3*M);
        double L = L0 + DL; // true longitude, degree
        L = L - 360*(INT(L/360)); // Normalize to (0, 360)
        return L;
    }
    public static double NewMoon(int k) {
        //return CC2K.newMoonTime(k);
        return NewMoonAA98(k);
    }
    
    /**
     * Julian day number of the kth new moon after (or before) the New Moon of 1900-01-01 13:51 GMT.
     * Accuracy: 2 minutes
     * Algorithm from: Astronomical Algorithms, by Jean Meeus, 1998
     * @param k
     * @return the Julian date number (number of days since noon UTC on 1 January 4713 BC) of the New Moon
     */
    public static double NewMoonAA98(int k) {
        double T = k/1236.85; // Time in Julian centuries from 1900 January 0.5
        double T2 = T * T;
        double T3 = T2 * T;
        double dr = PI/180;
        double Jd1 = 2415020.75933 + 29.53058868*k + 0.0001178*T2 - 0.000000155*T3;
        Jd1 = Jd1 + 0.00033*Math.sin((166.56 + 132.87*T - 0.009173*T2)*dr); // Mean new moon
        double M = 359.2242 + 29.10535608*k - 0.0000333*T2 - 0.00000347*T3; // Sun's mean anomaly
        double Mpr = 306.0253 + 385.81691806*k + 0.0107306*T2 + 0.00001236*T3; // Moon's mean anomaly
        double F = 21.2964 + 390.67050646*k - 0.0016528*T2 - 0.00000239*T3; // Moon's argument of latitude
        double C1=(0.1734 - 0.000393*T)*Math.sin(M*dr) + 0.0021*Math.sin(2*dr*M);
        C1 = C1 - 0.4068*Math.sin(Mpr*dr) + 0.0161*Math.sin(dr*2*Mpr);
        C1 = C1 - 0.0004*Math.sin(dr*3*Mpr);
        C1 = C1 + 0.0104*Math.sin(dr*2*F) - 0.0051*Math.sin(dr*(M+Mpr));
        C1 = C1 - 0.0074*Math.sin(dr*(M-Mpr)) + 0.0004*Math.sin(dr*(2*F+M));
        C1 = C1 - 0.0004*Math.sin(dr*(2*F-M)) - 0.0006*Math.sin(dr*(2*F+Mpr));
        C1 = C1 + 0.0010*Math.sin(dr*(2*F-Mpr)) + 0.0005*Math.sin(dr*(2*Mpr+M));
        double deltat;
        if (T < -11) {
            deltat= 0.001 + 0.000839*T + 0.0002261*T2 - 0.00000845*T3 - 0.000000081*T*T3;
        } else {
            deltat= -0.000278 + 0.000265*T + 0.000262*T2;
        };
        double JdNew = Jd1 + C1 - deltat;
        return JdNew;
    }
    private static int INT(double d) {
        return (int)Math.floor(d);
    }
    private static double getSunLongitude(int dayNumber, double timeZone) {
        return SunLongitude(dayNumber - 0.5 - timeZone/24);
    }
    private static int getNewMoonDay(int k, double timeZone) {
        double jd = NewMoon(k);
        return INT(jd + 0.5 + timeZone/24);
    }
    private static int getLunarMonth11(int yy, double timeZone) {
        double off = jdFromDate(31, 12, yy) - 2415021.076998695;
        int k = INT(off / 29.530588853);
        int nm = getNewMoonDay(k, timeZone);
        int sunLong = INT(getSunLongitude(nm, timeZone)/30);
        if (sunLong >= 9) {
            nm = getNewMoonDay(k-1, timeZone);
        }
        return nm;
    }
    private static int getLeapMonthOffset(int a11, double timeZone) {
        int k = INT(0.5 + (a11 - 2415021.076998695) / 29.530588853);
        int last; // Month 11 contains point of sun longutide 3*PI/2 (December solstice)
        int i = 1; // We start with the month following lunar month 11
        int arc = INT(getSunLongitude(getNewMoonDay(k+i, timeZone), timeZone)/30);
        do {
            last = arc;
            i++;
            arc = INT(getSunLongitude(getNewMoonDay(k+i, timeZone), timeZone)/30);
        } while (arc != last && i < 14);
        return i-1;
    }
    
    /**
     * Hàm chuyển đổi từ Dương Lịch sang Âm Lịch
     * @param dd : Ngày dương lịch 1 - 31
     * @param mm : Tháng dương lịch 1 - 12
     * @param yy : Năm dương lịch
     * @param timeZone : Múi giờ 0 - 11
     * @return array of [lunarDay, lunarMonth, lunarYear, leapOrNot]
     */
    public static int[] convertSolar2Lunar(int dd, int mm, int yy, double timeZone) {
        int lunarDay, lunarMonth, lunarYear, lunarLeap;
        int dayNumber = jdFromDate(dd, mm, yy);
        int k = INT((dayNumber - 2415021.076998695) / 29.530588853);
        int monthStart = getNewMoonDay(k+1, timeZone);
        if (monthStart > dayNumber) {
            monthStart = getNewMoonDay(k, timeZone);
        }
        int a11 = getLunarMonth11(yy, timeZone);
        int b11 = a11;
        if (a11 >= monthStart) {
            lunarYear = yy;
            a11 = getLunarMonth11(yy-1, timeZone);
        } else {
            lunarYear = yy+1;
            b11 = getLunarMonth11(yy+1, timeZone);
        }
        lunarDay = dayNumber-monthStart+1;
        int diff = INT((monthStart - a11)/29);
        lunarLeap = 0;
        lunarMonth = diff+11;
        if (b11 - a11 > 365) {
            int leapMonthDiff = getLeapMonthOffset(a11, timeZone);
            if (diff >= leapMonthDiff) {
                lunarMonth = diff + 10;
                if (diff == leapMonthDiff) {
                    lunarLeap = 1;
                }
            }
        }
        if (lunarMonth > 12) {
            lunarMonth = lunarMonth - 12;
        }
        if (lunarMonth >= 11 && diff < 4) {
            lunarYear -= 1;
        }
        return new int[]{lunarDay, lunarMonth, lunarYear, lunarLeap};
    }
    
    public static int[] convertSolar2LunarInVietnam(Date date) {
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		return convertSolar2LunarInVietnam(dayOfMonth, month, year);
    }
    
    public static int[] convertSolar2LunarInVietnam(int day, int month, int year) {    		
		return VietCalendar.convertSolar2Lunar(day, month, year, getVNMTimeZone());
    }
    
    public static VNMDate convertSolar2LunarInVietnamese(Date date) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	int[] temp = convertSolar2LunarInVietnam(date);
    	VNMDate result = new VNMDate();
    	result.setDayOfMonth(temp[DAY]);
    	result.setMonth(temp[MONTH]);
    	result.setYear(temp[YEAR]);
    	result.setHourOfDay(cal.get(Calendar.HOUR_OF_DAY));
    	result.setMinute(cal.get(Calendar.MINUTE));
    	return result;
    }
    
    public static VNMDate convertSolar2LunarInVietnamese(int minute, int hour, int dayOfMonth, int month, int year) {
    	int[] temp = convertSolar2LunarInVietnam(dayOfMonth, month + 1, year);;
    	VNMDate result = new VNMDate();
    	result.setDayOfMonth(temp[DAY]);
    	result.setMonth(temp[MONTH]);
    	result.setYear(temp[YEAR]);
    	result.setHourOfDay(hour);
    	result.setMinute(minute);
    	return result;
    }
    
    public static int compare(VNMDate date1, VNMDate date2) {
    	if (date1.getYear() > date2.getYear())
    		return 1;
    	else if (date1.getYear() < date2.getYear())
    		return -1;
    	else {
    		if (date1.getMonth() > date2.getMonth())
    			return 1;
    		else if (date1.getMonth() < date2.getMonth())
    			return -1;
    		else {
    			if (date1.getDayOfMonth() > date2.getDayOfMonth())
        			return 1;
        		else if (date1.getDayOfMonth() < date2.getDayOfMonth())
        			return -1;
        		else {
        			if (date1.getHourOfDay() > date2.getHourOfDay())
            			return 1;
            		else if (date1.getHourOfDay() < date2.getHourOfDay())
            			return -1;
            		else {
            			if (date1.getMinute() > date2.getMinute())
                			return 1;
                		else if (date1.getMinute() < date2.getMinute())
                			return -1;
                		else {
                			return 0;
                		}
            		}
        		}
    		}
    	}
    }
    
    private static double getVNMTimeZone() {
    	return +7;
    	//Calendar calendar = Calendar.getInstance();
		//TimeZone tz = calendar.getTimeZone();
		//return tz.getRawOffset() / 3600000;	
    }
    
    /**
     * Hàm chuyển đổi từ Âm Lịch sang Dương Lịch
     * @param lunarDay : ngày Âm Lịch 0 - 30
     * @param lunarMonth : tháng Âm Lịch 1 - 12
     * @param lunarYear : năm Âm Lịch
     * @param timeZone
     * @return
     */
    public static int[] convertLunar2Solar(int lunarDay, int lunarMonth, int lunarYear, double timeZone) {
    	// try to convert with lunarLeap = false
    	int[] solars = convertLunar2Solar(lunarDay, lunarMonth, lunarYear, false, timeZone);
    	// convert back to solar to check the result
    	int[] lunars = convertSolar2Lunar(solars[DAY], solars[MONTH], solars[YEAR], timeZone);
    	if (lunars[DAY] == lunarDay && lunars[MONTH] == lunarMonth && lunars[YEAR] == lunarYear) {
    		return solars;
    	} else return convertLunar2Solar(lunarDay, lunarMonth, lunarYear, true, timeZone);
    }
    
    /**
     * Hàm chuyển đổi từ Âm Lịch sang Dương Lịch
     * @param lunarDay ngày Âm Lịch 1-30
     * @param lunarMonth tháng Âm Lịch 1 - 12
     * @param lunarYear năm Âm Lịch
     * @return
     */
    public static int[] convertLunar2Solar(int lunarDay, int lunarMonth, int lunarYear) {
    	double timeZone = getVNMTimeZone();
    	return convertLunar2Solar(lunarDay, lunarMonth, lunarYear, timeZone);
    }
    
    public static Date convertLunar2Solar(VNMDate vnmDate) {
    	int[] temp = convertLunar2Solar(vnmDate.getDayOfMonth(), vnmDate.getMonth(), vnmDate.getYear());
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.DAY_OF_MONTH, temp[0]);
    	cal.set(Calendar.MONTH, temp[1] - 1);
    	cal.set(Calendar.YEAR, temp[2]);
    	cal.set(Calendar.HOUR_OF_DAY, vnmDate.getHourOfDay());
    	cal.set(Calendar.MINUTE, vnmDate.getMinute());
    	return cal.getTime();
    }
    
    public static VNMDate convertLuner2SolarInVietnamese(int minute, int hourOfDay, int dayOfMonth, int month, int year) {
    	int[] temp = convertLunar2Solar(dayOfMonth, month, year);
    	VNMDate date = new VNMDate();
    	date.setDayOfMonth(temp[DAY]);
    	date.setMonth(temp[MONTH] - 1);
    	date.setYear(temp[YEAR]);
    	date.setMinute(minute);
    	date.setHourOfDay(hourOfDay);
    	return date;
    }
    
    /**
     * Hàm chuyển đổi từ Âm Lịch sang Dương Lịch
     * @param lunarDay : ngày Âm Lịch 1 - 30
     * @param lunarMonth : tháng Âm Lịch 1 - 12
     * @param lunarYear : năm Âm Lịch
     * @param lunarLeap : năm nhuận? 0 - 1
     * @param timeZone
     * @return
     */
    public static int[] convertLunar2Solar(int lunarDay, int lunarMonth, int lunarYear, boolean lunarLeap, double timeZone) {
        int a11, b11;
        if (lunarMonth < 11) {
            a11 = getLunarMonth11(lunarYear-1, timeZone);
            b11 = getLunarMonth11(lunarYear, timeZone);
        } else {
            a11 = getLunarMonth11(lunarYear, timeZone);
            b11 = getLunarMonth11(lunarYear+1, timeZone);
        }
        int k = INT(0.5 + (a11 - 2415021.076998695) / 29.530588853);
        int off = lunarMonth - 11;
        if (off < 0) {
            off += 12;
        }
        if (b11 - a11 > 365) {
            int leapOff = getLeapMonthOffset(a11, timeZone);
            int leapMonth = leapOff - 2;
            if (leapMonth < 0) {
                leapMonth += 12;
            }
            if (lunarLeap && lunarMonth != leapMonth) {
                System.out.println("Invalid input!");
                return new int[] {0, 0, 0};
            } else if (lunarLeap || off >= leapOff) {
                off += 1;
            }
        }
        int monthStart = getNewMoonDay(k+off, timeZone);
        return jdToDate(monthStart + lunarDay - 1);
    }    
    
    protected static int getSolarTerm(int dd, double timeZone) {
    	return INT(SunLongitude(dd - 0.5 - timeZone/24.0) / PI * 12);    
    }
    
    public static void main(String args[]) {
    	System.out.println("Hello");
    }
    
    public static Holiday getHoliday(Date date) {
    	VNMDate vnmDate = VietCalendar.convertSolar2LunarInVietnamese(date);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	for (Holiday day : HOLIDAYS) {
    		if (day.isSolar()) {
    			if (day.getMonth() == (cal.get(Calendar.MONTH) + 1) && day.getDay() == cal.get(Calendar.DAY_OF_MONTH)) return day;
    		} else {
    			if (vnmDate.getMonth() == day.getMonth() && vnmDate.getDayOfMonth() == day.getDay()) return day;
    		}
			
		}
    	return null;
    }
    
    public static class Holiday {
    	private int day;
    	private int month;
    	private boolean solar;
    	private String description;
    	private boolean isOffDay = true;
    	private String[] backgroundImageUrls;
    	
		public Holiday(int day, int month, boolean solar, boolean isOffDay, String description) {
			super();
			this.day = day;
			this.month = month;
			this.solar = solar;
			this.isOffDay = isOffDay;
			this.description = description;
		}
		public int getDay() {
			return day;
		}
		public void setDay(int day) {
			this.day = day;
		}
		public int getMonth() {
			return month;
		}
		public void setMonth(int month) {
			this.month = month;
		}
		public boolean isSolar() {
			return solar;
		}
		public void setSolar(boolean solar) {
			this.solar = solar;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public boolean isOffDay() {
			return isOffDay;
		}
		public void setOffDay(boolean isOffDate) {
			this.isOffDay = isOffDate;
		}
		public String[] getBackgroundImageUrls() {
			return backgroundImageUrls;
		}
		public void setBackgroundImageUrls(String[] backgroundImageUrls) {
			this.backgroundImageUrls = backgroundImageUrls;
		}
    	
    }
}
