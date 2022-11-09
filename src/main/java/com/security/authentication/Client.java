package com.security.authentication;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        Tasks task = Tasks.AccessControlList;
        ClientService client = new ClientService(task);
        ClientService client2 = new ClientService(task);

        client.login("Alice", "AlicePassword");
        client.print("ssdf", "printer 1");
        client2.login("Bob", "BobPassword");
        client2.print("ssdf", "printer 1");

    }
}
