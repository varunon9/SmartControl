package me.varunon9.smartcontrol;

import java.util.regex.Pattern;

public class ValidateIPAndPort {
	private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");//to validate ip address
    public  boolean validateIP(final String ip) {
        return PATTERN.matcher(ip).matches();
    }
    public boolean validatePort(String portNumber) {
        if ((portNumber != null) && (portNumber.length() == 4) && (portNumber.matches(".*\\d.*"))) {
            if( (Integer.parseInt(portNumber) > 1023))
                return true;
            else
                return false;
        }
        else 
            return false;
    }
}
