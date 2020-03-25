package com.pagemanage.pagecomponent.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pagemanage.pagecomponent.exception.BusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;

import java.io.Closeable;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ToolUtils {


    private static final Logger logger = LogManager.getLogger();

    /*
     * 判断对象是否为空
     * */
    public static boolean isNull(Object obj) {
        return null == obj;
    }

    /**
     * 判断字符串是否为空  或者空字符串
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(String obj) {

        return (obj == null) || (obj.trim().length() == 0);
    }

    /**
     * 多数组判空
     *
     * @param list
     * @return
     */
    public static boolean judgeList(List list) {

        boolean flag = !ToolUtils.isNull(list) && list.size() >= 1;

        if (flag && list.size() == 1) {

            return !ToolUtils.isNull(list.get(0));
        }

        return flag;
    }

    /**
     * 多数组判空,有一个非空，都是true
     *
     * @param list
     * @return
     */
    public static boolean judgeLists(List... list) {

        for (List aList : list) {
            if (judgeList(aList)) {
                continue;
            }
            return false;
        }
        return true;
    }

    /**
     * 两个对象相同属性 赋值转换
     *
     * @param source 源对象
     * @param target 目标对象
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> K copyProperties(T source, K target) {

        BeanUtils.copyProperties(source, target);

        return target;
    }

    public static <T, K> K autoCopyProperties(T source, Class<K> target) {

        return JSONObject.parseObject(JSON.toJSONString(source), target);
    }


    /**
     * 判断字符串（不限数量）是否为空  或者空字符串
     *
     * @param
     * @return
     */
    public static boolean isAnyEmpty(String... objs) {

        if (null == objs) {
            return true;
        }

        for (String obj : objs) {
            if ((null == obj) || "".equals(obj) || obj.trim().length() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对象（不限数量）是否为空  或者空字符串
     *
     * @param
     * @return
     */
    public static boolean isNull(Object... objs) {

        if (null == objs) {
            return true;
        }

        for (Object obj : objs) {
            if ((null == obj) || ("".equals(obj))) {
                return true;
            }
        }
        return false;
    }

    public static <T> String getSimpleName(Class<T> clz, String arg) {

        return ToolUtils.isEmpty(arg) ? arg : clz.getSimpleName().toLowerCase();

    }

    public static void closeIO(Closeable... closeables) {

        try {

            if (closeables != null) {

                for (Closeable close : closeables) {

                    if (close != null) {
                        close.close();
                    }
                }
            }
        } catch (IOException e) {
            logger.error("关流异常" + e);
            throw new BusinessException("关流异常");
        }
    }

    /**
     * 对空进行处理为空字符串
     *
     * @param obj
     * @return
     */
    public static String parseNull(String obj) {

        return null == obj ? "" : obj;
    }

    /**
     * 得到相应长度的随机数（最长19位）
     *
     * @param size 随机数的长度
     * @return
     */
    public static String getRandomNumBySize(int size) {

        return extractRandomNum(getRandomFileName(), size);
    }

    /**
     * 线程安全 YYYYMMDDHHMMssSSS+5位随机数(19位)
     *
     * @return
     */
    public static String getRandomFileName() {
        Lock lock = new ReentrantLock();
        String datetime = null;
        int rannum = 0;// 获取5位随机数
        try {
            if (lock.tryLock(5000, TimeUnit.MILLISECONDS)) {//获取不到锁，就等5秒，如果5秒后还是获取不到就返回false
                datetime = new SimpleDateFormat("yyyyMMddHHmmssSSS")
                        .format(new Date());
                rannum = ThreadLocalRandom.current()
                        .nextInt(10000, 99999);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new BusinessException("获取线程锁异常");
        } finally {
            lock.unlock();//释放锁
        }

        return datetime + rannum;// 当前时间+随机数
    }

    public static String randomString(int length) {

        if (length <= 0) {
            length = 10;
        } else if (length > 50) {
            length = 50;
        }
        StringBuilder s = new StringBuilder(50);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写还是小写
            s.append((char) (choice + random.nextInt(26)));
        }
        return s.toString();
    }

    /**
     * 在输入的数中,随机抽几个数组成新的随机数
     *
     * @param oldRandomNum
     * @param size
     * @return
     */
    public static String extractRandomNum(String oldRandomNum, int size) {
        if (oldRandomNum.length() <= size) {
            return oldRandomNum;
        }

        char[] oldChars = oldRandomNum.toCharArray();
        char[] newChars = new char[size];

        List<Integer> list = new ArrayList<Integer>();

        int oldSize = oldRandomNum.length();
        for (int i = 0; i < size; ) {
            int num = ThreadLocalRandom.current().nextInt(0, oldSize);

            if (i == 0 && oldChars[num] == '0') {
                continue;
            }

            if (list.contains(num)) {
                continue;
            }

            newChars[i] = oldChars[num];
            list.add(num);
            i++;
        }


        return String.valueOf(newChars);
    }


    /**
     * 查看一个字符串是否可以转换为数字  不能总以Integer.parseInt() 是否抛出异常来判断
     *
     * @param str 字符串
     * @return true 可以; false 不可以
     */
    public static boolean isStr2Num(String str) {
        if ((null == str) || ("".equals(str))) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isStr2NumOne(String str) {
        if (str.length() == 1) {
            try {
                int i = Integer.parseInt(str);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean isStr2NumTwo(String str) {
        if (str.length() == 2) {
            try {
                int i = Integer.parseInt(str);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean isStr2Double(String str) {

        if ((null == str) || ("".equals(str))) {
            return false;
        }

        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否是中文
     *
     * @param str
     * @return
     */
    public static boolean isChineseMatching(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static boolean isStrMatching(String str) {
        if ((null == str) || ("".equals(str))) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[0-9a-zA-Z]{10,19}$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static long isStr2Timestamp(String time) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;

        try {
            date = format.parse(time);
        } catch (ParseException e) {
            logger.error("format time isStr2Timestamp ParseException ... " + e);
            throw new BusinessException("参数或参数格式不正确");
        }

        return date.getTime();
    }


    public static Integer isStrToNumber(String str) {

        return isStr2Num(str) ? Integer.parseInt(str) : null;
    }

    /**
     * 判断参数是否为0
     *
     * @param bigDecimal
     * @return
     */
    public static Boolean judgeIsZero(BigDecimal bigDecimal) {

        return bigDecimal.compareTo(BigDecimal.ZERO) == 0;
    }

    public static double paserDouble(String value) {

        double num = 0.00;

        try {
            num = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            logger.error("paserDouble NumberFormatException ..." + e.getMessage() + e);
            return num;
        }
        return num;
    }
}

