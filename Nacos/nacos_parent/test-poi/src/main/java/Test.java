import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {

    public static void main(String[] args) throws Exception {
        List<CoordinateVO> list = readFile();
        List<String> list2 = new ArrayList<>();
        for(CoordinateVO c:list){
            list2.add(c.getDataValue());
        }
        Random random = new Random();
        System.out.println("============元数据大小==========="+list.size());
        System.out.println("=============新数据大小=========="+list2.size());
        List<Integer> ids = new ArrayList<>();
        int rows = list.size();
        for(int i=0;i<rows;i++){
            int intRandom = random.nextInt(list2.size());
            boolean contains = ids.contains(intRandom);
            if(!contains){
                ids.add(intRandom);
            }else{
                rows+=1;// 确保每条元素都能被抽取到
            }
        }
        System.out.println(ids.size());
        for(Integer i:ids){
            if((i%8)==0) {
                System.out.println("===========================");
            }
            System.out.println(ids.get(i));
        }
        
    }

    private static List<CoordinateVO> readFile()throws Exception {
        //获取文件路径
        String filePath = "C:\\Users\\etjav\\Desktop\\names.xlsx";
        File file = new File(filePath);
        //获取文件后缀
        String finishFile = filePath.substring(filePath.lastIndexOf(".") + 1);
        InputStream inputStream = new FileInputStream(file);
        //这里需要注意XSSFWorkbook能够处理xlsx的文件
        //而HSSFWorkbook能够处理xls的文件，不然会报错，这样写是为了更好的兼容处理两种格式
        Workbook workbook;
        if ("xlsx".equals(finishFile)) {
            workbook = new XSSFWorkbook(inputStream);
        } else {
            workbook = new HSSFWorkbook(inputStream);
        }
        //这里是读取第几个sheet
        Sheet sheet = workbook.getSheetAt(0);
        //这里是读取总行数
        int rows = sheet.getPhysicalNumberOfRows();
        List<CoordinateVO> allList = new ArrayList<>();
        //1.找到所有的元素对象，然后用list存放
        for (int i = 0; i <rows; i++) {
            Row row = sheet.getRow(i);//获取每一行
            int columns = 0;
            columns = row.getLastCellNum();
//            if (1 == i) {
//                //获取每一行的最后一列的列号，即总列数,这里需要注意一下，
//                //这种方法读取第一行的列数的时候会多读取一列，所以这里要减1
//                columns = row.getLastCellNum() - 1;
//            } else {
//                columns = row.getLastCellNum();
//            }
            for (int j = 0; j < columns; j++) {
                //获取每个单元格
                Cell cell = row.getCell(j);
                //设置单元格类型
                cell.setCellType(CellType.STRING);
                //获取单元格数据
                String cellValue = cell.getStringCellValue();
                CoordinateVO coordinateVO = new CoordinateVO();
                coordinateVO.setCoordinateX(i);
                coordinateVO.setCoordinateY(j);
                coordinateVO.setDataValue(cellValue);
                allList.add(coordinateVO);
            }
        }
        System.out.println(allList.size());
        return allList;
    }
}
