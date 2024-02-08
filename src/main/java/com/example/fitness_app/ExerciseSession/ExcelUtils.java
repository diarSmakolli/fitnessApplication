package com.example.fitness_app.ExerciseSession;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExcelUtils {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<ExerciseSessionEntity> exercisesList;

    public ExcelUtils(List<ExerciseSessionEntity> exercisesList) {
        this.exercisesList = exercisesList;
        workbook = new XSSFWorkbook();
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer){
            cell.setCellValue((Integer) value);
        }else if (value instanceof Double){
            cell.setCellValue((Double) value);
        }else if (value instanceof Boolean){
            cell.setCellValue((Boolean) value);
        }else if (value instanceof Long){
            cell.setCellValue((Long) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void createHeaderRow() {
        sheet = workbook.createSheet("Exercise information");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        createCell(row, 0, "Exercise information", style);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));
        font.setFontHeightInPoints((short) 10);

        row = sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Id", style);
        createCell(row, 1, "Activity type", style);
        createCell(row, 2, "Difficulty Level", style);
        createCell(row, 3, "Duration", style);
        createCell(row, 4, "Distance", style);
        createCell(row, 5, "Weight", style);
        createCell(row, 6, "Notes", style);
    }

    private void writeExerciseData() {
        int rowCount = 2;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for(ExerciseSessionEntity exerciseSessionEntity : exercisesList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, exerciseSessionEntity.getId(), style);
            createCell(row, columnCount++, exerciseSessionEntity.getActivityType(), style);
            createCell(row, columnCount++, exerciseSessionEntity.getDifficultyLevel(), style);
            createCell(row, columnCount++, exerciseSessionEntity.getDuration(), style);
            createCell(row, columnCount++, exerciseSessionEntity.getDistance(), style);
            createCell(row, columnCount++, exerciseSessionEntity.getWeight(), style);
            createCell(row, columnCount++, exerciseSessionEntity.getNotes(), style);
        }

    }

    public void exportDataToExcel(HttpServletResponse response) throws IOException {
        createHeaderRow();
        writeExerciseData();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }



}
