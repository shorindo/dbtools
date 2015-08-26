package com.shorindo.xls2erm;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Diagram {
    public PageSetting page_setting = new PageSetting();
    public Dictionary dictionary = new Dictionary();
    @XmlElementWrapper(name="contents")
    @XmlElement(name="table")
    public List<Table> contents = new ArrayList<Table>();
    public Diagram() {
        Table table;
        table = addTable("PERSON", "個人");
        Column column = new Column();
        column.physical_name = "COLUMN_1";
        column.logical_name = "カラム１";
        column.type = "TEXT";
        table.addColumn(column);
        table = addTable("GROUP", "集団");
    }
    public int getDictionarySize() {
        return dictionary.word.size();
    }
    public void addWord(Word word) {
        dictionary.addWord(word);
    }
    public Table addTable(String physName, String logiName) {
        Table table = new Table(this, physName, logiName);
        //dictionary.addWord(new Word(physName, logiName));
        contents.add(table);
        return table;
    }
}

class PageSetting {
    public boolean direction_horizontal = true;
    public int scale = 100;
    public String paper_size = "A4 120 x 297 mm";
    public int top_margin = 30;
    public int left_margin = 30;
    public int bottom_margin = 30;
    public int right_margin = 30;
}

class Dictionary {
    public List<Word> word = new ArrayList<Word>();
    public Dictionary() {
    }
    public void addWord(Word word) {
        word.id = this.word.size();
        this.word.add(word);
    }
}

class Word {
    public int id;
    public Short length;
    public Short decimal;
    public boolean array = false;
    public Short array_dimension;
    public boolean unsigned = false;
    public boolean zerofill = false;
    public String description;
    public String logical_name;
    public String physical_name;
    public Word() {
    }
}

class Table {
    public String physical_name;
    public String logical_name;
    public List<Column> normal_column = new ArrayList<Column>();
    private Diagram diagram;
    public Table(Diagram diagram, String physName, String logiName) {
        this.diagram = diagram;
        physical_name = physName;
        logical_name = logiName;
    }
    public void addColumn(Column column) {
        normal_column.add(column);
        Word word = new Word();
        word.id = diagram.getDictionarySize();
        word.physical_name = column.physical_name;
        word.logical_name = column.logical_name;
        diagram.addWord(word);
    }
}

class Column {
    public int word_id;
    public int id;
    public String description;
    public String unique_key_name;
    public String logical_name;
    public String physical_name;
    public String type;
    public String constraint;
    public String default_value;
    public boolean auto_increment;
    public boolean foreign_key;
    public boolean not_null;
    public boolean primary_key;
    public boolean unique_key;
    public String character_set;
    public String collation;
    public Sequence sequence;
}

class Sequence {
}