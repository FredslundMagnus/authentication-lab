package com.security.authentication;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PrintServant extends UnicastRemoteObject implements PrintService {
    private Map<String, String> config;
    private HashMap<String, ArrayList<String>> queues;

    protected PrintServant() throws RemoteException {
        super();
        this.config = new HashMap<String, String>();
        this.queues = new HashMap<String, ArrayList<String>>();
    }

    @Override
    public void print(String filename, String printer) throws RemoteException {
        queues.get(printer).add(filename);
    }

    @Override
    public ArrayList<String> queue(String printer) throws RemoteException {
        return queues.get(printer);
    }

    @Override
    public void topQueue(String printer, int job) throws RemoteException {
        String old = queues.get(printer).remove(job);
        queues.get(printer).add(0, old);
    }

    @Override
    public String start() throws RemoteException {
        return "The printing service has started.";
    }

    @Override
    public String stop() throws RemoteException {
        return "The printing service has stopped.";
    }

    @Override
    public void restart() throws RemoteException {
        stop();
        this.queues = new HashMap<String, ArrayList<String>>();
        start();
    }

    @Override
    public String status(String printer) throws RemoteException {
        return queues.get(printer).size() == 0 ? "Ready" : "Printing";
    }

    @Override
    public String readConfig(String parameter) throws RemoteException {
        return config.get(parameter);
    }

    @Override
    public void setConfig(String parameter, String value) throws RemoteException {
        config.put(parameter, value);
    }
}
