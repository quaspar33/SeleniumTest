package com.web.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordFromSmsParser {
    public String parse(String input) {
//        Pattern pattern = Pattern.compile("\\d{6}");
        Pattern pattern = Pattern.compile("to:\\s*(\\S+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
//            return matcher.group(0);
        }
        return null;
    }
}
