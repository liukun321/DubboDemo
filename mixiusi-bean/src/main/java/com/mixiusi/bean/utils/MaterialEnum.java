package com.mixiusi.bean.utils;
/**
 * 咖啡机物料料盒编号和物料名称对应关系
 * @author liukun
 *
 */
public enum MaterialEnum {
	Number1("正常咖啡豆", 1), Number2("低因咖啡豆", 2), Number3("抹茶粉", 3), Number4("可可粉", 4),
	Number5("牛奶", 5), Number6("香草糖浆", 6), Number7("榛果糖浆", 7), Number8("焦糖糖浆", 8),
	Number9("纯糖浆", 9), Number10("桶装水", 10), Number11("杯子", 11);  
    // 成员变量  
    private String name;  
    private int index;  
    // 构造方法  
    private MaterialEnum(String name, int index) {  
        this.name = name;  
        this.index = index;  
    }  
    // 普通方法  
    public static String getName(int index) {  
        for (MaterialEnum c : MaterialEnum.values()) {  
            if (c.getIndex() == index) {  
                return c.name;  
            }  
        }  
        return null;  
    }
	public String getName() {
		return name;
	}
	public int getIndex() {
		return index;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setIndex(int index) {
		this.index = index;
	}
    
}
