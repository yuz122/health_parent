import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class POITest {
    /*@Test
    public void test1() throws IOException {
        //创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook("E:\\黑马文件\\加密视频\\传智健康\\day05\\素材\\预约设置模板文件\\ordersetting_template.xlsx");
        //获取工作簿中的工作表
        XSSFSheet sheet = workbook.getSheetAt(0);
        //获取工作表中的行
        for (Row row : sheet) {
            //遍历获取行中的列
            for (Cell cell : row) {
                //用该方法获取只能获取字符串形式的数据
                String stringCellValue = cell.getStringCellValue();
                System.out.println(stringCellValue);
            }
        }
        //最后将工作部关闭
        workbook.close();
    }

    @Test
    public void test2() throws IOException {
        //创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook("E:\\黑马文件\\加密视频\\传智健康\\day05\\素材\\预约设置模板文件\\ordersetting_template.xlsx");
        //获取工作簿中的工作表
        XSSFSheet sheet = workbook.getSheetAt(0);
        //获取表中的最后一行
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {//因为当前行是从0开始计数(如是两行数据)lastRowNum=1
            //遍历表中的行数据
            XSSFRow row = sheet.getRow(i);
            //获取表中的最后一行
            short lastCellNum = row.getLastCellNum();
            //遍历表中的列
            for(short j = 0; j< lastCellNum ;j++){//当前获取列是从1开始计数(如是两行数据)lastCellNum=2
                String stringCellValue = row.getCell(j).getStringCellValue();
                System.out.println(stringCellValue);
            }
        }
        workbook.close();
    }
*/
    @Test
    public void test3() throws IOException {
        //创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建表名
        XSSFSheet sheet = workbook.createSheet("POITest");
        //创建行
        XSSFRow row = sheet.createRow(0);
        //创建列
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("地址");
        row.createCell(2).setCellValue("性别");

        XSSFRow row1 = sheet.createRow(1);
        //创建列
        row.createCell(0).setCellValue("张三");
        row.createCell(1).setCellValue("重庆");
        row.createCell(2).setCellValue("男");

        XSSFRow row2 = sheet.createRow(2);
        //创建列
        row.createCell(0).setCellValue("李四");
        row.createCell(1).setCellValue("北京");
        row.createCell(2).setCellValue("女");

        //用字节输出流将文件写入硬盘
        FileOutputStream outputStream = new FileOutputStream(new File("D:\\POITest.xslx"));
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
        workbook.close();
    }

}
