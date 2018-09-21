package com.example.zava.mymobileapp.Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zava on 03.09.2018.
 */

public class Validations {

  public static final Pattern LAST_NAME_PATTERN = Pattern.compile("[А-Яа-я]+|[a-zA-Z]+");
  public static final Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9]{8,24}");
  public static final Pattern FIRST_NAME_PATTERN = Pattern.compile("[А-Яа-я]+|[a-zA-Z]+");
  public static final Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9+_.-]+@[a-zA-Z]" +
      "+\\.[A-Za-z]{2,4}");
  public static final Pattern PHONE_PATTERN = Pattern.compile("\\+[0-9]{12}");


  public static boolean isValidPassword(final String password) {
    if (password.length() < 8) {
      return false;
    }
    Matcher matcher;
    matcher = PASSWORD_PATTERN.matcher(password);
    return matcher.matches();
  }

  public static boolean isValidFirstName(final String first_name) {
    if (first_name.length() < 1) {
      return false;
    }
    Matcher matcher;
    matcher = FIRST_NAME_PATTERN.matcher(first_name);
    return matcher.matches();
  }


  public static boolean isValidLastName(final String last_name) {
    if (last_name.length() < 3) {
      return false;
    }
    Matcher matcher;
    matcher = LAST_NAME_PATTERN.matcher(last_name);
    return matcher.matches();
  }

  public static boolean isValidEmail(final String email) {
    if (email.length() < 6) {
      return false;
    }
    Matcher matcher;
    matcher = EMAIL_PATTERN.matcher(email);
    return matcher.matches();
  }

  public static boolean isValidPhoneNumber(final String phone_number) {
    Matcher matcher;
    matcher = PHONE_PATTERN.matcher(phone_number);
    return matcher.matches();
  }
}
