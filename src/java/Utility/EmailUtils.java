package Utility;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class EmailUtils {

    private static final String FROM_EMAIL = "dangtrieulmht@gmail.com";
    private static final String APP_PASSWORD = "rlvlpxzsppiptxpg"; // Mật khẩu ứng dụng 16 ký tự viết liền không dấu cách

    // Hàm sinh mã ngẫu nhiên 6 chữ số
    public static String generateOTP() {
        Random random = new Random();
        int number = 100000 + random.nextInt(900000);
        return String.valueOf(number);
    }

    // Hàm xử lý kết nối server Google và gửi mail chứa OTP đến biến động 'toEmail'
    public static void sendOTP(String toEmail, String otp) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            // Sử dụng MimeMessage chuẩn của jakarta.mail
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(FROM_EMAIL, "HỆ THỐNG BOOKSTORE", "UTF-8"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

            message.setSubject("Mã Xác Thực OTP Cấp Lại Mật Khẩu", "UTF-8");

            String rawHtml = "<div style=\"max-width: 500px; margin: 20px auto; font-family: 'Segoe UI', Arial, sans-serif; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 15px rgba(0,0,0,0.1); border: 1px solid #e1e8ed;\">"
                    + "  <div style=\"background-color: #007bff; padding: 25px; text-align: center;\">"
                    + "    <h2 style=\"color: #ffffff; margin: 0; font-size: 22px; font-weight: 600; letter-spacing: 0.5px;\">Khôi Phục Mật Khẩu</h2>"
                    + "  </div>"
                    + "  <div style=\"padding: 30px; background-color: #ffffff; color: #333333; line-height: 1.6;\">"
                    + "    <p style=\"font-size: 15px; margin-top: 0;\">Xin chào,</p>"
                    + "    <p style=\"font-size: 15px;\">Chúng tôi nhận được yêu cầu cấp lại mật khẩu cho tài khoản của bạn. Vui lòng sử dụng mã mã OTP dưới đây để xác thực:</p>"
                    + "    <div style=\"text-align: center; margin: 30px 0;\">"
                    + "      <div style=\"display: inline-block; background-color: #f8f9fa; border: 2px dashed #007bff; padding: 12px 30px; font-size: 32px; font-weight: bold; color: #dc3545; letter-spacing: 6px; border-radius: 8px; text-shadow: 1px 1px 0px #fff;\">"
                    + otp
                    + "      </div>"
                    + "    </div>"
                    + "    <p style=\"font-size: 13px; color: #6c757d; margin-bottom: 0; background-color: #fff3cd; color: #856404; padding: 10px; border-radius: 6px; border-left: 4px solid #ffeeba;\">"
                    + "      ⚠️ <strong>Lưu ý:</strong> Mã này có hiệu lực trong vòng 5 phút. Tuyệt đối không chia sẻ mã này với bất kỳ ai để đảm bảo an toàn tài khoản."
                    + "    </p>"
                    + "  </div>"
                    + "  <div style=\"background-color: #f8f9fa; padding: 15px; text-align: center; font-size: 12px; color: #6c757d; border-top: 1px solid #e1e8ed;\">"
                    + "    © 2026 Bookstore System. All rights reserved."
                    + "  </div>"
                    + "</div>";

// ? ÉP ĐỌC CHUỖI THEO ĐÚNG MẢNG BYTES UTF-8 ĐỂ CHỐNG LỖI COMPILER
            String htmlContent = new String(rawHtml.getBytes("UTF-8"), "UTF-8");
            message.setContent(htmlContent, "text/html; charset=UTF-8");

            Transport.send(message);
            System.out.println("👉 Đã gửi OTP thành công với giao diện mới!");

        } catch (Exception e) {
            e.printStackTrace();
            throw new MessagingException("Lỗi gửi mail: " + e.getMessage());
        }
    }
}
