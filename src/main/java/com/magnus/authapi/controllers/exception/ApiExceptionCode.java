package com.magnus.authapi.controllers.exception;

public class ApiExceptionCode {

  // ** REGISTRATION ** //
  public final static String EMAIL_NULL = "email_null";
  public final static String INVALID_EMAIL = "invalid_email";
  public final static String FIRSTNAME_NULL = "firstname_null";
  public final static String FIRSTNAME_TOO_SHORT = "firstname_too_short";
  public final static String FIRSTNAME_TOO_LONG = "firstname_too_long";
  public final static String LASTNAME_NULL = "lastname_null";
  public final static String LASTNAME_TOO_SHORT = "lastname_too_short";
  public final static String LASTNAME_TOO_LONG = "lastname_too_long";
  public final static String USERNAME_NULL = "username_null";
  public final static String USERNAME_TOO_SHORT = "username_too_short";
  public final static String USERNAME_TOO_LONG = "username_too_long";
  public final static String PASSWORD_NULL = "password_null";
  public final static String PASSWORD_TOO_SHORT = "password_too_short";
  public final static String PASSWORD_TOO_LONG = "password_too_long";
  public static final String EMAIL_ALREADY_TAKEN = "email_already_taken";
  public static final String USERNAME_ALREADY_TAKEN = "username_already_taken";
  public static final String INVALID_REFRESH_TOKEN = "invalid_refresh_token";
}
