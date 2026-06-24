package Utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class InputValidator {

    // Regex tên tiếng Việt: Chữ cái và khoảng trắng, không số, không ký tự đặc biệt
    private static final String NOMINAL_NAME_REGEX = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂÂÊÔƠƯưăâêôơư\\s]+$";

    /**
     * 1. Kiểm tra chuỗi có trống hoặc null hay không.
     */
    public static boolean isValidString(String input) {
        return input != null && !input.trim().isEmpty();
    }

    /**
     * 2. Kiểm tra chuỗi có phải là số nguyên (Integer) hợp lệ.
     */
    public static boolean isValidInt(String input) {
        if (!isValidString(input)) return false;
        try {
            Integer.parseInt(input.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 3. Kiểm tra chuỗi có phải là số thực Float hợp lệ.
     */
    public static boolean isValidFloat(String input) {
        if (!isValidString(input)) return false;
        try {
            Float.parseFloat(input.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 4. Kiểm tra chuỗi có phải là số thực Double hợp lệ.
     */
    public static boolean isValidDouble(String input) {
        if (!isValidString(input)) return false;
        try {
            Double.parseDouble(input.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 5. Kiểm tra lựa chọn Có/Không (Chấp nhận: yes, no, y, n, true, false - không phân biệt hoa thường).
     */
    public static boolean isValidYesNo(String input) {
        if (!isValidString(input)) return false;
        String trimmed = input.trim().toLowerCase();
        return trimmed.equals("yes") || trimmed.equals("no") 
            || trimmed.equals("y") || trimmed.equals("n")
            || trimmed.equals("true") || trimmed.equals("false");
    }

    /**
     * 6. Kiểm tra họ tên hợp lệ (Hỗ trợ tiếng Việt, không chứa số/ký tự đặc biệt).
     */
    public static boolean isValidNominalName(String input) {
        if (!isValidString(input)) return false;
        return input.trim().matches(NOMINAL_NAME_REGEX);
    }
    /**
     * 7. Chuẩn hóa tên
     */
    public static String formatNominalName(String input) {
        if (!isValidString(input)) {
            return "";
        }
        
        // Bước 1: Loại bỏ khoảng trắng thừa ở đầu, cuối và thay thế các khoảng trắng thừa ở giữa bằng 1 khoảng trắng duy nhất
        String cleaned = input.trim().replaceAll("\\s+", " ");
        
        // Tách chuỗi thành mảng các từ
        String[] words = cleaned.split(" ");
        StringBuilder formattedName = new StringBuilder();
        
        // Bước 2: Duyệt từng từ để chuẩn hóa chữ hoa/chữ thường
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (!word.isEmpty()) {
                // Chuyển ký tự đầu thành chữ hoa, các ký tự còn lại thành chữ thường
                String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
                
                formattedName.append(capitalizedWord);
                
                // Nếu chưa phải từ cuối cùng thì thêm khoảng trắng để ngăn cách
                if (i < words.length - 1) {
                    formattedName.append(" ");
                }
            }
        }
        
        return formattedName.toString();
    }
    // Hàm mã hóa mật khẩu một chiều MD5
    public static String hashPassword(String password) {
        if (password == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                // Chuyển đổi mảng byte sang dạng chuỗi Hexadecimal (32 ký tự)
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString(); 
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Lỗi thuật toán mã hóa: " + e.getMessage());
        }
        //dasdas
    }
}