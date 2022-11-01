package com.security.authentication;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Hello world!
 */
public class Client {
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        PrintService service = (PrintService) Naming.lookup("rmi://localhost:5099/print");
        System.out.println(service.login("FakeUser", "strongPassword"));
        System.out.println(service.login("Magnus", "wrongPassword"));
        String token = service.login("Magnus", "strongPassword");
        System.out.println(token);
        service.print("file_01", "Printer 01", token, 0);
        service.queue("Printer 01", token, 1);
        service.setConfig("user", "Magnus", token, 2);
        System.out.println(service.readConfig("user", token, 3));
        System.out.println(service.readConfig("user", token, 4));
        System.out.println(service.readConfig("user", token, 5));
        service.logout(token);
        service.queue("Printer 01", token, 1);
    }
}
