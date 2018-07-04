package com.mixiusi.bean.utils;

import java.text.SimpleDateFormat;
import java.util.List;


public class StringUtils
{
	private static String SPACE = "   ";
	
	public static boolean isBlank(String s)
	{
		return s == null || s.trim().length() <= 0 ;
	}
	
	public static boolean equals(String s,String...arr)
	{
		if (isBlank(s) || arr == null || arr.length <=0)
			return false;
		for (String p: arr)
			if (s.equalsIgnoreCase(p))
				return true ;
		return false ;
	}
	
	public static boolean isTrue(String s)
	{
		return equals(s,"true","yes","open","1","on");
	}
	
	/**
	 * 判断是否为合法的日期时间字符串
	 *
	 * @param str_input
	 * @return boolean;符合为true,不符合为false
	 */
	public static boolean isDate(String str_input, String rDateFormat) {
		if (!isNull(str_input)) {
			SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
			formatter.setLenient(false);
			try {
				formatter.format(formatter.parse(str_input));
			} catch (Exception e) {
				return false;
			}
			return true;
		}
		return false;
	}

	public static boolean isNull(String str) {
		if (str == null || "".equals(str) || str.trim().length() == 0)
			return true;
		else
			return false;
	}

	// 将NULL转换成空字符串
	public static String null2Str(Object value) {
		return value == null || "null".equals(value.toString()) ? "" : value.toString();
	}

	public static String null2Str(String value) {
		return value == null || "null".equals(value) ? "" : value.trim();
	}

	public static String nullToString(String value) {
		return value == null || "null".equals(value) ? "" : value.trim();
	}

	public static String nullToString(Object value) {
		return value == null ? "" : value.toString();
	}

	public static Long nullToLong(Object value){
		return value == null || "null".equals(value.toString()) ? 0L: stringToLong(value.toString().trim());
	}

//	public static Long nullToCloneLong(Object value){
//		return value == null || NumberUtils.isNumber(value.toString()) == false  ? null : stringToLong(value.toString());
//	}

	public static Integer nullToInteger(Object value){
		return value == null || "null".equals(value.toString()) ? 0: stringToInteger(value.toString());
	}

	public static Boolean nullToBoolean(Object value){
		if(value == null
				|| "null".equals(value.toString()))
			return false;
		if("1".equals(value.toString().trim())
				|| "true".equalsIgnoreCase(value.toString().trim()))
			return true;
		if("1".equals(value.toString().trim())
				|| "yes".equalsIgnoreCase(value.toString().trim()))
			return true;
		return false;
	}

	public static Long stringToLong(String value) {
		Long l;
		value = nullToString(value);
		if ("".equals(value)) {
			l = 0L;
		} else {
			try {
				l = Long.valueOf(value);

			} catch (Exception e) {
				l = 0L;
			}
		}
		return l;
	}
	
	public static Integer stringToInteger(String value) {
		Integer l;
		value = nullToString(value);
		if ("".equals(value)) {
			l = 0;
		} else {
			try {
				l = Integer.valueOf(value);

			} catch (Exception e) {
				l = 0;
			}
		}
		return l;
	}
	
    /**
     * 检查对象是否为空
     * @param obj
     * @return boolean
     */
    public static boolean isNull(Object obj)
    {
        if (obj == null) {
            return true;
        } else if (String.class.isInstance(obj)) {
            return isNull((String)obj);
        } else if(List.class.isInstance(obj)) {
            return ((List) obj).size() == 0;
        }else {
            return false;
        }
    }
    
    /** 
     * 删除起始字符 
     * @param s 
     * @return 
     * @author zjs 2017年10月13日 
     */  
    public static String trimStart(String str,String trim){  
        if(str==null)  
            return null;  
        return str.replaceAll("^("+trim+")+", "");  
    }  
    /** 
     * 删除末尾字符 
     * @param s 
     * @return 
     * @author zjs 2017年10月13日 
     */  
    public static String trimEnd(String str,String trim){  
        if(str==null)  
            return null;  
        return str.replaceAll("("+trim+")+$", "");  
    }  
    /**
     * 将Double类型的数值转换为String类型，单位元转换为分
     * @param value
     * @return
     */
    public static String convertDoubleToStr(Double value) {
    	if(null == value)
    		return null;
    	String s = Double.toString(value*100);
		String a = s.substring(0, s.lastIndexOf("."));
		return a;
    }
    
    /**
     * 格式化输出json
     */
    public static String formatJson(String json)  
    {  
        StringBuffer result = new StringBuffer();  
        int length = json.length();  
        int number = 0;  
        char key = 0;  
          
        //遍历输入字符串。  
        for (int i = 0; i < length; i++)  
        {  
            //1、获取当前字符。  
            key = json.charAt(i);  
              
            //2、如果当前字符是前方括号、前花括号做如下处理：  
            if((key == '[') || (key == '{') )  
            {  
                //（1）如果前面还有字符，并且字符为“：”，打印：换行和缩进字符字符串。  
                if((i - 1 > 0) && (json.charAt(i - 1) == ':'))  
                {  
                    result.append(System.getProperty("line.separator"));  
                    result.append(indent(number));  
                }  
                //（2）打印：当前字符。  
                result.append(key);  
                  
                //（3）前方括号、前花括号，的后面必须换行。打印：换行。  
                result.append(System.getProperty("line.separator"));  
                  
                //（4）每出现一次前方括号、前花括号；缩进次数增加一次。打印：新行缩进。  
                number++;  
                result.append(indent(number));  
                //（5）进行下一次循环。  
                continue;  
            }  
              
            //3、如果当前字符是后方括号、后花括号做如下处理：  
            if((key == ']') || (key == '}') )  
            {  
                //（1）后方括号、后花括号，的前面必须换行。打印：换行。  
                result.append(System.getProperty("line.separator"));  
                  
                //（2）每出现一次后方括号、后花括号；缩进次数减少一次。打印：缩进。  
                number--;  
                result.append(indent(number));  
                  
                //（3）打印：当前字符。  
                result.append(key);  
                  
                //（4）如果当前字符后面还有字符，并且字符不为“，”，打印：换行。  
                if(((i + 1) < length) && (json.charAt(i + 1) != ','))  
                {  
                    result.append(System.getProperty("line.separator"));  
                }  
                  
                //（5）继续下一次循环。  
                continue;  
            }  
              
            //4、如果当前字符是逗号。逗号后面换行，并缩进，不改变缩进次数。  
            if((key == ','))  
            {  
                result.append(key);  
                result.append(System.getProperty("line.separator"));  
                result.append(indent(number));  
                continue;  
            }  
              
            //5、打印：当前字符。  
            result.append(key);  
        }  
          
        return result.toString();  
    }  
      
    /** 
     * 返回指定次数的缩进字符串。每一次缩进三个空格，即SPACE。 
     * @param number 缩进次数。 
     * @return 指定缩进次数的字符串。 
     */  
    private static String  indent(int number)  
    {  
        StringBuffer result = new StringBuffer();  
        for(int i = 0; i < number; i++)  
        {  
            result.append(SPACE);  
        }  
        return result.toString();  
    }  
    
    

}
