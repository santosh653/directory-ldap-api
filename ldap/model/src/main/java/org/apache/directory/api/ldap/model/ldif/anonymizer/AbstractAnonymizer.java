/*
 *   Licensed to the Apache Software Foundation (ASF) under one
 *   or more contributor license agreements.  See the NOTICE file
 *   distributed with this work for additional information
 *   regarding copyright ownership.  The ASF licenses this file
 *   to you under the Apache License, Version 2.0 (the
 *   "License"); you may not use this file except in compliance
 *   with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 *
 */

package org.apache.directory.api.ldap.model.ldif.anonymizer;

import java.util.HashMap;
import java.util.Map;

import org.apache.directory.api.ldap.model.schema.SchemaManager;

/**
 * An abstract class implementing the default behavior of an Aninymizer instance
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public abstract class AbstractAnonymizer<K> implements Anonymizer<K>
{
    /** The SchemaManager instance */
    protected SchemaManager schemaManager;
    
    /** The map of AttributeType'sOID we want to anonymize. They are all associated with anonymizers */
    protected Map<String, Anonymizer> attributeAnonymizers = new HashMap<String, Anonymizer>();

    /**
     * {@inheritDoc}
     */
    public void setSchemaManager( SchemaManager schemaManager )
    {
        this.schemaManager = schemaManager;
    }
    
    
    /**
     * Set the list of existing anonymizers
     *
     * @param attributeAnonymizers The list of existing anonymizers
     */
    public void setAnonymizers( Map<String, Anonymizer> attributeAnonymizers )
    {
        this.attributeAnonymizers = attributeAnonymizers;
    }
}