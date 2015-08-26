package com.shorindo.ermaint;

import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlElement;

public class Diagram {
    private PageSetting pageSetting = new PageSetting();
    private int categoryIndex = 0;
    private float zoom = 1.0f;
    private int x = 0;
    private int y = 0;
    private Color defaultColor = new Color();
    private Color color = new Color();
    private Settings settings;
    private Dictionary dictionary;
    private TablespaceSet tablespaceSet;
    private Contents contents;
    private String columnGroups = "";
    private String testDataList = "";
    private String sequenceSet = "";
    private String triggerSet = "";
    private String changeTrackingList = "";
    
    public static void main(String args[]) {
        Writer out = new OutputStreamWriter(System.out);
        JAXB.marshal(new Diagram(), out);
    }
    /*==========================================================================
     * getter and setter
     */
    @XmlElement(name="page_setting")
    public PageSetting getPageSetting() {
        return pageSetting;
    }
    public void setPageSetting(PageSetting pageSetting) {
        this.pageSetting = pageSetting;
    }
    @XmlElement(name="category_index")
    public int getCategoryIndex() {
        return categoryIndex;
    }
    public void setCategoryIndex(int categoryIndex) {
        this.categoryIndex = categoryIndex;
    }
    public float getZoom() {
        return zoom;
    }
    public void setZoom(float zoom) {
        this.zoom = zoom;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    @XmlElement(name="default_color")
    public Color getDefaultColor() {
        return defaultColor;
    }
    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public Settings getSettings() {
        return settings;
    }
    public void setSettings(Settings settings) {
        this.settings = settings;
    }
    public Dictionary getDictionary() {
        return dictionary;
    }
    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }
    public TablespaceSet getTablespaceSet() {
        return tablespaceSet;
    }
    public void setTablespaceSet(TablespaceSet tablespaceSet) {
        this.tablespaceSet = tablespaceSet;
    }
    public Contents getContents() {
        return contents;
    }
    public void setContents(Contents contents) {
        this.contents = contents;
    }
    public String getColumnGroups() {
        return columnGroups;
    }
    public void setColumnGroups(String columnGroups) {
        this.columnGroups = columnGroups;
    }
    public String getTestDataList() {
        return testDataList;
    }
    public void setTestDataList(String testDataList) {
        this.testDataList = testDataList;
    }
    public String getSequenceSet() {
        return sequenceSet;
    }
    public void setSequenceSet(String sequenceSet) {
        this.sequenceSet = sequenceSet;
    }
    public String getTriggerSet() {
        return triggerSet;
    }
    public void setTriggerSet(String triggerSet) {
        this.triggerSet = triggerSet;
    }
    public String getChangeTrackingList() {
        return changeTrackingList;
    }
    public void setChangeTrackingList(String changeTrackingList) {
        this.changeTrackingList = changeTrackingList;
    }
}
