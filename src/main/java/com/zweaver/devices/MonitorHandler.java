
package com.zweaver.devices;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import javax.swing.JFrame;


public class MonitorHandler {
    public static void openOnSameMonitor(Window activeWindow, JFrame activeFrame) {
        // open new frame on same monitor
        GraphicsConfiguration config = activeWindow.getGraphicsConfiguration();
        GraphicsDevice myScreen = config.getDevice();
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] allScreens = env.getScreenDevices();
        int myScreenIndex = -1;
        for (int i = 0; i < allScreens.length; i++) {
            if (allScreens[i].equals(myScreen))
            {
                myScreenIndex = i;
                break;
            }
        }
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gd = ge.getScreenDevices();
        int width = 0, height = 0;
        if( myScreenIndex > -1 && myScreenIndex < gd.length ) {
            width = gd[myScreenIndex].getDefaultConfiguration().getBounds().width;
            height = gd[myScreenIndex].getDefaultConfiguration().getBounds().height;
            activeFrame.setLocation(
                ((width / 2) - (activeFrame.getSize().width / 2)) + gd[myScreenIndex].getDefaultConfiguration().getBounds().x, 
                ((height / 2) - (activeFrame.getSize().height / 2)) + gd[myScreenIndex].getDefaultConfiguration().getBounds().y
            );
            activeFrame.setVisible(true);
            activeFrame.setLocationRelativeTo(activeWindow);
        } else {
            throw new RuntimeException( "No Screens Found" );
        }
    }
}
