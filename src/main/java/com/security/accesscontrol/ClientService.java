package com.security.accesscontrol;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.lang.model.type.NullType;

public class ClientService {
    private PrintService service;
    private int unique;
    private String token;

    public ClientService(Tasks accessProtocol) throws MalformedURLException, RemoteException, NotBoundException {
        this.service = (PrintService) Naming.lookup("rmi://localhost:5099/" + accessProtocol.name());
        this.unique = 0;
    }

    public void login(String username, String password) {
        try {
            this.token = service.login(username, password);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
        if (token == null) {
            System.out.println("Login failed.");
        } else {
            System.out.println("You have logged in.");
        }
    };

    public void logout() {
        try {
            service.logout(token);
            System.out.println("You have logged out.");
            this.token = null;
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    };

    public void print(String filename, String printer) {
        this.unique = unique + 1;
        try {
            Response<NullType> response = service.print(filename, printer, token, unique);
            if (!((response.token().equals(token)) && (response.unique() == unique))) {
                throw new RemoteException("Server response could not be authenticated.");
            }
            System.out.println("Print succeded succesfully.");
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

    };

    public void queue(String printer) {
        this.unique = unique + 1;
        try {
            Response<ArrayList<String>> response = service.queue(printer, token, unique);
            if (!((response.token().equals(token)) && (response.unique() == unique))) {
                throw new RemoteException("Server response could not be authenticated.");
            }
            for (int i = 0; i < response.value().size(); i++) {
                System.out.println("<" + (i + 1) + ">   <" + response.value().get(i) + ">");
            }
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    };

    public void topQueue(String printer, int job) {
        this.unique = unique + 1;
        try {
            Response<NullType> response = service.topQueue(printer, job - 1, token, unique);
            if (!((response.token().equals(token)) && (response.unique() == unique))) {
                throw new RemoteException("Server response could not be authenticated.");
            }
            System.out.println("topQueue succeded succesfully.");
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    };

    public void start() {
        this.unique = unique + 1;
        try {
            Response<String> response = service.start(token, unique);
            if (!((response.token().equals(token)) && (response.unique() == unique))) {
                throw new RemoteException("Server response could not be authenticated.");
            }
            System.out.println(response.value());
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    };

    public void stop() {
        this.unique = unique + 1;
        try {
            Response<String> response = service.stop(token, unique);
            if (!((response.token().equals(token)) && (response.unique() == unique))) {
                throw new RemoteException("Server response could not be authenticated.");
            }
            System.out.println(response.value());
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    };

    public void restart() {
        this.unique = unique + 1;
        try {
            Response<String> response = service.restart(token, unique);
            if (!((response.token().equals(token)) && (response.unique() == unique))) {
                throw new RemoteException("Server response could not be authenticated.");
            }
            System.out.println(response.value());
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    };

    public void status(String printer) {
        this.unique = unique + 1;
        try {
            Response<String> response = service.status(printer, token, unique);
            if (!((response.token().equals(token)) && (response.unique() == unique))) {
                throw new RemoteException("Server response could not be authenticated.");
            }
            System.out.println(response.value());
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    };

    public void readConfig(String parameter) {
        this.unique = unique + 1;
        try {
            Response<String> response = service.readConfig(parameter, token, unique);
            if (!((response.token().equals(token)) && (response.unique() == unique))) {
                throw new RemoteException("Server response could not be authenticated.");
            }
            System.out.println(response.value());
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    };

    public void setConfig(String parameter, String value) {
        this.unique = unique + 1;
        try {
            Response<NullType> response = service.setConfig(parameter, value, token, unique);
            if (!((response.token().equals(token)) && (response.unique() == unique))) {
                throw new RemoteException("Server response could not be authenticated.");
            }
            System.out.println("setConfig succeded succesfully.");
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    };
}
