package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    public String DB_URL(){
        return "";
    }

    public static Config config;

    private Config(){
        createConfigFileIfNotExist();
    }

    public static Config getConfig(){

        if(config == null){
            config = new Config();
        }

        return config;

    }

    private void createConfigFileIfNotExist(){

        Properties props = new Properties();

        // Set the properties to be saved
        props.setProperty("application_name", "NewProj");

        // Write the file
        try {
            File configFile = new File("config.xml");
            if(!configFile.exists() || configFile.isDirectory()){
                FileOutputStream out = new FileOutputStream(configFile);
                props.storeToXML(out,"Configuration");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getFromConfig(String key){

        try {

            Properties properties = new Properties();

            FileInputStream in;
            in = new FileInputStream("config.xml");
            properties.loadFromXML(in);

            if(!properties.containsKey(key)){
                return null;
            }

            return properties.getProperty(key).toString();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void setToConfig(){

    }

}
