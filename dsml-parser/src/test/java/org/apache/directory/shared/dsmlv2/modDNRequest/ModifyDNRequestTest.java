/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */

package org.apache.directory.shared.dsmlv2.modDNRequest;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.directory.junit.tools.Concurrent;
import org.apache.directory.junit.tools.ConcurrentJunitRunner;
import org.apache.directory.shared.dsmlv2.AbstractTest;
import org.apache.directory.shared.dsmlv2.Dsmlv2Parser;
import org.apache.directory.shared.ldap.model.message.Control;
import org.apache.directory.shared.ldap.model.message.ModifyDnRequest;
import org.apache.directory.shared.util.Strings;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Tests for the Modify Dn Request parsing
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
@RunWith(ConcurrentJunitRunner.class)
@Concurrent()
public class ModifyDNRequestTest extends AbstractTest
{
    /**
     * Test parsing of a request with the (optional) requestID attribute
     */
    @Test
    public void testRequestWithRequestId()
    {
        Dsmlv2Parser parser = null;
        try
        {
            parser = new Dsmlv2Parser();

            parser.setInput( ModifyDNRequestTest.class.getResource( "request_with_requestID_attribute.xml" )
                .openStream(), "UTF-8" );

            parser.parse();
        }
        catch ( Exception e )
        {
            fail( e.getMessage() );
        }

        ModifyDnRequest modifyDNRequest = ( ModifyDnRequest ) parser.getBatchRequest().getCurrentRequest();

        assertEquals( 456, modifyDNRequest.getMessageId() );
    }


    /**
     * Test parsing of a request with the (optional) requestID attribute equals to 0
     */
    @Test
    public void testRequestWithRequestIdEquals0()
    {
        testParsingFail( ModifyDNRequestTest.class, "request_with_requestID_equals_0.xml" );
    }


    /**
     * Test parsing of a request with a (optional) Control element
     */
    @Test
    public void testRequestWith1Control()
    {
        Dsmlv2Parser parser = null;
        try
        {
            parser = new Dsmlv2Parser();

            parser.setInput( ModifyDNRequestTest.class.getResource( "request_with_1_control.xml" ).openStream(),
                "UTF-8" );

            parser.parse();
        }
        catch ( Exception e )
        {
            fail( e.getMessage() );
        }

        ModifyDnRequest modifyDNRequest = ( ModifyDnRequest ) parser.getBatchRequest().getCurrentRequest();
        Control control = modifyDNRequest.getCurrentControl();

        assertEquals( 1, modifyDNRequest.getControls().size() );
        assertTrue( control.isCritical() );
        assertEquals( "1.2.840.113556.1.4.643", control.getOid() );
        assertEquals( "Some text", Strings.utf8ToString((byte[]) control.getValue()) );
    }


    /**
     * Test parsing of a request with a (optional) Control element with Base64 Value
     */
    @Test
    public void testRequestWith1ControlBase64Value()
    {
        Dsmlv2Parser parser = null;
        try
        {
            parser = new Dsmlv2Parser();

            parser.setInput( ModifyDNRequestTest.class.getResource( "request_with_1_control_base64_value.xml" )
                .openStream(), "UTF-8" );

            parser.parse();
        }
        catch ( Exception e )
        {
            fail( e.getMessage() );
        }

        ModifyDnRequest modifyDNRequest = ( ModifyDnRequest ) parser.getBatchRequest().getCurrentRequest();
        Control control = modifyDNRequest.getCurrentControl();

        assertEquals( 1, modifyDNRequest.getControls().size() );
        assertTrue( control.isCritical() );
        assertEquals( "1.2.840.113556.1.4.643", control.getOid() );
        assertEquals( "DSMLv2.0 rocks!!", Strings.utf8ToString((byte[]) control.getValue()) );
    }


