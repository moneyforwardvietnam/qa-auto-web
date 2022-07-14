package core.helper;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.passay.LengthComplexityRule.ERROR_CODE;

public class StringHelper {

    private StringHelper() {
        //Do nothing
    }

    private static final LogHelper logger = LogHelper.getInstance();

    public static String generateEmail(String email) {
        String delimiter = "@";
        List<String> value = Arrays.asList(email.split(delimiter));
        String string1 = value.get(0) + "_" + randomString(6);
        return string1 + delimiter + value.get(1);
    }

    public static String generateRandomPassword(int length) {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            static final String SPECIAL_CHARS = "!@#$%^&*()_+";

            public String getErrorCode() {
                return ERROR_CODE;
            }

            public String getCharacters() {
                return SPECIAL_CHARS;
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        String password = gen.generatePassword(length, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
        logger.info(password);
        return password;
    }

    public static List<String> getDataFromString(String input, String spliter) {
        return Arrays.asList(input.split(spliter));
    }

    public static String generateEmailAliasAddress(String emailAddress, String suffixString) {
        String outEmailAddress = emailAddress.split("@")[0];
        outEmailAddress = outEmailAddress + "+" + suffixString + "@";
        outEmailAddress = outEmailAddress + emailAddress.split("@")[1];

        return outEmailAddress;
    }


    public static List<List<String>> generateCombinations(List<String> listElements) {
        List<List<String>> listCombinations = new ArrayList<>();

        int totalElements = listElements.size();
        int totalCombinations = (int) Math.pow(2.0, totalElements);

        for (int i = 1; i < totalCombinations; i++) {
            List<String> combination = new ArrayList<>();
            String code = Integer.toBinaryString(totalCombinations | i).substring(1);

            for (int j = 0; j < totalElements; j++) {
                if (code.charAt(j) == '1') {
                    combination.add(listElements.get(j));
                }
            }

            listCombinations.add(combination);
        }

        return listCombinations;
    }


    public static String convertStringToDecimalNumber(String format, String number) {
        return String.format(format, new BigDecimal(number));
    }

    public static String convertStringTo2DigitNumber(String number) {
        return convertStringToDecimalNumber("%.2f", number);
    }

    public static String getRandomElementFromList(List<String> list) {
        SecureRandom rand = new SecureRandom();
        return list.get(rand.nextInt(list.size())).trim();
    }

    public static String randomEmail(String userEmail, String emailDomain, long randomNum) {
        return userEmail + randomNum + emailDomain;
    }

    public static String xpathExpression(String value) {
        if (!value.contains("'"))
            return '\'' + value + '\'';
        else if (!value.contains("\""))
            return '"' + value + '"';
        else
            return "concat('" + value.replace("'", "',\"'\",'") + "')";
    }

    public static String generateRandomPhoneNumberString() {
        SecureRandom rand = new SecureRandom();
        int num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
        int num2 = rand.nextInt(743);
        int num3 = rand.nextInt(10000);

        DecimalFormat df3 = new DecimalFormat("000"); // 3 zeros
        DecimalFormat df4 = new DecimalFormat("0000"); // 4 zeros

        String phoneNumber = df3.format(num1) + df3.format(num2) + df4.format(num3);
        logger.info(String.format("Generate phone number %s", phoneNumber));
        return phoneNumber;
    }

    private static String randomString(int count, boolean letters, boolean numbers) {
        String numberChars = "0123456789";
        String alphabetChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String chrs = "";
        chrs = numbers ? chrs + numberChars : chrs;
        chrs = letters ? chrs + alphabetChars : chrs;
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.ints(count, 0, chrs.length()).mapToObj(chrs::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }

    public static String randomString(int count) {
        return randomString(count, true, true);
    }

    public static String randomNumberic(int count) {
        return randomString(count, false, true);
    }

    public static String randomAlphabetic(int count) {
        return randomString(count, true, false);
    }
}
