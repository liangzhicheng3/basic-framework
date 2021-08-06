package com.liangzhicheng.common.utils;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.exception.TransactionException;
import com.liangzhicheng.config.http.HttpConnectionManager;
import com.liangzhicheng.config.http.HttpDeleteRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description 常用工具类
 * @author liangzhicheng
 * @since 2020-07-29
 */
public class SysToolUtil {

    /**
     * @description 生成id
     * @param
     * @return
     */
    public static String generateId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * @description 生成用户昵称
     * @return String
     */
    public static String generateNickname() {
        String nickname = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < 8; i++) {
            String str = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(str)) {
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                nickname += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(str)) {
                nickname += String.valueOf(random.nextInt(10));
            }
        }
        return "用户_" + nickname;
    }

    /**
     * @description 获取访问地址前缀
     * @param request
     * @return String
     */
    public static String getAccessUrlPrefix(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    /**
     * @description 获取真实的ip地址
     * @param request
     * @return String
     */
    public static String getAccessUrl(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(isNotBlank(ip)){
            //多次反向代理后会有多个IP值，第一个IP才是真实IP
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0, index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(isNotBlank(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * @description 判断String参数是否为空，参数数量可变
     * @param strs
     * @return boolean
     */
    public static boolean isBlank(String ... strs){
        for(String s : strs){
            if(StringUtils.isBlank(s)){
                return true;
            }
        }
        return false;
    }

    /**
     * @description 判断多个参数是否为空，参数数量可变
     * @param strs
     * @return boolean
     */
    public static boolean isNotBlank(String ... strs){
        return !isBlank(strs);
    }

    /**
     * @description 判断对象参数是否为空
     * @param value
     * @return boolean
     */
    public static boolean isNull(Object value){
        return Objects.isNull(value);
    }

    /**
     * @description 判断对象参数是否为空
     * @param value
     * @return boolean
     */
    public static boolean isNotNull(Object value){
        return !isNull(value);
    }

    /**
     * @description 判断String参数是否为数字字符
     * @param str
     * @return boolean
     */
    public static boolean isNumber(String str){
        if(isBlank(str)){
            return false;
        }
        if(StringUtils.isNumeric(str)){
            return true;
        }
        return false;
    }

    /**
     * @description 判断String参数是否为数字字符，参数数量可变
     * @param strs
     * @return boolean
     */
    public static boolean isNumber(String ... strs){
        for(String s : strs){
            if(!isNumber(s)){
                return false;
            }
        }
        return true;
    }

    /**
     * @description 判断String参数是否为小数
     * @param str
     * @return boolean
     */
    public static boolean isDouble(String str){
        if(isBlank(str)){
            return false;
        }
        Pattern pattern = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");
        Matcher m = pattern.matcher(str);
        return m.matches();
    }

    /**
     * @description 判断String参数是否为小数，参数数量可变
     * @param strs
     * @return boolean
     */
    public static boolean isDouble(String ... strs){
        for(String s : strs){
            if(!isDouble(s)){
                return false;
            }
        }
        return true;
    }

    /**
     * @description 判断String参数是否是一个正确的手机号码
     * @param phone
     * @return boolean
     */
    public static boolean isPhone(String phone) {
        if(isBlank(phone) || phone.length() != 11){
            return false;
        }
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * @description 判断是否邮箱格式
     * @param email
     * @return boolean
     */
    public static boolean isEmail(String email) {
        Pattern emailPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher matcher = emailPattern.matcher(email);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    /**
     * @description 判断String参数是否中文，是返回true
     * @param str
     * @return boolean
     */
    public static boolean isChinese(String str) {
        if(isNotBlank(str)){
            char[] arr = str.toCharArray();
            if(arr != null && arr.length > 0){
                for(char c : arr){
                    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
                    if (ub != Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                            && ub != Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                            && ub != Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                            && ub != Character.UnicodeBlock.GENERAL_PUNCTUATION
                            && ub != Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                            && ub != Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * @description 向上取整
     * @param num
     * @param size
     * @return int
     */
    public static int upCeil(int num, int size) {
        if (size == 0) {
            return 0;
        }
        int n1 = num / size;
        double n2 = num % size;
        if (n2 > 0) {
            n1 += 1;
        }
        return n1;
    }

    /**
     * @description 向下取整
     * @param num
     * @param size
     * @return int
     */
    public static int downCeil(int num, int size){
        if(size == 0){
            return 0;
        }
        int n1 = num / size;
        Math.floor(n1);
        return n1;
    }

    /**
     * @description 判断String参数是否存在可变参数中，如果不存在返回true
     * @param str
     * @param strs
     * @return boolean
     */
    public static boolean notIn(String str, String ... strs){
        for(String s : strs){
            if(s.equals(str)){
                return false;
            }
        }
        return true;
    }

    /**
     * @description 判断String参数是否存在可变参数中，如果存在返回true
     * @param str
     * @param strs
     * @return boolean
     */
    public static boolean in(String str, String ... strs){
        for(String s : strs){
            if(s.equals(str)){
                return true;
            }
        }
        return false;
    }

    /**
     * @description 判断String可变参数中是否存在，存在返回true
     * @param strs
     * @return boolean
     */
    public static boolean inOneByNotBlank(String ... strs) {
        for (String s : strs) {
            if(isNotBlank(s)){
                return true;
            }
        }
        return false;
    }

    /**
     * @description 精确计算两个数相除，v1除以v2(Integer类型)
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double divide(Integer v1, Integer v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        BigDecimal bd = b1.divide(b2, 10, BigDecimal.ROUND_HALF_UP);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    /**
     * @description 精确计算两个数相除，v1除以v2(String类型)
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double divide(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        BigDecimal bd = b1.divide(b2, 10, BigDecimal.ROUND_HALF_UP);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    /**
     * @description 精确计算两个数相乘，v1乘以v2(Double类型)
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double divide(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        BigDecimal bd = b1.divide(b2, 10, BigDecimal.ROUND_HALF_UP);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    /**
     * @description 精确计算两个数相乘，v1乘以v2(Integer类型)
     * @param v1
     * @param v2
     * @return Integer
     */
    public static Integer multiply(Integer v1, Integer v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        BigDecimal bd = b1.multiply(b2);
        return bd.intValue();
    }

    /**
     * @description 精确计算两个数相乘，v1乘以v2(String类型)
     * @param v1
     * @param v2
     * @return Integer
     */
    public static Integer multiply(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        BigDecimal bd = b1.multiply(b2);
        return bd.intValue();
    }

    /**
     * @description 精确计算两个数相乘，v1乘以v2(Double类型)
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double multiply(Integer v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        BigDecimal bd = b1.multiply(b2).setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    /**
     * @description 精确计算两个数相乘，v1乘以v2(Double类型)
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double multiply(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        BigDecimal bd = b1.multiply(b2).setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    /**
     * @description 将转化得到的String不是科学计数法，如：4.3062319920812602E17->43062319920812602.00
     * @param d
     * @param pattern
     * @return String
     */
    public static String double2String(Double d, String pattern){
        if(d != null){
            if(isBlank(pattern)){
                pattern = "0.00";
            }
            DecimalFormat df = new DecimalFormat(pattern);
            return df.format(d);
        }
        return null;
    }

    /**
     * @description Double金额转String字符串(例如：超过万转成1.00万)
     * @param num
     * @return String
     */
    public static String double2String(Double num) {
        String str = "";
        if (num >= 10000 && num < 1000000) {
            num = num * 0.0001;
            DecimalFormat df = new DecimalFormat("######0.0");
            str = df.format(num);
            str += "万";
        } else if (num >= 1000000 && num < 10000000) {
            num = num * 0.000001;
            DecimalFormat df = new DecimalFormat("######0.0");
            str = df.format(num);
            str += "百万";
        } else if (num >= 10000000) {
            num = num * 0.0000001;
            DecimalFormat df = new DecimalFormat("######0.0");
            str = df.format(num);
            str += "千万";
        } else {
            str = Double.toString(num);
        }
        return str;
    }

    /**
     * @description 随机生成6位数的字符串
     * @param
     * @return String
     */
    public static String random(){
        int num = (int)((Math.random() * 9 + 1) * 100000);
        return String.valueOf(num);
    }

    /**
     * @description 随机生成一个16进制颜色
     * @return String
     */
    public static String makeColor(){
        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();
        r = r.length() == 1 ? "0" + r : r ;
        g = g.length() == 1 ? "0" + g : g ;
        b = b.length() == 1 ? "0" + b : b ;
        String color = r + g + b;
        return color;
    }

    /**
     * @description 返回将number补0，长度为length位后的字符
     * @param number 要补0的数字
     * @param length 补0后的长度
     * @return String
     */
    public static String toLength(int number, int length){
        return String.format("%0" + length + "d", number);
    }

    /**
     * @description 判断字符串长度，中文为2，字母为1
     * @param value
     * @return int
     */
    public static int stringToLength(String value) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * @param date
     * @param format 传null默认为 yyyy-MM-dd HH:mm:ss
     * @return String
     * @description Date日期格式化成String字符串
     */
    public static String dateToString(Date date, String format) {
        SimpleDateFormat sf = null;
        if (isBlank(format)) {
            sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            sf = new SimpleDateFormat(format);
        }
        return sf.format(date);
    }

    /**
     * @param str
     * @param format
     * @return Date
     * @throws ParseException Date
     * @description String字符串格式化成Date日期
     */
    public static Date stringToDate(String str, String format) {
        if (isBlank(str)) {
            return null;
        }
        if (isBlank(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sf.parse(str);
        } catch (ParseException e) {
            throw new TransactionException(ApiConstant.PARAM_DATE_ERROR);
        }
        return date;
    }

    /**
     * @param date
     * @return LocalDateTime
     * @throws ParseException
     * @description Date日期格式转化成LocalDateTime格式
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        LocalDateTime localDateTime = null;
        if(date != null){
            try{
                Instant instant = date.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                localDateTime = instant.atZone(zoneId).toLocalDateTime();
            }catch(Exception e){
                throw new TransactionException(ApiConstant.PARAM_DATE_ERROR);
            }
        }
        return localDateTime;
    }

    /**
     * @param str
     * @param format
     * @return LocalDateTime
     * @throws ParseException
     * @description String字符串格式转化成LocalDateTime格式
     */
    public static LocalDateTime stringToLocalDateTime(String str, String format) {
        if (isBlank(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        LocalDateTime localDateTime = null;
        try{
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
            localDateTime = LocalDateTime.parse(str, dtf);
        }catch(Exception e){
            throw new TransactionException(ApiConstant.PARAM_DATE_ERROR);
        }
        return localDateTime;
    }

    /**
     * @description String字符串格式转化成LocalDate格式
     * @param str
     * @return LocalDate
     */
    public static LocalDate stringToLocalDate(String str){
        DateTimeFormatter dtf = null;
        LocalDate localDate = null;
        try{
            dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            localDate = LocalDate.parse(str, dtf);
        }catch(Exception e){
            throw new TransactionException(ApiConstant.PARAM_DATE_ERROR);
        }
        return localDate;
    }

    /**
     * @description String字符串格式转化成LocalTime格式
     * @param str
     * @param format
     * @return LocalTime
     */
    public static LocalTime stringToLocalTime(String str, String format) {
        if (isBlank(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        DateTimeFormatter dtf = null;
        LocalTime localTime = null;
        try{
            dtf = DateTimeFormatter.ofPattern(format);
            localTime = LocalTime.parse(str, dtf);
        }catch(Exception e){
            throw new TransactionException(ApiConstant.PARAM_DATE_ERROR);
        }
        return localTime;
    }

    /**
     * @param localDateTime
     * @return Date
     * @throws ParseException
     * @description LocalDateTime格式转化成Date日期格式
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        Date date = null;
        try{
            ZoneId zoneId = ZoneId.systemDefault();
            ZonedDateTime zdt = localDateTime.atZone(zoneId);
            date = Date.from(zdt.toInstant());
        }catch(Exception e){
            throw new TransactionException(ApiConstant.PARAM_DATE_ERROR);
        }
        return date;
    }

    /**
     * @param localDateTime
     * @param format
     * @return String
     * @throws ParseException
     * @description LocalDateTime格式转化成String字符串格式
     */
    public static String localDateTimeToString(LocalDateTime localDateTime, String format) {
        if(isBlank(format)){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        String dateStr = "";
        try{
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
            dateStr = localDateTime.format(dtf);
        }catch(Exception e){
            throw new TransactionException(ApiConstant.PARAM_DATE_ERROR);
        }
        return dateStr;
    }

    /**
     * @description 两个时间相比较，返回布尔值
     * @param time1
     * @param time2
     * @return boolean
     */
    public static boolean localDateTimeGT(LocalDateTime time1, LocalDateTime time2){
        if(time1 == null || time2 == null){
            return false;
        }
        long currentTime = time1.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long endTime = time2.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        if(currentTime > endTime){
            return true;
        }
        return false;
    }

    /**
     * @description 返回日期参数加value天后的Date日期
     * @param date
     * @param value
     * @return Date
     */
    public static Date dateAdd(Date date, int value){
        if(date == null || value < 1){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, value);
        return calendar.getTime();
    }

    /**
     * @description 返回日期参数加value天后的LocalDateTime日期
     * @param localDateTime
     * @param value
     * @return LocalDateTime
     */
    public static LocalDateTime localDateTimeAdd(LocalDateTime localDateTime, int value, String type){
        if (localDateTime == null || value < 1) {
            return null;
        }
        LocalDateTime time = null;
        if(in(type, "days", "months")){
            if("days".equals(type)){
//                LocalDateTime time = localDateTime.plus(value, ChronoUnit.DAYS);
                time = localDateTime.plusDays(value);
            }
            if("months".equals(type)){
//                LocalDateTime time = localDateTime.plus(value, ChronoUnit.MONTHS);
                time = localDateTime.plusMonths(value);
            }
        }
        return time;
    }

    /**
     * @description 返回日期参数减value天后的Date日期
     * @param date
     * @param value
     * @return Date
     */
    public static Date dateSub(Date date, int value){
        if(date == null || value < 1){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 0 - value);
        return calendar.getTime();
    }

    /**
     * @description 返回日期参数减value天后的LocalDateTime日期
     * @param localDateTime
     * @param value
     * @param type
     * @return LocalDateTime
     */
    public static LocalDateTime localDateTimeSub(LocalDateTime localDateTime, int value, String type){
        if (localDateTime == null || value < 1) {
            return null;
        }
        LocalDateTime time = null;
        if(in(type, "days", "months")){
            if("days".equals(type)){
                time = localDateTime.minusDays(value);
            }
            if("months".equals(type)){
                time = localDateTime.minusMonths(value);
            }
        }
        return time;
    }

    /**
     * @description 返回日期参数加value天后的Date日期(不要星期天和星期天一)
     * @param date
     * @param value
     * @return Date
     */
    public static Date dateAddNot7An1(Date date, int value){
        if(date == null || value < 1){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        for(int i = value; i > 0; i--){
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            while(calendar.get(Calendar.DAY_OF_WEEK) == 7 || calendar.get(Calendar.DAY_OF_WEEK) == 1){
                calendar.add(Calendar.DAY_OF_YEAR, 1);
            }
        }
        return calendar.getTime();
    }

    /**
     * @description 计算两个Date日期参数之间相差多少天
     * @param min
     * @param max
     * @return int
     */
    public static int daySubBoth(Date min, Date max){
        if(min == null || max == null || min.getTime() > max.getTime()){
            return 0;
        }
        int result = (int)((max.getTime() - min.getTime()) / 1000 / 3600 / 24);
        return result;
    }

    /**
     * @description 返回年份参数加value后的Date日期
     * @param date
     * @param value
     * @return Date
     */
    public static Date yearAdd(Date date, int value){
        if(date == null || value < 1){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + value);
        return calendar.getTime();
    }

    /**
     * @description 返回年份参数减value后的Date日期
     * @param date
     * @param value
     * @return Date
     */
    public static Date yearSub(Date date,int value){
        if(date == null || value < 1){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - value);
        return calendar.getTime();
    }

    /**
     * @description 返回两个日期相减，date1 - date2的年份差
     * @param date1
     * @param date2
     * @return int
     */
    public static int yearSubBoth(Date date1, Date date2){
        if(date1 != null && date2 != null){
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(date1);
            cal2.setTime(date2);
            return (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR));
        }
        return 0;
    }

    /**
     * @description 判断两个Date日期参数是否是同一天
     * @param date1
     * @param date2
     * @return boolean
     */
    public static boolean isSameDay(Date date1, Date date2){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        if(cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR)){
            return false;
        }
        if(cal1.get(Calendar.MONTH) != cal2.get(Calendar.MONTH)){
            return false;
        }
        if(cal1.get(Calendar.DAY_OF_MONTH) != cal2.get(Calendar.DAY_OF_MONTH)){
            return false;
        }
        return true;
    }

    /**
     * @description 获取LocalDateTime当前时间毫秒数
     * @return long
     */
    public static long getEpochMilliByCurrentTime(){
        return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * @description 获取Date日期参数对应星期几参数
     * @param date
     * @return Date
     */
    public static Date getWeekByDate(Date date){
        if(date == null){
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        if(day == 1){//星期天
            return dateSub(date, 6);
        }
        if(day == 2){//星期一
            return date;
        }
        if(day == 3){//星期二
            return dateSub(date, 1);
        }
        if(day == 4){//星期三
            return dateSub(date, 2);
        }
        if(day == 5){//星期四
            return dateSub(date, 3);
        }
        if(day == 6){//星期五
            return dateSub(date, 4);
        }
        if(day == 7){//星期六
            return dateSub(date, 5);
        }
        return date;
    }

    /**
     * @description 获取Date日期格式当前月份的第一天
     * @param date
     * @return Date
     */
    public static Date getMonthFirst(Date date) {
        if(date == null){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * @description 获取Date日期格式当前月份的最后一天
     * @param date
     * @return Date
     */
    public static Date getMonthLast(Date date) {
        if(date == null){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * @description 获取Date日期格式当周星期一至星期天的日期
     * @param date
     * @return List
     */
    public static List<Date> getWeekList(Date date){
        if(date == null){
            return null;
        }
        List<Date> list = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        if(day == 1){//星期天
            Date start = dateSub(date, 6);
            list.add(start);
            for(int i = 0; i < 6; i++){
                start = dateAdd(start, 1);
                list.add(start);
            }
        }
        if(day == 2){//星期一
            Date start = date;
            list.add(start);
            for(int i = 0; i < 6; i++){
                start = dateAdd(start, 1);
                list.add(start);
            }
        }
        if(day == 3){//星期二
            Date start = dateSub(date, 1);
            list.add(start);
            for(int i = 0; i < 6; i++){
                start = dateAdd(start, 1);
                list.add(start);
            }
        }
        if(day == 4){//星期三
            Date start = dateSub(date, 2);
            list.add(start);
            for(int i = 0; i < 6; i++){
                start = dateAdd(start, 1);
                list.add(start);
            }
        }
        if(day == 5){//星期四
            Date start = dateSub(date, 3);
            list.add(start);
            for(int i = 0; i < 6; i++){
                start = dateAdd(start, 1);
                list.add(start);
            }
        }
        if(day == 6){//星期五
            Date start = dateSub(date, 4);
            list.add(start);
            for(int i = 0; i < 6; i++){
                start = dateAdd(start, 1);
                list.add(start);
            }
        }
        if(day == 7){//星期六
            Date start = dateSub(date, 5);
            list.add(start);
            for(int i = 0; i < 6; i++){
                start = dateAdd(start, 1);
                list.add(start);
            }
        }
        return list;
    }

    /**
     * @description 下划线字符串字段转化成驼峰字段
     * @param list
     * @return List
     */
    public static List<Map<String, Object>> formatHumpList(List<Map<String, Object>> list){
        List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
        for(Map<String, Object> map : list){
            newList.add(formatHumpMap(map));
        }
        return newList;
    }

    public static Map<String, Object> formatHumpMap(Map<String, Object> map){
        Map<String, Object> newMap = new HashMap<String, Object>();
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Object> entry = it.next();
            String key = entry.getKey();
            String newKey = lineToHump(key);
            Object value = entry.getValue();
            if(value != null && value != ""){
                newMap.put(newKey, value);
            }else{
                newMap.put(newKey, "");
            }
        }
        return newMap;
    }

    public static String lineToHump(String colName){
        StringBuilder sb = new StringBuilder();
        String[] str = colName.toLowerCase().split("_");
        int i = 0;
        for(String s : str){
            if (s.length() == 1) {
                s = s.toUpperCase();
            }
            i++;
            if (i == 1){
                sb.append(s);
                continue;
            }
            if (s.length() > 0){
                sb.append(s.substring(0, 1).toUpperCase());
                sb.append(s.substring(1));
            }
        }
        return sb.toString();
    }

    /**
     * @description 字符串以split为分割符，转换成List
     * @param str
     * @param split
     * @return List
     */
    public static List<String> strToList(String str, String split){
        if(isBlank(str, split)){
            return null;
        }
        List<String> list = null;
        String[] strArr = str.split(split);
        if(strArr != null && strArr.length > 0){
            list = new ArrayList<String>();
            for(String s : strArr){
                if(!isBlank(s)){
                    list.add(s);
                }
            }
        }
        return list;
    }

    /**
     * @description 去掉List<String>中的重复值
     * @param oldList
     * @return List
     */
    public static List<String> trimList(List<String> oldList){
        List<String> list = new ArrayList<String>();
        if(oldList != null && oldList.size() > 0){
            for(String str : oldList){
                if(!list.contains(str)){
                    list.add(str);
                }
            }
        }
        return list;
    }

    /**
     * @description 获取两个List的交集
     * @param firstList
     * @param secondList
     * @return List<String>
     */
//    public static List<String> getListBoth(List<String> firstList, List<String> secondList) {
//        List<String> resultList = new ArrayList<String>();
//        LinkedList<String> result = new LinkedList<String>(firstList); //大集合用LinkedList
//        HashSet<String> othHash = new HashSet<String>(secondList); //小集合用HashSet
//        Iterator<String> iter = result.iterator(); //Iterator迭代器进行数据操作
//        while(iter.hasNext()) {
//            if(!othHash.contains(iter.next())) {
//                iter.remove();
//            }
//        }
//        resultList = new ArrayList<String>(result);
//        return resultList;
//    }
    public static List<String> getListBoth(List<String> firstList, List<String> secondList) {
        List<String> resultList = Lists.newArrayList();
        LinkedList<String> first = Lists.newLinkedList(firstList); //大集合用LinkedList
        HashSet<String> second = new HashSet<String>(secondList); //小集合用HashSet
        Iterator<String> iterator = first.iterator(); //Iterator迭代器进行数据操作
        while(iterator.hasNext()) {
            if(!second.contains(iterator.next())) {
                iterator.remove();
            }
        }
        resultList = Lists.newArrayList(first);
        return resultList;
    }

    /**
     * @description 判断集合是否为空并且数量大于0
     * @param collection
     * @return boolean
     */
    public static boolean listSizeGT(Collection collection) {
        if (collection == null || collection.size() < 1) {
            return false;
        }
        return true;
    }

    /**
     * @description 将request中的Xml格式转化成Map
     * @param request
     * @return Map
     */
    public static Map<String,String> xmlToMap(HttpServletRequest request){
        try{
            Map<String,String> map = new HashMap<String, String>();
            SAXReader reader = new SAXReader();
            InputStream ins = request.getInputStream();
            Document doc = reader.read(ins);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for(Element e : list){
                map.put(e.getName(), e.getText());
            }
            ins.close();
            return map;
        }catch(DocumentException e){
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    /**
     * @description 将String的Xml格式转化成Map
     * @param xml
     * @return Map
     */
    public static Map<String,String> xmlToMap(String xml){
        try{
            Map<String,String> map = new HashMap<String, String>();
            Document doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for(Element e : list){
                map.put(e.getName(), e.getText());
            }
            return map;
        }catch(DocumentException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @description 将Map转化成Xml格式
     * @param map
     * @return String
     */
    public static String mapToXml(Map map){
        if(map != null){
            Element root = DocumentHelper.createElement("xml");
            Document document = DocumentHelper.createDocument(root);
            Set<String> set = map.keySet();
            for(String key : set){
                if(map.get(key) != null){
                    root.addElement(key).addText(String.valueOf(map.get(key)));
                }
            }
            return document.asXML();
        }
        return "";
    }

    /**
     * @description 获取当前页码
     * @param value
     * @return Long
     */
    public static Long getPage(Integer value){
        if(value == null || value < 1){
            Integer page = 1;
            return Long.valueOf(page + "");
        }
        return Long.valueOf(value + "");
    }

    /**
     * @description 获取每页数量
     * @param value
     * @return
     */
    public static Long getPageSize(Integer value){
        if(value == null || value < 1){
            Integer pageSize = 10;
            return Long.valueOf(pageSize + "");
        }
        return Long.valueOf(value + "");
    }

    /**
     * @description 分页查询结果集
     * @param page
     * @return Map<String, Object>
     */
    public static Map<String, Object> pageResult(IPage page){
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("records", page.getRecords());
        resultMap.put("total", page.getTotal());
        resultMap.put("page", page.getCurrent());
        resultMap.put("pageSize", page.getSize());
        return resultMap;
    }

    /**
     * @Description 将字符串中的emoji表情转换成可以在utf-8字符集数据库中保存的格式(表情占4个字节，需要utf8mb4字符集)
     * @param str
     * @return String
     */
    public static String emojiConvert(String str){
        if(isNotBlank(str)){
            try{
                String patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";
                Pattern pattern = Pattern.compile(patternString);
                Matcher matcher = pattern.matcher(str);
                StringBuffer sb = new StringBuffer();
                while(matcher.find()) {
                    matcher.appendReplacement(sb,"[[" + URLEncoder.encode(matcher.group(1),"UTF-8") + "]]");
                }
                matcher.appendTail(sb);
                return sb.toString();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @description 还原utf-8字符集数据库中保存的含转换后emoji表情的字符串
     * @param str
     * @return String
     */
    public static String emojiRecovery(String str) {
        if(isNotBlank(str)){
            try{
                if(str == null){
                    str = "";
                }
                String patternString = "\\[\\[(.*?)\\]\\]";
                Pattern pattern = Pattern.compile(patternString);
                Matcher matcher = pattern.matcher(str);
                StringBuffer sb = new StringBuffer();
                while(matcher.find()) {
                    matcher.appendReplacement(sb, URLDecoder.decode(matcher.group(1), "UTF-8"));
                }
                matcher.appendTail(sb);
                return sb.toString();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @description 判断文件是否图片(String类型)
     * @param suffix
     * @return boolean
     */
    public static boolean isImage(String suffix) {
        return imageDispose(suffix);
    }

    /**
     * @description 判断文件是否图片(MultipartFile类型)
     * @param file
     * @return boolean
     */
    public static boolean isImage(MultipartFile file) {
        String suffix = getFileSuffix(file);
        return imageDispose(suffix);
    }

    /**
     * @description 判断文件是否图片公用方法
     * @param suffix
     * @return boolean
     */
    private static boolean imageDispose(String suffix){
        boolean ok = false;
        if(isNotBlank(suffix)) {
            String[] arr = {".png", ".jpg", ".jpeg", ".gif", ".bmp"};
            for(String s : arr) {
                if(suffix.toLowerCase().equals(s)) {
                    ok = true;
                    return ok;
                }
            }
        }
        return ok;
    }

    /**
     * @description 判断文件是否视频(String类型)
     * @param suffix
     * @return boolean
     */
    public static boolean isVideo(String suffix) {
        return videoDispose(suffix);
    }


    /**
     * @description 判断文件是否视频(MultipartFile类型)
     * @param file
     * @return boolean
     */
    public static boolean isVideo(MultipartFile file) {
        String suffix = getFileSuffix(file);
        return videoDispose(suffix);
    }

    /**
     * @description 判断文件是否视频公用方法
     * @param suffix
     * @return boolean
     */
    private static boolean videoDispose(String suffix){
        boolean ok = false;
        if(isNotBlank(suffix)) {
            String[] arr = {".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb", ".mpeg", ".mpg",".ogg", ".ogv", ".mov", ".wmv", ".mp4", ".webm", ".mp3", ".wav", ".mid"};
            for(String s : arr) {
                if(suffix.toLowerCase().equals(s)) {
                    ok = true;
                    return ok;
                }
            }
        }
        return ok;
    }

    /**
     * @description 获取文件后缀，包含.
     * @param file
     * @return String
     */
    public static String getFileSuffix(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if(!fileName.contains(".")){
            return null;
        }
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        return suffix;
    }

    /**
     * @description 向指定Url发送Post方法的请求，String方式
     * @param url
     * @param param
     * @param charset
     * @return String
     */
    public static String sendPost(String url, String param, String charset) {
        String result = "";
        if (charset == null) {
            charset = "UTF-8";
        }
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        try {
            httpClient = HttpConnectionManager.getInstance().getHttpClient();
            httpPost = new HttpPost(url);
            // 设置连接超时,设置读取超时
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(10000).build();
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
            // 设置参数
            StringEntity se = new StringEntity(param, charset);
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * @description 向指定Url发送Post方法的请求，map方式
     * @param url
     * @param map
     * @return String
     */
    public static String sendPost(String url, Map<String, ?> map){
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String param = "";
        Iterator<String> it = map.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            param += key + "=" + map.get(key) + "&";
        }
        try{
            URL realUrl = new URL(url);
            //打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //发送请求参数
            out.print(param);
            //flush输出流的缓冲
            out.flush();
            //定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{//使用finally关闭输出流、输入流
            try{
                if(out != null){
                    out.close();
                }
                if(in != null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @description 向指定Url发送Post方法的请求，xml方式
     * @param url
     * @param xml
     * @return String
     */
    public static String sendPost(String url, String xml) {
        try {
            //发送POST请求
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + xml.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(xml);
            out.flush();
            out.close();
            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                info("--- connect failed!");
                return "";
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return "";
    }

    /**
     * @description 向指定Url发送Get方法的请求，String方式
     * @param url
     * @return String
     */
    public static String sendGet(String url) {
        try {
            //发送get请求
            URL getUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) getUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                SysToolUtil.info("--- get() : connect failed");
                return "";
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @description 向指定Url发送Get方法的请求，String请求体参数方式与Map请求头参数方式
     * @param url
     * @param map
     * @return String
     */
    public static String sendGet(String url, Map<String, Object> map) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        //配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(3000)
                .setConnectTimeout(3000)
                .setSocketTimeout(3000).build();
        httpGet.setConfig(requestConfig);
        httpGet.setHeader("Content-Type", map.get("contentType") + "");
        CloseableHttpResponse response = null;
        String result = null;
        try {
            response = httpClient.execute(httpGet); //发送请求
            SysToolUtil.info("StatusCode -> " + response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity,"utf-8");
            SysToolUtil.info("------ get request : " + result);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            httpGet.releaseConnection();
        }
        return result;
    }

    /**
     * @description 向指定Url发送Put方法的请求，String请求体参数方式与Map请求头参数方式
     * @param urlPath
     * @param param json参数
     * @param map
     * @return String
     */
    public static String sendPut(String urlPath, Object param, Map<String, Object> map) {
        //创建连接
        String encode = "utf-8";
        //HttpClients.createDefault()等价于 HttpClientBuilder.create().build();
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPut httpput = new HttpPut(urlPath);
        /**header中通用属性*/
        httpput.setHeader("Accept","*/*");
        httpput.setHeader("Accept-Encoding","gzip, deflate");
        httpput.setHeader("Cache-Control","no-cache");
        httpput.setHeader("Connection", "keep-alive");
        /**业务参数*/
        httpput.setHeader("Content-Type", map.get("contentType") + "");
        //组织请求参数
        StringEntity stringEntity = new StringEntity(JSON.toJSONString(param), encode);
        httpput.setEntity(stringEntity);
        String content = null;
        CloseableHttpResponse  httpResponse = null;
        try {
            //响应信息
            httpResponse = closeableHttpClient.execute(httpput);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            closeableHttpClient.close();  //关闭连接、释放资源
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * @description 向指定Url发送Delete方法的请求，String请求体参数方式与Map请求头参数方式
     * @param url
     * @param param
     * @param map
     * @return String
     */
    public static String sendDelete(String url, String param, Map<String, Object> map) {
        CloseableHttpClient client = null;
        HttpDeleteRequest httpDelete = null;
        String result = null;
        try {
            client = HttpClients.createDefault();
            httpDelete = new HttpDeleteRequest(url);
            httpDelete.addHeader("Content-Type", map.get("contentType") + "");
            httpDelete.setHeader("Accept", "application/json; charset=utf-8");
            httpDelete.setEntity(new StringEntity(param));
            CloseableHttpResponse response = client.execute(httpDelete);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
            if (200 == response.getStatusLine().getStatusCode()) {
                info("DELETE方式请求远程调用成功.msg={" + result + "}");
            }
        } catch (Exception e) {
            error("DELETE方式请求远程调用失败,errorMsg={" + e.getMessage() + "}", SysToolUtil.class);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @description 去除待带script、src的语句，转义替换后的value值
     * @param value
     * @return String
     */
    public static String replaceXSS(String value) {
        if (value != null) {
            try{
                value = value.replace("+","%2B");   //'+' replace to '%2B'
                value = URLDecoder.decode(value, "utf-8");
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }catch(IllegalArgumentException e){
                e.printStackTrace();
            }

            //Avoid null characters
            value = value.replaceAll("\0", "");

            //Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            //Avoid anything in a src='...' type of e­xpression
            /*scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");*/

            //Remove any lonesome </script> tag
            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            //Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            //Avoid eval(...) e­xpressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            //Avoid e­xpression(...) e­xpressions
            scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            //Avoid javascript:... e­xpressions
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            //Avoid alert:... e­xpressions
            scriptPattern = Pattern.compile("alert", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            //Avoid onload= e­xpressions
            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = Pattern.compile("vbscript[\r\n| | ]*:[\r\n| | ]*", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
        }
        return XSSFilter(value);
    }

    /**
     * @description 特殊字符过滤
     * @param value
     * @return String
     */
    public static String XSSFilter(String value) {
        if (value == null) {
            return null;
        }
        StringBuffer result = new StringBuffer(value.length());
        for (int i = 0; i < value.length(); ++i) {
            switch (value.charAt(i)) {
                case '<':
                    result.append("<");
                    break;
                case '>':
                    result.append(">");
                    break;
                case '"':
                    result.append("\"");
                    break;
                case '\'':
                    result.append("'");
                    break;
                case '%':
                    result.append("%");
                    break;
                case ';':
                    result.append(";");
                    break;
                case '(':
                    result.append("(");
                    break;
                case ')':
                    result.append(")");
                    break;
                case '&':
                    result.append("&");
                    break;
                case '+':
                    result.append("+");
                    break;
                default:
                    result.append(value.charAt(i));
                    break;
            }
        }
        return result.toString();
    }

    /**
     * @description 敏感词过滤，替换为*
     * @param content
     * @return String
     */
    public static String replaceContent(String content) {
        return SysContent1Util.getInstance().replaceSensitiveWord(content, 2, "*");
    }

    /**
     * @description 打印日志(info级别)
     * @param info
     */
    public static void info(Object info) {
        LogManager.getLogger(SysToolUtil.class).info(info);
    }

    /**
     * @description 打印日志(info级别)
     * @param info
     * @param clazz
     */
    public static void info(Object info, Class<?> clazz) {
        LogManager.getLogger(clazz).info(info);
    }

    /**
     * @description 打印日志(warn级别)
     * @param info
     * @param clazz
     */
    public static void warn(Object info,Class<?> clazz) {
        LogManager.getLogger(clazz).warn(info);
    }

    /**
     * @description 打印日志(error级别)
     * @param error
     * @param clazz
     */
    public static void error(Object error,Class<?> clazz) {
        LogManager.getLogger(clazz).error(error);
    }

}