    /**
     * Test parsing of a request with a (optional) Control element with empty value
     */
    @Test
    public void testRequestWith1ControlEmptyValue()
    {
        Dsmlv2Parser parser = null;
        try
        {
            parser = new Dsmlv2Parser();

            parser.setInput( ModifyDNRequestTest.class.getResource( "request_with_1_control_empty_value.xml" )
                .openStream(), "UTF-8" );

            parser.parse();
        }
        catch ( Exception e )
        {
            fail( e.getMessage() );
        }

        ModifyDnRequest modifyDNRequest = ( ModifyDnRequest ) parser.getBatchRequest().getCurrentRequest();
        Control control = modifyDNRequest.getCurrentControl();

        assertEquals( 1, modifyDNRequest.getControls().size() );
        assertTrue( control.isCritical() );
        assertEquals( "1.2.840.113556.1.4.643", control.getOid() );
        assertFalse( control.hasValue() );
    }


    /**
     * Test parsing of a request with 2 (optional) Control elements
     */
    @Test
    public void testRequestWith2Controls()
    {
        Dsmlv2Parser parser = null;
        try
        {
            parser = new Dsmlv2Parser();

            parser.setInput( ModifyDNRequestTest.class.getResource( "request_with_2_controls.xml" ).openStream(),
                "UTF-8" );

            parser.parse();
        }
        catch ( Exception e )
        {
            fail( e.getMessage() );
        }

        ModifyDnRequest modifyDNRequest = ( ModifyDnRequest ) parser.getBatchRequest().getCurrentRequest();
        Control control = modifyDNRequest.getCurrentControl();

        assertEquals( 2, modifyDNRequest.getControls().size() );
        assertFalse( control.isCritical() );
        assertEquals( "1.2.840.113556.1.4.789", control.getOid() );
        assertEquals( "Some other text", Strings.utf8ToString((byte[]) control.getValue()) );
    }


    /**
     * Test parsing of a request with 3 (optional) Control elements without value
     */
    @Test
    public void testRequestWith3ControlsWithoutValue()
    {
        Dsmlv2Parser parser = null;
        try
        {
            parser = new Dsmlv2Parser();

            parser.setInput( ModifyDNRequestTest.class.getResource( "request_with_3_controls_without_value.xml" )
                .openStream(), "UTF-8" );

            parser.parse();
        }
        catch ( Exception e )
        {
            fail( e.getMessage() );
        }

        ModifyDnRequest modifyDNRequest = ( ModifyDnRequest ) parser.getBatchRequest().getCurrentRequest();
        Control control = modifyDNRequest.getCurrentControl();

        assertEquals( 3, modifyDNRequest.getControls().size() );
        assertTrue( control.isCritical() );
        assertEquals( "1.2.840.113556.1.4.456", control.getOid() );
        assertFalse( control.hasValue() );
    }


    /**
     * Test parsing of a request without dn attribute
     */
    @Test
    public void testRequestWithoutDnAttribute()
    {
        testParsingFail( ModifyDNRequestTest.class, "request_without_dn_attribute.xml" );
    }


    /**
     * Test parsing of a request without newrdn attribute
     */
    @Test
    public void testRequestWithoutNewRdnAttribute()
    {
        testParsingFail( ModifyDNRequestTest.class, "request_without_newrdn_attribute.xml" );
    }


    /**
     * Test parsing of a request without dn and newrdn attributes
     */
    @Test
    public void testRequestWithDnAndNewRdnAttributes()
    {
        Dsmlv2Parser parser = null;
        try
        {
            parser = new Dsmlv2Parser();

            parser.setInput( ModifyDNRequestTest.class.getResource( "request_with_dn_and_newrdn_attributes.xml" )
                .openStream(), "UTF-8" );

            parser.parse();
        }
        catch ( Exception e )
        {
            fail( e.getMessage() );
        }

        ModifyDnRequest modifyDNRequest = ( ModifyDnRequest ) parser.getBatchRequest().getCurrentRequest();

        assertEquals( "cn=Bob Rush,ou=Dev,dc=Example,dc=COM", modifyDNRequest.getName().getNormName() );

        assertEquals( "cn=Steve Jobs", modifyDNRequest.getNewRdn().getNormName() );
    }


