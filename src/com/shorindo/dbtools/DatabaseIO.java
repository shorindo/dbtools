/*
 * Copyright 2015 Shorindo, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shorindo.dbtools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

/**
 * 
 */
public class DatabaseIO extends Connector {
    public static final int HEADER_ROW = 0;
    public static final int START_ROW = HEADER_ROW + 1;
    public static final int START_COL = 0;
    public static final String NULL_EXPR = "{NULL}";
    Properties props = new Properties();
    Map<String,TableMeta> tableMap = new TreeMap<String,TableMeta>();
    
    public static void main(String args[]) {
        try {
            DatabaseIO dbio = new DatabaseIO();
            //dbio.exportToXls(new File("data/sample_XXX.xls"));
            dbio.importFromXls(new File("data/sample.xls"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
    
    private static void trace(Object msg) {
        System.out.println("[T] " + msg);
    }
    private static void debug(Object msg) {
        System.out.println("[D] " + msg);
    }
    private static void info(Object msg) {
        System.out.println("[I] " + msg);
    }

    public DatabaseIO() {
        try {
            loadTableMeta();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int sql(String sql, Object...params) throws SQLException {
        Connection conn = null;
        int result = 0;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            result = stmt.executeUpdate();
            stmt.close();
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return result;
    }
    public <T>T queryForObject(Class<T> resultClass, String sql, Object...params) throws SQLException {
        return null;
    }
    public <T> List<T> queryForList(Class<T> resultClass, String sql, Object...params) throws SQLException {
        return null;
    }
    public void importFromXls(File xls)throws IOException, SQLException {
        Connection conn = getConnection();
        info("File:" + xls.getName());
        FileInputStream is = new FileInputStream(xls);
        HSSFWorkbook book = new HSSFWorkbook(is);
        for (int i = 0; i < book.getNumberOfSheets(); i++) {
            HSSFSheet sheet = book.getSheetAt(i);
            String tableName = sheet.getSheetName();
            //TableMeta tableMeta = new TableMeta(conn, tableName);
            info("Sheet:" + tableName);

            HSSFRow row = sheet.getRow(HEADER_ROW);
            int colNum = START_COL;
            boolean hasCol = true;
            List<String> colNames = new ArrayList<String>();
            while (hasCol) {
                //debug("[" + HEADER_ROW + "," + colNum + "]");
                HSSFCell cell = row.getCell(colNum);
                if (cell == null) {
                    break;
                }
                String columnName = cell.getStringCellValue();
                if (columnName == null || columnName.length() == 0) {
                    hasCol = false;
                } else {
                    colNames.add(columnName);
                }
                colNum++;
            }
            StringBuffer sb = new StringBuffer("INSERT INTO " + tableName + " (");
            String sep = "";
            for (String colName : colNames) {
                sb.append(sep + colName);
                sep = ",";
            }
            sb.append(") VALUES (");
            sep = "";
            for (String colName : colNames) {
                sb.append(sep + "?");
                sep = ",";
            }
            sb.append(")");
            //debug(sb.toString());
            for (int rowNum = START_ROW; rowNum <= sheet.getLastRowNum(); rowNum++) {
                trace("Row:" + String.valueOf(rowNum));
                row = sheet.getRow(rowNum);
                PreparedStatement stmt = conn.prepareStatement(sb.toString());
                for (int j = 0; j < colNames.size(); j++) {
                    HSSFCell cell = row.getCell(j);
                    //println(cell.getStringCellValue());
                    switch (cell.getCellType()) {
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        if (NULL_EXPR.equals(cell.getStringCellValue())) {
                            stmt.setObject(j + 1,  null);
                        } else
                        if (DateUtil.isCellDateFormatted(cell)) {
                            stmt.setDate(j + 1, new java.sql.Date(cell.getDateCellValue().getTime()));
                        } else {
                            stmt.setDouble(j + 1, row.getCell(j).getNumericCellValue());
                        }
                        break;
                    default:
                        String cellValue = row.getCell(j).getStringCellValue();
                        if (NULL_EXPR.equals(cellValue)) {
                            cellValue = null;
                        }
                        stmt.setString(j + 1, cellValue);
                    }
                }
                stmt.executeUpdate();
                stmt.close();
            }
        }

        book.close();
        if (conn != null)
            try {
                conn.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
    }
    
    public void exportToXls(File xls) throws Exception {
        Connection conn = getConnection();
        HSSFWorkbook book = new HSSFWorkbook();
        for (String key : tableMap.keySet()) {
            TableMeta tableMeta = tableMap.get(key);
            List<ColumnMeta> columnList = tableMeta.getColumnList();
            trace("Table:" + tableMeta.getName());
            HSSFSheet sheet = book.createSheet(tableMeta.getName());
            int rowNum = HEADER_ROW;
            HSSFRow row = sheet.createRow(rowNum);
            int colNum = START_COL;
            for (ColumnMeta columnMeta : tableMeta.getColumnList()) {
                HSSFCell cell = row.createCell(colNum);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellValue(columnMeta.getName());
                colNum++;
            }
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableMeta.getName());
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                row = sheet.createRow(++rowNum);
                colNum = START_COL;
                for (ColumnMeta columnMeta : columnList) {
                    Object value = rset.getObject(columnMeta.getName());
                    HSSFCell cell = row.createCell(colNum++);
                    cell.setCellValue(value == null ? NULL_EXPR : value.toString());
                }
            }
            rset.close();
        }
        conn.close();
        book.write(new FileOutputStream(xls));
    }

    private void loadTableMeta() throws Exception {
        Connection conn = null;
        try {
            conn = getConnection();
            ResultSet rset = conn.getMetaData().getTables(null, null, "%", new String[]{ "TABLE" });
            while (rset.next()) {
                String tableName = rset.getString("TABLE_NAME");
                tableMap.put(tableName.toUpperCase(), new TableMeta(conn, tableName));
            }
            rset.close();
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    public static class TableMeta {
        private String name;
        private List<ColumnMeta> columnList = new ArrayList<ColumnMeta>();
        private List<ColumnMeta> pkList = new ArrayList<ColumnMeta>();
        public TableMeta(Connection conn, String tableName) throws SQLException {
            this.name = tableName;
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rset = meta.getColumns(null, null, tableName, null);
            //debug(tableName);
            while (rset.next()) {
                ColumnMeta columnMeta = new ColumnMeta(
                        rset.getString("COLUMN_NAME"),
                        rset.getString("TYPE_NAME"));
                columnList.add(columnMeta);
            }
            rset.close();
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public List<ColumnMeta> getColumnList() {
            return columnList;
        }
        public void setColumnList(List<ColumnMeta> columnList) {
            this.columnList = columnList;
        }
        public List<ColumnMeta> getPkList() {
            return pkList;
        }
        public void setPkList(List<ColumnMeta> pkList) {
            this.pkList = pkList;
        }
    }
    public static class ColumnMeta {
        private String name;
        private Class<?>  type;
        public ColumnMeta(String columnName, String typeName) {
            this.name = columnName;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Class<?> getType() {
            return type;
        }
        public void setType(Class<?> type) {
            this.type = type;
        }
    }
}
