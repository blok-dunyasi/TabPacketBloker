package mc.obliviate.tabpacketblocker.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {


    public static void log(String text, File file) {
        try {
            Writer writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8)));

            writer.write(text + "\n");
            writer.flush();
            writer.close();
        }
        catch (IOException eb) {

            eb.printStackTrace();
        }
    }



    public static String getDate() { return (new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss")).format(new Date()); }



}

