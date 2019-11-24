/*
 * Copyright 2014 the original author or authors.
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
package org.gradle.api.internal.artifacts.repositories.resolver

import org.gradle.api.internal.artifacts.ImmutableModuleIdentifierFactory
import org.gradle.api.internal.artifacts.ivyservice.IvyContextManager
import org.gradle.api.internal.artifacts.repositories.transport.RepositoryTransport
import org.gradle.internal.resource.local.FileResourceRepository
import org.gradle.internal.resource.local.FileStore
import org.gradle.internal.resource.local.LocallyAvailableResourceFinder
import spock.lang.Specification

class IvyResolverTest extends Specification {

    def "has useful string representation"() {
        expect:
        def resolver = resolver()
        resolver.toString() == "Ivy repository 'repo'"
    }

    def "resolvers are differentiated by m2compatible flag"() {
        given:
        def resolver1 = resolver()
        def resolver2 = resolver()

        resolver1.addIvyPattern(new IvyResourcePattern("ivy1"))
        resolver1.addArtifactPattern(new IvyResourcePattern("artifact1"))
        resolver2.addIvyPattern(new IvyResourcePattern("ivy1"))
        resolver2.addArtifactPattern(new IvyResourcePattern("artifact1"))
        resolver2.m2compatible = true

        expect:
        resolver1.id != resolver2.id
    }

    private IvyResolver resolver() {
        new IvyResolver("repo", Stub(RepositoryTransport), Stub(LocallyAvailableResourceFinder), false, Stub(FileStore), Stub(IvyContextManager), Mock(ImmutableModuleIdentifierFactory), null, Stub(FileResourceRepository))
    }
}