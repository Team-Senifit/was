
package com.senifit.was.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ParseXlsxService {

    public List<List<Map<String, Object>>> parse(MultipartFile file) throws IOException {
        try (InputStream is = file.getInputStream()) {
            return parse(is);
        }
    }

    public List<List<Map<String, Object>>> parse(InputStream is) throws IOException {
        List<List<Map<String, Object>>> sheets = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(is)) {
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                if (sheet == null) return Collections.emptyList();

                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                int headerRowIdx = findFirstNonEmptyRow(sheet);
                if (headerRowIdx == -1) return Collections.emptyList();

                List<String> headers = readHeaders(sheet.getRow(headerRowIdx), evaluator);

                List<Map<String, Object>> rows = new ArrayList<>();
                for (int r = headerRowIdx + 1; r <= sheet.getLastRowNum(); r++) {
                    Row row = sheet.getRow(r);
                    if (row == null) continue;

                    Map<String, Object> mapped = readRow(row, headers, evaluator);
                    if (mapped.isEmpty() || isAllValuesNullOrBlank(mapped)) {
                        continue; // 완전 빈 행은 스킵
                    }
                    rows.add(mapped);
                }
                sheets.add(rows);
            }
        }
        return sheets;
    }

    private int findFirstNonEmptyRow(Sheet sheet) {
        for (int r = sheet.getFirstRowNum(); r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) continue;
            for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
                Cell cell = row.getCell(c);
                if (cell != null && cell.getCellType() != CellType.BLANK && asString(cell, null) != null && !asString(cell, null).isBlank()) {
                    return r;
                }
            }
        }
        return -1;
    }

    private List<String> readHeaders(Row headerRow, FormulaEvaluator evaluator) {
        int lastCell = headerRow.getLastCellNum();
        List<String> headers = new ArrayList<>(Math.max(lastCell, 0));
        for (int c = 0; c < lastCell; c++) {
            Cell cell = headerRow.getCell(c);
            String name = asString(cell, evaluator);
            if (name == null || name.isBlank()) {
                name = "col_" + (c + 1);
            } else {
                name = name.trim();
            }
            headers.add(name);
        }
        return headers;
    }

    private Map<String, Object> readRow(Row row, List<String> headers, FormulaEvaluator evaluator) {
        Map<String, Object> map = new LinkedHashMap<>();
        int last = Math.min(row.getLastCellNum(), headers.size());
        for (int c = 0; c < last; c++) {
            String key = headers.get(c);
            Cell cell = row.getCell(c);
            Object value = asObject(cell, evaluator);
            map.put(key, value);
        }
        return map;
    }

    private boolean isAllValuesNullOrBlank(Map<String, Object> m) {
        for (Object v : m.values()) {
            if (v == null) continue;
            if (v instanceof String s) {
                if (!s.isBlank()) return false;
            } else {
                return false;
            }
        }
        return true;
    }

    private String asString(Cell cell, FormulaEvaluator evaluator) {
        Object o = asObject(cell, evaluator);
        return (o == null) ? null : String.valueOf(o);
    }

    private Object asObject(Cell cell, FormulaEvaluator evaluator) {
        if (cell == null) return null;

        CellType type = cell.getCellType();
        if (type == CellType.FORMULA && evaluator != null) {
            type = evaluator.evaluateFormulaCell(cell);
        }

        switch (type) {
            case STRING:
                String s = cell.getStringCellValue();
                return (s != null) ? s.trim() : null;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                }
                double d = cell.getNumericCellValue();
                if (Math.floor(d) == d && d >= Long.MIN_VALUE && d <= Long.MAX_VALUE) {
                    return (long) d;
                }
                return d;
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case BLANK:
            case _NONE:
            case ERROR:
            default:
                return null;
        }
    }
}
