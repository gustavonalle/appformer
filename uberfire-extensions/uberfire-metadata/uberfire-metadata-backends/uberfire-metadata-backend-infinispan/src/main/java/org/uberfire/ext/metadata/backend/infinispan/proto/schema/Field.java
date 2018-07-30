/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.uberfire.ext.metadata.backend.infinispan.proto.schema;

public class Field {

    private final ProtobufScope scope;
    private final String type;
    private final String name;
    private final int index;
    private final boolean sortable;
    private final boolean searchable;

    public Field(ProtobufScope scope,
                 String type,
                 String name,
                 int index) {
        this(scope,
             type,
             name,
             index,
             false,
             false);
    }

    public Field(ProtobufScope scope,
                 ProtobufType type,
                 String name,
                 int index) {
        this(scope,
             type,
             name,
             index,
             false,
             false);
    }

    public Field(ProtobufScope scope,
                 ProtobufType type,
                 String name,
                 int index,
                 boolean sortable,
                 boolean searchable) {
        this(scope,
             type.toString(),
             name,
             index,
             sortable,
             searchable);
    }

    public Field(ProtobufScope scope,
                 String type,
                 String name,
                 int index,
                 boolean sortable,
                 boolean searchable) {
        this.scope = scope;
        this.type = type;
        this.name = name;
        this.index = index;
        this.sortable = sortable;
        this.searchable = searchable;
    }

    public ProtobufScope getScope() {
        return scope;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public boolean isSortable() {
        return sortable;
    }

    public boolean isSearchable() {
        return searchable;
    }
}