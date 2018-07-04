package com.mixiusi.bean.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StringUtil {
	private final static Log log = LogFactory.getLog(StringUtil.class);
	/**
	 * 验证是否是电话号码
	 * @param phone
	 * @return
	 */
	public static boolean isPhoneNumber(String phone) {
		String str = "^1[0-9]{10}$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(phone);
		return m.matches();
	}
	/**
	 * 验证是否是邮箱
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
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
		if (str == null)
			return true;
		else
			return false;
	}
	public static boolean isNullStr(String str) {
		if (str == null||str.equals(""))
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
	
	public static List<Long> stringToLongArray(String value) throws Exception{
		List<Long> ls = new ArrayList<Long> ();
		if(StringUtil.isNullStr(value)){
			return ls;
		}
		
		try{
			String[] ids = value.split(",");
			for(int i = 0; i < ids.length; i ++ ){
				if(StringUtil.isNumber(ids[i])){
					ls.add(Long.parseLong(ids[i]));
				}
			}
		}catch(Exception e){
			throw e;
		}
		return ls;
	} 
	
	public static List<Long> stringToLongArray(Object value) throws Exception{
		List<Long> ls = new ArrayList<Long> ();
		if(value == null || StringUtil.isNullStr(value.toString())){
			return ls;
		}
		
		try{
			String[] ids = value.toString().split(",");
			for(int i = 0; i < ids.length; i ++ ){
				if(StringUtil.isNumber(ids[i])){
					ls.add(Long.parseLong(ids[i]));
				}
			}
		}catch(Exception e){
			throw e;
		}
		return ls;
	} 
	
	public static List<String> strToStrArray(String value, String splitChar)throws Exception{
		List<String> ls = new ArrayList<String> ();
		if(value == null || "".equals(value)){
			return ls;
		}
		
		try{
			String[] ids = value.split(splitChar);
			for(int i = 0; i < ids.length; i ++ ){
				if(!StringUtil.isNullStr(ids[i])){
					ls.add(ids[i].trim());
				}
			}
		}catch(Exception e){
			throw e;
		}
		return ls;
	} 
	
	public static String strArrayToString(List<String> list, String splitChar)throws Exception{
		StringBuffer sb = new StringBuffer ("");
		if(list == null || list.size() == 0){
			return sb.toString();
		}
		
		try{
			for(Iterator<String> it = list.iterator(); it.hasNext(); ){
				String value = it.next();
				sb.append(value);
				if(it.hasNext()){
					sb.append(splitChar);
				}
			}
		}catch(Exception e){
			throw e;
		}
		return sb.toString();
	} 
	
	public static String strSetToString(Set<String> set, String splitChar)throws Exception{
		StringBuffer sb = new StringBuffer ("");
		if(set == null || set.size() == 0){
			return sb.toString();
		}
		
		try{
			for(Iterator<String> it = set.iterator(); it.hasNext(); ){
				String value = it.next();
				sb.append(value);
				if(it.hasNext()){
					sb.append(splitChar);
				}
			}
		}catch(Exception e){
			throw e;
		}
		return sb.toString();
	} 
	
	public static List<Long> setToList(Set<Long> set){
		List<Long> list = new ArrayList<Long> ();
		if(set != null && set.size() > 0){
			for(Iterator<Long> it = set.iterator(); it.hasNext(); ){
				list.add(it.next());
			}
		}
		return list;
	}
	
	public static String longArrayToString(List<Long> values){
		StringBuffer sb = new StringBuffer ();
		if(values == null || values.size() == 0){
			return sb.toString();
		}
		
		try{
			boolean isFirst = true;
			Set<Long> setLong = new HashSet<Long> ();
			for(Iterator<Long> it = values.iterator(); it.hasNext(); ){
				Long value = it.next();
				if(setLong == null || !setLong.contains(value)){
					if(isFirst == false)
						sb.append(",");
					sb.append(value);
					isFirst = false;
				}
			}
		}catch(Exception e){
			log.error("errorMsg:"+e.getMessage());
		}
		return sb.toString();
	}
	
	public static String longArray2String(List<Long> values){
		boolean isChangeDate = false;
		StringBuffer sb = new StringBuffer ();
		if(values == null || values.size() == 0)
			return null;
		
		try{
			Set<Long> setLong = new HashSet<Long> ();
			for(Iterator<Long> it = values.iterator(); it.hasNext(); ){
				Long value = it.next();
				if(setLong == null || !setLong.contains(value)){
					sb.append("'" + value + "',");
					setLong.add(value);
					isChangeDate = true;
				}
			}
		}catch(Exception e){
			log.error("errorMsg:"+e.getMessage());
		}
		return isChangeDate ? sb.toString().substring(0, sb.toString().length() - 1) : sb.toString();
	}
	
	public static List<Long> strListToLongList(List<String> strList)throws Exception{
		List<Long> longList = new ArrayList<Long> ();
		try{
			for(String str : strList){
				longList.add(Long.valueOf(str));
			}
		}catch(Exception e){
			throw e;
		}
		return longList;
	}
	
	public static Long stringToLong(Object value) {
		Long l;
		value = nullToString(value);
		if ("".equals(value)) {
			l = 0L;
		} else {
			try {
				l = Long.valueOf(value.toString());
			} catch (Exception e) {
				l = 0L;
			}
		}
		return l;
	}
	
	/**
	 * 判断字符串是否是整数
	 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value.trim());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static int parseInteger(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * 判断字符串是否是浮点数
	 */
	public static boolean isDouble(String value) {
		try {
			Double d=Double.parseDouble(value.trim());
			String tempD=d.toString();
			if (tempD.contains("."))
				return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是数字
	 */
	public static boolean isNumber(String value) {
		if(value == null || StringUtil.isNullStr(value)) return false;
		return isInteger(value) || isDouble(value);
	}

	/**
	 * 判断字符串是否布尔
	 * @param value
	 * @return
	 */
	public static boolean isBoolean(String value){
		try {
			Boolean b = Boolean.parseBoolean(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/** 判断是否为时间 * */
	public static boolean isDate(String value) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			sdf.parse(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 
	 * 中文转换--文章换行的转换
	 * 
	 * @param str
	 * 
	 * @return
	 */

	public static String getText(String str) {
		if (str == null)
			return ("");
		if (str.equals(""))
			return ("");
		// 建立一个StringBuffer来处理输入数据
		StringBuffer buf = new StringBuffer(str.length() + 6);
		char ch = '\n';
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch == '\r') {
				buf.append(" ");
			} else if (ch == '\n') {
				buf.append(" ");
			} else if (ch == '\t') {
				buf.append("    ");
			} else if (ch == ' ') {
				buf.append(" ");
			} else if (ch == '\'') {
				buf.append("\\'");
			} else if(ch == '<'){
				buf.append("\\<");
			} else if(ch == '>'){
				buf.append("\\>");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}
	//清除特殊字符
	public static String getescapeText(String str) {
		if (str == null)
			return ("");
		if (str.equals(""))
			return ("");
		// 建立一个StringBuffer来处理输入数据
		StringBuffer buf = new StringBuffer(str.length() + 6);
		char ch = '\n';
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch == '\r') {
				buf.append("");
			} else if (ch == '\n') {
				buf.append("");
			} else if (ch == '\t') {
				buf.append("");
			} else if (ch == ' ') {
				buf.append("");
			} else if (ch == '\'') {
				buf.append("");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}
	
	/**
	 * ASCII码表第48～57号为0～9十个阿拉伯数字；65～90号为26个大写英文字母，97～122号为26个小写英文字母
	 */
	public static boolean isCharAndNum(String source){
		StringCharacterIterator sci = new StringCharacterIterator(source);

		for (char c = sci.first(); c != StringCharacterIterator.DONE; c = sci.next()) {
			if ((c > 47 && c < 58) || (c > 64 && c < 91) || (c > 96 && c < 123)) 
				continue;
			else
				return false;
		}
		return true;
	}
	
	/**
	 * 清除所有特殊字符，只保留中英文字符和数字
	 * @param str
	 * @return
	 */
	public static String getEscapeText(String str){
		try{
			String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";     
		    Pattern p = Pattern.compile(regEx);        
		    Matcher m = p.matcher(str);  
		    return m.replaceAll("");
		}catch (Exception e) {
			log.error("errorMsg:"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 清除所有特殊字符，只保留中英文字符和数字
	 * @param str
	 * @return
	 */
	public static boolean isEscapeText(String str){
		boolean flag=false;
		try{
			String regEx = "[`~!@#$%^&*()+=|{}':;',…\\[\\].<>/?~！@#￥%…&*（）——+|{}【】‘；：”“’。，、？]";     
			Pattern p = Pattern.compile(regEx);        
			Matcher m = p.matcher(str);  
			if(m.find())flag=true;
		}catch (Exception e) {
			log.error("errorMsg:"+e.getMessage());
		}
		return flag;
	}
	
	/**
	 * 判断字符串中是否包含除中英文字符和数字外的特殊字符，包含返回true
	 * @param str
	 * @return
	 */
	public static boolean haveEscapeText(String str){
		if(str.replaceAll("[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*-*_*\\s*", "").length()==0){
			return false;
	    }else{
	    	return true;
	    }
	}
	/**
	 * 
	 * 根据转义列表对字符串进行转义(escape)。
	 * 
	 * @param source
	 *            待转义的字符串
	 * 
	 * @param escapeCharMap
	 *            转义列表
	 * 
	 * @return 转义后的字符串
	 */

	public static String escapeCharacter(String source, HashMap escapeCharMap) {

		if (source == null || source.length() == 0) {

			return source;

		}

		if (escapeCharMap.size() == 0) {

			return source;

		}

		StringBuffer sb = new StringBuffer(source.length() + 100);

		StringCharacterIterator sci = new StringCharacterIterator(source);

		for (char c = sci.first();

		c != StringCharacterIterator.DONE;

		c = sci.next()) {

			String character = String.valueOf(c);

			if (escapeCharMap.containsKey(character)) {

				character = (String) escapeCharMap.get(character);

			}

			sb.append(character);

		}

		return sb.toString();

	}

	/**
	 * 
	 * 中文转换--文章换行的转换
	 * 
	 * @param str
	 * 
	 * @return
	 */

	public static String changeEnter(String str) {
		if (str == null)
			return ("");
		if (str.equals(""))
			return ("");
		// 建立一个StringBuffer来处理输入数据
		StringBuffer buf = new StringBuffer(str.length() + 6);
		char ch = '\n';
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch == '\r') {
				buf.append("|");
			} else if (ch == '\n') {
				buf.append("|");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	// 截掉url左边的一级目录名,如/wap/news/index.xml -> /news/index.xml
	public static String trimLeftNode(String str) {
		if (str == null)
			return "";

		if (str.startsWith("/")) {
			int ind = str.indexOf("/", 1);
			if (ind > 0)
				return str.substring(ind);
		}
		return str;
	}
	
	// 截掉url右边的一级目录名,如/wap/news/index.xml -> /wap/news/
	public static String trimRightNode(String str){
		if (str == null)
			return "";

		if(str.endsWith("/")){ 
			str = str.substring(0, str.length() - 1);
		}
		
		int index = 0;
		if ((index = str.lastIndexOf("/")) > 0) {
			return str.substring(0, index);
		}
		return str;
	}

	public static String generatedUrl(int pageType, List<String> sourceList, String nodestr, int maxint) {
		List<String> nodeList = new ArrayList<String>();
		Random rmd = new Random();
		String rstr = "";
		Set<String> cpSet = new HashSet<String>();
		Set<Integer> distNum = new HashSet<Integer>();
		Set<String> distCp = new HashSet<String>();
		for (int i = 0; i < sourceList.size(); i++) {
			String tmpstr = sourceList.get(i);
			if (getSpstr(tmpstr, 1).equals(nodestr)) {
				nodeList.add(tmpstr);
				cpSet.add(getSpstr(tmpstr, 3));
			}
		}
		if (nodeList.size() > maxint) {
			for (int i = 0; i < maxint;) {
				int tmpint = rmd.nextInt(nodeList.size());
				String tmpstr = nodeList.get(tmpint);
				if ((distCp.add(getSpstr(tmpstr, 3)) || distCp.size() >= cpSet
						.size())
						&& distNum.add(tmpint)) {
					rstr += "<a href='" + getSpstr(tmpstr, 4) + "'>"
					+ getSpstr(tmpstr, 2) + "</a><br/>";
					i++;
				}
			}
		} else {
			for (int i = 0; i < nodeList.size(); i++) {
				String tmpstr = nodeList.get(i);
				rstr += "<a href='" + getSpstr(tmpstr, 4) + "'>"
				+ getSpstr(tmpstr, 2) + "</a><br/>";
			}
		}
		return rstr;
	}

	public static String getSpstr(String spstr, int level) {
		String rstr = "";
		for (int i = 0; i < level; i++) {
			if (spstr.indexOf("|*") == -1) {
				rstr = spstr;
				return rstr;
			} else {
				rstr = spstr.substring(0, spstr.indexOf("|*"));
			}
			spstr = spstr.substring(spstr.indexOf("|*") + 2, spstr.length());
		}
		return rstr;
	}

	public static String toString(Object obj) {
		try {
			return obj.toString();
		} catch (Exception e) {
			return "";
		}
	}
	
//	public static String getEncrypt(String mobile,String pid){
//		StringBuffer buf = new StringBuffer();
//		buf.append(mobile);
//		buf.append(pid);
//		buf.append("MDN2000");
////		buf.append(CmsConstants.SYSTEMCONFIG.get("PORTAL_MDN"));//"MDN2000"
//		String md5String = buf.toString();
//        com.wondertek.mobilevideo.core.util.MD5 md5 = new com.wondertek.mobilevideo.core.util.MD5();
//        byte[] byteone = md5String.getBytes();
//        return md5.md5Str(byteone, 0, byteone.length);
//	}
	
	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
        '9', 'a', 'b', 'c', 'd', 'e', 'f' }; 
	
	/**
     * 把byte[]数组转换成十六进制字符串表示形式
     * @param tmp    要转换的byte[]
     * @return 十六进制字符串表示形式
     */
    public static String byteToHexString(byte[] tmp) {
    	if(tmp == null){
    		throw new NullPointerException();
    	}
    	int len = tmp.length;
        char str[] = new char[len * 2];
        int i = 0;
        for(byte b:tmp){
        	str[i*2] = hexDigits[b >>> 4 & 0xf]; // 取字节中高 4 位的数字转换, 
            str[i*2+1] = hexDigits[b & 0xf]; // 取字节中低 4 位的数字转换
            i++;
        }
        return new String(str);
    }
    
    /**
     * 得到一个Long值的指定长度的字符串形式
     * 
     * @param l
     * @param len
     * @return
     */
    public static String getStringByAppointLen(Long l,int len){
    	return getStringByAppointLen(l.toString(), len, true);
    }
    
    /**
     * 得到一个Integer值的指定长度的字符串形式
     * NOTE:	不足的前面添'0'
     * @param i
     * @param len
     * @return
     */
    public static String getStringByAppointLen(Integer i,int len){
    	return getStringByAppointLen(i.toString(), len, true);
    }
    
    /**
     * 得到一个String值的指定长度的字符串形式
     * NOTE:	不足的前面添'0'
     * 			
     * @param s
     * @param len
     * @param cutHead
     * 		当s的长度大于len时，截取方式：true,截掉头部；否则从截掉尾部
     * 		例如getStringByAppointLen("12345",3,true) ---> "345"
     * @return
     */
    public static String getStringByAppointLen(String s,int len,boolean cutHead){
    	if(s == null || len <=0){
    		s = "";
    	}
    	if(len > s.length()){
    		int size = len - s.length();
    		StringBuffer sb = new StringBuffer();
    		while(size -- > 0){
    			sb.append("0");
    		}
    		sb.append(s);
    		return sb.toString();
    	}else if(len == s.length()){
    		return s;
    	}else{
    		if(cutHead){
    			return s.substring(s.length() - len, s.length());
    		}else{
    			return s.substring(0,len);
    		}
    	}
    }
	
	/**
     * 通过ID生成存储路径
     * @param id
     * @return
     */
    public static String getFileDirPathById(Long id){
    	String s = StringUtil.getStringByAppointLen(id, 12);
		StringBuffer sb = new StringBuffer();
		sb.append(s.substring(0,3)).append("/")
			.append(s.substring(3,6)).append("/")
			.append(s.substring(6,9)).append("/")
			.append(s.substring(9,12)).append("/");
		return sb.toString();
	}
	
    public static Boolean string2Boolean(String str){
    	try{
    		if("0".equals(str))
    			return Boolean.FALSE;
    		else if("1".equals(str))
    			return Boolean.TRUE;
    		else if("false".equalsIgnoreCase(str))
    			return Boolean.FALSE;
    		else if("true".equalsIgnoreCase(str))
    			return Boolean.TRUE;
    	}catch(Exception e){
    		return Boolean.FALSE;
    	}
		return Boolean.FALSE;
	}
    
    public static String trimSqlIds(String ids){
    	String tmpIds=ids;
    	if(tmpIds!=null&&!"".equals(tmpIds)){
		if(tmpIds.endsWith(","))
			tmpIds=tmpIds.substring(0,tmpIds.length()-1);
		if(tmpIds.startsWith(","))
			tmpIds=tmpIds.substring(1,tmpIds.length());
    	}else tmpIds="";
    	return tmpIds;
    }
    
    public static String formatData(String tpl,double data){
    	DecimalFormat df = new DecimalFormat(tpl);
		String format = df.format(data);
		
		return format;
    }
    
	/**
	 * 对关键词过滤SQL特殊字符
	 * @param name
	 * @return
	 */
	/*public static String parese(String name) {
		if(name==null||"".equals(name))return "";
		name = StringUtils.replace(name, ">", "");
		name = StringUtils.replace(name, "<", "");
		name = StringUtils.replace(name, "\"", ""); //'双引号
		name = StringUtils.replace(name,"'", "");// '单引号
		name = StringUtils.replace(name, " ", "");// '空格
		name = StringUtils.replace(name, "	", "");// 'tab键值
		name = StringUtils.replace(name, "\r", "");// '换行
		name = StringUtils.replace(name, "\n", "");// '回车 
		name = StringUtils.replace(name, "?", "");// '回车 
		return name;
	}*/
	
	/*public static String object2String(Object value) {
		if (null == value)
			return "";
		String result = "";
		String df = "yyyy-MM-dd HH:mm:ss:SSS";
		if (value instanceof String)
			result = org.apache.commons.lang.StringUtils.trimToEmpty((String) value);
		else if (value instanceof Integer)
			result = ((Integer) value).toString();
		else if (value instanceof Date)
			result = (new SimpleDateFormat(df)).format((Date) value);
		else if (value instanceof Long)
			result = ((Long) value).toString();
		else if (value instanceof Number)
			result = ((Number) value).toString();
		else if (value instanceof Boolean)
			result = ((Boolean) value) ? "true" : "false";
		else if (value instanceof Short)
			result = ((Short) value).toString();
		else if (value instanceof Double)
			result = ((Double) value).toString();
		else if (value instanceof Float)
			result = ((Number) value).toString();
		else
			result = value.toString();
		return result;
	}*/
}
