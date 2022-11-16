package com.security.accesscontrol;

import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.type.NullType;

abstract class AbstractServant extends UnicastRemoteObject implements PrintService {
    protected AbstractServant(String configFile) throws RemoteException {
        super();
        this.configFile = configFile;
        this.logins = new HashMap<String, String>();
        this.config = new HashMap<String, String>();
        this.queues = new HashMap<String, ArrayList<String>>();
        this.tokens = new HashMap<String, ArrayList<String>>();
        this.users = new HashMap<String, String>();
        this.random = new SecureRandom();

        if (!configFile.contains("AfterChange")) {
            logins.put("Alice", hash("AlicePassword"));
            logins.put("Bob", hash("BobPassword"));
            logins.put("Cecilia", hash("CeciliaPassword"));
            logins.put("David", hash("DavidPassword"));
            logins.put("Erica", hash("EricaPassword"));
            logins.put("Fred", hash("FredPassword"));
            logins.put("George", hash("GeorgePassword"));
        } else {
            logins.put("Alice", hash("AlicePassword"));
            logins.put("Cecilia", hash("CeciliaPassword"));
            logins.put("David", hash("DavidPassword"));
            logins.put("Erica", hash("EricaPassword"));
            logins.put("Fred", hash("FredPassword"));
            logins.put("George", hash("GeorgePassword"));
            logins.put("Henry", hash("HenryPassword"));
            logins.put("Ida", hash("IdaPassword"));
        }
    }

    protected String configFile;
    protected Map<String, String> config;
    protected HashMap<String, ArrayList<String>> queues;
    protected Map<String, String> logins;
    protected SecureRandom random;
    protected Map<String, ArrayList<String>> tokens;
    protected Map<String, String> users;

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

    private String createToken(String username) {
        String token = Integer.toString(random.nextInt());
        if (token == null) {
            return null;
        }
        tokens.put(token, new ArrayList<String>());
        users.put(token, username);
        return token;
    }

    protected boolean hasAccess(String user, String method) {
        return true;
    }

    protected String validate(String token, int unique, String method) throws RemoteException {
        if (tokens.get(token) == null) {
            throw new RemoteException("Authentication Failed - Token not valid");
        }
        if (tokens.get(token).contains(Integer.toString(unique))) {
            throw new RemoteException("Authentication Failed - Is replay attack");
        }
        if (tokens.get(token).size() >= 10) {
            tokens.remove(token);
            users.remove(token);
            throw new RemoteException("Authentication Failed - Token expired");
        }
        tokens.get(token).add(Integer.toString(unique));
        String user = users.get(token);
        if (!hasAccess(user, method)) {
            throw new RemoteException("Access Denied - You have not Access to '" + method + "'.");
        }
        return user;
    }

    private void log(String username, String message) {
        System.out.println(username + ": " + message);
    }

    @Override
    public Response<NullType> print(String filename, String printer, String token, int unique) throws RemoteException {
        String username = validate(token, unique, "print");
        if (queues.get(printer) == null) {
            queues.put(printer, new ArrayList<String>());
        }
        queues.get(printer).add(filename);
        log(username, "Printed '" + filename + "' on printer '" + printer + "'.");
        return new Response<NullType>(null, token, unique);
    }

    @Override
    public Response<ArrayList<String>> queue(String printer, String token, int unique) throws RemoteException {
        String username = validate(token, unique, "queue");
        if (queues.containsKey(printer) == false) {
            queues.put(printer, new ArrayList<String>());
        }
        ArrayList<String> q = queues.get(printer);
        log(username, "Accessed queue for printer '" + printer + "' with queue: " + q.toString() + ".");
        return new Response<ArrayList<String>>(q, token, unique);
    }

    @Override
    public Response<NullType> topQueue(String printer, int job, String token, int unique) throws RemoteException {
        String username = validate(token, unique, "topQueue");
        String old = queues.get(printer).remove(job);
        queues.get(printer).add(0, old);
        log(username, "Moved job '" + job + "' to the top of the queue for printer '" + printer + "'.");
        return new Response<NullType>(null, token, unique);
    }

    @Override
    public Response<String> start(String token, int unique) throws RemoteException {
        String username = validate(token, unique, "start");
        log(username, "Started printing service.");
        return new Response<String>("The printing service has started.", token, unique);
    }

    @Override
    public Response<String> stop(String token, int unique) throws RemoteException {
        String username = validate(token, unique, "stop");
        log(username, "Stopped printing service.");
        return new Response<String>("The printing service has stopped.", token, unique);
    }

    @Override
    public Response<String> restart(String token, int unique) throws RemoteException {
        String username = validate(token, unique, "restart");
        this.queues = new HashMap<String, ArrayList<String>>();
        log(username, "Restarted printing service.");
        return new Response<String>("The printing service has been restarted.", token, unique);
    }

    @Override
    public Response<String> status(String printer, String token, int unique) throws RemoteException {
        String username = validate(token, unique, "status");
        String status;
        if (queues.get(printer) == null) {
            status = "Ready";
        } else {
            status = queues.get(printer).size() == 0 ? "Ready" : "Printing";
        }
        log(username, "Checked status: " + status + ".");
        return new Response<String>(status, token, unique);
    }

    @Override
    public Response<String> readConfig(String parameter, String token, int unique) throws RemoteException {
        String username = validate(token, unique, "readConfig");
        String value = config.get(parameter);
        log(username, "Read config of '" + parameter + "' with value '" + value + "'.");
        return new Response<String>(value, token, unique);
    }

    @Override
    public Response<NullType> setConfig(String parameter, String value, String token, int unique)
            throws RemoteException {
        String username = validate(token, unique, "setConfig");
        config.put(parameter, value);
        log(username, "Set config of '" + parameter + "' with value '" + value + "'.");
        return new Response<NullType>(null, token, unique);
    }

    @Override
    public String login(String username, String password) throws RemoteException {
        if (logins.get(username) == null) {
            System.out.println("Failed login for " + username + " (Not existing user).");
            return null;
        }
        if (!logins.get(username).equals(hash(password))) {
            System.out.println("Failed login for " + username + " (Wrong password).");
            return null;
        }
        log(username, "Logged in.");
        return createToken(username);
    }

    @Override
    public void logout(String token) throws RemoteException {
        tokens.remove(token);
        String username = users.get(token);

        users.remove(token);
        if (username != null) {
            log(username, "Logged out.");
        } else {
            throw new RemoteException("No user was logged in.");
        }
    }
}
