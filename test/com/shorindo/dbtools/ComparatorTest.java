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

import java.util.Date;
import java.util.List;

import org.junit.Test;

/**
 * 
 */
public class ComparatorTest {
    public static class StringComparator extends Comparator<StringComparator> {
        private String s;
        public StringComparator(String s) {
            this.s = s;
        }
        public String getString() {
            return s;
        }
        @Override
        public Difference diff(StringComparator other) {
            // TODO Auto-generated method stub
            return null;
        }
    }
    @Test
    public void testString_1() {
        StringComparator expect = new StringComparator("A");
        StringComparator actual = new StringComparator("A");
        assertTrue(expect.diff(actual).isSame());
    }
    @Test
    public void testString_2() {
        StringComparator expect = new StringComparator("A");
        StringComparator actual = new StringComparator("B");
        assertFalse(expect.diff(actual).isSame());
    }
    @Test
    public void testString_3() {
        StringComparator expect = new StringComparator(null);
        StringComparator actual = new StringComparator("B");
        assertFalse(expect.diff(actual).isSame());
    }
    @Test
    public void testString_4() {
        StringComparator expect = new StringComparator("A");
        StringComparator actual = new StringComparator(null);
        assertFalse(expect.diff(actual).isSame());
    }
    
    public static class BooleanComparator extends Comparator<BooleanComparator> {
        private Boolean b;
        public BooleanComparator(Boolean b) {
            this.b = b;
        }
        public Boolean getValue() {
            return b;
        }
        @Override
        public Difference diff(BooleanComparator other) {
            //return eq(b, other.getValue());
            return new Difference();
        }
    }
    @Test
    public void testBoolean_1() {
        BooleanComparator expect = new BooleanComparator(true);
        BooleanComparator actual = new BooleanComparator(true);
        assertTrue(expect.diff(actual).isSame());
    }
    @Test
    public void testBoolean_2() {
        BooleanComparator expect = new BooleanComparator(true);
        BooleanComparator actual = new BooleanComparator(false);
        assertFalse(expect.diff(actual).isSame());
    }
    @Test
    public void testBoolean_3() {
        BooleanComparator expect = new BooleanComparator(null);
        BooleanComparator actual = new BooleanComparator(true);
        assertFalse(expect.diff(actual).isSame());
    }
    @Test
    public void testBoolean_4() {
        BooleanComparator expect = new BooleanComparator(false);
        BooleanComparator actual = new BooleanComparator(null);
        assertFalse(expect.diff(actual).isSame());
    }
    
    public static class ShortComparator extends Comparator<ShortComparator> {
        private Short value;
        public ShortComparator(Short value) {
            this.value = value;
        }
        public Short getValue() {
            return value;
        }
        @Override
        public Difference diff(ShortComparator other) {
            //return eq(value, other.getValue());
            return new Difference();
        }
    }
    @Test
    public void testShort_1() {
        ShortComparator expect = new ShortComparator((short)1);
        ShortComparator actual = new ShortComparator((short)1);
        assertTrue(expect.diff(actual).isSame());
    }
    @Test
    public void testShort_2() {
        ShortComparator expect = new ShortComparator((short)1);
        ShortComparator actual = new ShortComparator((short)2);
        assertFalse(expect.diff(actual).isSame());
    }
    @Test
    public void testShort_3() {
        ShortComparator expect = new ShortComparator(null);
        ShortComparator actual = new ShortComparator((short)1);
        assertFalse(expect.diff(actual).isSame());
    }
    @Test
    public void testShort_4() {
        ShortComparator expect = new ShortComparator((short)2);
        ShortComparator actual = new ShortComparator(null);
        assertFalse(expect.diff(actual).isSame());
    }
    
    public static class DateComparator extends Comparator<DateComparator> {
        private Date value;
        public DateComparator(Date value) {
            this.value = value;
        }
        public Date getValue() {
            return value;
        }
        @Override
        public Difference diff(DateComparator other) {
            //return eq(value, other.getValue());
            return new Difference();
        }
    }
    @Test
    public void testDate_1() {
        Date d = new Date();
        DateComparator expect = new DateComparator(d);
        DateComparator actual = new DateComparator(new Date(d.getTime()));
        assertTrue(expect.diff(actual).isSame());
    }
    @Test
    public void testDate_2() {
        DateComparator expect = new DateComparator(new Date(1));
        DateComparator actual = new DateComparator(new Date(2));
        assertFalse(expect.diff(actual).isSame());
    }
    @Test
    public void testDate_3() {
        DateComparator expect = new DateComparator(null);
        DateComparator actual = new DateComparator(new Date());
        assertFalse(expect.diff(actual).isSame());
    }
    @Test
    public void testDate_4() {
        DateComparator expect = new DateComparator(new Date());
        DateComparator actual = new DateComparator(null);
        assertFalse(expect.diff(actual).isSame());
    }
}
