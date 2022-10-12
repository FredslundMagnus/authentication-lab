package com.security.authentication;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrintServant extends UnicastRemoteObject implements PrintService {
    protected PrintServant() throws RemoteException {
        super();
    }

    @Override
    public void print(String filename, String printer) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void queue(String printer) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void topQueue(String printer, int job) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void start() throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void stop() throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void restart() throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void status(String printer) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void readConfig(String parameter) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setConfig(String parameter, String value) throws RemoteException {
        // TODO Auto-generated method stub

    }
}
