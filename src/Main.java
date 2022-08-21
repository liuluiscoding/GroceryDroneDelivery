import edu.gatech.cs6310.*;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Grocery Express Delivery Service!");

        String userRole = Util.authenticate();
        int num_trials = 2;
        while (num_trials > 0) {
            if (userRole != null) {
                DeliveryService simulator = new DeliveryService();
                simulator.commandLoop(userRole);
                break;
            } else {
                System.out.println("Incorrect user id and password combination. You have " +
                        num_trials + " chances left.");
                userRole = Util.authenticate();
            }
        }
    }
}
