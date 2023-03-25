import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
public class LoadScenario {
    public static int diffLvl;
    public static int mineN;
    public static int supermine;
    public static int timer;
    public void load(String address){

        int[] data = new int[4];

        try {
            File scenario = new File(address);
            FileReader fr = new FileReader(scenario);
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            for (int i=0; (i < 4 || line != null); i++){
                try{
                    if (((line == null || line == "") && i < 4) || (line != null && i >= 4)){
                        throw new InvalidDescriptionException(); //Για το αν λείπει γραμμή ή υπάρχει παραπάνω γραμμή (InvalidDescriptionException)
                    }
                } catch (InvalidDescriptionException e){
                    return;
                }
                line = br.readLine();
            }

            FileReader newfr = new FileReader(scenario);
            BufferedReader newbr = new BufferedReader(newfr);
            for (int i=0; i<4; i++){
                String buff = newbr.readLine();
                data[i] = Integer.parseInt(buff); //είναι τσεκαρισμένο ότι είναι 4 γραμμές και τις φορτώνει στα data
            }

            Check check = new Check();

            try{
                if (!check.areValuesOK(data[0], data[1], data[2], data[3])){
                    throw new InvalidValueException(); //Για το αν είναι λάθος οι τιμές (InvalidValueException)
                }
            }
            catch (InvalidValueException e) {
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(data[0] + " " + data[1] + " " + data[2] + " " + data[3]); //για να βλέπω τι τιμές μπαίνουν στη load

        diffLvl = data[0];
        mineN = data[1];
        supermine = data[3];
        timer = data[2];
    }

}
