package com.security.authentication;

import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PrintServant extends UnicastRemoteObject implements PrintService {
    private Map<String, String> config;
    private HashMap<String, ArrayList<String>> queues;
    private Map<String, String> logins;
    private SecureRandom random;
    private Map<String, ArrayList<String>> tokens;

    protected PrintServant() throws RemoteException {
        super();
        this.logins = new HashMap<String, String>();
        this.config = new HashMap<String, String>();
        this.queues = new HashMap<String, ArrayList<String>>();
        this.tokens = new HashMap<String, ArrayList<String>>();
        this.random = new SecureRandom();
        logins.put("Magnus", hash("strongPassword"));
        logins.put("Emily", hash("strongPassword123"));
        logins.put("Chunxue", hash("strongPassword456"));
        logins.put("Arianna", hash("strongPassword789"));
    }

    public static String hash(String value) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest((value).getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No algorithm");
            return null;
        }
    }

    private String createToken() {
        String token = Integer.toString(random.nextInt());
        if (token == null) {
            return null;
        }
        tokens.put(token, new ArrayList<String>());
        return token;
    }

    private void validate(String token, int unique) throws RemoteException {
        if (tokens.get(token) == null) {
            throw new RemoteException("Authentication Failed - Token not valid");
        }
        if (tokens.get(token).contains(Integer.toString(unique))) {
            throw new RemoteException("Authentication Failed - Is replay attack");
        }
        if (tokens.get(token).size() >= 5) {
            tokens.remove(token);
            throw new RemoteException("Authentication Failed - Token expired");
        }
        tokens.get(token).add(Integer.toString(unique));
    }

    @Override
    public void print(String filename, String printer, String token, int unique) throws RemoteException {
        validate(token, unique);
        if (queues.get(printer) == null) {
            queues.put(printer, new ArrayList<String>());
        }
        queues.get(printer).add(filename);
    }

    @Override
    public ArrayList<String> queue(String printer, String token, int unique) throws RemoteException {
        validate(token, unique);
        return queues.get(printer);
    }

    @Override
    public void topQueue(String printer, int job, String token, int unique) throws RemoteException {
        validate(token, unique);
        String old = queues.get(printer).remove(job);
        queues.get(printer).add(0, old);
    }

    @Override
    public String start(String token, int unique) throws RemoteException {
        validate(token, unique);
        return "The printing service has started.";
    }

    @Override
    public String stop(String token, int unique) throws RemoteException {
        validate(token, unique);
        return "The printing service has stopped.";
    }

    @Override
    public String restart(String token, int unique) throws RemoteException {
        validate(token, unique);
        this.queues = new HashMap<String, ArrayList<String>>();
        return "The printing service has been restarted.";
    }

    @Override
    public String status(String printer, String token, int unique) throws RemoteException {
        validate(token, unique);
        return queues.get(printer).size() == 0 ? "Ready" : "Printing";
    }

    @Override
    public String readConfig(String parameter, String token, int unique) throws RemoteException {
        validate(token, unique);
        return config.get(parameter);
    }

    @Override
    public void setConfig(String parameter, String value, String token, int unique) throws RemoteException {
        validate(token, unique);
        config.put(parameter, value);
    }

    @Override
    public String login(String username, String password) throws RemoteException {
        if (logins.get(username) == null) {
            return null;
        }
        if (logins.get(username).equals(hash(password))) {
            return createToken();
        }
        return null;
    }
}
