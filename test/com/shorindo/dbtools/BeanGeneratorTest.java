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

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.bind.JAXB;

import org.junit.Test;

import com.shorindo.dbtools.BeanGenerator;

/**
 * 
 */
public class BeanGeneratorTest {

    @Test
    public void testCreate() {
        BeanGenerator.create("com.shorindo.sample", new File("generated"));
    }

//    @Test
    public void testType() throws SQLException {
        String drop =
            "DROP TABLE IF EXISTS test_type";
        String create =
            "CREATE TABLE test_type (" +
//            "COL_BIT BIT" +
//            "COL_BIT10 BIT(10)" +
//            "COL_TINYINT TINYINT" +
//            "COL_BOOL BOOL" +
//            "COL_BOOLEAN BOOLEAN," +
//            "COL_SMALLINT SMALLINT," +
//            "COL_SMALLINT_UNSIGNED SMALLINT UNSIGNED," +
//            "COL_MEDIUMINT MEDIUMINT," +
//            "COL_MEDIUMINT_UNSIGNED MEDIUMINT UNSIGNED" +
//            "COL_INT INT," +
//            "COL_INT_UNSIGNED INT UNSIGNED," +
//            "COL_BIGINT BIGINT," +
//            "COL_BIGINT_UNSIGNED BIGINT UNSIGNED," +
//            "COL_FLOAT FLOAT" +
//            "COL_FLOAT105 FLOAT(10,5)," +
//            "COL_DOUBLE DOUBLE" +
//            "COL_DOUBLE105 DOUBLE(10,5)," +
//            "COL_DECIMAL DECIMAL," +
//            "COL_DECIMAL10 DECIMAL(10)," +
//            "COL_DECIMAL105 DECIMAL(10,5)" +
//            "COL_DATE DATE" +
//            "COL_DATETIME DATETIME," +
//            "COL_TIMESTAMP TIMESTAMP" +
//            "COL_TIME TIME," +
//            "COL_YEAR YEAR," +
//            "COL_YEAR2 YEAR(2)," +
//            "COL_CHAR100 CHAR(100)," +
//            "COL_VARCHAR100 VARCHAR(100)," +
//            "COL_VARCHAR100_BINARY VARCHAR(100) BINARY" +
//            "COL_BINARY BINARY" +
//            "COL_BINARY100 BINARY(100)" +
//            "COL_VARBINARY VARBINARY," +
//            "COL_TINYBLOB TINYBLOB" +
//            "COL_TINYTEXT TINYTEXT," +
//            "COL_BLOB BLOB" +
//            "COL_TEXT TEXT" +
//            "COL_MEDIUMBLOB MEDIUMBLOB," +
//            "COL_MEDIUMTEXT MEDIUMTEXT," +
//            "COL_LONGBLOB LONGBLOB," +
//            "COL_LONGTEXT LONGTEXT" +
//            "COL_ENUM ENUM" +
//            "COL_SET SET" +
            ")";
        String insert =
            "INSERT INTO test_type VALUES (0)";
        String select =
            "SELECT * FROM test_type";
        BeanGenerator generator = new BeanGenerator();
        Connection conn = generator.getConnection();
        conn.createStatement().executeUpdate(drop);
        conn.createStatement().executeUpdate(create);
        conn.createStatement().executeUpdate(insert);
        ResultSet rset = conn.createStatement().executeQuery(select);
        if (rset.next()) {
            ResultSetMetaData meta = rset.getMetaData();
            System.out.print(meta.getColumnTypeName(1) + " => ");
            System.out.println(rset.getObject(1).getClass());
        }
    }
    
    @Test
    public void testJson() {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        try {
            engine.put("foo", 123);
            String json = "{\"text\":{\"bar\":\"foo\"}, \"bar\":123, baz:\"zzz\"}";
            //Object o = engine.eval("JSON.parse('" + json + "')");
            //Object o = engine.eval("(" + json + ").text.bar");
            Object o = engine.eval("foo");
            System.out.println(o);
            assertEquals("foo", o);
        } catch (ScriptException e) {
            e.printStackTrace();
            fail();
        }
    }
}
