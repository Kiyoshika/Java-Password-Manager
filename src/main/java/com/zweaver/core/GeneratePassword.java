package com.zweaver.core;

import java.util.Random;
import javax.swing.JCheckBox;

public class GeneratePassword {
    private String[] letterPool = {
      "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
      "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
    };
    
    private String[] symbolsPool = {
        "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "+", "=", "~", ":", ";", "`", "?"
    };
    
    private String[] numbersPool = {
      "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"  
    };
    
    public String generatePassword(
            JCheckBox useNumbersCheckBox,
            JCheckBox useSymbolsCheckBox,
            JCheckBox mixCasesCheckBox,
            JCheckBox startsWithLetterCheckBox,
            int passwordLength,
            float symbolsToLettersRatio,
            String profileName
    ) {
        String newPassword = "";
        Random rand = new Random();
        
        // iterate over password length
        for (int i = 0; i < passwordLength; ++i) {
            // make first character a letter if selected
            if (i == 0 && startsWithLetterCheckBox.isSelected()) {
                newPassword += letterPool[rand.nextInt(letterPool.length)];
             continue;   
            }
            // use symbols to letter ratio to decide which pool to take from
            if (rand.nextFloat() <= symbolsToLettersRatio && (useNumbersCheckBox.isSelected() || useSymbolsCheckBox.isSelected())) {
                if (useNumbersCheckBox.isSelected() && useSymbolsCheckBox.isSelected()) {
                    if(rand.nextInt(2) == 0) {
                        newPassword += numbersPool[rand.nextInt(numbersPool.length)];
                    } else {
                        newPassword += symbolsPool[rand.nextInt(numbersPool.length)];
                    }
                    
                }
                else if (useNumbersCheckBox.isSelected() && !useSymbolsCheckBox.isSelected()) {
                    newPassword += numbersPool[rand.nextInt(numbersPool.length)];
                } else {
                    newPassword += symbolsPool[rand.nextInt(numbersPool.length)];
                }
            }
            
            
            else {
                if (mixCasesCheckBox.isSelected()) {
                    newPassword += letterPool[rand.nextInt(letterPool.length)];
                } else {
                    newPassword += letterPool[rand.nextInt(letterPool.length)].toLowerCase();
                }
            }
        }
        return newPassword;
    }
}
