package com.security.accesscontrol;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.lang.model.type.NullType;

public interface PrintService extends Remote {
    public String login(String username, String password) throws RemoteException;

    public void logout(String token) throws RemoteException;

    public Response<NullType> print(String filename, String printer, String token, int unique) throws RemoteException; // prints
                                                                                                                       // file
                                                                                                                       // filename
                                                                                                                       // on
                                                                                                                       // the
                                                                                                                       // specified
                                                                                                                       // printer

    public Response<ArrayList<String>> queue(String printer, String token, int unique) throws RemoteException; // lists
                                                                                                               // the
                                                                                                               // print
                                                                                                               // queue
                                                                                                               // for a
                                                                                                               // given
                                                                                                               // printer
                                                                                                               // on the
                                                                                                               // user's
                                                                                                               // display
                                                                                                               // in
                                                                                                               // lines
                                                                                                               // of the
                                                                                                               // form
                                                                                                               // <job
                                                                                                               // number>
                                                                                                               // <file
                                                                                                               // name>

    public Response<NullType> topQueue(String printer, int job, String token, int unique) throws RemoteException; // moves
                                                                                                                  // job
                                                                                                                  // to
                                                                                                                  // the
                                                                                                                  // top
                                                                                                                  // of
                                                                                                                  // the
                                                                                                                  // queue

    public Response<String> start(String token, int unique) throws RemoteException; // starts the print server

    public Response<String> stop(String token, int unique) throws RemoteException; // stops the print server

    public Response<String> restart(String token, int unique) throws RemoteException; // stops the print server, clears
                                                                                      // the print queue and starts the
                                                                                      // print server again

    public Response<String> status(String printer, String token, int unique) throws RemoteException; // prints status of
                                                                                                     // printer on the
                                                                                                     // user's display

    public Response<String> readConfig(String parameter, String token, int unique) throws RemoteException; // prints the
                                                                                                           // value of
                                                                                                           // the
                                                                                                           // parameter
                                                                                                           // on the
                                                                                                           // user's
                                                                                                           // display

    public Response<NullType> setConfig(String parameter, String value, String token, int unique)
            throws RemoteException; // sets the parameter to value
}
