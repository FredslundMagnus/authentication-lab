package com.security.authentication;

import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AccessControlListServant extends AbstractServant {
    protected AccessControlListServant(String configFile) throws RemoteException {
        super(configFile);
    }

    @Override
    public boolean hasAccess(String user, String method) {
        try {
            File myObj = new File(configFile);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (line.startsWith(method + ",", 0)) {
                    List<String> usersWithAccess = Arrays.asList(line.split(",")).subList(1,
                            Arrays.asList(line.split(",")).size());
                    if (usersWithAccess.contains(user)) {
                        myReader.close();
                        return true;
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return false;
    }
}
