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


import java.util.Map;
import java.util.Random;

import org.apache.directory.api.ldap.model.entry.Attribute;
import org.apache.directory.api.ldap.model.entry.DefaultAttribute;
import org.apache.directory.api.ldap.model.entry.StringValue;
import org.apache.directory.api.ldap.model.entry.Value;
import org.apache.directory.api.ldap.model.exception.LdapInvalidAttributeValueException;


/**
 * A default anonymizer for attributes that are HR
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public class StringAnonymizer extends AbstractAnonymizer<String>
{
    /** Create a random generator */
    Random random = new Random( System.currentTimeMillis() );


    /**
     * Anonymize an attribute using pure random values (either chars of bytes, depending on the Attribute type)
     */
    public Attribute anonymize( Map<Value<String>, Value<String>> valueMap, Attribute attribute )
    {
        Attribute result = new DefaultAttribute( attribute.getAttributeType() );
        random.setSeed( System.nanoTime() );

        for ( Value<?> value : attribute )
        {
            Value<String> anonymized =  valueMap.get( value );
            
            if ( anonymized != null )
            {
                try
                {
                    result.add( anonymized.getString() );
                }
                catch ( LdapInvalidAttributeValueException e )
                {
                    // TODO : handle that
                }
            }
            else
            {
                if ( value instanceof StringValue )
                {
                    String strValue = value.getNormValue().toString();
                    int length = strValue.length();
    
                    // Same size
                    char[] newValue = new char[length];
    
                    for ( int i = 0; i < length; i++ )
                    {
                        newValue[i] = ( char ) ( random.nextInt( 'Z' - 'A' ) + 'A' );
                    }
    
                    try
                    {
                        String newValueStr = new String( newValue );
                        result.add( newValueStr );
                        
                        Value<String> anonValue = new StringValue( attribute.getAttributeType(), newValueStr );
                        valueMap.put( ( Value<String> ) value, anonValue );
                    }
                    catch ( LdapInvalidAttributeValueException e )
                    {
                        // TODO : handle that
                    }
                }
                else
                {
                    byte[] byteValue = value.getBytes();
    
                    // Same size
                    byte[] newValue = new byte[byteValue.length];
    
                    for ( int i = 0; i < byteValue.length; i++ )
                    {
                        newValue[i] = ( byte ) random.nextInt();
                    }
    
                    try
                    {
                        result.add( newValue );
                    }
                    catch ( LdapInvalidAttributeValueException e )
                    {
                        // TODO : handle that
                    }
                }
            }
        }

        return result;
    }
}
