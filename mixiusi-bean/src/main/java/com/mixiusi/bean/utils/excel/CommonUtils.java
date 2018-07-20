package com.mixiusi.bean.utils.excel;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class CommonUtils {
    private static final Logger log=Logger.getLogger(CommonUtils.class);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss ");

    private File excelFile;

    private InputStream fileInStream;

    private Workbook workBook;

    //%%%%%%%%-------常量部分 开始----------%%%%%%%%%
    /**
     * 默认的开始读取的行位置为第一行（索引值为0）
     */
    private final static int READ_START_POS = 0;
    /**
     * 默认结束读取的行位置为最后一行（索引值=0，用负数来表示倒数第n行）
     */
    private final static int READ_END_POS = 0;
    /**
     * 默认Excel内容的开始比较列位置为第一列（索引值为0）
     */
    private final static int COMPARE_POS = 0;
    /**
     * 默认多文件合并的时需要做内容比较（相同的内容不重复出现）
     */
    private final static boolean NEED_COMPARE = true;
    /**
     * 默认多文件合并的新文件遇到名称重复时，进行覆盖
     */
    private final static boolean NEED_OVERWRITE = true;

    /**
     * 默认只操作一个sheet
     */
    private final static boolean ONLY_ONE_SHEET = true;

    /**
     * 默认读取第一个sheet中（只有当ONLY_ONE_SHEET = true时有效）
     */
    private final static int SELECTED_SHEET = 0;

    /**
     * 默认从第一个sheet开始读取（索引值为0）
     */
    private final static int READ_START_SHEET= 0;

    /**
     * 默认在最后一个sheet结束读取（索引值=0，用负数来表示倒数第n行）
     */
    private final static int READ_END_SHEET = 0;

    /**
     * 默认打印各种信息
     */
    private final static boolean PRINT_MSG = true;

    //%%%%%%%%-------常量部分 结束----------%%%%%%%%%
    //%%%%%%%%-------字段部分 开始----------%%%%%%%%%
    /**
     * Excel文件路径
     */
    private String excelPath = "data.xlsx";

    /**
     * 设定开始读取的位置，默认为0
     */
    private static int startReadPos = READ_START_POS;


    /**
     * 设定开始读取的sheet，默认为0
     */
    private int startSheetIdx = READ_START_SHEET;

    /**
     * 设定结束读取的sheet，默认为0，用负数来表示倒数第n行
     */
    private int endSheetIdx = READ_END_SHEET;

    /**
     * 设定是否打印消息
     */
    private boolean printMsg = PRINT_MSG;

    /**
     * 设定结束读取的位置，默认为0，用负数来表示倒数第n行
     */
    private static int endReadPos = READ_END_POS;

    /**
     * 设定开始比较的列位置，默认为0
     */
    private static int comparePos = COMPARE_POS;

    /**
     *  设定汇总的文件是否需要替换，默认为true
     */
    private static boolean isOverWrite = NEED_OVERWRITE;

    /**
     *  设定是否需要比较，默认为true(仅当不覆写目标内容是有效，即isOverWrite=false时有效)
     */
    private static boolean isNeedCompare = NEED_COMPARE;

    /**
     * 设定是否只操作第一个sheet
     */
    private boolean onlyReadOneSheet = ONLY_ONE_SHEET;

    /**
     * 设定操作的sheet在索引值
     */
    private int selectedSheetIdx =SELECTED_SHEET;

    /**
     * 设定操作的sheet的名称
     */
    private String selectedSheetName = "";



    //%%%%%%%%-------字段部分 结束----------%%%%%%%%%
    /***************************************************************/

    /***************************************************************/
    HttpServletResponse response;
    // 文件名
    private String fileName ;
    //文件保存路径
    private String fileDir;
    //sheet名
    private String sheetName;
    //表头字体
    private String titleFontType = "Arial Unicode MS";
    //表头背景色
    private String titleBackColor = "C1FBEE";
    //表头字号
    private short titleFontSize = 12;
    //添加自动筛选的列 如 A:M
    private String address = "";
    //正文字体
    private String contentFontType = "Arial Unicode MS";
    //正文字号
    private short contentFontSize = 12;
    //Float类型数据小数位
    private String floatDecimal = ".00";
    //Double类型数据小数位
    private String doubleDecimal = ".00";
    //设置列的公式
    private String colFormula[] = null;

    DecimalFormat floatDecimalFormat=new DecimalFormat(floatDecimal);
    DecimalFormat doubleDecimalFormat=new DecimalFormat(doubleDecimal);

    private HSSFWorkbook workbook = null;

    public CommonUtils() {
    }

    public CommonUtils(HttpServletResponse response,String fileName,String sheetName) {
        this.response = response;
        this.sheetName = sheetName;
        workbook = new HSSFWorkbook();
    }



    public CommonUtils(String fileDir,String sheetName) {
        this.fileDir = fileDir;
        this.sheetName = sheetName;
        workbook = new HSSFWorkbook();
    }

    public CommonUtils(File file) throws Exception {
        this.excelFile = file;
        this.fileInStream = new FileInputStream(this.excelFile);
        this.workBook = WorkbookFactory.create(this.fileInStream);
    }

    /**
     * 功能：创建CellStyle样式
     * @param 	wb				HSSFWorkbook
     * @param 	backgroundColor	背景色
     * @param 	foregroundColor	前置色
     * @param	font			字体
     * @return	CellStyle
     */
    public static CellStyle createCellStyle(HSSFWorkbook wb, short backgroundColor, short foregroundColor, short halign, Font font){
        CellStyle cs=wb.createCellStyle();
        cs.setAlignment(halign);
        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs.setFillBackgroundColor(backgroundColor);
        cs.setFillForegroundColor(foregroundColor);
        cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs.setFont(font);
        return cs;
    }

    private static HSSFCellStyle cellstyle = null;
    private static HSSFCellStyle createCellStyle(Workbook wb) {
        cellstyle = (HSSFCellStyle) wb.createCellStyle();
        cellstyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellstyle.setAlignment(CellStyle.THICK_VERT_BANDS);
        cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return cellstyle;
    }

    private static CellStyle createBorderedStyle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }

    /**
     * 功能：创建带边框的CellStyle样式
     * @param 	wb				HSSFWorkbook
     * @param 	backgroundColor	背景色
     * @param 	foregroundColor	前置色
     * @param	font			字体
     * @return	CellStyle
     */
    public static CellStyle createBorderCellStyle(HSSFWorkbook wb,short backgroundColor,short foregroundColor,short halign,Font font){
        CellStyle cs=wb.createCellStyle();
        cs.setAlignment(halign);
        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs.setFillBackgroundColor(backgroundColor);
        cs.setFillForegroundColor(foregroundColor);
        cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs.setFont(font);
        cs.setBorderLeft(CellStyle.BORDER_DASHED);
        cs.setBorderRight(CellStyle.BORDER_DASHED);
        cs.setBorderTop(CellStyle.BORDER_DASHED);
        cs.setBorderBottom(CellStyle.BORDER_DASHED);
        return cs;
    }
    /**
     * 功能：创建CELL
     * @param 	row		HSSFRow
     * @param 	cellNum	int
     * @param 	style	HSSFStyle
     * @return	HSSFCell
     */
    public static HSSFCell createCell(HSSFRow row, int cellNum, CellStyle style){
        HSSFCell cell=row.createCell(cellNum);
        cell.setCellStyle(style);
        return cell;
    }
    /**
     * 功能：合并单元格
     * @param 	sheet		HSSFSheet
     * @param 	firstRow	int
     * @param 	lastRow		int
     * @param 	firstColumn	int
     * @param 	lastColumn	int
     * @return	int			合并区域号码
     */
    public static int mergeCell(HSSFSheet sheet, int firstRow, int lastRow, int firstColumn, int lastColumn){
        return sheet.addMergedRegion(new CellRangeAddress(firstRow,lastRow,firstColumn,lastColumn));
    }
    /**
     * 功能：创建字体
     * @param 	wb			HSSFWorkbook
     * @param 	boldweight	short
     * @param 	color		short
     * @return	Font
     */
    public static Font createFont(HSSFWorkbook wb,short boldweight,short color,short size){
        Font font=wb.createFont();
        font.setBoldweight(boldweight);
        font.setColor(color);
        font.setFontHeightInPoints(size);
        return font;
    }
    /**
     * 设置合并单元格的边框样式
     * @param	sheet	HSSFSheet
     * @param 	ca		CellRangAddress
     * @param 	style	CellStyle
     */
    public static void setRegionStyle(HSSFSheet sheet, CellRangeAddress ca,CellStyle style) {
        for (int i = ca.getFirstRow(); i <= ca.getLastRow(); i++) {
            HSSFRow row = HSSFCellUtil.getRow(i, sheet);
            for (int j = ca.getFirstColumn(); j <= ca.getLastColumn(); j++) {
                HSSFCell cell = HSSFCellUtil.getCell(row, j);
                cell.setCellStyle(style);
            }
        }
    }

    /**
     * 功能：创建HSSFSheet工作簿
     * @param 	wb	HSSFWorkbook
     * @param 	sheetName	String
     * @return	HSSFSheet
     */
    public static HSSFSheet createSheet(HSSFWorkbook wb,String sheetName){
        HSSFSheet sheet=wb.createSheet(sheetName);
        sheet.setDefaultColumnWidth(12);
        sheet.setGridsPrinted(false);
        sheet.setDisplayGridlines(false);
        return sheet;
    }
    /**
     * 功能：创建HSSFRow
     * @param 	sheet	HSSFSheet
     * @param 	rowNum	int
     * @param 	height	int
     * @return	HSSFRow
     */
    public static HSSFRow createRow(HSSFSheet sheet,int rowNum,int height){
        HSSFRow row=sheet.createRow(rowNum);
        row.setHeight((short)height);
        return row;
    }

    /**
     * 功能：将HSSFWorkbook写入Excel文件
     * @param 	wb		HSSFWorkbook
     * //@param 	absPath	写入文件的相对路径
     * @param 	fileName	文件名
     */
    public static void writeWorkbook(HSSFWorkbook wb,String fileName){
        FileOutputStream fos=null;
        try {
            fos=new FileOutputStream(fileName);
            wb.write(fos);
        } catch (FileNotFoundException e) {
            log.error(new StringBuffer("[").append(e.getMessage()).append("]").append(e.getCause()));
        } catch (IOException e) {
            log.error(new StringBuffer("[").append(e.getMessage()).append("]").append(e.getCause()));
        } finally{
            try {
                if(fos!=null){
                    fos.close();
                }
            } catch (IOException e) {
                log.error(new StringBuffer("[").append(e.getMessage()).append("]").append(e.getCause()));
            }
        }
    }

    /**
     * 取Excel所有数据，包含header
     * @return  List<String[]>
     */
    public static  List<List<Object>> getExcelData(File file, int sheetIndex, boolean isNeedDecimal){
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        Workbook wbs;
        InputStream io = null;
        try {
            io = new FileInputStream(file);
            if (file.getName().endsWith("xls")) {
                wbs = new HSSFWorkbook(io);
            }else{
                wbs = new XSSFWorkbook(io);
            }
            Sheet childSheet = wbs.getSheetAt(sheetIndex);
            for(int i = childSheet.getFirstRowNum();i <= childSheet.getLastRowNum();i++){
                Row row = childSheet.getRow(i);
                List<Object> data = new ArrayList<Object>();
                for(int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++ ){
                    Cell cell = row.getCell(j);
                    data.add(getCellData(cell,isNeedDecimal));
                }
                dataList.add(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                io.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  dataList;
    }

    public static Object getCellData(Cell cell,boolean isNeedDecimal){
        int cellType = cell.getCellType();
        Object data = null;
        switch (cellType) {
            case Cell.CELL_TYPE_FORMULA :
                data =cell.getCellFormula();
                cell.setCellType(Cell.CELL_TYPE_STRING);
                data = cell.getStringCellValue();
                if(data!=null){
                    data = data.toString().replaceAll("#N/A","").trim();
                }

                break;
            case Cell.CELL_TYPE_NUMERIC :
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // 如果是在Date类型，则取得该Cell的Date值
                    Date date = cell.getDateCellValue();
                    if (date!=null) {
                        data = dateFormat.format(date);
                    }
                } else {
                    // 如果是纯数字
                    // 取得当前cell的数值
                    if (isNeedDecimal) {
                        data = cell.getNumericCellValue();
                    }else {
                        data = (int)cell.getNumericCellValue();
                    }
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN :
                data = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_STRING :
                data = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BLANK :
                data = "";
                break;
            default :
                data = "";
                break;
        }
        return data;
    }

    public static List<List<Object>> getExcelData(File file,boolean isNeedDecimal){
        return getExcelData(file,0,isNeedDecimal);
    }

    /**
     * 写入一组值
     *
     * @param sheetNum
     *            写入的sheet的编号
     * @param fillRow
     *            是写入行还是写入列
     * @param startRowNum
     *            开始行号
     * @param startColumnNum
     *            开始列号
     * @param contents
     *            写入的内容数组
     * @throws Exception
     */
    public void writeArrayToExcel(int sheetNum, boolean fillRow, int startRowNum, int startColumnNum, Object[] contents) throws Exception {
        Sheet sheet = this.workBook.getSheetAt(sheetNum);
        writeArrayToExcel(sheet, fillRow, startRowNum, startColumnNum, contents);
    }

    /**
     * 写入一组值
     *
     * @param sheetName
     *            写入的sheet的名称
     * @param fillRow
     *            是写入行还是写入列
     * @param startRowNum
     *            开始行号
     * @param startColumnNum
     *            开始列号
     * @param contents
     *            写入的内容数组
     * @throws Exception
     */
    public void writeArrayToExcel(String sheetName, boolean fillRow,
                                  int startRowNum, int startColumnNum, Object[] contents)
            throws Exception {
        Sheet sheet = this.workBook.getSheet(sheetName);
        writeArrayToExcel(sheet, fillRow, startRowNum, startColumnNum, contents);
    }

    private void writeArrayToExcel(Sheet sheet, boolean fillRow, int startRowNum, int startColumnNum, Object[] contents) throws Exception {
        for (int i = 0, length = contents.length; i < length; i++) {
            int rowNum;
            int columnNum;
            // 以行为单位写入
            if (fillRow) {
                rowNum = startRowNum;
                columnNum = startColumnNum + i;
            }
            // 　以列为单位写入
            else {
                rowNum = startRowNum + i;
                columnNum = startColumnNum;
            }
            this.writeToCell(sheet, rowNum, columnNum, convertString(contents[i]));
        }
    }

    /**
     * 向一个单元格写入值
     *
     * @param sheetNum
     *            sheet的编号
     * @param rowNum
     *            行号
     * @param columnNum
     *            列号
     * @param value
     *            写入的值
     * @throws Exception
     */
    public void writeToExcel(int sheetNum, int rowNum, int columnNum,
                             Object value) throws Exception {
        Sheet sheet = this.workBook.getSheetAt(sheetNum);
        this.writeToCell(sheet, rowNum, columnNum, value);
    }

    /**
     * 向一个单元格写入值
     *
     * @param sheetName
     *            sheet的名称
     * @param rowNum
     *            单元格的位置
     * @param value
     *            写入的值
     * @throws Exception
     */
    public void writeToExcel(String sheetName, int rowNum, int columnNum,
                             Object value) throws Exception {
        Sheet sheet = this.workBook.getSheet(sheetName);
        this.writeToCell(sheet, rowNum, columnNum, value);
    }

    /**
     * 向一个单元格写入值
     *
     * @param sheetNum
     *            sheet的编号
     * @param columnRowNum
     *            单元格的位置
     * @param value
     *            写入的值
     * @throws Exception
     */
    public void writeToExcel(int sheetNum, String columnRowNum, Object value)
            throws Exception {
        Sheet sheet = this.workBook.getSheetAt(sheetNum);
        this.writeToCell(sheet, columnRowNum, value);
    }

    /**
     * 向一个单元格写入值
     *
     * @param sheetName
     *            sheet的名称
     * @param columnRowNum
     *            单元格的位置
     * @param value
     *            写入的值
     * @throws Exception
     */
    public void writeToExcel(String sheetName, String columnRowNum, Object value)
            throws Exception {
        Sheet sheet = this.workBook.getSheet(sheetName);
        this.writeToCell(sheet, columnRowNum, value);
    }

    private void writeToCell(Sheet sheet, String columnRowNum, Object value)
            throws Exception {
        int[] rowNumColumnNum = convertToRowNumColumnNum(columnRowNum);
        int rowNum = rowNumColumnNum[0];
        int columnNum = rowNumColumnNum[1];
        this.writeToCell(sheet, rowNum, columnNum, value);
    }

    /**
     * 将单元格的行列位置转换为行号和列号
     *
     * @param columnRowNum
     *            行列位置
     * @return 长度为2的数组，第1位为行号，第2位为列号
     */
    private static int[] convertToRowNumColumnNum(String columnRowNum) {
        columnRowNum = columnRowNum.toUpperCase();
        char[] chars = columnRowNum.toCharArray();
        int rowNum = 0;
        int columnNum = 0;
        for (char c : chars) {
            if ((c >= 'A' && c <= 'Z')) {
                columnNum = columnNum * 26 + ((int) c - 64);
            } else {
                rowNum = rowNum * 10 + new Integer(c + "");
            }
        }
        return new int[] { rowNum - 1, columnNum - 1 };
    }

    private void writeToCell(Sheet sheet, int rowNum, int columnNum,
                             Object value) throws Exception {
        Row row = sheet.getRow(rowNum);
        Cell cell = row.getCell(columnNum);
        if (cell == null) {
            cell = row.createCell(columnNum);
        }
        cell.setCellValue(convertString(value));
    }

    /**
     * 读取一个单元格的值
     *
     * @param sheetName
     *            sheet的名称
     * @param columnRowNum
     *            单元格的位置
     * @return
     * @throws Exception
     */
    public Object readCellValue(String sheetName, String columnRowNum)
            throws Exception {
        Sheet sheet = this.workBook.getSheet(sheetName);
        int[] rowNumColumnNum = convertToRowNumColumnNum(columnRowNum);
        int rowNum = rowNumColumnNum[0];
        int columnNum = rowNumColumnNum[1];
        Row row = sheet.getRow(rowNum);
        if (row != null) {
            Cell cell = row.getCell(columnNum);
            if (cell != null) {
                return getCellValue(cell);
            }
        }
        return null;
    }


    /**
     * 插入一行并参照与上一行相同的格式
     *
     * @param sheetNum
     *            sheet的编号
     * @param rowNum
     *            插入行的位置
     * @throws Exception
     */
    public void insertRowWithFormat(int sheetNum, int rowNum) throws Exception {
        Sheet sheet = this.workBook.getSheetAt(sheetNum);
        insertRowWithFormat(sheet, rowNum);
    }

    /**
     * 插入一行并参照与上一行相同的格式
     *
     * @param sheetName
     *            sheet的名称
     * @param rowNum
     *            插入行的位置
     * @throws Exception
     */
    public void insertRowWithFormat(String sheetName, int rowNum)
            throws Exception {
        Sheet sheet = this.workBook.getSheet(sheetName);
        insertRowWithFormat(sheet, rowNum);
    }

    private void insertRowWithFormat(Sheet sheet, int rowNum) throws Exception {
        sheet.shiftRows(rowNum, rowNum + 1, 1);
        Row newRow = sheet.createRow(rowNum);
        Row oldRow = sheet.getRow(rowNum - 1);
        for (int i = oldRow.getFirstCellNum(); i < oldRow.getLastCellNum(); i++) {
            Cell oldCell = oldRow.getCell(i);
            if (oldCell != null) {
                CellStyle cellStyle = oldCell.getCellStyle();
                newRow.createCell(i).setCellStyle(cellStyle);
            }
        }
    }

    /**
     * 重命名一个sheet
     *
     * @param sheetNum
     *            sheet的编号
     * @param newName
     *            新的名称
     */
    public void renameSheet(int sheetNum, String newName) {
        this.workBook.setSheetName(sheetNum, newName);
    }

    /**
     * 重命名一个sheet
     *
     * @param oldName
     *            旧的名称
     * @param newName
     *            新的名称
     */
    public void renameSheet(String oldName, String newName) {
        int sheetNum = this.workBook.getSheetIndex(oldName);
        this.renameSheet(sheetNum, newName);
    }

    /**
     * 删除一个sheet
     *
     * @param sheetName
     *            sheet的名称
     */
    public void removeSheet(String sheetName) {
        this.workBook.removeSheetAt(this.workBook.getSheetIndex(sheetName));
    }

    /**
     * 写入Excel文件并关闭
     */
    public void writeAndClose() {
        if (this.workBook != null) {
            try {
                FileOutputStream fileOutStream = new FileOutputStream(
                        this.excelFile);
                this.workBook.write(fileOutStream);
                if (fileOutStream != null) {
                    fileOutStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.fileInStream != null) {
            try {
                this.fileInStream.close();
            } catch (Exception e) {
            }
        }
    }

    private static String convertString(Object value) {
        if (value == null) {
            return "";
        } else {
            return value.toString();
        }
    }

    /**
     * 设置表头字体.
     * @param titleFontType
     */
    public void setTitleFontType(String titleFontType) {
        this.titleFontType = titleFontType;
    }
    /**
     * 设置表头背景色.
     * @param titleBackColor 十六进制
     */
    public void setTitleBackColor(String titleBackColor) {
        this.titleBackColor = titleBackColor;
    }
    /**
     * 设置表头字体大小.
     * @param titleFontSize
     */
    public void setTitleFontSize(short titleFontSize) {
        this.titleFontSize = titleFontSize;
    }
    /**
     * 设置表头自动筛选栏位,如A:AC.
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * 设置正文字体.
     * @param contentFontType
     */
    public void setContentFontType(String contentFontType) {
        this.contentFontType = contentFontType;
    }
    /**
     * 设置正文字号.
     * @param contentFontSize
     */
    public void setContentFontSize(short contentFontSize) {
        this.contentFontSize = contentFontSize;
    }
    /**
     * 设置float类型数据小数位 默认.00
     * @param doubleDecimal 如 ".00"
     */
    public void setDoubleDecimal(String doubleDecimal) {
        this.doubleDecimal = doubleDecimal;
    }
    /**
     * 设置doubel类型数据小数位 默认.00
     * @param floatDecimalFormat 如 ".00
     */
    public void setFloatDecimalFormat(DecimalFormat floatDecimalFormat) {
        this.floatDecimalFormat = floatDecimalFormat;
    }
    /**
     * 设置列的公式
     * @param colFormula  存储i-1列的公式 涉及到的行号使用@替换 如A@+B@
     */
    public void setColFormula(String[] colFormula) {
        this.colFormula = colFormula;
    }

    /**
     * 将16进制的颜色代码写入样式中来设置颜色
     * @param style  保证style统一
     * @param color 颜色：66FFDD
     * @param index 索引 8-64 使用时不可重复
     * @return
     */
    public CellStyle setColor(CellStyle style,String color,short index){
        if(color!=""&&color!=null){
            //转为RGB码
            int r = Integer.parseInt((color.substring(0,2)),16);   //转为16进制
            int g = Integer.parseInt((color.substring(2,4)),16);
            int b = Integer.parseInt((color.substring(4,6)),16);
            //自定义cell颜色
            HSSFPalette palette = workbook.getCustomPalette();
            palette.setColorAtIndex((short)index, (byte) r, (byte) g, (byte) b);

            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(index);
        }
        return style;
    }

    /**
     * 设置字体并加外边框
     * @param style  样式
     * @param style  字体名
     * @param style  大小
     * @return
     */
    public CellStyle setFontAndBorder(CellStyle style,String fontName,short size){
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints(size);
        font.setFontName(fontName);
        font.setBold(true);
        style.setFont(font);
        style.setBorderBottom(CellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(CellStyle.BORDER_THIN);//左边框
        style.setBorderTop(CellStyle.BORDER_THIN);//上边框
        style.setBorderRight(CellStyle.BORDER_THIN);//右边框
        return style;
    }

    /**
     * 删除文件
     * @param
     * @return
     */
    public boolean deleteExcel(){
        boolean flag = false;
        File file = new File(this.fileDir);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                file.delete();
                flag = true;
            }
        }
        return flag;
    }
    /**
     * 删除文件
     * @param path
     * @return
     */
    public boolean deleteExcel(String path){
        boolean flag = false;
        File file = new File(path);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                file.delete();
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 复制一个单元格样式到目的单元格样式
     *
     * @param fromStyle
     * @param toStyle
     */
    public static void copyCellStyle(CellStyle fromStyle, CellStyle toStyle) {
        toStyle.setAlignment(fromStyle.getAlignment());
        // 边框和边框颜色
        toStyle.setBorderBottom(fromStyle.getBorderBottom());
        toStyle.setBorderLeft(fromStyle.getBorderLeft());
        toStyle.setBorderRight(fromStyle.getBorderRight());
        toStyle.setBorderTop(fromStyle.getBorderTop());
        toStyle.setTopBorderColor(fromStyle.getTopBorderColor());
        toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());
        toStyle.setRightBorderColor(fromStyle.getRightBorderColor());
        toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());
        // 背景和前景
        toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());
        toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());
        // 数据格式
        toStyle.setDataFormat(fromStyle.getDataFormat());
        toStyle.setFillPattern(fromStyle.getFillPattern());
        // toStyle.setFont(fromStyle.getFont(null));
        toStyle.setHidden(fromStyle.getHidden());
        toStyle.setIndention(fromStyle.getIndention());// 首行缩进
        toStyle.setLocked(fromStyle.getLocked());
        toStyle.setRotation(fromStyle.getRotation());// 旋转
        toStyle.setVerticalAlignment(fromStyle.getVerticalAlignment());
        toStyle.setWrapText(fromStyle.getWrapText());
    }

    /**
     * 获取合并单元格的值
     * @param sheet
     * @param
     * @param
     * @return
     */
    public static void setMergedRegion(Sheet sheet) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            // 获取合并单元格位置
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstRow = ca.getFirstRow();
            if (startReadPos - 1 > firstRow) {// 如果第一个合并单元格格式在正式数据的上面，则跳过。
                continue;
            }
            int lastRow = ca.getLastRow();
            int mergeRows = lastRow - firstRow;// 合并的行数
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            // 根据合并的单元格位置和大小，调整所有的数据行格式，
            for (int j = lastRow + 1; j <= sheet.getLastRowNum(); j++) {
                // 设定合并单元格
                sheet.addMergedRegion(new CellRangeAddress(j, j + mergeRows, firstColumn, lastColumn));
                j = j + mergeRows;// 跳过已合并的行
            }
        }
    }

    /**
     * 查找某行数据是否在Excel表中存在，返回行数。
     *
     * @Title: findInExcel
     * @Date : 2014-9-11 下午02:23:12
     * @param sheet
     * @param row
     * @return
     */
    public static int findInExcel(Sheet sheet, Row row) {
        int pos = -1;
        try {
            // 如果覆写目标文件，或者不需要比较，则直接返回
            if (isOverWrite || !isNeedCompare) {
                return pos;
            }
            for (int i = startReadPos; i <= sheet.getLastRowNum() + endReadPos; i++) {
                Row r = sheet.getRow(i);
                if (r != null && row != null) {
                    String v1 = getCellValue(r.getCell(comparePos));
                    String v2 = getCellValue(row.getCell(comparePos));
                    if (v1.equals(v2)) {
                        pos = i;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pos;
    }

    /**
     * 写excel.
     * @param titleColumn  对应bean的属性名
     * @param titleName   excel要导出的表名
     * @param titleSize   列宽
     * @param dataList  数据
     */
    public void wirteExcel(String titleColumn[],String titleName[],int titleSize[],List<?> dataList){
        //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
        Sheet sheet = workbook.createSheet(this.sheetName);
        //新建文件
        OutputStream out = null;
        try {
            if(fileDir!=null){
                //有文件路径
                out = new FileOutputStream(fileDir);
            }else{
                //否则，直接写到输出流中
                out = response.getOutputStream();
                fileName = fileName+".xls";
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition", "attachment; filename="
                        + URLEncoder.encode(fileName, "UTF-8"));
            }
            //写入excel的表头
            Row titleNameRow = workbook.getSheet(sheetName).createRow(0);
            //设置样式
            HSSFCellStyle titleStyle = workbook.createCellStyle();
            titleStyle = (HSSFCellStyle) setFontAndBorder(titleStyle, titleFontType, (short) titleFontSize);
            titleStyle = (HSSFCellStyle) setColor(titleStyle, titleBackColor, (short)10);
            for(int i = 0;i < titleName.length;i++){
                sheet.setColumnWidth(i, titleSize[i]*256);    //设置宽度
                Cell cell = titleNameRow.createCell(i);
                cell.setCellStyle(titleStyle);
                cell.setCellValue(titleName[i].toString());
            }
            //为表头添加自动筛选
            if(!"".equals(address)){
                CellRangeAddress c = (CellRangeAddress) CellRangeAddress.valueOf(address);
                sheet.setAutoFilter(c);
            }
            //通过反射获取数据并写入到excel中
            if(dataList!=null && dataList.size()>0){
                //设置样式
                HSSFCellStyle dataStyle = workbook.createCellStyle();
                titleStyle = (HSSFCellStyle) setFontAndBorder(titleStyle, contentFontType, (short) contentFontSize);
                if(titleColumn.length > 0){
                    for(int rowIndex = 1;rowIndex<=dataList.size();rowIndex++){
                        Object obj = dataList.get(rowIndex-1);     //获得该对象
                        Class clsss = obj.getClass();     //获得该对对象的class实例
                        Row dataRow = workbook.getSheet(sheetName).createRow(rowIndex);
                        for(int columnIndex = 0;columnIndex < titleColumn.length;columnIndex++){
                            String title = titleColumn[columnIndex].toString().trim();
                            if(!"".equals(title)){  //字段不为空
                                //使首字母大写
                                String UTitle = Character.toUpperCase(title.charAt(0))+ title.substring(1, title.length()); // 使其首字母大写;
                                String methodName  = "get"+UTitle;
                                // 设置要执行的方法
                                Method method = clsss.getDeclaredMethod(methodName);
                                //获取返回类型
                                String returnType = method.getReturnType().getName();
                                String data = method.invoke(obj)==null?"":method.invoke(obj).toString();
                                Cell cell = dataRow.createCell(columnIndex);
                                if(data!=null&&!"".equals(data)){
                                    if("int".equals(returnType)){
                                        cell.setCellValue(Integer.parseInt(data));
                                    }else if("long".equals(returnType)){
                                        cell.setCellValue(Long.parseLong(data));
                                    }else if("float".equals(returnType)){
                                        cell.setCellValue(floatDecimalFormat.format(Float.parseFloat(data)));
                                    }else if("double".equals(returnType)){
                                        cell.setCellValue(doubleDecimalFormat.format(Double.parseDouble(data)));
                                    }else{
                                        cell.setCellValue(data);
                                    }
                                }
                            }else{   //字段为空 检查该列是否是公式
                                if(colFormula!=null){
                                    String sixBuf = colFormula[columnIndex].replace("@", (rowIndex+1)+"");
                                    Cell cell = dataRow.createCell(columnIndex);
                                    cell.setCellFormula(sixBuf.toString());
                                }
                            }
                        }
                    }
                }
            }
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通用读取Excel
     *
     * @Title: readExcel
     * @Date : 2014-9-11 上午11:26:53
     * @param wb
     * @return
     */
    private List<Row> readExcel(Workbook wb) {
        List<Row> rowList = new ArrayList<Row>();

        int sheetCount = 1;//需要操作的sheet数量

        Sheet sheet = null;
        if(onlyReadOneSheet){   //只操作一个sheet
            // 获取设定操作的sheet(如果设定了名称，按名称查，否则按索引值查)
            sheet =selectedSheetName.equals("")? wb.getSheetAt(selectedSheetIdx):wb.getSheet(selectedSheetName);
        }else{                          //操作多个sheet
            sheetCount = wb.getNumberOfSheets();//获取可以操作的总数量
        }

        // 获取sheet数目
        for(int t=startSheetIdx; t<sheetCount+endSheetIdx;t++){
            // 获取设定操作的sheet
            if(!onlyReadOneSheet) {
                sheet =wb.getSheetAt(t);
            }
            //获取最后行号
            int lastRowNum = sheet.getLastRowNum();
            if(lastRowNum>0){    //如果>0，表示有数据
                log.info("\n开始读取名为【"+sheet.getSheetName()+"】的内容：");
            }
            Row row = null;
            // 循环读取
            for (int i = startReadPos; i <= lastRowNum + endReadPos; i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    rowList.add(row);
                    log.info("第"+(i+1)+"行：");
                    // 获取每一单元格的值
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        String value = getCellValue(row.getCell(j));
                        if (!value.equals("")) {
                            log.info(value + " | ");
                        }
                    }
                    log.info("");
                }
            }
        }
        return rowList;
    }

    /***
     * 读取Excel(97-03版，xls格式)
     *
     * @throws Exception
     *
     * @Title: readExcel
     * @Date : 2014-9-11 上午09:53:21
     */
    public List<Row> readExcel_xls(String xlsPath) throws IOException {
        // 判断文件是否存在
        File file = new File(xlsPath);
        if (!file.exists()) {
            throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");
        }
        HSSFWorkbook wb = null;// 用于Workbook级的操作，创建、删除Excel
        List<Row> rowList = new ArrayList<Row>();
        try {
            // 读取Excel
            wb = new HSSFWorkbook(new FileInputStream(file));
            // 读取Excel 97-03版，xls格式
            rowList = readExcel(wb);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rowList;
    }

    /**
     * 自动根据文件扩展名，调用对应的读取方法
     *
     * @Title: writeExcel
     * @Date : 2014-9-11 下午01:50:38
     * @param xlsPath
     * @throws IOException
     */
    public List<Row> readExcel(String xlsPath) throws IOException{
        //扩展名为空时，
        if (xlsPath.equals("")){
            throw new IOException("文件路径不能为空！");
        }else{
            File file = new File(xlsPath);
            if(!file.exists()){
                throw new IOException("文件不存在！");
            }
        }
        //获取扩展名
        String ext = xlsPath.substring(xlsPath.lastIndexOf(".")+1);
        try {
            if("xls".equals(ext)){              //使用xls方式读取
                return readExcel_xls(xlsPath);
            }else if("xlsx".equals(ext)){       //使用xlsx方式读取
                return readExcel_xlsx(xlsPath);
            }else{                                  //依次尝试xls、xlsx方式读取
                log.info("您要操作的文件没有扩展名，正在尝试以xls方式读取...");
                try{
                    return readExcel_xls(xlsPath);
                } catch (IOException e1) {
                    log.info("尝试以xls方式读取，结果失败！，正在尝试以xlsx方式读取...");
                    try{
                        return readExcel_xlsx(xlsPath);
                    } catch (IOException e2) {
                        log.info("尝试以xls方式读取，结果失败！\n请您确保您的文件是Excel文件，并且无损，然后再试。");
                        throw e2;
                    }
                }
            }
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 自动根据文件扩展名，调用对应的写入方法
     *
     * @Title: writeExcel
     * @Date : 2014-9-11 下午01:50:38
     * @param rowList
     * @param xlsPath
     * @throws IOException
     */
    public void writeExcel(List<Row> rowList, String xlsPath) throws IOException {
        //扩展名为空时，
        if (xlsPath.equals("")){
            throw new IOException("文件路径不能为空！");
        }
        //获取扩展名
        String ext = xlsPath.substring(xlsPath.lastIndexOf(".")+1);
        try {
            if("xls".equals(ext)){              //使用xls方式写入
                writeExcel_xls(rowList,xlsPath);
            }else if("xlsx".equals(ext)){       //使用xlsx方式写入
                writeExcel_xlsx(rowList,xlsPath);
            }else{                                  //依次尝试xls、xlsx方式写入
                log.info("您要操作的文件没有扩展名，正在尝试以xls方式写入...");
                try{
                    writeExcel_xls(rowList,xlsPath);
                } catch (IOException e1) {
                    log.info("尝试以xls方式写入，结果失败！，正在尝试以xlsx方式读取...");
                    try{
                        writeExcel_xlsx(rowList,xlsPath);
                    } catch (IOException e2) {
                        log.info("尝试以xls方式写入，结果失败！\n请您确保您的文件是Excel文件，并且无损，然后再试。");
                        throw e2;
                    }
                }
            }
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 修改Excel（97-03版，xls格式）
     *
     * @Title: writeExcel_xls
     * @Date : 2014-9-11 下午01:50:38
     * @param rowList
     * @param dist_xlsPath
     * @throws IOException
     */
    public void writeExcel_xls(List<Row> rowList, String dist_xlsPath) throws IOException {
        writeExcel_xls(rowList, excelPath,dist_xlsPath);
    }

    /**
     * 修改Excel（97-03版，xls格式）
     *
     * @Title: writeExcel_xls
     * @Date : 2014-9-11 下午01:50:38
     * @param rowList
     * @param dist_xlsPath
     * @throws IOException
     */
    public void writeExcel_xlsx(List<Row> rowList, String dist_xlsPath) throws IOException {
        writeExcel_xls(rowList, excelPath , dist_xlsPath);
    }

    /**
     * //读取Excel 2007版，xlsx格式
     *
     * @Title: readExcel_xlsx
     * @Date : 2014-9-11 上午11:43:11
     * @return
     * @throws IOException
     */
    public List<Row> readExcel_xlsx() throws IOException {
        return readExcel_xlsx(excelPath);
    }

    /**
     * 修改Excel（97-03版，xls格式）
     *
     * @Title: writeExcel_xls
     * @Date : 2014-9-11 下午01:50:38
     * @param rowList
     * @param src_xlsPath
     * @param dist_xlsPath
     * @throws IOException
     */
    public void writeExcel_xls(List<Row> rowList, String src_xlsPath, String dist_xlsPath) throws IOException {
        // 判断文件路径是否为空
        if (dist_xlsPath == null || dist_xlsPath.equals("")) {
            log.info("文件路径不能为空");
            throw new IOException("文件路径不能为空");
        }
        // 判断文件路径是否为空
        if (src_xlsPath == null || src_xlsPath.equals("")) {
            log.info("文件路径不能为空");
            throw new IOException("文件路径不能为空");
        }
        // 判断列表是否有数据，如果没有数据，则返回
        if (rowList == null || rowList.size() == 0) {
            log.info("文档为空");
            return;
        }
        try {
            HSSFWorkbook wb = null;
            // 判断文件是否存在
            File file = new File(dist_xlsPath);
            if (file.exists()) {
                // 如果复写，则删除后
                if (isOverWrite) {
                    file.delete();
                    // 如果文件不存在，则创建一个新的Excel
                    // wb = new HSSFWorkbook();
                    // wb.createSheet("Sheet1");
                    wb = new HSSFWorkbook(new FileInputStream(src_xlsPath));
                } else {
                    // 如果文件存在，则读取Excel
                    wb = new HSSFWorkbook(new FileInputStream(file));
                }
            } else {
                // 如果文件不存在，则创建一个新的Excel
                // wb = new HSSFWorkbook();
                // wb.createSheet("Sheet1");
                wb = new HSSFWorkbook(new FileInputStream(src_xlsPath));
            }
            // 将rowlist的内容写到Excel中
            writeExcel(wb, rowList, dist_xlsPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改Excel（2007版，xlsx格式）
     *
     * @Title: writeExcel_xlsx
     * @Date : 2014-9-11 下午01:50:38
     * @param rowList
     * @param src_xlsPath
     * @throws IOException
     */
    public void writeExcel_xlsx(List<Row> rowList, String src_xlsPath, String dist_xlsPath) throws IOException {
        // 判断文件路径是否为空
        if (dist_xlsPath == null || dist_xlsPath.equals("")) {
            log.info("文件路径不能为空");
            throw new IOException("文件路径不能为空");
        }
        // 判断文件路径是否为空
        if (src_xlsPath == null || src_xlsPath.equals("")) {
            log.info("文件路径不能为空");
            throw new IOException("文件路径不能为空");
        }
        // 判断列表是否有数据，如果没有数据，则返回
        if (rowList == null || rowList.size() == 0) {
            log.info("文档为空");
            return;
        }
        try {
            // 读取文档
            XSSFWorkbook wb = null;
            // 判断文件是否存在
            File file = new File(dist_xlsPath);
            if (file.exists()) {
                // 如果复写，则删除后
                if (isOverWrite) {
                    file.delete();
                    // 如果文件不存在，则创建一个新的Excel
                    // wb = new XSSFWorkbook();
                    // wb.createSheet("Sheet1");
                    wb = new XSSFWorkbook(new FileInputStream(src_xlsPath));
                } else {
                    // 如果文件存在，则读取Excel
                    wb = new XSSFWorkbook(new FileInputStream(file));
                }
            } else {
                // 如果文件不存在，则创建一个新的Excel
                // wb = new XSSFWorkbook();
                // wb.createSheet("Sheet1");
                wb = new XSSFWorkbook(new FileInputStream(src_xlsPath));
            }
            // 将rowlist的内容添加到Excel中
            writeExcel(wb, rowList, dist_xlsPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * //读取Excel 2007版，xlsx格式
     *
     * @Title: readExcel_xlsx
     * @Date : 2014-9-11 上午11:43:11
     * @return
     * @throws Exception
     */
    public List<Row> readExcel_xlsx(String xlsPath) throws IOException {
        // 判断文件是否存在
        File file = new File(xlsPath);
        if (!file.exists()) {
            throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");
        }
        XSSFWorkbook wb = null;
        List<Row> rowList = new ArrayList<Row>();
        try {
            FileInputStream fis = new FileInputStream(file);
            // 去读Excel
            wb = new XSSFWorkbook(fis);
            // 读取Excel 2007版，xlsx格式
            rowList = readExcel(wb);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rowList;
    }


    /***
     * 读取单元格的值
     *
     * @Title: getCellValue
     * @Date : 2014-9-11 上午10:52:07
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        Object result = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    result = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    result = cell.getNumericCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    result = cell.getCellFormula();
                    break;
                case Cell.CELL_TYPE_ERROR:
                    result = cell.getErrorCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    break;
                default:
                    break;
            }
        }
        return result.toString();
    }

    /**
     * 获取单元格中的值
     *
     * @param cell 单元格
     * @return
     */
//    public static Object getCellValue(Cell cell) {
//        int type = cell.getCellType();
//        switch (type) {
//            case Cell.CELL_TYPE_STRING:
//                return (Object) cell.getStringCellValue();
//            case Cell.CELL_TYPE_NUMERIC:
//                Double value = cell.getNumericCellValue();
//                return (Object) (value.intValue());
//            case Cell.CELL_TYPE_BOOLEAN:
//                return (Object) cell.getBooleanCellValue();
//            case Cell.CELL_TYPE_FORMULA:
//                return (Object) cell.getArrayFormulaRange().formatAsString();
//            case Cell.CELL_TYPE_BLANK:
//                return (Object) "";
//            default:
//                return null;
//        }
//    }

    /**
     * 修改Excel，并另存为
     *
     * @Title: WriteExcel
     * @Date : 2014-9-11 下午01:33:59
     * @param wb
     * @param rowList
     * @param xlsPath
     */
    private void writeExcel(Workbook wb, List<Row> rowList, String xlsPath) {
        if (wb == null) {
            log.info("操作文档不能为空！");
            return;
        }
        Sheet sheet = wb.getSheetAt(0);// 修改第一个sheet中的值
        // 如果每次重写，那么则从开始读取的位置写，否则果获取源文件最新的行。
        int lastRowNum = isOverWrite ? startReadPos : sheet.getLastRowNum() + 1;
        int t = 0;//记录最新添加的行数
        log.info("要添加的数据总条数为："+rowList.size());
        for (Row row : rowList) {
            if (row == null) continue;
            // 判断是否已经存在该数据
            int pos = CommonUtils.findInExcel(sheet, row);
            Row r = null;// 如果数据行已经存在，则获取后重写，否则自动创建新行。
            if (pos >= 0) {
                sheet.removeRow(sheet.getRow(pos));
                r = sheet.createRow(pos);
            } else {
                r = sheet.createRow(lastRowNum + t++);
            }
            //用于设定单元格样式
            CellStyle newstyle = wb.createCellStyle();
            //循环为新行创建单元格
            for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
                Cell cell = r.createCell(i);// 获取数据类型
                cell.setCellValue(getCellValue(row.getCell(i)));// 复制单元格的值到新的单元格
                // cell.setCellStyle(row.getCell(i).getCellStyle());//出错
                if (row.getCell(i) == null) continue;
                CommonUtils.copyCellStyle(row.getCell(i).getCellStyle(), newstyle); // 获取原来的单元格样式
                cell.setCellStyle(newstyle);// 设置样式
                // sheet.autoSizeColumn(i);//自动跳转列宽度
            }
        }
        log.info("其中检测到重复条数为:" + (rowList.size() - t) + " ，追加条数为："+t);
        // 统一设定合并单元格
        CommonUtils.setMergedRegion(sheet);
        try {
            // 重新将数据写入Excel中
            FileOutputStream outputStream = new FileOutputStream(xlsPath);
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            log.info("写入Excel时发生错误！ ");
            e.printStackTrace();
        }
    }

    public String getExcelPath() {
        return excelPath;
    }

    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }

    public int getStartReadPos() {
        return startReadPos;
    }

    public void setStartReadPos(int startReadPos) {
        this.startReadPos = startReadPos;
    }

    public int getStartSheetIdx() {
        return startSheetIdx;
    }

    public void setStartSheetIdx(int startSheetIdx) {
        this.startSheetIdx = startSheetIdx;
    }

    public int getEndSheetIdx() {
        return endSheetIdx;
    }

    public void setEndSheetIdx(int endSheetIdx) {
        this.endSheetIdx = endSheetIdx;
    }

    public boolean isPrintMsg() {
        return printMsg;
    }

    public void setPrintMsg(boolean printMsg) {
        this.printMsg = printMsg;
    }

    public int getEndReadPos() {
        return endReadPos;
    }

    public void setEndReadPos(int endReadPos) {
        this.endReadPos = endReadPos;
    }

    public int getComparePos() {
        return comparePos;
    }

    public void setComparePos(int comparePos) {
        this.comparePos = comparePos;
    }

    public boolean isOverWrite() {
        return isOverWrite;
    }

    public void setOverWrite(boolean overWrite) {
        isOverWrite = overWrite;
    }

    public boolean isNeedCompare() {
        return isNeedCompare;
    }

    public void setNeedCompare(boolean needCompare) {
        isNeedCompare = needCompare;
    }

    public boolean isOnlyReadOneSheet() {
        return onlyReadOneSheet;
    }

    public void setOnlyReadOneSheet(boolean onlyReadOneSheet) {
        this.onlyReadOneSheet = onlyReadOneSheet;
    }

    public int getSelectedSheetIdx() {
        return selectedSheetIdx;
    }

    public void setSelectedSheetIdx(int selectedSheetIdx) {
        this.selectedSheetIdx = selectedSheetIdx;
    }

    public String getSelectedSheetName() {
        return selectedSheetName;
    }

    public void setSelectedSheetName(String selectedSheetName) {
        this.selectedSheetName = selectedSheetName;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getTitleFontType() {
        return titleFontType;
    }

    public String getTitleBackColor() {
        return titleBackColor;
    }

    public short getTitleFontSize() {
        return titleFontSize;
    }

    public String getAddress() {
        return address;
    }

    public String getContentFontType() {
        return contentFontType;
    }

    public short getContentFontSize() {
        return contentFontSize;
    }

    public String getFloatDecimal() {
        return floatDecimal;
    }

    public void setFloatDecimal(String floatDecimal) {
        this.floatDecimal = floatDecimal;
    }

    public String getDoubleDecimal() {
        return doubleDecimal;
    }

    public String[] getColFormula() {
        return colFormula;
    }

    public DecimalFormat getFloatDecimalFormat() {
        return floatDecimalFormat;
    }

    public DecimalFormat getDoubleDecimalFormat() {
        return doubleDecimalFormat;
    }

    public void setDoubleDecimalFormat(DecimalFormat doubleDecimalFormat) {
        this.doubleDecimalFormat = doubleDecimalFormat;
    }

    public HSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public static HSSFCellStyle getCellstyle() {
        return cellstyle;
    }

    public static void setCellstyle(HSSFCellStyle cellstyle) {
        CommonUtils.cellstyle = cellstyle;
    }
}
