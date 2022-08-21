package edu.gatech.cs6310;
import java.util.Scanner;
import java.time.LocalDateTime;

class User
{
    private String last_name;
    private String first_name;
    private String phone_number;

    User(String _last_name, String _first_name, String _phone_number)
    {
        Encryption encryption = new Encryption(EncryptPwd.ENCRYPT_PWD);
        this.last_name = encryption.Encrypt(_last_name);
        this.first_name = encryption.Encrypt(_first_name);
        this.phone_number = encryption.Encrypt(_phone_number);
    }

    public String get_last_name() {
        Encryption encryption = new Encryption(EncryptPwd.DECRYPT_PWD);
        return encryption.Decrypt(this.last_name);
    }

    public String get_first_name() {
        Encryption encryption = new Encryption(EncryptPwd.DECRYPT_PWD);
        return encryption.Decrypt(this.first_name);
    }

    public String get_phone_number() {
        Encryption encryption = new Encryption(EncryptPwd.DECRYPT_PWD);
        return encryption.Decrypt(this.phone_number);
    }
}