package com.magnus.authenticationapi.email;

public interface EmailSender {
  void send(String to, String email, String subject);

  String buildEmail(String name, String link);
}
