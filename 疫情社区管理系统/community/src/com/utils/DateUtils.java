package com.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName : DateUtils  //类名
 * @Description : 时间日期工具类  //描述
 * @Date: 2022/12/13 0013  22:12
 */

public class DateUtils {
    /*
     * 获取2022-12-12格式的日期
     *
     * */
    public static String getDay() {
        DateFormat dateInstance = DateFormat.getDateInstance();
        return dateInstance.format(new Date());
    }

    /*
     * 检查日期是否符合 2022-12-12格式
     *
     *   检查用户登记的时间是否为正确的时间
     *
     */
    public static boolean checkDateFormat(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            Date target = sdf.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;

    }

    /*
    *
    * 检查用户登记的时间是否在接下来的30天内
    * */
    public static boolean isRightDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date target = sdf.parse(date);
            Date today = new Date();
            long day =  (target.getTime() - today.getTime())/(1000 * 60 * 60 * 24);

            if(day <0 || day > 30){
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }
    /*
     *
     * 获取当前的时间 2022-12-12 20:40:22
     *
     * */
    public static String getNowTime() {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateformat.format(new Date());
    }

    public static void main(String[] args) {

    }
}
