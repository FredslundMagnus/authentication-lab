package com.security.authentication;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrintServant extends UnicastRemoteObject implements PrintService {

    protected PrintServant() throws RemoteException {
        super();
    }

    @Override
    public String echo(String input) throws RemoteException {
        return "From server: " + input;
    }

}
