package com.mixiusi.protocol;

public class MachineMaterialMap {
	
	// 料盒对应配料关联
	public static final int MATERIAL_WATER = 10; //water
	
	public static final int MATERIAL_BOX_1 = 1;  //奶粉
	
	public static final int MATERIAL_BOX_2 = 2;  //糿
	
	public static final int MATERIAL_BOX_3 = 3;  //巧克势
	
	public static final int MATERIAL_BOX_4 = 4;  //奶茶
	
	public static final int MATERIAL_COFFEE_BEAN = 9; //咖啡bean

    public static final int MATERIAL_COFFEE_CUP_NUM = 100; //咖啡cup
	
	// 警戒倿
//		public static final int MATERIAL_WATER_ALARM_VALUE = 4000;  //ml
	
//		public static final int MATERIAL_BOX_1_ALARM_VALUE = 200;   //g
	
//		public static final int MATERIAL_BOX_2_ALARM_VALUE = 100;   //g
	
//		public static final int MATERIAL_BOX_3_ALARM_VALUE = 100;   //g
	
//		public static final int MATERIAL_BOX_4_ALARM_VALUE = 150;   //g
	
//		public static final int MATERIAL_COFFEE_BEAN_ALARM_VALUE = 200; //g

//  public static final int MATERIAL_COFFEE_CUP_NUM_ALARM_VALUE = 50; //丿

    // 朿低库存忿
    public static final int MATERIAL_WATER_LIMIT_VALUE = 1500;  //ml

    public static final int MATERIAL_BOX_1_LIMIT_VALUE = 0;   //g

    public static final int MATERIAL_BOX_2_LIMIT_VALUE = 0;   //g

    public static final int MATERIAL_BOX_3_LIMIT_VALUE = 0;   //g

    public static final int MATERIAL_BOX_4_LIMIT_VALUE = 0;   //g

    public static final int MATERIAL_COFFEE_BEAN_LIMIT_VALUE = 0; //g

    public static final int MATERIAL_COFFEE_CUP_NUM_LIMIT_VALUE = 4; //丿

    // 转换因子
    public static final double FACTOR_WATER = 1;

    public static final double FACTOR_BOX_1 = 1.6; // g/s

    public static final double FACTOR_BOX_2 = 2.7;

    public static final double FACTOR_BOX_3 = 2.2;

    public static final double FACTOR_BOX_4 = 2.2;

    public static final double FACTOR_COFFEE_BEAN = 1.0;

    public static int transferToMachine(int index, double value){
        int ret = 0;
        switch (index){
            case MATERIAL_WATER:
                ret = (int)((value / FACTOR_WATER) * 10.0);
                break;
            case MATERIAL_BOX_1:
                ret = (int)((value / FACTOR_BOX_1) * 10.0);
                break;
            case MATERIAL_BOX_2:
                ret = (int)((value / FACTOR_BOX_2) * 10.0);
                break;
            case MATERIAL_BOX_3:
                ret = (int)((value / FACTOR_BOX_3) * 10.0);
                break;
            case MATERIAL_BOX_4:
                ret = (int)((value / FACTOR_BOX_4) * 10.0);
                break;
            case MATERIAL_COFFEE_BEAN:
                ret = (int)((value / FACTOR_COFFEE_BEAN) * 10.0);
                break;
        }

        return ret;
    }
}
