package com.seven.library.utils;

import android.text.TextUtils;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检查、校验工具辅助类
 *
 * @author seven
 */
public class CheckUtils {

    private static volatile CheckUtils checkUtil;

    private CheckUtils() {

    }

    public static CheckUtils getInstance() {

        if (checkUtil == null) {

            synchronized (CheckUtils.class) {


                checkUtil = new CheckUtils();
            }

        }

        return checkUtil;
    }

    /**
     * 验证是否是手机号码
     *
     * @param str
     * @return
     */
    public boolean isMobile(String str) {
        Pattern pattern = Pattern.compile("1[0-9]{10}");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 验证密码(规则：12-20位包含英文、数字、特殊字符--不含空格)
     *
     * @param str
     * @return
     */
    public boolean isPassword(String str) {
        Pattern pattern = Pattern.compile("[!-~]{6,10}");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 验证身份证号是否符合规则
     *
     * @param str 身份证号
     * @return
     */
    public boolean isIdCard(String str) {
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return str.matches(regx) || str.matches(reg1) || str.matches(regex);
    }

    /**
     *
     * @param area
     * @param formula
     * @return
     */
    public double formula(double area, String formula) {

        if (TextUtils.isEmpty(formula))
            return 0;

        String newFormula = formula.replace("S", String.valueOf(area)).replace("C", "0").replace("H", "1");

        JexlEngine jexlEngine = new JexlBuilder().create();
        JexlExpression jexlExpression = jexlEngine.createExpression(newFormula);
        Object evaluate = jexlExpression.evaluate(null);

        return Double.parseDouble(evaluate.toString());
    }

}
