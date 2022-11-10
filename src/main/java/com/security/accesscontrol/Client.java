package com.security.accesscontrol;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        Tasks task = Tasks.RoleBased;
        ClientService client = new ClientService(task);
        ClientService client2 = new ClientService(task);

        client.login("Alice", "AlicePassword");
        client.print("File 01", "Printer 01");
        client2.login("Bob", "BobPassword");
        client2.print("File 01", "Printer 01");
    }
}