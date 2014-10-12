/*
 * Copyright 2012. Bloomberg Finance L.P.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:  The above
 * copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
package com.bloomberglp.blpapi.examples;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.swing.*;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Event;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.MessageIterator;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;
import com.bloomberglp.blpapi.SessionOptions;

public class SimpleHistoryExample {

    public static void main(String[] args) throws Exception
    {
        SimpleHistoryExample example = new SimpleHistoryExample();
        example.run(args);
        System.out.println("Press ENTER to quit");
        System.in.read();
    }

    private void run(String[] args) throws Exception
    {
        String serverHost = "10.8.8.1";
        int serverPort = 8194;

        SessionOptions sessionOptions = new SessionOptions();
        sessionOptions.setServerHost(serverHost);
        sessionOptions.setServerPort(serverPort);

        System.out.println("Connecting to " + serverHost + ":" + serverPort);
        Session session = new Session(sessionOptions);
        if (!session.start()) {
            System.err.println("Failed to start session.");
            return;
        }
        if (!session.openService("//blp/refdata")) {
            System.err.println("Failed to open //blp/refdata");
            return;
        }
        Service refDataService = session.getService("//blp/refdata");
        Request request = refDataService.createRequest("HistoricalDataRequest");
        
        JFrame frame = new JFrame("InputDialog Example #1");
        String stock = JOptionPane.showInputDialog(frame, "Enter a stock index:");
        
        //JFrame frame = new JFrame("InputDialog Example #1");
        //String stock = JOptionPane.showInputDialog(frame, "Enter a time frame:");
        
        Element securities = request.getElement("securities");
       // securities.appendValue(stock2 + " Equity");
        securities.appendValue(stock + " Equity");
 
        Element fields = request.getElement("fields");
        fields.appendValue("PX_LAST");

        request.set("periodicityAdjustment", "ACTUAL");
        request.set("periodicitySelection", "MONTHLY");
        request.set("startDate", "19700101");
        request.set("endDate", "20141231");
        request.set("maxDataPoints", 20);
        request.set("returnEids", true);


        session.sendRequest(request, null);
        PrintWriter writer = new PrintWriter("asd.txt","UTF-8");
        PrintWriter writer2 = new PrintWriter("asd2.txt","UTF-8");

        while (true) {
            Event event = session.nextEvent();
            MessageIterator msgIter = event.messageIterator();
            while (msgIter.hasNext()) {
                Message msg = msgIter.next();
	            if (event.eventType() == Event.EventType.RESPONSE) {
	            	Element securityData = msg.getElement("securityData");
	            	Element fd = securityData.getElement("fieldData");
	            	System.out.println(securityData.getElement("fieldData"));
	            	
	            	 for (int i = 0; i < fd.numValues(); ++i) {
	            		 writer.println(fd.getValueAsElement(i).getElementAsString("PX_LAST"));
	            		 Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fd.getValueAsElement(i).getElementAsString("date"));
	            		 String datestring = new SimpleDateFormat("MMM yyyy").format(date);
	            		 writer2.println(datestring);
	            	 }
	            	Runtime.getRuntime().exec( new String[] { "open", "macbro.app"} );
	            	//ProcessBuilder p = new ProcessBuilder(new String[] {"open", "/Users/gio/Documents/workspace/Bloomberg/macbro.dmg"});
	            	//Process p = Runtime.getRuntime().exec("/Users/gio/Documents/workspace/Bloomberg/macbros.app");
	                writer.close();
	                writer2.close();
	                break;
	            }
            }
        }

    }
}
