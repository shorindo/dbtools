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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public class Logger {
    public enum LogLevel {
        DEBUG, INFO, WARN, ERROR
    };
    private static Map<Class<?>,Logger> map = new HashMap<Class<?>,Logger>();
    private Class<?> targetClass;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static Logger getLogger(Class<?> clazz) {
        Logger logger = map.get(clazz);
        if (logger == null) {
            logger = new Logger(clazz);
            map.put(clazz, logger);
        }
        return logger;
    }
    
    protected Logger(Class<?> clazz) {
        targetClass = clazz;
    }
    private void log(LogLevel level, Object... args) {
        StringBuffer sb = new StringBuffer();
        sb.append(format.format(new Date()));
        sb.append(" [" + level.name() + "] ");
        sb.append(targetClass.getSimpleName() + " - ");
        for (Object arg : args) {
            sb.append(arg.toString());
        }
        System.out.println(sb.toString());
    }
    public void debug(Object... args) {
        log(LogLevel.DEBUG, args);
    }
    public void info(Object... args) {
        log(LogLevel.INFO, args);
    }
    public void warn(Object... args) {
        log(LogLevel.WARN, args);
    }
    public void error(Object... args) {
        log(LogLevel.ERROR, args);
    }
}
