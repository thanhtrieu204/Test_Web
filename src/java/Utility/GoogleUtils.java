package Utility;

import Models.Customer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

public class GoogleUtils {

    public static String GOOGLE_CLIENT_ID = "153135038021-v2i38brv0f598ark9j6hd3khq0it79dr.apps.googleusercontent.com";
    public static String GOOGLE_CLIENT_SECRET = "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";
    public static String GOOGLE_REDIRECT_URI = "http://localhost:9999/Project/user/login-google";
    public static String GOOGLE_LINK_GET_TOKEN = "https://oauth2.googleapis.com/token";
    public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";

    // Đổi mã 'code' lấy 'access_token' từ Google
    public static String getToken(final String code) throws IOException {
        String response = Request.Post(GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form()
                        .add("client_id", GOOGLE_CLIENT_ID)
                        .add("client_secret", GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", GOOGLE_REDIRECT_URI)
                        .add("code", code)
                        .add("grant_type", "authorization_code").build())
                .execute().returnContent().asString();
        
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        return jobj.get("access_token").toString().replaceAll("\"", "");
    }

    // Dùng 'access_token' lấy thông tin tài khoản Gmail thật
    public static Customer getUserInfo(final String accessToken) throws IOException {
        String link = GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        
        Customer c = new Customer();
        // Google trả về id dạng chuỗi số dài, ta dùng email làm username luôn cho tiện quản lý
        String email = jobj.get("email").toString().replaceAll("\"", "");
        String name = jobj.get("name").toString().replaceAll("\"", "");
        
        c.setEmail(email);
        c.setFullName(name);
        c.setUsername(email); // Lấy Email làm Username đăng nhập hệ thống luôn
        c.setActive(1);
        return c;
    }
}