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
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class BeanGenerator extends Connector {
    private static final Logger LOG = Logger.getLogger(BeanGenerator.class);

    public static void create(String packageName, File dest) {
        try {
            Connection conn = getConnection();
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rset = meta.getTables(null, null, null, null);
            while (rset.next()) {
                String tableName = rset.getString("TABLE_NAME");
                BeanClass beanClass = new BeanClass(packageName, tableName);
                ResultSet cset = meta.getColumns(null, null, tableName, null);
                while (cset.next()) {
                    beanClass.addMember(cset.getString("TYPE_NAME"), cset.getString("COLUMN_NAME"));
                }
                cset.close();
                LOG.debug(beanClass.getSource());
            }
            rset.close();
            conn.close();
        } catch (SQLException e) {
            LOG.error(e);
        }
    }
    
    protected static String getBeanName(String in, boolean upcase) {
        String parts[] = in.split("_");
        StringBuffer sb = new StringBuffer();
        for (String part : parts) {
            sb.append(part.substring(0, 1).toUpperCase());
            sb.append(part.substring(1).toLowerCase());
        }
        if (!upcase) {
            sb.replace(0, 1, sb.substring(0, 1).toLowerCase());
        }
        return sb.toString();
    }
    
    protected static class BeanClass {
        private String packageName;
        private String tableName;
        private String className;
        private List<BeanMember> memberList = new ArrayList<BeanMember>();

        public BeanClass(String packageName, String tableName) {
            this.packageName = packageName;
            this.tableName = tableName;
            this.className = getBeanName(tableName, true);
        }
        public void addMember(String typeName, String memberName) {
            memberList.add(new BeanMember(typeName, memberName));
        }
        public String getSource() {
            StringBuffer sb = new StringBuffer();
            sb.append("package " + packageName + ";\n");
            sb.append("\n");
            sb.append("public class " + className + " {\n");
            for (BeanMember member : memberList) {
                sb.append(member.getDeclare());
            }
            sb.append("}\n");
            return sb.toString();
        }
    }
    
    protected static class BeanMember {
        private String typeName;
        private String className;
        private String columnName;
        private String memberName;
        public BeanMember(String typeName, String columnName) {
            this.typeName = typeName;
            this.columnName = columnName;
            this.memberName = getBeanName(columnName, false);
        }
        public String getTypeName() {
            return typeName;
        }
        public String getColumnName() {
            return columnName;
        }
        public String getMemberName() {
            return memberName;
        }
        public String getDeclare() {
            return "    private " + typeName + " " + memberName + ";\n";
        }
    }
}
