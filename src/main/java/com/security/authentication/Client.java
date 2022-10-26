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
        System.out.println("Before");
        service.start();
        service.setConfig("user", "Magnus");
        System.out.println("After");
        System.out.println(service.readConfig("user"));
    }
}
