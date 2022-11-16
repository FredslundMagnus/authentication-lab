package com.security.accesscontrol;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Client {
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {

        //
        // Small Example Case
        //

        // Tasks task = Tasks.RoleBased;
        // ClientService clientA = new ClientService(task);
        // ClientService clientB = new ClientService(task);

        // clientA.login("Alice", "AlicePassword");
        // clientA.print("File 01", "Printer 01");
        // clientB.login("Bob", "BobPassword");
        // clientB.print("File 01", "Printer 01");

        //
        // Prepare all combinations
        //

        ArrayList<String> users = new ArrayList<String>();
        users.add("Alice");
        users.add("Bob");
        users.add("Cecilia");
        users.add("David");
        users.add("Erica");
        users.add("Fred");
        users.add("George");
        users.add("Henry");
        users.add("Ida");

        ArrayList<Consumer<ClientService>> methods = new ArrayList<Consumer<ClientService>>();
        methods.add((accessControl) -> accessControl.print("File 01", "Printer 01"));
        methods.add((accessControl) -> accessControl.queue("Printer 01"));
        methods.add((accessControl) -> accessControl.topQueue("Printer 01", 1));
        methods.add((accessControl) -> accessControl.start());
        methods.add((accessControl) -> accessControl.stop());
        methods.add((accessControl) -> accessControl.restart());
        methods.add((accessControl) -> accessControl.status("Printer 01"));
        methods.add((accessControl) -> accessControl.readConfig("Size"));
        methods.add((accessControl) -> accessControl.setConfig("Size", "A4"));

        ArrayList<ClientService> before = new ArrayList<ClientService>();
        before.add(new ClientService(Tasks.RoleBased));
        before.add(new ClientService(Tasks.AccessControlList));

        ArrayList<ClientService> after = new ArrayList<ClientService>();
        after.add(new ClientService(Tasks.RoleBasedAfterChange));
        after.add(new ClientService(Tasks.AccessControlListAfterChange));

        //
        // Test all combinations (before)
        //

        for (String user : users) {
            System.out.println(user + " Before");
            for (ClientService accessControl : before) {
                accessControl.login(user, user + "Password");
            }
            for (Consumer<ClientService> method : methods) {
                for (ClientService accessControl : before) {
                    method.accept(accessControl);
                }
            }
            for (ClientService accessControl : before) {
                accessControl.logout();
            }
            System.out.println("");
        }

        //
        // Test all combinations (after)
        //

        for (String user : users) {
            System.out.println(user + " After");
            for (ClientService accessControl : after) {
                accessControl.login(user, user + "Password");
            }
            for (Consumer<ClientService> method : methods) {
                for (ClientService accessControl : after) {
                    method.accept(accessControl);
                }
            }
            for (ClientService accessControl : after) {
                accessControl.logout();
            }
            System.out.println("");
        }

    }
}
