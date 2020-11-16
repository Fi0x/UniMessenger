package unimessenger.util;

public class Parsers {      //TODO Change to be good!

    public static String ParseCookie(String raw){
        String refined = "";

        char[] temp = raw.toCharArray();

        try {
            for(int i = 0; i<temp.length; i++){
                if(temp[i]=='z' && temp[i+1]=='u' && temp[i+2]=='i' && temp[i+3]=='d' && temp[i+4]=='='){
                    int count = 5;
                    while (temp[i+count] != ';'){
                        refined += temp[i+count];
                        count++;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            refined = "Error in Method";
        }

        return  refined;
    }
}
