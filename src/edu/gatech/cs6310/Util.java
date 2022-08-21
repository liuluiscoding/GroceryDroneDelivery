package edu.gatech.cs6310;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Util {
    public static Map<String, String> read_user_file(String filePath) {
        Map<String, String> textMap = new HashMap<String, String>();
        BufferedReader br = null;

        try {
            File file = new File(filePath);
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();    // skip header line

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String userid = parts[0].trim();
                String info = parts[1].trim();

                if (!userid.equals("") && !info.equals(""))
                    textMap.put(userid, info);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return textMap;
    }

    public static boolean validate_user(String userId,
                                 String password,
                                 Map<String, String> infoMap) {
        if (infoMap.containsKey(userId) && infoMap.get(userId).equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public static String authenticate() {
        String current_dir = System.getProperty("user.dir");
        Map<String, String> infoMap = read_user_file(current_dir + "/user_info.txt");
        Map<String, String> roleMap = read_user_file(current_dir + "/user_role.txt");

        // Read user input and validate identity
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        System.out.print("user id: ");
        String userId = scanner.nextLine();
        System.out.print("password: ");
        String password = scanner.nextLine();
        Encryption encryption = new Encryption(EncryptPwd.ENCRYPT_PWD);
        String passwordEncrypted = encryption.Encrypt(password);
        if (validate_user(userId, passwordEncrypted, infoMap)) {
            String userRole = roleMap.get(userId);

            if (userRole != null) {
                System.out.println("Login successful! Signed in as role: " + userRole);
                return userRole;
            } else {
                System.out.println("Your user role has not been assigned yet. Bye-bye.");
                return null;
            }
        }
        return null;
    }
}
