/**
 * Copyright 2005-2014 Restlet
 * 
 * The contents of this file are subject to the terms of one of the following
 * open source licenses: Apache 2.0 or LGPL 3.0 or LGPL 2.1 or CDDL 1.0 or EPL
 * 1.0 (the "Licenses"). You can select the license that you prefer but you may
 * not use this file except in compliance with one of these Licenses.
 * 
 * You can obtain a copy of the Apache 2.0 license at
 * http://www.opensource.org/licenses/apache-2.0
 * 
 * You can obtain a copy of the LGPL 3.0 license at
 * http://www.opensource.org/licenses/lgpl-3.0
 * 
 * You can obtain a copy of the LGPL 2.1 license at
 * http://www.opensource.org/licenses/lgpl-2.1
 * 
 * You can obtain a copy of the CDDL 1.0 license at
 * http://www.opensource.org/licenses/cddl1
 * 
 * You can obtain a copy of the EPL 1.0 license at
 * http://www.opensource.org/licenses/eclipse-1.0
 * 
 * See the Licenses for the specific language governing permissions and
 * limitations under the Licenses.
 * 
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly at
 * http://www.restlet.com/products/restlet-framework
 * 
 * Restlet is a registered trademark of Restlet
 */

package es.upv.grc.andropi.server;



import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;

import org.restlet.resource.ServerResource;

import es.upv.grc.andropi.common.AndroPiInterface;
import es.upv.grc.andropi.common.AndroPiInterface.Type;
import es.upv.grc.andropi.common.AndroPiRule;
import es.upv.grc.andropi.common.AndroPiStatus;
import es.upv.grc.andropi.common.RootResource;

/**
 * Mail server resource implementing the {@link MailResource} interface.
 */
public class RootServerResource extends ServerResource implements RootResource {

	@Override
    public AndroPiStatus getAndroPiStatus() {
    	AndroPiStatus status = new AndroPiStatus();
        Enumeration<NetworkInterface> interfaces = null;
		try {
			interfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        while(interfaces.hasMoreElements()){
        	try {
        		NetworkInterface iface = interfaces.nextElement();
        		if(!iface.isLoopback()){
        			status.addInterface(new AndroPiInterface(iface, Type.WIFISTA));
        		}
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        AndroPiRule flow = null;
        flow = new AndroPiRule( 1, 10, 0, new Date(0), 10, 15 , 14, 15, -1,-1);
        status.addFlow(flow);
        return status;
    }
}
