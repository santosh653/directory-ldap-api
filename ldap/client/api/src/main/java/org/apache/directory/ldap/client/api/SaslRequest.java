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

package org.apache.directory.ldap.client.api;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.directory.shared.ldap.model.message.Control;
import org.apache.directory.shared.util.StringConstants;
import org.apache.directory.shared.util.Strings;


/**
 * Holds the data required to complete the SASL operation
 *  
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
// TODO make this class abstract
public class SaslRequest
{
    /** The mechanism used to decode user identity */
    protected String saslMechanism;

    /** The list of controls */
    protected List<Control> controls = new ArrayList<Control>();

    /** The username */
    protected String username;

    /** The credentials */
    protected byte[] credentials;

    /** The realm name on the server */
    protected String realmName;

    /** The authorization ID of the entity */
    protected String authorizationId;


    /**
     * Creates a new instance of SaslRequest.
     *
     * @param saslMechanism
     *      the SASL mechanism
     */
    protected SaslRequest( String saslMechanism )
    {
        this.saslMechanism = saslMechanism;
    }


    /**
     * Adds the given controls.
     *
     * @param controls the controls
     */
    public void addAllControls( Control[] controls )
    {
        this.controls.addAll( Arrays.asList( controls ) );
    }


    /**
     * Adds the given control.
     *
     * @param control the control
     */
    public void addControl( Control control )
    {
        this.controls.add( control );
    }


    /**
     * Gets the authorization ID.
     *
     * @return the authorization ID
     */
    public String getAuthorizationId()
    {
        return authorizationId;
    }


    /**
     * Gets the controls.
     *
     * @return the controls
     */
    public Control[] getControls()
    {
        return controls.toArray( new Control[0] );
    }


    /**
     * Gets the crendentials
     *
     * @return the credentials
     */
    public byte[] getCredentials()
    {
        if ( credentials != null )
        {
            return credentials;
        }
        else
        {
            return StringConstants.EMPTY_BYTES;
        }
    }


    /**
     * Gets realm name.
     *
     * @return the realm name
     */
    public String getRealmName()
    {
        return realmName;
    }


    /**
     * Gets the SASL mechanism.
     *
     * @return the SASL mechanism
     */
    public String getSaslMechanism()
    {
        return saslMechanism;
    }


    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername()
    {
        return username;
    }


    /**
     * Sets the Authorization ID
     *
     * @param authorizationId The authorization ID
     */
    public void setAuthorizationId( String authorizationId )
    {
        this.authorizationId = authorizationId;
    }


    /**
     * Sets the credentials.
     *
     * @param credentials the credentials
     */
    public void setCredentials( byte[] credentials )
    {
        this.credentials = credentials;
    }


    /**
     * Sets the credentials.
     *
     * @param credentials the credentials
     */
    public void setCredentials( String credentials )
    {
        this.credentials = Strings.getBytesUtf8( credentials );
    }


    /**
     * Sets the realm name.
     * 
     * @param realmName The realm name
     */
    protected void setRealmName( String realmName )
    {
        this.realmName = realmName;
    }


    /**
     * Sets the SASL mechanism
     *
     * @param saslMechanism the SASL mechanism
     */
    protected void setSaslMechanism( String saslMechanism )
    {
        this.saslMechanism = saslMechanism;
    }


    /**
     * Sets the username.
     *
     * @param username the username
     */
    public void setUsername( String username )
    {
        this.username = username;
    }
}