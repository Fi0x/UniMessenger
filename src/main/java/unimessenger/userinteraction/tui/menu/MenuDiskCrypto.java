package unimessenger.userinteraction.tui.menu;

import unimessenger.abstraction.storage.StorageCrypto;
import unimessenger.userinteraction.tui.Inputs;
import unimessenger.userinteraction.tui.Out;

import java.security.UnrecoverableKeyException;

public class MenuDiskCrypto
{
    public static void showMenu()
    {
        System.out.println("Do you want to encrypt your stored information on disc?");
        System.out.println("If you have ever encrypted anything, you won't be able to read it without the Password\n");

        System.out.println("1) No disk encryption");
        System.out.println("2) Use a password");
        System.out.println("3) Delete stored user-information");
//        int userInput = Inputs.getIntAnswerFrom("Please enter the number of the option you would like to choose.");
        int userInput = 1;//TODO: Use user-input instead

        switch(userInput)
        {
            case 2:
                if(usePassword()) break;
                else showMenu();
                break;
            case 3:
                if(!deleteFiles()) System.out.println("Files not deleted");
                else Out.newBuilder("Local user-information deleted").a().ALERT().print();
                showMenu();
                break;

            case 1:
                StorageCrypto.setPassphrase("");
                break;
            default:
                Out.newBuilder("Invalid option").a().WARNING().print();
                showMenu();
                break;
        }
    }

    private static boolean usePassword()
    {
        String pw = Inputs.getStringAnswerFrom("Please enter your Password (if you used encryption on this device before, decrypt by entering the old password)");
        StorageCrypto.setPassphrase(pw);

        //Creating a crypto file to test the validity of the passphrase
        try
        {
            new StorageCrypto();
        } catch(UnrecoverableKeyException e)
        {
            Out.newBuilder("Wrong Password, please try again").a().WARNING().print();
            return false;
        }
        return true;
    }

    private static boolean deleteFiles()
    {
        String answer = Inputs.getStringAnswerFrom("Are you certain? All files will be deleted beyond recovery! Type DELETE to confirm");
        if(answer.equalsIgnoreCase("DELETE"))
        {
            StorageCrypto.removeAll();
            return true;
        }
        return false;
    }
}