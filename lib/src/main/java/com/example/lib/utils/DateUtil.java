package com.example.lib.utils;

import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaochengxiao on 2017/12/18.
 */

public class DateUtil {

    public static String millisecond2Date(String millSecond, String formatStr) {
        if (millSecond == null || millSecond.isEmpty() || millSecond.equals("null")) {
            return "";
        }
        if (formatStr == null || formatStr.isEmpty()) {
            formatStr = "yyyy/MM/dd HH:mm:ss";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(new Date(Long.valueOf(millSecond)));
    }

    public static String millisecond2Date(Long millSecond) {
        return millisecond2Date(millSecond, "");
    }

    public static Date parse(String millSecond, String formatStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.parse(millSecond);
    }

    //将instant变成字符串标准格式
    public static String getStringByInstant(String type, String tempTime) {
        if(tempTime==null||tempTime.equals("")){
            return "";
        }
        DateTime dt = new DateTime(tempTime);
        Instant instant;
        if(type.equals("yyyy-MM-dd")){
             instant=dt.toInstant().plus(TimeUnit.HOURS.toMillis(8));
        }else{
            instant=dt.toInstant().plus(TimeUnit.HOURS.toMillis(8));
        }
//        Instant instant=dt.toInstant().plus(TimeUnit.HOURS.toMillis(8));;
//        Instant instant=dt.toInstant();
        DateTimeFormatter forPattern = DateTimeFormat.forPattern(type);
        String newtime=instant.toString(forPattern);
        return newtime;
    }
    //讲string转成instant,这个string是标准时间格式
    public static String getInstantByStr(String type, String tempTime)  {
        if(tempTime==null||tempTime.equals("")){
            return "";
        }
        Date date= null;
        try {
            date = parse(tempTime,type);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateTime dt = new DateTime(date);
        Instant instant=dt.toInstant().plus(TimeUnit.HOURS.toMillis(8));
        String newtime=instant.toString();
        return newtime;
    }
//    1、将字符串转换为时间
//    DateTimeFormatter forPattern = DateTimeFormat.forPattern(“yyyy-MM-dd”);
//    DateTime dateTime=forPattern.parseDateTime(“2018-01-01”);
//    format = DateTimeFormat.forPattern(“yyyy年MM月dd日 HH:mm:ss”);
//    dateTime=forPattern.parseDateTime(“2018年01月01日 23:25:35”);
//2、将时间转换为字符串
//    DateTime dateTime=new DateTime();
//    String dateString = dateTime.toString(“yyyyMMdd”);
//    DateTimeFormatter forPattern = DateTimeFormat.forPattern(“yyyy年MM月dd日 HH:mm:ss”);
//    dateString=forPattern.print(dateTime);



//根据出生日期计算年龄
    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }

    public static String millisecond2Date(Long millSecond, String formatStr) {
        if (millSecond == null) {
            return "";
        }
        if (formatStr == null || formatStr.isEmpty()) {
            formatStr = "yyyy-MM-dd HH:mm:ss";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(new Date(millSecond));
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds   精确到秒的字符串
     * @param formatStr
     * @return
     */
    public static String timeSecond2Date(String seconds, String formatStr) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        return millisecond2Date(seconds + "000", formatStr);
    }

    public static String date2TimeStamp(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long getDiffTime(long tempTime) {
        return new Date().getTime() - tempTime;
    }

    public static String getTimeByDate(Date tempTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(tempTime);
    }
    public static String getTimeByDateFormat(Date tempTime,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(tempTime);
    }
    public static String getTimeByDateNoss(Date tempTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(tempTime);
    }
    public static String getTimeByDateYMD(Date tempTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(tempTime);
    }
    public static void showDateAndTime(TextView tv) {
        TimePickerView pvTime = new TimePickerBuilder(Utils.getActivity(tv), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
//                Toast.makeText(Utils.getActivity(tv), DateUtil.getTimeByDateNoss(date), Toast.LENGTH_SHORT).show();
                tv.setText(DateUtil.getTimeByDateNoss(date));
            }
        }).setType(new boolean[]{true, true, true, true, true, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
//                        .setContentSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
//                        .setTitleText("Title")//标题文字
//                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
//                        .isCyclic(true)//是否循环滚动
//                        .setTitleColor(Color.BLACK)//标题文字颜色
//                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                        .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                        .setRangDate(startDate,endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
        pvTime.show();
    }
    //只显示年月日
    public static void showDateAndBirthday(TextView tv) {
        TimePickerView pvTime = new TimePickerBuilder(Utils.getActivity(tv), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
//                Toast.makeText(Utils.getActivity(tv), DateUtil.getTimeByDateNoss(date), Toast.LENGTH_SHORT).show();
                tv.setText(DateUtil.getTimeByDateYMD(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
//                        .setContentSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
//                        .setTitleText("Title")//标题文字
//                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
//                        .isCyclic(true)//是否循环滚动
//                        .setTitleColor(Color.BLACK)//标题文字颜色
//                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                        .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                        .setRangDate(startDate,endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
        pvTime.show();
    }

}
