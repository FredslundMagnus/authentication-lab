package com.security.authentication;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RoleBasedServant extends AbstractServant {
    protected RoleBasedServant(String configFile) throws RemoteException {
        super(configFile);
    }

    @Override
    public boolean hasAccess(String user, String method) {
        try {
            File myObj = new File(configFile);
            Scanner myReader = new Scanner(myObj);
            ArrayList<String> acceptedRoles = new ArrayList<String>();

            // Get method names
            String line = myReader.nextLine();
            List<String> methods = Arrays.asList(line.split(": ")[1].split(","));
            int methodIndex = methods.indexOf(method);
            if (methodIndex == -1) {
                myReader.close();
                throw new Exception("Unknown method");
            }

            // Get role acess
            while (myReader.hasNextLine()) {
                line = myReader.nextLine();
                if (line.isEmpty()) {
                    break;
                }
                String role = line.split(": ")[0];
                List<Boolean> permissions = Arrays.asList(line.split(": ")[1].split(",")).stream()
                        .map(value -> value.equals("1")).collect(Collectors.toList());
                if (permissions.get(methodIndex)) {
                    acceptedRoles.add(role);
                }
            }

            // Get roles of user:
            while (myReader.hasNextLine()) {
                line = myReader.nextLine();
                if (line.startsWith(user + ": ")) {
                    List<String> userRoles = Arrays.asList(line.split(": ")[1].split(","));
                    List<Boolean> roleHaveAcces = userRoles.stream().map(role -> acceptedRoles.contains(role))
                            .collect(Collectors.toList());
                    if (roleHaveAcces.contains(true)) {
                        myReader.close();
                        return true;
                    }
                }
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return false;
    }
}
