package utils;

import org.openqa.selenium.Cookie;

import java.io.*;
import java.util.Date;

public class CookieManager {

    private static final String COOKIE_FILE = "session.cookie";

    // Cookie’yi dosyaya kaydet
    public static void saveCookie(Cookie cookie) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(COOKIE_FILE))) {
            oos.writeObject(cookie);
            System.out.println("[INFO] Session cookie saved to file: " + cookie.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Cookie dosyadan yükle (ve expire check yap)
    public static Cookie loadCookie() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(COOKIE_FILE))) {
            Cookie cookie = (Cookie) ois.readObject();

            // expire süresi varsa kontrol et
            Date expiry = cookie.getExpiry();
            if (expiry != null && expiry.before(new Date())) {
                System.out.println("[INFO] Saved cookie is expired, ignoring.");
                clearCookieFile();
                return null;
            }
            return cookie;

        } catch (Exception e) {
            System.out.println("[INFO] No saved cookie found.");
            return null;
        }
    }

    // Cookie dosyasını sil
    public static void clearCookieFile() {
        File f = new File(COOKIE_FILE);
        if (f.exists() && f.delete()) {
            System.out.println("[INFO] Expired cookie file deleted.");
        }
    }
}
