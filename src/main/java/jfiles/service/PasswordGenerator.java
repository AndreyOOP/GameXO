package jfiles.service;

import org.springframework.stereotype.Service;

/**Service for password generation. By default it has 4 characters length, letters and ends up by number<br>
 * There is an option to define password length and include or not special character<br>
 *  */
@Service("PasswordGenerator")
public class PasswordGenerator {

    private int     passwordLength = 4;
    private Boolean addSpecialChar = false;

    private String specialChar  = "*&%|/^$#@";
    private String number       = "0123456789";
    private String alphabet     = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public PasswordGenerator(){}

    /**Method generates password based on password length and special character parameter values (setted or default) */
    public String generate(){

        String newPassword = "";

        int alphabetLength = alphabet.length();

        for(int i=0; i<passwordLength-1; i++){

            newPassword += alphabet.charAt( randomIndex( alphabetLength));
        }

        newPassword += number.charAt( randomIndex( number.length()));

        if(addSpecialChar){

            int ind = randomIndex( passwordLength-1) + 1;

            String begin = newPassword.substring(0, ind-1);
            char   mid   = specialChar.charAt( randomIndex( specialChar.length()));
            String end   = newPassword.substring(ind);

            newPassword  = begin + mid + end;
        }

        return newPassword;
    }

    private int randomIndex(int length){

        return (int)( length* Math.random());
    }

    public void setPasswordLength(int passwordLength) {
        this.passwordLength = passwordLength;
    }

    public void setAddSpecialChar(Boolean addSpecialChar) {
        this.addSpecialChar = addSpecialChar;
    }
}
