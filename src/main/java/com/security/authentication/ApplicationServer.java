package com.security.authentication;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ApplicationServer {
    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(5099);
        registry.rebind("AccessControlList", new AccessControlListServant("AccessControlList.csv"));
        registry.rebind("RoleBased", new RoleBasedServant("RoleBased.csv"));
        registry.rebind("AccessControlListAfterChange", new AccessControlListServant("AccessControlListAfterChange.csv"));
        registry.rebind("RoleBasedAfterChange", new RoleBasedServant("RoleBasedAfterChange.csv"));
    }
}