    /**
     * Test parsing of a request with deleteoldrdn to true
     */
    @Test
    public void testRequestWithDeleteOldRdnTrue()
    {
        Dsmlv2Parser parser = null;
        try
        {
            parser = new Dsmlv2Parser();

            parser.setInput(
                ModifyDNRequestTest.class.getResource( "request_with_deleteoldrdn_true.xml" ).openStream(), "UTF-8" );

            parser.parse();
        }
        catch ( Exception e )
        {
            fail( e.getMessage() );
        }

        ModifyDnRequest modifyDNRequest = ( ModifyDnRequest ) parser.getBatchRequest().getCurrentRequest();

        assertTrue( modifyDNRequest.getDeleteOldRdn() );
    }


    /**
     * Test parsing of a request with deleteoldrdn to 1
     */
    @Test
    public void testRequestWithDeleteOldRdn1()
    {
        Dsmlv2Parser parser = null;
        try
        {
            parser = new Dsmlv2Parser();

            parser.setInput( ModifyDNRequestTest.class.getResource( "request_with_deleteoldrdn_1.xml" ).openStream(),
                "UTF-8" );

            parser.parse();
        }
        catch ( Exception e )
        {
            fail( e.getMessage() );
        }

        ModifyDnRequest modifyDNRequest = ( ModifyDnRequest ) parser.getBatchRequest().getCurrentRequest();

        assertTrue( modifyDNRequest.getDeleteOldRdn() );
    }


    /**
     * Test parsing of a request with deleteoldrdn to false
     */
    @Test
    public void testRequestWithDeleteOldRdnFalse()
    {
        Dsmlv2Parser parser = null;
        try
        {
            parser = new Dsmlv2Parser();

            parser.setInput( ModifyDNRequestTest.class.getResource( "request_with_deleteoldrdn_false.xml" )
                .openStream(), "UTF-8" );

            parser.parse();
        }
        catch ( Exception e )
        {
            fail( e.getMessage() );
        }

        ModifyDnRequest modifyDNRequest = (ModifyDnRequest) parser.getBatchRequest().getCurrentRequest();

        assertFalse( modifyDNRequest.getDeleteOldRdn() );
    }


    /**
     * Test parsing of a request with deleteoldrdn to 0
     */
    @Test
    public void testRequestWithDeleteOldRdn0()
    {
        Dsmlv2Parser parser = null;
        try
        {
            parser = new Dsmlv2Parser();

            parser.setInput( ModifyDNRequestTest.class.getResource( "request_with_deleteoldrdn_0.xml" ).openStream(),
                "UTF-8" );

            parser.parse();
        }
        catch ( Exception e )
        {
            fail( e.getMessage() );
        }

        ModifyDnRequest modifyDNRequest = ( ModifyDnRequest ) parser.getBatchRequest().getCurrentRequest();

        assertFalse( modifyDNRequest.getDeleteOldRdn() );
    }


    /**
     * Test parsing of a request with deleteoldrdn to an error value
     */
    @Test
    public void testRequestWithDeleteOldRdnError()
    {
        testParsingFail( ModifyDNRequestTest.class, "request_with_deleteoldrdn_error.xml" );
    }


    /**
     * Test parsing of a request with newSuperior attribute
     */
    @Test
    public void testRequestWithNewSuperior()
    {
        Dsmlv2Parser parser = null;
        try
        {
            parser = new Dsmlv2Parser();

            parser.setInput( ModifyDNRequestTest.class.getResource( "request_with_newSuperior_attribute.xml" )
                .openStream(), "UTF-8" );

            parser.parse();
        }
        catch ( Exception e )
        {
            fail( e.getMessage() );
        }

        ModifyDnRequest modifyDNRequest = ( ModifyDnRequest ) parser.getBatchRequest().getCurrentRequest();

        assertEquals( "cn=Steve Jobs,ou=Dev,dc=apple,dc=com", modifyDNRequest.getNewSuperior().getNormName() );
    }


    /**
     * Test parsing of a request with a needed requestID attribute
     * 
     * DIRSTUDIO-1
     */
    @Test
    public void testRequestWithNeededRequestId()
    {
        testParsingFail( ModifyDNRequestTest.class, "request_with_needed_requestID.xml" );
    }
}
