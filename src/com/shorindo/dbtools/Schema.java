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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

/**
 * 
 */
public class Schema {
    private String namespace;
    private List<Entity> entityList = new ArrayList<Entity>();
    private List<Relation> relationList = new ArrayList<Relation>();
    
    @XmlElement(name="namespace")
    public String getNamespace() {
        return namespace;
    }
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
    @XmlElements({
        @XmlElement(name="table", type=Table.class),
        @XmlElement(name="view",  type=View.class)
    })
    public List<Entity> getEntityList() {
        return entityList;
    }
    public void setEntityList(List<Entity> entityList) {
        this.entityList = entityList;
    }
    public Schema addEntity(Entity entity) {
        entityList.add(entity);
        return this;
    }
    public static interface Entity {}
    public static class Table implements Entity {
        private String name;
        private List<String> aliasList;
        private List<Column> columnList;
        private PrimaryKey primaryKey;
        public String getName() {
            return name;
        }
        public Table setName(String name) {
            this.name = name;
            return this;
        }
        @XmlElement(name="alias")
        public List<String> getAliasList() {
            return aliasList;
        }
        public Table setAliasList(List<String> aliasList) {
            this.aliasList = aliasList;
            return this;
        }
        public Table addAlias(String alias) {
            aliasList.add(alias);
            return this;
        }
        @XmlElement(name="column")
        public List<Column> getColumnList() {
            return columnList;
        }
        public Table setColumnList(List<Column> columnList) {
            this.columnList = columnList;
            return this;
        }
        public Table addColumn(Column column) {
            columnList.add(column);
            return this;
        }
        @XmlElement(name="primaryKey")
        public PrimaryKey getPrimaryKey() {
            return primaryKey;
        }
        public Table setPrimaryKey(PrimaryKey primaryKey) {
            this.primaryKey = primaryKey;
            return this;
        }
        public Table addPrimaryKey(String columnName) {
            primaryKey.addColumnName(columnName);
            return this;
        }
    }
    public static class Column {
        private String name;
        private List<String> aliasList;
        private String type;
        private int size;
        private int decimal;
        private boolean notNull = false;
        private boolean unique = false;
        public String getName() {
            return name;
        }
        public Column setName(String name) {
            this.name = name;
            return this;
        }
        @XmlElement(name="alias")
        public List<String> getAliasList() {
            return aliasList;
        }
        public Column setAliasList(List<String> aliasList) {
            this.aliasList = aliasList;
            return this;
        }
        public Column addAlias(String alias) {
            aliasList.add(alias);
            return this;
        }
        public String getType() {
            return type;
        }
        public Column setType(String type) {
            this.type = type;
            return this;
        }
        public int getSize() {
            return size;
        }
        public Column setSize(int size) {
            this.size = size;
            return this;
        }
        public int getDecimal() {
            return decimal;
        }
        public Column setDecimal(int decimal) {
            this.decimal = decimal;
            return this;
        }
        public boolean isNotNull() {
            return notNull;
        }
        public Column setNotNull(boolean notNull) {
            this.notNull = notNull;
            return this;
        }
        public boolean isUnique() {
            return unique;
        }
        public void setUnique(boolean unique) {
            this.unique = unique;
        }
    }
    public static class PrimaryKey {
        private List<String> columnNameList = new ArrayList<String>();
        @XmlElement(name="columnName")
        public List<String> getColumnNameList() {
            return columnNameList;
        }
        public PrimaryKey setColumnNameList(List<String> columnNameList) {
            this.columnNameList = columnNameList;
            return this;
        }
        public PrimaryKey addColumnName(String columnName) {
            columnNameList.add(columnName);
            return this;
        }
    }
    public static class View implements Entity {
    }
    public static class Relation {
    }
}
