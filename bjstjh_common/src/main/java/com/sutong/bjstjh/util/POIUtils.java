package com.sutong.bjstjh.util;

import com.sutong.bjstjh.exception.BreakRulesException;
import com.sutong.bjstjh.exception.DataStaleException;
import com.sutong.bjstjh.result.ResultCode;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class POIUtils {
    private final static String xls = "xls";
    private final static String xlsx = "xlsx";
    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    /**
     * 读入excel文件，解析后返回
     * @param file
     * @throws IOException
     * @throws ParseException
     */
    public static List<String[]> readExcel(MultipartFile file) throws IOException, ParseException {
        //检查文件
        checkFile(file);
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<String[]>();
        if(workbook != null){
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null){
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum  = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行的所有行
                for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if(row == null){
                        continue;
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
                    int lastCellNum = row.getPhysicalNumberOfCells();
                    String[] cells = new String[row.getPhysicalNumberOfCells()];
                    //循环当前行
                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
                    }
                    list.add(cells);
                }
            }
            workbook.close();
        }
        return list;
    }

    //校验文件是否合法
    public static void checkFile(MultipartFile file) throws IOException{
        //判断文件是否存在
        if(null == file){
            throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
            throw new IOException(fileName + "不是excel文件");
        }
    }
    public static Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith(xls)){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith(xlsx)){
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    public static String getCellValue(Cell cell) throws ParseException{
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //如果当前单元格内容为日期类型，需要特殊处理
        String dataFormatString = cell.getCellStyle().getDataFormatString();
        if(dataFormatString.equals("m/d/yy")){
        	String trim = String.valueOf(cell).trim();
        	String regex = "^\\d{4,}/\\d{1,2}/\\d{1,2}";
        	//如果解析的文件就是一个斜杠，则替换为空字符串
        	if(trim.equals("/")) {
        		return "";
        	//如果是日期，就格式化一下
        	} else if(trim.matches(regex)) {
        		return sdf.format(sdf.parse(trim));
        	}
        	//不知道传个啥玩意
        	return trim;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: //字符串
            	cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = null;
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = cell.toString();
                break;
        }
        return cellValue;
    }


    /**
     * 	将excel解析后的数据转成map,key是列头,value是列值
     * @param excelFile
     * @return
     * @throws IOException
     * @author pengwz
     * @date 2019年12月28日 下午5:19:12
     */
    public static Map<String,List<Object>> caseExcelMap(MultipartFile excelFile) throws IOException{
    	Map<String,List<Object>> excelMap = new HashMap<String, List<Object>>();
    	int maxRow = 0;
    	try {
    		List<String[]> readExcel = POIUtils.readExcelAll(excelFile);
    		//确定最长的哪条
    		for (String[] strings : readExcel) {
    			maxRow = maxRow < strings.length ? strings.length : maxRow;
    		}
    		String key = "";
    		outer:
    			for (int i = 0; i < 1/*readExcel.size()*/; i++) {
    				int j = 0;
    				inner:
    				for (int k = 0; k < maxRow; k++) {
    					boolean isKey = true;
    					List<Object> valueList = new ArrayList<Object>();
    					for (;j < maxRow; ) {
    						String string = null;
    						try {
    							if(isKey) {
    								key = readExcel.get(i++)[j];
    								isKey = false;
   								} else {
   									string = readExcel.get(i++)[j];
   									valueList.add(string == null ? null : string.trim());
   								}
    						} catch (Exception e) {
    							j++;
    							i = 0;
    							valueList.add(string == null ? null : string.trim());
    							excelMap.put(trimStr(key), valueList);
    							continue inner;
    						}
   							if(valueList.size() == (readExcel.size()-1)) {
   								j++;
   								i = 0;
   								excelMap.put(trimStr(key), valueList);
   							    continue inner;
    						}
    					}
    					continue outer;
    				}
    		}
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    	return excelMap;
    }
    /**
     * 	针对"车牌不符工单管理-导入车牌不符工单表"的特殊处理
     * @param excelFile
     * @return  返回的集合元素只包括两个，第一个是表头数据；第二个是表身数据
     * @author pengwz
     * @date 2019年12月31日 上午10:04:21
     */
    @SuppressWarnings("all")
    public static <T extends Object> List<T> caseExcelMapByVeh(MultipartFile excelFile) {
    	//用于橙装表头数据
    	Map<String,String> titleMap = new HashMap<String, String>();
    	try {
    		//标识当前ecxel最长的列
    		int maxLenght = 0;
    		List<String[]> readExcel = POIUtils.readExcel(excelFile);
    		for (int i = 0; i < readExcel.size(); i++) {
    			maxLenght = maxLenght < readExcel.get(i).length ? readExcel.get(i).length : maxLenght;
			}
    		//循环将表头信息取出
    		boolean isTitle = true;
    		int titleIndex = 0;
    		outer:
    		for (int i = 0; i < readExcel.size(); i++) {
    			titleIndex = i;
    			String[] strs = readExcel.get(i);
    				//解析表头
    			for (String str : strs) {
    				if(isTitle) {
        				//如果为空 直接跳过
    					if(str == null || str.trim().equals(""))
    						continue;
    					//如果不包含冒号,说明已经将表头数据解析完毕
    					if(!str.contains(":") && !str.contains("：")) {
    						//表头解析完毕后,设为false
    						isTitle = false;
    						// 下标回退1,在于恢复break前的下标
    						i = i - 1;
    						break outer;
    					}
    					//如果包含冒号,说明是表头数据
    					String[] splitTitle = splitTitle(str);
    					if(splitTitle.length < 1 || splitTitle.length > 2)
    						throw new DataStaleException(ResultCode.DATA_ERROR, "解析表头数据失败,检查传入的Excel"
    								+ "是否合格或将程序对其做出相应的调整.");
    					titleMap.put(trimStr(splitTitle[0]), splitTitle.length == 2 ? splitTitle[1] : null);
    				}
				}
			}
    		List<String[]> subList = readExcel.subList(titleIndex, readExcel.size());
    		String[] strArr = new String[maxLenght];
    		int strIndex = 0;
    		int subListIndex = 0;
    		outer:
    		for (String[] strings : subList) {
    			for (int i = 0; i < strings.length; i++) {
    				if(strings[i] == null || strings[i].trim().equals("")
    						|| strings[i].contains("系统内信息"))
						continue;
    				strArr[strIndex++] = trimStr(strings[i]);
    				if(strIndex == maxLenght) {
    					++subListIndex;
    					break outer;
    				}
				}
			}
    		//创建一个新的集合,用于组装处理好的数据
    		List<String[]> resutList = new ArrayList<String[]>();
    		resutList.add(strArr);
    		//只取正文部分
    		List<String[]> subList2 = subList.subList(subListIndex+1, subList.size());
    		for (String[] strings : subList2) {
    			for (String str : strings) {
					if(trimStr(str) == null || trimStr(str).equals("")) {
						continue;
					}else {
						resutList.add(strings);
						break;
					}
				}
			}
    		//将末尾行去掉,只去去后一行
    		resutList = resutList.subList(0, resutList.size()-1);
    		Map<String, List<Object>> excelMap = caseExcelMap(resutList);
    		List<T> list = new ArrayList<T>();
    		list.add((T) titleMap);
    		list.add((T)excelMap);
    		return list;
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	throw new BreakRulesException(ResultCode.ERROR, "车牌不符工单表Excel导入失败");
    }
    /**
     * 	将readExcel解析好的文件进行行专列,key是列头,value是列尾集合
     * @param readExcel
     * @return
     * @throws IOException
     * @author pengwz
     * @date 2019年12月31日 下午4:21:49
     */
    private static Map<String,List<Object>> caseExcelMap(List<String[]> readExcel) throws IOException{
    	Map<String,List<Object>> excelMap = new HashMap<String, List<Object>>();
    	int maxRow = 0;
    	try {
    		//确定最长的哪条
    		for (String[] strings : readExcel) {
    			maxRow = maxRow < strings.length ? strings.length : maxRow;
    		}
    		String key = "";
    		outer:
    			for (int i = 0; i < 1/*readExcel.size()*/; i++) {
    				int j = 0;
    				inner:
    				for (int k = 0; k < maxRow; k++) {
    					boolean isKey = true;
    					List<Object> valueList = new ArrayList<Object>();
    					for (;j < maxRow; ) {
    						String string = null;
    						try {
    							if(isKey) {
    								key = readExcel.get(i++)[j];
    								isKey = false;
   								} else {
   									string = readExcel.get(i++)[j];
   									valueList.add(string);
   								}
    						} catch (Exception e) {
    							j++;
    							i = 0;
    							valueList.add(string);
    							excelMap.put(trimStr(key), valueList);
    							continue inner;
    						}
   							if(valueList.size() == (readExcel.size()-1)) {
   								j++;
   								i = 0;
   								excelMap.put(trimStr(key), valueList);
   							    continue inner;
    						}
    					}
    					continue outer;
    				}
    		}
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    	return excelMap;
    }

    /**
     * 	处理字符串空格问题
     * @param str
     * @return
     * @author pengwz
     * @date 2019年12月31日 上午10:11:50
     */
    private static String trimStr(String str) {
    	if(str == null) return null;
    	String trim = str.trim();
    	return trim.replace(" ", "");
    }
    /**
     * 	对传入的字符串按照冒号拆分
     * @param str
     * @return
     * @author pengwz
     * @date 2019年12月31日 上午10:27:16
     */
    private static String[] splitTitle(String str) {
    	String[] split = new String[] {};
    	// 英文冒号
    	if(str.contains(":")) {
    		split = str.split(":");
    	}
    	// 中文冒号
    	if(str.contains("：")) {
    		split = str.split("：");
    	}
    	return split;
    }

    /**
     *	 从第一行开始读入excel文件，解析后返回
     * @param file
     * @throws IOException
     * @throws ParseException
     */
    private static List<String[]> readExcelAll(MultipartFile file) throws IOException, ParseException {
        //检查文件
        checkFile(file);
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<String[]>();
        if(workbook != null){
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null){
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum  = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行的所有行
                for(int rowNum = firstRowNum;rowNum <= lastRowNum;rowNum++){
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if(row == null){
                        continue;
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
                    int lastCellNum = row.getPhysicalNumberOfCells();
                    String[] cells = new String[row.getPhysicalNumberOfCells()];
//                    if(firstCellNum == lastCellNum) {
//                    	System.err.println("-------------------------");
//                    	Cell cell = row.getCell(firstCellNum);
//                    	cells[firstCellNum-1] = getCellValue(cell);
//                    }
                    //循环当前行
                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = caseDate(getCellValue(cell));
//                        System.err.println("============= 原："+cell+"; 后："+cells[cellNum]);
                    }
                    list.add(cells);
                }
            }
            workbook.close();
        }
        return list;
    }
    /**
     * 	将中文日期改为数字日期,
     * 	比如当值为 29-三月-2018 ,则转换为 2018/03/29
     * @param value
     * @return
     * @author pengwz
     * @date 2020年1月14日 下午2:21:25
     */
    private static String caseDate(String value) {
    	if(value == null || value.trim().equals(""))
    		return value;
    	value = value.trim();
    	//29-三月-2018
    	String regex = "^\\d{1,2}-(一月|二月|三月|四月|五月|六月|七月|八月|九月|十月|十一月|十二月)-\\d{4,4}";
    	if(value.matches(regex)) {
    		StringBuilder sb = new StringBuilder();
    		String[] split = value.split("-");
    		//年
    		sb.append(split[2]);
    		sb.append("/");
    		//月
    		sb.append(month(split[1]));
    		sb.append("/");
    		//日
    		sb.append(split[0]);
    		return sb.toString().trim();
    	}
    	return value;
    }

    private static String month(String str) {
    	switch (str) {
	    	case "一月":
	    		return "01";
	    	case "二月":
	    		return "02";
	    	case "三月":
	    		return "03";
	    	case "四月":
	    		return "04";
	    	case "五月":
	    		return "05";
	    	case "六月":
	    		return "06";
	    	case "七月":
	    		return "07";
	    	case "八月":
	    		return "08";
	    	case "九月":
	    		return "09";
	    	case "十月":
	    		return "10";
	    	case "十一月":
	    		return "11";
	    	case "十二月":
	    		return "12";
	    	default:
	    		throw new BreakRulesException(ResultCode.DATA_ERROR, "无法转换日期中的月份");
    	}
    }

    public static void main(String[] args) throws Exception {
//    		String str = "1111/1/4";
//    		String regex = "^\\d{4,}/\\d{1,2}/\\d{1,2}";
//    		System.err.println(str.matches(regex));
//    		String sss = "2017/3/17";
//    		Date parse = sdf.parse(sss);
//    		String format = sdf.format(parse);
//    		System.err.println(format);
    	String value = "29-三月-2018";
		caseDate(value );
	}

}
