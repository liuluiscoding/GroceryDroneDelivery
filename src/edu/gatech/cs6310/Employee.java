package edu.gatech.cs6310;
import java.util.Scanner;
import java.time.LocalDateTime;

class Employee extends User
{
    private String ssn;

    Employee(String _last_name, String _first_name, String _phone_number, String _ssn)
    {
        super(_last_name, _first_name, _phone_number);
        Encryption encryption = new Encryption(EncryptPwd.ENCRYPT_PWD);
        this.ssn = encryption.Encrypt(_ssn);
    }

    public String get_ssn()
    {
        Encryption encryption = new Encryption(EncryptPwd.DECRYPT_PWD);
        return encryption.Decrypt(this.ssn);
    }
}