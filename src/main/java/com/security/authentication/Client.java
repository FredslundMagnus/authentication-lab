package com.security.authentication;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Hello world!
 */
public class Client {
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        ClientService client = new ClientService();

        client.login("FakeUser", "strongPassword");
        client.login("Magnus", "wrongPassword");
        client.login("Magnus", "strongPassword");
        client.print("file_01", "Printer 01");
        client.print("file_03", "Printer 01");
        client.print("file_07", "Printer 01");
        client.queue("Printer 01");
        client.topQueue("Printer 01", 2);
        client.queue("Printer 01");
        client.setConfig("pageType", "A4");
        client.readConfig("pageType");
        client.logout();
        client.queue("Printer 01");
        client.login("Emily", "strongPassword123");
        client.readConfig("pageType");
        client.queue("Printer 01");
        client.status("Printer 01");
        client.topQueue("Printer 01", 3);
        client.queue("Printer 01");
        client.restart();
        client.status("Printer 01");
        client.queue("Printer 01");
        ClientService client2 = new ClientService();
        client2.login("Chunxue", "strongPassword456");
        client.print("file_02", "Printer 01");
        client2.print("file_05", "Printer 01");
        client.queue("Printer 01");
    }
}
