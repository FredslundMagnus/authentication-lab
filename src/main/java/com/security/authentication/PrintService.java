package com.security.authentication;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface PrintService extends Remote {
    public void print(String filename, String printer) throws RemoteException;   // prints file filename on the specified printer
    public ArrayList<String> queue(String printer) throws RemoteException;   // lists the print queue for a given printer on the user's display in lines of the form <job number>   <file name>
    public void topQueue(String printer, int job) throws RemoteException;   // moves job to the top of the queue
    public String start() throws RemoteException;   // starts the print server
    public String stop() throws RemoteException;   // stops the print server
    public void restart() throws RemoteException;   // stops the print server, clears the print queue and starts the print server again
    public String status(String printer) throws RemoteException;  // prints status of printer on the user's display
    public String readConfig(String parameter) throws RemoteException;   // prints the value of the parameter on the user's display
    public void setConfig(String parameter, String value) throws RemoteException;   // sets the parameter to value
}
