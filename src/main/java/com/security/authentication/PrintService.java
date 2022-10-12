package com.security.authentication;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrintService extends Remote {
    public String echo(String input) throws RemoteException;
}
