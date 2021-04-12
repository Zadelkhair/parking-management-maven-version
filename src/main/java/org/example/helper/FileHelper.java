package org.example.helper;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHelper {

    public int createFile(String pathname){

        try {
            java.io.File myObj = new java.io.File(pathname);
            myObj.getParentFile().mkdirs();

            if (myObj.createNewFile()) {
                return 1;
            } else {
                return 0;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean createFile(String pathname,boolean clearContent){


        int out = createFile(pathname);

        if(out == 1 && clearContent){
            clearFileContent(pathname);
        }

        return out!=-1;
    }

    public boolean clearFileContent(String fileName){
        return writeToFile(fileName,false,"");
    }

    public boolean writeToFile(String fileName,boolean append,String data){
        try {
            FileWriter myWriter = new FileWriter(fileName,append);
            myWriter.write(data);
            myWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String readAllFile(String pathname){
        try {
            String data = "";

            java.io.File myObj = new java.io.File(pathname);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data += myReader.nextLine() + "\n";
            }
            myReader.close();
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
