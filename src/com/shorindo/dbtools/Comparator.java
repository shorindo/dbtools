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

import java.util.List;

/**
 * 
 */
public abstract class Comparator<T> {
    public boolean eq(Object expect, Object actual) {
        if (expect == null && actual == null) {
            return true;
        } else if (expect == null && actual != null) {
            return false;
        } else if (expect != null && actual == null) {
            return false;
        } else if (expect.equals(actual)) {
            return true;
        } else {
            return false;
        }
    }
    public abstract Difference diff(T other);
    
    public static class Difference {
        public boolean isSame() {
            return false;
        }
    }
}
