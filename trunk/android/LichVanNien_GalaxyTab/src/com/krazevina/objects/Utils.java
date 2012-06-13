package com.krazevina.objects;
import java.util.Date;
import java.util.Vector;
import java.util.Calendar;
public class Utils {

    public static final String DATE_FORMAT = "MMM yyyy";
    public static final String FULL_DATE_FORMAT = "MMMMM, yyyy";
    public static final String DAY_IN_WEEK_FORMAT = "EEEEE";
    public String[][] datatemp;
    public int countcells=0;
    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }      
        return date.toString();
    }
 public static final double PI = Math.PI;
 
    public static int jdFromDate(int dd, int mm, int yy) {
        int a = (14 - mm) / 12;
        int y = yy + 4800 - a;
        int m = mm + 12 * a - 3;
        int jd = dd + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400 - 32045;
        if (jd < 2299161) {
            jd = dd + (153 * m + 2) / 5 + 365 * y + y / 4 - 32083;
        }
        // jd = jd - 1721425;
        return jd;
    }

    public static int[] jdToDate(int jd) {
        int a, b, c;
        if (jd > 2299160) { // After 5/10/1582, Gregorian calendar
            a = jd + 32044;
            b = (4 * a + 3) / 146097;
            c = a - (b * 146097) / 4;
        } else {
            b = 0;
            c = jd + 32082;
        }
        int d = (4 * c + 3) / 1461;
        int e = c - (1461 * d) / 4;
        int m = (5 * e + 2) / 153;
        int day = e - (153 * m + 2) / 5 + 1;
        int month = m + 3 - 12 * (m / 10);
        int year = b * 100 + d - 4800 + m / 10;
        return new int[]{day, month, year};
    }
    public static double SunLongitude(double jdn) {
                return SunLongitudeAA98(jdn);
    }

    public static double SunLongitudeAA98(double jdn) {
        double T = (jdn - 2451545.0) / 36525; // Time in Julian centuries from
        // 2000-01-01 12:00:00 GMT
        double T2 = T * T;
        double dr = PI / 180; // degree to radian
        double M = 357.52910 + 35999.05030 * T - 0.0001559 * T2 - 0.00000048 * T * T2; // mean anomaly, degree
        double L0 = 280.46645 + 36000.76983 * T + 0.0003032 * T2; // mean
       
        double DL = (1.914600 - 0.004817 * T - 0.000014 * T2) * Math.sin(dr * M);
        DL = DL + (0.019993 - 0.000101 * T) * Math.sin(dr * 2 * M) + 0.000290 * Math.sin(dr * 3 * M);
        double L = L0 + DL; // true longitude, degree
        L = L - 360 * (INT(L / 360)); // Normalize to (0, 360)
        return L;
    }

    public static double NewMoon(int k) {
        return NewMoonAA98(k);
    }
    public static double NewMoonAA98(int k) {
        double T = k / 1236.85;
        // 0.5
        double T2 = T * T;
        double T3 = T2 * T;
        double dr = PI / 180;
        double Jd1 = 2415020.75933 + 29.53058868 * k + 0.0001178 * T2 - 0.000000155 * T3;
        Jd1 = Jd1 + 0.00033 * Math.sin((166.56 + 132.87 * T - 0.009173 * T2) * dr); // Mean
       
        double M = 359.2242 + 29.10535608 * k - 0.0000333 * T2 - 0.00000347 * T3; // Sun's mean anomaly
        double Mpr = 306.0253 + 385.81691806 * k + 0.0107306 * T2 + 0.00001236 * T3; // Moon's mean anomaly
        double F = 21.2964 + 390.67050646 * k - 0.0016528 * T2 - 0.00000239 * T3; // Moon's argument of latitude
        double C1 = (0.1734 - 0.000393 * T) * Math.sin(M * dr) + 0.0021 * Math.sin(2 * dr * M);
        C1 = C1 - 0.4068 * Math.sin(Mpr * dr) + 0.0161 * Math.sin(dr * 2 * Mpr);
        C1 = C1 - 0.0004 * Math.sin(dr * 3 * Mpr);
        C1 = C1 + 0.0104 * Math.sin(dr * 2 * F) - 0.0051 * Math.sin(dr * (M + Mpr));
        C1 = C1 - 0.0074 * Math.sin(dr * (M - Mpr)) + 0.0004 * Math.sin(dr * (2 * F + M));
        C1 = C1 - 0.0004 * Math.sin(dr * (2 * F - M)) - 0.0006 * Math.sin(dr * (2 * F + Mpr));
        C1 = C1 + 0.0010 * Math.sin(dr * (2 * F - Mpr)) + 0.0005 * Math.sin(dr * (2 * Mpr + M));
        double deltat;
        if (T < -11) {
            deltat = 0.001 + 0.000839 * T + 0.0002261 * T2 - 0.00000845 * T3 - 0.000000081 * T * T3;
        } else {
            deltat = -0.000278 + 0.000265 * T + 0.000262 * T2;
        }

        double JdNew = Jd1 + C1 - deltat;
        return JdNew;
    }

    public static int INT(double d) {
        return (int) Math.floor(d);
    }

    public static double getSunLongitude(int dayNumber, double timeZone) {
        return SunLongitude(dayNumber - 0.5 - timeZone / 24);
    }

    public static int getNewMoonDay(int k, double timeZone) {
        double jd = NewMoon(k);
        return INT(jd + 0.5 + timeZone / 24);
    }
    public static int getLunarMonth11(int yy, double timeZone) {
        double off = jdFromDate(31, 12, yy) - 2415021.076998695;
        int k = INT(off / 29.530588853);
        int nm = getNewMoonDay(k, timeZone);
        int sunLong = INT(getSunLongitude(nm, timeZone) / 30);
        if (sunLong >= 9) {
            nm = getNewMoonDay(k - 1, timeZone);
        }
        return nm;
    }

    public static int getLeapMonthOffset(int a11, double timeZone) {
        int k = INT(0.5 + (a11 - 2415021.076998695) / 29.530588853);
        int last;
        int i = 1;
        int arc = INT(getSunLongitude(getNewMoonDay(k + i, timeZone), timeZone) / 30);
        do {
            last = arc;
            i++;
            arc = INT(getSunLongitude(getNewMoonDay(k + i, timeZone), timeZone) / 30);
        } while (arc != last && i < 14);
        return i - 1;
    }

    public static int[] convertSolar2Lunar(int dd, int mm, int yy,
            double timeZone) {
        int lunarDay, lunarMonth, lunarYear, lunarLeap;
        int dayNumber = jdFromDate(dd, mm, yy);
        int k = INT((dayNumber - 2415021.076998695) / 29.530588853);
        int monthStart = getNewMoonDay(k + 1, timeZone);
        if (monthStart > dayNumber) {
            monthStart = getNewMoonDay(k, timeZone);
        }
        int a11 = getLunarMonth11(yy, timeZone);
        int b11 = a11;
        if (a11 >= monthStart) {
            lunarYear = yy;
            a11 = getLunarMonth11(yy - 1, timeZone);
        } else {
            lunarYear = yy + 1;
            b11 = getLunarMonth11(yy + 1, timeZone);
        }
        lunarDay = dayNumber - monthStart + 1;
        int diff = INT((monthStart - a11) / 29);
        lunarLeap = 0;
        lunarMonth = diff + 11;
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


    public static int[] convertLunar2Solar(int lunarDay, int lunarMonth,
            int lunarYear, int lunarLeap, double timeZone) {
        int a11, b11;
        if (lunarMonth < 11) {
            a11 = getLunarMonth11(lunarYear - 1, timeZone);
            b11 = getLunarMonth11(lunarYear, timeZone);
        } else {
            a11 = getLunarMonth11(lunarYear, timeZone);
            b11 = getLunarMonth11(lunarYear + 1, timeZone);
        }
        int k = INT(0.5 + (a11 - 2415021.076998695) / 29.530588853);
        int off = lunarMonth - 11;
        if (off < 0) {
            off += 12;
        }
        if (b11 - a11 > 365) 
        {
            int leapOff = getLeapMonthOffset(a11, timeZone);
            int leapMonth = leapOff - 2;
            if (leapMonth < 0) {
                leapMonth += 12;
            }
            if (lunarLeap != 0 && lunarMonth != leapMonth) {                
                return new int[]{0, 0, 0};
                
            } else if (lunarLeap != 0 || off >= leapOff) {
                off += 1;
            }
        }
        
        int monthStart = getNewMoonDay(k + off, timeZone);
        return jdToDate(monthStart + lunarDay - 1);
    }
    static YearlyEvent[] YEARLY_EVENTS = {
    	new YearlyEvent(1, 1, "Mồng Một Tết Nguyên Đán"),
    	new YearlyEvent(2, 1, "Mồng Hai Tết Nguyên Đán"),
        new YearlyEvent(3, 1, "Mồng Ba Tết Nguyên Đán"),
        new YearlyEvent(15, 1, "Lễ Thượng Nguyên (Rằm tháng Giêng)"),
        new YearlyEvent(23, 2, "Tiết Thanh Minh"),
        new YearlyEvent(10, 3, "Giỗ Tổ Hùng Vương"),
        new YearlyEvent(15, 4, "Phật Đản"),
        new YearlyEvent(5, 5, "Lễ Đoan Ngọ"),
        new YearlyEvent(15, 7, "Lễ Trung Nguyên (Vu Lan)"),
        new YearlyEvent(15, 8, "Têt Trung Thu (Rằm tháng 8)"),
        new YearlyEvent(10, 10, "Lễ Thương Tân"),
        new YearlyEvent(15, 10, "Lễ Hạ Nguyên"),
        new YearlyEvent(23, 12, "Ông Táo Chầu Trời")
    };
    
    static YearlyEvent[] PUBLIC_LUNAR_HOLIDAYS = {
    	new YearlyEvent(1, 1, "Mồng Một Tết Nguyên Đán"),
    	new YearlyEvent(2, 1, "Mồng Hai Tết Nguyên Đán"),
        new YearlyEvent(3, 1, "Mồng Ba Tết Nguyên Đán"),
        new YearlyEvent(10, 3, "Giỗ Tổ Hùng Vương"),
    };
    
    static YearlyEvent[] PUBLIC_SOLAR_HOLIDAYS = {
    	new YearlyEvent(1, 1, "Tết Dương Lịch"),
    	new YearlyEvent(30, 4, "Ngày giải phóng Miền Nam"),
        new YearlyEvent(1, 5, "Ngày Quốc Tế Lao Động"),
        new YearlyEvent(2, 9, "Ngày Quốc Khánh"),
        
    };

    static YearlyEvent[] SPECIAL_YEARLY_EVENTS = {
        new YearlyEvent(1, 1, "Tết Dương lịch"),
        new YearlyEvent(3, 2, "Ngày thành lập Đảng Cộng Sản Việt Nam"),
        new YearlyEvent(14, 2, "Ngày lễ Valentine"),
        new YearlyEvent(27, 2, "Ngày Thầy thuốc Việt Nam"),
        new YearlyEvent(8, 3, "Ngày Quốc tế Phụ nữ"),
        new YearlyEvent(26, 3, "Ngày thành lập Đoàn TNCSHCM"),
        new YearlyEvent(30, 4, "Ngày miền Nam hoàn toàn Giải Phóng"),
        new YearlyEvent(1, 5, "Ngày Quốc tế Lao Động"),
        new YearlyEvent(6, 5, "Ngày Quốc tế môi trường"),
        new YearlyEvent(7, 5, "Ngày chiến thắng lịch sử Điện Biên Phủ"),
        new YearlyEvent(9, 5, "Ngày của mẹ"),
        new YearlyEvent(1, 6, "Ngày Quốc tế Thiếu nhi"),
        new YearlyEvent(20, 6, "Ngày của cha"),
        new YearlyEvent(21, 6, "Ngày Báo chí Việt Nam"),
        new YearlyEvent(28, 6, "Ngày Gia đình Việt Nam"),
        new YearlyEvent(27, 7, "Ngày Thương binh Liệt sỹ"),
        new YearlyEvent(28, 7, "Ngày thành lập Công đoàn Việt Nam"),
        new YearlyEvent(19, 8, "Ngày CMT8 thành công"),
        new YearlyEvent(2, 9, "Ngày Quốc Khánh"),
        new YearlyEvent(10, 9, "Ngày Thành lập Mặt Trận Tổ Quốc Việt Nam"),
        new YearlyEvent(23, 9, "Ngày Nam Bộ kháng chiến"),
        new YearlyEvent(1, 10, "Ngày Quốc tế người cao tuổi"),
        new YearlyEvent(14, 10, "Ngày thành lập hội Nông dân Việt Nam"),
        new YearlyEvent(20, 10, "Ngày Phụ nữ Việt Nam"),
        new YearlyEvent(20, 11, "Ngày Nhà giáo Việt Nam"),
        //new YearlyEvent(22, 10, "Ngày Sinh nhật Bé yêu"),
        new YearlyEvent(22, 12, "Ngày thành lập QĐND Việt Nam"),
        new YearlyEvent(25, 12, "Lễ Giáng Sinh")
    };

    public static class YearlyEvent {

        private int day,  month;
        private String info;

        public YearlyEvent(int dd, int mm, String info) {
            this.day = dd;
            this.month = mm;
            this.info = info;
        }
        

        public int getDay() {
            return day;
        }

        public int getMonth() {
            return month;
        }

        public String getInfo() {
            return info;
        }
    }

    public static String getDayYearlyEvent(int dd, int mm) {
        Vector events = findYearlyEvents(dd, mm);
        String ret = "";
        for (int i = 0; i < events.size(); i++) {
            ret += ((YearlyEvent) events.elementAt(i)).getInfo();
        }
        return ret;
    }
    public static String getDaySpecialYearlyEvent(int dd, int mm) {
        Vector events = findSpecialYearlyEvents(dd, mm);
        String ret = "";
        for (int i = 0; i < events.size(); i++) {
         //   ret += ((YearlyEvent) events.elementAt(i)).getInfo() + "\r\n";
        	   ret += ((YearlyEvent) events.elementAt(i)).getInfo();
        }
        return ret;
    }

    public static Vector findSpecialYearlyEvents(int dd, int mm) {
        Vector ret = new Vector();
        for (int i = 0; i < SPECIAL_YEARLY_EVENTS.length; i++) {
            YearlyEvent evt = SPECIAL_YEARLY_EVENTS[i];
            if (evt.day == dd && evt.month == mm) {
                ret.addElement(evt);
            }
        }
        return ret;
    }

    public static Vector findYearlyEvents(int dd, int mm) {
        Vector ret = new Vector();
        for (int i = 0; i < YEARLY_EVENTS.length; i++) {
            YearlyEvent evt = YEARLY_EVENTS[i];
            if (evt.day == dd && evt.month == mm) {
                ret.addElement(evt);
            }
        }
        return ret;
    }

    public static boolean hasYearlyEvents(int dd, int mm) {
        for (int i = 0; i < YEARLY_EVENTS.length; i++) {
            YearlyEvent evt = YEARLY_EVENTS[i];
            if (evt.day == dd && evt.month == mm) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasSpecialYearlyEvents(int dd, int mm) {
        for (int i = 0; i < SPECIAL_YEARLY_EVENTS.length; i++) {
            YearlyEvent evt = SPECIAL_YEARLY_EVENTS[i];
            if (evt.day == dd && evt.month == mm) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean hasPublicSolarHolidays(int dd, int mm){
    	for(int i = 0;i<PUBLIC_SOLAR_HOLIDAYS.length;i++){
    		YearlyEvent evt = PUBLIC_SOLAR_HOLIDAYS[i];
    		if(evt.day == dd && evt.month == mm){
    			return true;
    		}
    	}
    	return false;
    }
    
    public static boolean hasPublicLunarHolidays(int dd, int mm){
    	for(int i = 0;i<PUBLIC_LUNAR_HOLIDAYS.length;i++){
    		YearlyEvent evt = PUBLIC_LUNAR_HOLIDAYS[i];
    		if(evt.day == dd && evt.month == mm){
    			return true;
    		}
    	}
    	return false;
    }

    //public static final String[] TUAN = {"Chủ nhật", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy"};
    public static final String[] THANG = {"Giêng", "Hai", "Ba", "Tư", "Năm",
        "Sáu", "Bảy", "Tám", "Chín", "Mười", "Mười một", "Chạp"};
    public static final String[] CAN = {"Gi\341p", "\u1EA4t", "B\355nh", "\u0110inh", "M\u1EADu", "K\u1EF7", "Canh", "T\342n", "Nh\342m", "Qu\375"};
    public static final String[] CHI = {"T\375", "S\u1EEDu", "D\u1EA7n", "M\343o", "Th\354n", "T\u1EF5", "Ng\u1ECD", "M\371i", "Th\342n", "D\u1EADu", "Tu\u1EA5t", "H\u1EE3i"};
    public static final String[] GIO_HD = {"110100101100", "001101001011",
        "110011010010", "101100110100", "001011001101", "010010110011"};
    public static final String[] TIETKHI = {"Xu\u00E2n ph\u00E2n", "Thanh minh", "C\u1ED1c v\u0169", "L\u1EADp h\u1EA1", "Ti\u1EC3u m\u00E3n", "Mang ch\u1EE7ng",
        "H\u1EA1 ch\u00ED", "Ti\u1EC3u th\u1EED", "\u0110\u1EA1i th\u1EED", "L\u1EADp thu", "X\u1EED th\u1EED", "B\u1EA1ch l\u1ED9",
        "Thu ph\u00E2n", "H\u00E0n l\u1ED9", "S\u01B0\u01A1ng gi\u00E1ng", "L\u1EADp \u0111\u00F4ng", "Ti\u1EC3u tuy\u1EBFt", "\u0110\u1EA1i tuy\u1EBFt",
        "\u0110\u00F4ng ch\u00ED", "Ti\u1EC3u h\u00E0n", "\u0110\u1EA1i h\u00E0n", "L\u1EADp xu\u00E2n", "V\u0169 Th\u1EE7y", "Kinh tr\u1EADp"};

    public static String getLunarMonthName(int lunarMonth) {
        return THANG[lunarMonth];
    }

    public static String getLunarYearName(int lunarYear) {
        return CAN[(lunarYear + 6) % 10] + " " + CHI[(lunarYear + 8) % 12];
    }

    public static String getLunarMonthCanChiName(int lunarYear, int lunarMonth) {
        return CAN[(lunarYear * 12 + lunarMonth + 3) % 10] + " " + CHI[(lunarMonth + 1) % 12];
    }

    public static String getLunarDayCanChiName(int jd) {
        return CAN[(jd + 9) % 10] + " " + CHI[(jd + 1) % 12];
    }

    public static String getTietKhiName(int jd) {
        return "Tiết " + TIETKHI[getSolarTerm(jd + 1, 7)];
    }

    public static String getZodiacTime(int jd) {
        int chiOfDay = (jd + 1) % 12;
        String gioHD = GIO_HD[chiOfDay % 6]; // same values for Ty' (1) and Ngo. (6), for Suu and Mui etc.

        //String result = "Giờ hoàng đạo: ";
        String result = "";
        int count = 0;
        for (int i = 0; i < 12; i++) {
            if (gioHD.charAt(i) == '1') {
                result += CHI[i];
                result += " (" + (i * 2 + 23) % 24 + "-" + (i * 2 + 1) % 24 + ")";
                if (count++ < 5) {
                    result += ", ";
                }
//                              if (count == 3) result += "\n";
            }
        }
        return result;
    }


    /*
     * Can cua gio Chinh Ty (00:00) cua ngay voi JDN nay
     */
    public static String getHourCan(int jdn) {
        return "Giờ " + CAN[(jdn - 1) * 2 % 10] + " " + CHI[0];
    }

    /*
     * Compute the longitude of the sun at any time. Parameter: floating number
     * jdn, the number of days since 1/1/4713 BC noon Algorithm from:
     * "Astronomical Algorithms" by Jean Meeus, 1998
     */
    public static double SunLongitudeAstronomical(double jdn) {
        double T, T2, dr, M, L0, DL, lambda, theta, omega;

        // Time in Julian centuries from 2000-01-01 12:00:00 GMT
        T = (jdn - 2451545.0) / 36525;
        T2 = T * T;

        // degree to radian
        dr = PI / 180;
        M = 357.52910 + 35999.05030 * T - 0.0001559 * T2 - 0.00000048 * T * T2; // mean
        // anomaly,
        // degree
        L0 = 280.46645 + 36000.76983 * T + 0.0003032 * T2; // mean longitude,
        // degree
        DL = (1.914600 - 0.004817 * T - 0.000014 * T2) * Math.sin(dr * M);
        DL = DL + (0.019993 - 0.000101 * T) * Math.sin(dr * 2 * M) + 0.000290 * Math.sin(dr * 3 * M);
        theta = L0 + DL; // true longitude, degree
        // obtain apparent longitude by correcting for nutation and aberration
        omega = 125.04 - 1934.136 * T;
        lambda = theta - 0.00569 - 0.00478 * Math.sin(omega * dr);
        // Convert to radians
        lambda = lambda * dr;
        lambda = lambda - PI * 2 * (INT(lambda / (PI * 2))); // Normalize to
        // (0, 2*PI)
        return lambda;
    }    
    public static int getSolarTerm(int dayNumber, int timeZone) {
        int ret = INT(SunLongitudeAstronomical(dayNumber - 0.5 - timeZone / 24.0) / PI * 12);
        return ret;
    }
    public static int NUMBER_COLUMN = 7;
    public static int NUMBER_ROW = 6;    
    public static String EMPTY = "0";
    public static int TIME_ZONE = 7;

    public String[][] getSolarMonth(int year, int month) 
    {   
    	
        String[][] data = new String[7][NUMBER_COLUMN];
        
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < NUMBER_COLUMN; j++) {
                data[i][j] = EMPTY;
            }
        }
        Calendar calendar = Calendar.getInstance();

        int numberDayOfMonth = getDayOfMonth(year, month);
        countcells=numberDayOfMonth;

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        int numberDayofPrevMonth;

        int beginWeek = calendar.get(Calendar.DAY_OF_WEEK);        
        int count = 0;
        int num = 0;
        int nextnum=0;        
        if(beginWeek==1)
        {
        	data[6][0]="0";
        }
        for (int i = 0; i < NUMBER_ROW; i++) 
        {
            for (int j = 0; j < NUMBER_COLUMN; j++)
            {
            	count++;  
            	
                if (count >= beginWeek && num < numberDayOfMonth)// + beginWeek - 1)
                {
                	
                    num++;                    
                    //data[i][j] = String.valueOf(count - beginWeek + 1);
                    EMPTY = String.valueOf(num);                    
                    data[i][j]=EMPTY;
                    data[6][1]=Integer.toString(count);
                    data[6][2]=String.valueOf(month);
                }
                else if(count<beginWeek)
                { 
                	countcells++;
                	if(month>1)
                	{
                		numberDayofPrevMonth = getDayOfMonth(year, month-1);
                		
                	}
                	else
                	{
                		numberDayofPrevMonth = getDayOfMonth(year-1, 12);
                		data[6][2]=String.valueOf(month);// luu thang 1
                		
                	}                	
                	 EMPTY = String.valueOf(numberDayofPrevMonth-beginWeek+count+1);                    
                     data[i][j]=EMPTY;
                     data[6][0]=Integer.toString(count);
                }
                else
                {                	
                	if(month<12)
                	{
                		numberDayofPrevMonth= getDayOfMonth(year, month+1);
                		
                	}
                	else
                	{
                		numberDayofPrevMonth= getDayOfMonth(year+1, 1);
                		data[6][2]=String.valueOf(month);// luu thang 12
                	}
                	nextnum++;
                	EMPTY = String.valueOf(nextnum);                    
                    data[i][j]=EMPTY;
                }
                	
            }
        }
        if(countcells>35)// neu nhieu hon 35 cells thi index1=councells
        	data[6][1]=String.valueOf(countcells);
        return data;
    }    
    public String[][] getLunarMonth(int year, int month)
    {    	
    	datatemp = new String[NUMBER_ROW][NUMBER_COLUMN];
        String[][] data = getSolarMonth(year, month);
        
        int index0,index1;
        index0=Integer.valueOf(data[6][0]);
        index1=Integer.valueOf(data[6][1]);
        
        int[] dmy = new int[4];
        int count = 0;  
        for(int i = 0; i < NUMBER_ROW; i++) {
            for(int j = 0; j < NUMBER_COLUMN; j++)
            {
            	count++;
            	int day = Integer.parseInt(data[i][j]);
            	dmy = convertSolar2Lunar(day, month, year, TIME_ZONE);
            	if(count<=index0 && month==1)
                	dmy = convertSolar2Lunar(day, 12, year-1, TIME_ZONE);                    
                else if(count<=index0 && month!=1)                    
                    dmy = convertSolar2Lunar(day, month-1, year, TIME_ZONE);
                
                if(count >index0 && count<=index1)
                         	dmy = convertSolar2Lunar(day, month, year, TIME_ZONE);
                
                if(count>index1 && month!=12)
                            dmy = convertSolar2Lunar(day, month+1, year, TIME_ZONE);
                else if(count>index1 && month==12)
                          	dmy = convertSolar2Lunar(day, 1, year+1, TIME_ZONE);
            
                data[i][j] = dmy[0] + "/" + dmy[1];
                datatemp[i][j]=dmy[0] + "/" + dmy[1]+"/"+dmy[2];
                datatemp[i][j]=dmy[0] + "/" + dmy[1]+"/"+dmy[2];                  
                
            }
            	
          }
        return data;
    }    
    public static int getDayOfMonth(int year, int month) {
        int num = 0;
        boolean leapYear = false;
        if ((year % 100) % 4 == 0) {
            leapYear = true;
        }

        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            num = 31;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            num = 30;
        } else if (leapYear) {
            num = 29;
        } else {
            num = 28;
        }
        return num;
    }
    public static boolean IsLeapMonth(int solarMonth, int solarYear, int TimeZone )
    {
    	int[] lunar= convertSolar2Lunar(1, solarMonth, solarYear, TimeZone);
    	if(lunar[3]!=0)
    		return true;
    	else 
    		return false;
    	 
    }
    public static int getLeapMonth(int lunarYear, int TimeZone){
		int a11 = getLunarMonth11(lunarYear-1, TimeZone);
        int leapOff = getLeapMonthOffset(a11, TimeZone);
        int leapMonth = leapOff - 2; 
        if(leapOff==13)
        	return -1;
        else
        {
            if (leapMonth < 0)           
                leapMonth += 12;
        }            
       return leapMonth;
    }
}