package com.zweaver.IO;

import static com.zweaver.core.CoreGUI.applicationsListBox;
import static com.zweaver.core.CoreGUI.applicationsListModel;
import static com.zweaver.core.CoreGUI.favoritesListBox;
import static com.zweaver.core.CoreGUI.favoritesListModel;
import static com.zweaver.core.CoreGUI.profilePasswordMap;
import static com.zweaver.core.CoreGUI.websitesListBox;
import static com.zweaver.core.CoreGUI.websitesListModel;
import com.zweaver.dialogue.OKDialogue;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import org.jasypt.util.text.AES256TextEncryptor;


public class ConfigHandler {
    public static void ImportConfig(String enteredUsername) {
        // import config options:
        // tabName<split>profileName<split>password
        
        try {
            AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
            textEncryptor.setPassword(enteredUsername);
           
            String path = System.getProperty("user.home") + File.separator + "passwordmanager" + File.separator + enteredUsername + File.separator + enteredUsername + "_settings.cfg";
            BufferedReader configFile = new BufferedReader(new FileReader(path));
            String currentLine;
            String[] parsedLine;
            
            String decryptedProfile;
            String decryptedPassword;
            
            while ( (currentLine = configFile.readLine()) != null ) {
                parsedLine = currentLine.split("<split>");
                // 2 - tab name, 3 - profile name, 4 - password
                decryptedProfile = textEncryptor.decrypt(parsedLine[3]);
                decryptedPassword = textEncryptor.decrypt(parsedLine[4]);

                if (parsedLine[2].equals("favorites")) {
                    profilePasswordMap.put(decryptedProfile, decryptedPassword);
                    favoritesListModel.addElement(decryptedProfile);
                } else if (parsedLine[2].equals("websites")) {
                    profilePasswordMap.put(decryptedProfile, decryptedPassword);
                    websitesListModel.addElement(decryptedProfile);
                } else if (parsedLine[2].equals("applications")) {
                    profilePasswordMap.put(decryptedProfile, decryptedPassword);
                    applicationsListModel.addElement(decryptedProfile);
                }
            }
            configFile.close();
        } catch (Exception e) {
            Window activeWindow = javax.swing.FocusManager.getCurrentManager().getActiveWindow();
            OKDialogue okd = new OKDialogue(activeWindow, "There was a problem importing your configuration.");
        }
    }
    
    public static void ExportConfig(String enteredUsername, String encryptedUserPassword) {
         // export config options:
       // user<split>password<split>tabName<split>profileName<split>password
       try {
           AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
           textEncryptor.setPassword(enteredUsername);
           String path = System.getProperty("user.home") + File.separator + "passwordmanager" + File.separator + enteredUsername + File.separator + enteredUsername + "_settings.cfg";
           FileWriter configFile = new FileWriter(path);
           
           String encryptedUsername = textEncryptor.encrypt(enteredUsername);
           String encryptedProfile;
           String encryptedPassword;

           
           // write favorites tab first
           for (int i = 0; i < favoritesListBox.getModel().getSize(); ++i) {
               encryptedProfile = textEncryptor.encrypt(favoritesListBox.getModel().getElementAt(i));
               encryptedPassword = textEncryptor.encrypt(profilePasswordMap.get(favoritesListBox.getModel().getElementAt(i)));
               configFile.write(encryptedUsername+"<split>"+encryptedUserPassword+"<split>"+"favorites<split>"+encryptedProfile+"<split>"+encryptedPassword+"\n");
           }
           
           // write websites tab second
           for (int i = 0; i < websitesListBox.getModel().getSize(); ++i) {
               encryptedUsername = textEncryptor.encrypt(enteredUsername);
               encryptedProfile = textEncryptor.encrypt(websitesListBox.getModel().getElementAt(i));
               encryptedPassword = textEncryptor.encrypt(profilePasswordMap.get(websitesListBox.getModel().getElementAt(i)));
               configFile.write(encryptedUsername+"<split>"+encryptedUserPassword+"<split>"+"websites<split>"+encryptedProfile+"<split>"+encryptedPassword+"\n");
           }
           
           // write applications tab last
           for (int i = 0; i < applicationsListBox.getModel().getSize(); ++i) {
               encryptedProfile = textEncryptor.encrypt(applicationsListBox.getModel().getElementAt(i));
               encryptedPassword = textEncryptor.encrypt(profilePasswordMap.get(applicationsListBox.getModel().getElementAt(i)));
               configFile.write(encryptedUsername+"<split>"+encryptedUserPassword+"<split>"+"applications<split>"+encryptedProfile+"<split>"+encryptedPassword+"\n");
           }
           configFile.close();
           Window activeWindow = javax.swing.FocusManager.getCurrentManager().getActiveWindow();
           OKDialogue okd = new OKDialogue(activeWindow, "Your configuration has been encrypted and exported to the following path: ");
           okd.messageSubText.setVisible(true);
           okd.messageSubText.setText(path);
       } catch (Exception e) {
           Window activeWindow = javax.swing.FocusManager.getCurrentManager().getActiveWindow();
           OKDialogue okd = new OKDialogue(activeWindow, "There was a problem exporting your configuration.");
       }
    }
}
