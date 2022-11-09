package com.security.authentication;

import java.rmi.RemoteException;

public class AccessControlListServant extends AbstractServant {
    protected AccessControlListServant(String configFile) throws RemoteException {
        super(configFile);
    }

    @Override
    public boolean hasAccess(String user, String method) {
        return true;
    }
}
