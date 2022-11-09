package com.security.authentication;

import java.rmi.RemoteException;

public class RoleBasedServant extends AbstractServant {
    protected RoleBasedServant(String configFile) throws RemoteException {
        super(configFile);
    }

    @Override
    public boolean hasAccess(String user, String method) {
        return true;
    }
}
