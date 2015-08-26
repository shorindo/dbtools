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

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 
 */
public abstract class Connector {
    protected static Connection getConnection() throws SQLException {
        try {
            InputStream is = Connector.class.getResourceAsStream("db.properties");
            Properties props = new Properties();
            props.load(is);
            is.close();
            Class.forName(props.getProperty("driver.className"));
            return DriverManager.getConnection(
                    props.getProperty("driver.url"),
                    props.getProperty("driver.user"),
                    props.getProperty("driver.pass"));
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }
}
