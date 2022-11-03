package com.alianza.demo.utils;

import com.alianza.demo.persistence.domain.Client;
import com.alianza.demo.rest.domain.ClientDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelHelper {
    static String[] HEADERS = { "Shared Key", "Business ID", "E-mail", "Phone", "Data Added" };
    static String SHEET = "Clients";

    public static ByteArrayInputStream clientsToExcel(List<Client> dtos) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERS[col]);
            }

            int rowIdx = 1;
            CellStyle cellStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));
            for (Client dto : dtos) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(dto.getSharedKey());
                row.createCell(1).setCellValue(dto.getBusinessId());
                row.createCell(2).setCellValue(dto.getEmail());
                row.createCell(3).setCellValue(dto.getPhone());
                Cell cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(dto.getDataAdded());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }

    }
}
