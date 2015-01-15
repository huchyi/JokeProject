package com.android.joke.jokeproject.common;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils {

    /**
     * 判断字符创的内容是否为空
     *
     * @param str
     * @return true,字符串为null 或 ""
     */
    public static boolean isEmpty(String str) {
        if (null == str || "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 秒数转为 mm:ss的格式
     *
     * @param second 秒
     * @return mm:ss
     */
    public static String formatss2mmColomss(int second) {
        if (second > 3600) return "00:00";
        return String.format("%02d", second / 60) + ":" + String.format("%02d", second % 60);
    }

    public static String cleanNull(String src) {
        if (StringUtils.isEmpty(src) || "null".equals(src.toLowerCase().trim())) {
            return "";
        }
        return src;
    }

    public static boolean isNullOrNullStr(String str) {
        if (null == str || "".equals(str.trim()) || "null".equals(str.toLowerCase().trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str 需要判断的字符串
     * @return true:是字符串；false:不是字符串
     */
    public static boolean isNumeric(String str) {
        if (isEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否为正整数或正小数
     *
     * @param str 需要判断的字符串
     * @return true:是字符串；false:不是字符串
     */
    public static boolean isNumOrFloat(String str) {
        Pattern pattern = Pattern.compile("^\\d+$|^\\d+\\.\\d+$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }



    public static String getTimeErgonHalfhour() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        boolean flag = minute / 30 == 0;
        String eventTimeLabel = "";
        if (flag) {
            eventTimeLabel = hour + ":00" + "~" + hour + ":30";
        } else {
            eventTimeLabel = hour + ":30" + "~" + (hour + 1) + ":00";
        }
        return eventTimeLabel;
    }

    public static char bin2Char(String binContent) {
        return (char) (Integer.parseInt((new BigInteger(binContent, 2).toString(10))));
    }

    public static String rightPad(String s, int length, char c) {
        StringBuffer sb = new StringBuffer(s);
        for (int i = length - s.length(); i > 0; i--)
            sb.append(c);
        return sb.toString();
    }

    public static String leftPad(String s, int length, char c) {
        StringBuffer sb = new StringBuffer();
        for (int i = length - s.length(); i > 0; i--)
            sb.append(c);
        sb.append(s);
        return sb.toString();
    }


    /** 得到当前的时间 */
    public static String getNowData(){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int sc = c.get(Calendar.SECOND);
        String hourStr;
        String minuteStr;
        String scStr;
        if(hour<10){
            hourStr = "0"+hour;
        }else{
            hourStr = String.valueOf(hour);
        }
        if(minute<10){
            minuteStr = "0"+minute;
        }else{
            minuteStr = String.valueOf(minute);
        }
        if(sc<10){
            scStr = "0"+sc;
        }else{
            scStr = String.valueOf(sc);
        }
        return  year+"/"+month+"/"+day+"  "+hourStr+":"+minuteStr+":"+scStr;
    }

}
