import java.util.*;
import java.io.*;

public class ATM
{
    private HashMap <String, Double> accounts;

    public ATM ()
    {
        accounts = new HashMap<String, Double> ();
    }

    public void openAccount (String userId, double amount) throws Exception
    {
        if (accounts.containsKey (userId))
        {
            throw new Exception ("Error: account already exists");
        }
        
        accounts.put(userId, amount);
    }

    public void closeAccount (String userId) throws Exception
    { 
        if (!accounts.containsKey (userId))
        {
            throw new Exception ("Error: not a valid account");
        }

        if (accounts.get(userId) != 0)
        {
            throw new Exception ("Error: Withdraw money before closing account");
        }

        accounts.remove(userId);
    }

    public double checkBalance (String userId) throws Exception 
    {
        if (!accounts.containsKey (userId))
        {
            throw new Exception ("Error: not a valid account");
        }
        return accounts.get (userId);
    }

    public double depositMoney (String userId, double amount) throws Exception
    {
        if (!accounts.containsKey (userId))
        {
            throw new Exception ("youre broke");
        }

        accounts.put (userId, accounts.get (userId) + amount);
        return amount; //return double of the deposited amount
    } 

    public double withdrawMoney (String userId, double amount) throws Exception
    {
        if (!accounts.containsKey (userId) || accounts.get (userId) > amount)
        {
            throw new Exception ("youre broke");
        }

        accounts.put (userId, accounts.get (userId) - amount);
        return amount; //return double of the deposited amount
    }

    public boolean transferMoney (String fromAccount, String toAccount, double amount) throws Exception
    {
        if (!accounts.containsKey (fromAccount) || !accounts.containsKey (toAccount))
        {
            // throw new Exception ("Error: invalid accounts");
            return false;
        }

        try {
            withdrawMoney (fromAccount, amount);
            depositMoney (toAccount, amount);
            return true; 
        }
        catch (Exception e)
        {
            // throw e;
            return false; 
        }
    }

    public void audit () throws IOException
    {
        PrintWriter pw = new PrintWriter(new BufferedWriter (new FileWriter ("AccountAudit.txt")));

        for (Map.Entry<String, Double> element: accounts.entrySet()) {
            pw.println ("UserID: " + element.getKey () + " Balance: " + element.getValue()); 
        }

        pw.close(); 
    }   
}