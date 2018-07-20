package com.mixiusi.bean.utils.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mixiusi.bean.utils.StringUtils;

/**
 * Simple to Introduction
 *
 * @ProjectName: [${project_name}]
 * @Package: [${package_name}.${file_name}]
 * @ClassName: [${type_name}]
 * @Description: [一句话描述该类的功能]
 * @Author: [${user}]
 * @CreateDate: [${date} ${time}]
 * @UpdateUser: [${user}]
 * @UpdateDate: [${date} ${time}]
 * @UpdateRemark: [说明本次修改内容]
 * @Version: [v1.0]
 */
public class ExcelExportUtils {

    public static final String FILE_EXTENSION_XLS = "xls";
    public static final String FILE_EXTENSION_XLSX = "xlsx";
    /**
     * 导出数据
     * @param os
     * @param data
     * @throws IOException
     */
    public static void exportToExcel(OutputStream os, List<List<String>> data) throws IOException {
        HSSFWorkbook wb=null;
        try {
            wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Data");
            for (int r = 0 ; r < data.size() ; r++) {
                HSSFRow row = sheet.createRow(r);
                List<String> cols = data.get(r);
                for (int c = 0 ; c < cols.size() ; c++) {
                    HSSFCell cell = row.createCell(c);
                    cell.setCellValue(new HSSFRichTextString(cols.get(c)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(wb!=null){
                wb.write(os);
            }
            os.flush();
            os.close();
        }
    }


    /**
     * 功能: 导出为Excel工作簿
     * 参数: sheetName[工作簿中的一张工作表的名称]
     * 参数: titleName[表格的标题名称]
     * 参数: headers[表格每一列的列名]
     * 参数: dataSet[要导出的数据源]
     * 参数: resultUrl[导出的excel文件地址]
     * 参数: pattern[时间类型数据的格式]
     */
    public static void exportExcel(String sheetName,String titleName,String[] headers,Collection<?> dataSet,String resultUrl,String pattern) {

        doExportExcel(sheetName,titleName,headers,dataSet,resultUrl,pattern);

    }

    /**
     * 功能:真正实现导出
     */
    private static void doExportExcel(String sheetName,String titleName,String[] headers,Collection<?> dataSet,String resultUrl,String pattern) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个工作表
        HSSFSheet sheet = workbook.createSheet(sheetName);
        // 设置工作表默认列宽度为20个字节
        sheet.setDefaultColumnWidth((short) 20);
        //在工作表中合并首行并居中
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,headers.length-1));

        // 创建[标题]样式
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        // 设置[标题]样式
        titleStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //创建[标题]字体
        HSSFFont titleFont = workbook.createFont();
        //设置[标题]字体
        titleFont.setColor(HSSFColor.WHITE.index);
        titleFont.setFontHeightInPoints((short) 24);
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把[标题字体]应用到[标题样式]
        titleStyle.setFont(titleFont);
        // 创建[列首]样式
        HSSFCellStyle headersStyle = workbook.createCellStyle();
        // 设置[列首]样式
        headersStyle.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
        headersStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headersStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headersStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headersStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headersStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headersStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //创建[列首]字体
        HSSFFont headersFont = workbook.createFont();
        //设置[列首]字体
        headersFont.setColor(HSSFColor.VIOLET.index);
        headersFont.setFontHeightInPoints((short) 12);
        headersFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把[列首字体]应用到[列首样式]
        headersStyle.setFont(headersFont);

        // 创建[表中数据]样式
        HSSFCellStyle dataSetStyle = workbook.createCellStyle();
        // 设置[表中数据]样式
        dataSetStyle.setFillForegroundColor(HSSFColor.GOLD.index);
        dataSetStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        dataSetStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        dataSetStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        dataSetStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        dataSetStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        dataSetStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        dataSetStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 创建[表中数据]字体
        HSSFFont dataSetFont = workbook.createFont();
        // 设置[表中数据]字体
        dataSetFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        dataSetFont.setColor(HSSFColor.BLUE.index);
        // 把[表中数据字体]应用到[表中数据样式]
        dataSetStyle.setFont(dataSetFont);
        //创建标题行-增加样式-赋值
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellStyle(titleStyle);
        titleCell.setCellValue(titleName);
        // 创建列首-增加样式-赋值
        HSSFRow row = sheet.createRow(1);
        for (short i = 0; i < headers.length; i++) {
            @SuppressWarnings("deprecation")
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(headersStyle);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        // 创建表中数据行-增加样式-赋值
        Iterator<?> it = dataSet.iterator();
        int index = 1;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            Object t = it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < fields.length; i++) {
                @SuppressWarnings("deprecation")
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(dataSetStyle);
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get"+ fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);
                try {
                    @SuppressWarnings("rawtypes")
                    Class tCls = t.getClass();
                    @SuppressWarnings("unchecked")
                    Method getMethod = tCls.getMethod(getMethodName,new Class[] {});
                    Object value = getMethod.invoke(t, new Object[] {});

                    // 如果是时间类型,按照格式转换
                    String textValue = null;
                    if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        textValue = value.toString();
                    }

                    // 利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^\\d+(\\.\\d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            // 不是数字做普通处理
                            cell.setCellValue(textValue);
                        }
                    }
                    OutputStream out=null;
                    try {
                        out = new FileOutputStream(resultUrl);
                        workbook.write(out);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally{
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                    //清理资源
                    try {
                        workbook.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     *
     * @param maps
     *            <String,String> maps 属性表，成员属性age为KEY，中文名称为VALUE
     * @param list
     *            <T> list 需要导出的数据列表对象
     * @param file
     *            file 指定输出文件位置，只能导出excel2003以上版本
     *
     * @return true 导出成功 false 导出失败
     */
    public static <T> boolean excelExport(Map<String, String> maps, List<T> list, File file) {

        try {
            Workbook wb = null;
            String filename = file.getName();
            String type = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            if (type.equals(FILE_EXTENSION_XLS)) {
                wb = new HSSFWorkbook();
            }
            if (type.equals(FILE_EXTENSION_XLSX)) {
                wb = new XSSFWorkbook();
            }
            CreationHelper createHelper = wb.getCreationHelper();
            Sheet sheet = wb.createSheet("sheet1");
            Set<String> sets = maps.keySet();
            Row row = sheet.createRow(0);
            int i = 0;
            // 定义表头
            for (Iterator<String> it = sets.iterator(); it.hasNext();) {
                String key = it.next();
                Cell cell = row.createCell(i++);
                cell.setCellValue(createHelper.createRichTextString(maps.get(key)));
            }
            // 填充表单内容
            System.out.println("--------------------100%");
            float avg = list.size() / 20f;
            int count = 1;
            for (int j = 0; j < list.size(); j++) {
                T p = list.get(j);
                Class classType = p.getClass();
                int index = 0;
                Row row1 = sheet.createRow(j+1);
                for (Iterator<String> it = sets.iterator(); it.hasNext();) {
                    String key = it.next();
                    String firstLetter = key.substring(0, 1).toUpperCase();
                    // 获得和属性对应的getXXX()方法的名字
                    String getMethodName = "get" + firstLetter+ key.substring(1);
                    // 获得和属性对应的getXXX()方法
                    Method getMethod = classType.getMethod(getMethodName, new Class[] {});
                    // 调用原对象的getXXX()方法
                    Object value = getMethod.invoke(p, new Object[] {});
                    Cell cell = row1.createCell(index++);
                    if(!StringUtils.isNull(value))
                    	cell.setCellValue(value.toString());
                }
                if (j > avg * count) {
                    count++;
                    System.out.print("I");
                }
                if (count == 20) {
                    System.out.print("I100%");
                    count++;
                }
            }
            FileOutputStream fileOut = new FileOutputStream(file);
            wb.write(fileOut);
            fileOut.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (SecurityException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

//    public static void main(String[] args) throws IOException {
//        FileOutputStream os = new FileOutputStream(new File("D:/mixiusi_work/Table.xlsx"));
//        List<List<String>> data = new ArrayList<List<String>>();
//        List<String> s = new ArrayList<String>();
//        s.add("11");
//        s.add("22");
//        s.add("33");
//        data.add(s);
//        List<String> s1 = new ArrayList<String>();
//        s1.add("11");
//        s1.add("22");
//        s1.add("33");
//        data.add(s1);
//        exportToExcel(os, data);
//        System.out.println("data:"+data);
//    }
}
