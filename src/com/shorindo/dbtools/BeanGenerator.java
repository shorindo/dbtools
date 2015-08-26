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
                BeanClass beanClass = new BeanClass(packageName, getBeanName(tableName, true));
                ResultSet cset = meta.getColumns(null, null, tableName, null);
                while (cset.next()) {
                    LOG.debug(getBeanName(cset.getString("COLUMN_NAME"), false));
                }
                cset.close();
                LOG.debug(beanClass.toString());
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
        private String className;
        public BeanClass(String packageName, String className) {
            this.packageName = packageName;
            this.className = className;
        }
        public void addMember(Class<?> clazz, String memberName) {
            
        }
        public String toString() {
            return
                "package " + packageName + ";\n" +
                "\n" +
                "public class " + className + " {\n" +
                "}\n";
        }
    }
    
    protected static class BeanMember {
        private Class<?> clazz;
        private String memberName;
        public BeanMember(Class<?> clazz, String memberName) {
            this.clazz = clazz;
            this.memberName = memberName;
        }
        public String toString() {
            return "private " + clazz.getSimpleName() + " " + memberName + ";\n";
        }
    }
}
