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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 */
public class DatabaseIOTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        DatabaseIO dbio = new DatabaseIO();
        dbio.sql(
            "CREATE TABLE test_database_io (" +
            "    type_int int," +
            "    type_float float," +
            "    type_text text," +
            "    type_date datetime" +
            ")"
            );
    }
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        DatabaseIO dbio = new DatabaseIO();
        dbio.sql("DROP TABLE test_database_io");
    }
    @Test
    public void test() {
        fail("Not yet implemented");
    }

}
