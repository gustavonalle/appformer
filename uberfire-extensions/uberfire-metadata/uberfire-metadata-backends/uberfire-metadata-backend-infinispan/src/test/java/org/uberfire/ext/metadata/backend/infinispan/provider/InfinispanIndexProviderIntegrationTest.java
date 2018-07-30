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

package org.uberfire.ext.metadata.backend.infinispan.provider;

import java.util.Collections;
import java.util.List;

import org.arquillian.cube.docker.junit.rule.ContainerDslRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uberfire.ext.metadata.model.KObject;
import org.uberfire.ext.metadata.model.impl.KObjectImpl;
import org.uberfire.ext.metadata.model.impl.KPropertyImpl;

import static org.junit.Assert.*;

public class InfinispanIndexProviderIntegrationTest {

    private Logger logger = LoggerFactory.getLogger(InfinispanIndexProviderIntegrationTest.class);

    @ClassRule
    public static ContainerDslRule infinispan = new ContainerDslRule("jboss/infinispan-server:9.3.0.Final")
            .withEnvironment("APP_USER",
                             "user")
            .withEnvironment("APP_PASS",
                             "user")
            .withPortBinding(8080,
                             11222);

    @Test
    public void test() {
        InfinispanIndexProvider provider = new InfinispanIndexProvider(new InfinispanContext(),
                                                                       new MappingProvider());

        KPropertyImpl<String> prop = new KPropertyImpl<>("aParentField.withSubfield",
                                                         "theValue",
                                                         true);

        KObject kObject = new KObjectImpl("1",
                                          "String",
                                          "java",
                                          "j",
                                          "key",
                                          Collections.singletonList(prop),
                                          true);

        KObject anotherKObject = new KObjectImpl("2",
                                                 "MyType",
                                                 "cid",
                                                 "java",
                                                 "key",
                                                 Collections.singletonList(prop),
                                                 true);

        provider.index(kObject);
        provider.index(anotherKObject);

        List<KObject> results = provider.getQueryFactory("java").from("String")
                .having("segment__id")
                .eq("j")
                .and()
                .having("key").eq("key")
                .build().list();

        assertTrue(results.size() > 0);

        results = provider.getQueryFactory("cid").from("MyType")
                .having("segment__id")
                .eq("java")
                .build().list();

        assertTrue(results.size() > 0);
    }
}