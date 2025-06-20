package my_computer.backendsymphony.service;

public interface EmailService {
    void sendEmail(String to, String title, String content);
}
