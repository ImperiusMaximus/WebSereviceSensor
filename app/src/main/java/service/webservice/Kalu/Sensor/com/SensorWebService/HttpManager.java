package service.webservice.Kalu.Sensor.com.SensorWebService;

import android.util.Base64;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpManager {


    public static String getData(RequestPackage requestPackage){
        BufferedReader reader = null;
        String uri = requestPackage.getUri();

        // Realizamos una petición
        if(requestPackage.getMethod().equals("GET")){
            uri += "?" + requestPackage.getEncodeParams();
        }

        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(requestPackage.getMethod());

            if(requestPackage.getMethod().equals("POST")){
                connection.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
                outputStreamWriter.write(requestPackage.getEncodeParams());
                outputStreamWriter.flush();
            }

            StringBuilder stringBuilder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line; //Cadena de texto que permitirá ir leyendo lo que va llegando

            while((line = reader.readLine()) != null){
                stringBuilder.append(line + "\n"); //append es para concatenar cadenas
            }
            return stringBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally { //Cada vez que leemos algo, un fichero, un buffer, ... es necesario cerrarlo o finalizarlo
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

    // Con seguridad. Usuario y contraseña
    public static String getData(String uri, String username, String password){
        BufferedReader reader = null;
        byte[] loginBytes = (username + ":" + password).getBytes();
        StringBuilder loginBuilder = new StringBuilder()
                .append("Basic ") // Con un espacio al final para que no de error
                .append(Base64.encodeToString(loginBytes, Base64.DEFAULT));

        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("Authorization", loginBuilder.toString());
            StringBuilder stringBuilder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line; //Cadena de texto que permitirá ir leyendo lo que va llegando
            while((line = reader.readLine()) != null){
                stringBuilder.append(line + "\n"); //append es para concatenar cadenas
            }
            return stringBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally { //Cada vez que leemos algo, un fichero, un buffer, ... es necesario cerrarlo o finalizarlo
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }


    public static String getData(String uri){ // Para el XML
        BufferedReader reader = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            StringBuilder stringBuilder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line; //Cadena de texto que permitirá ir leyendo lo que va llegando
            while((line = reader.readLine()) != null){
                stringBuilder.append(line + "\n"); //append es para concatenar cadenas
            }
            return stringBuilder.toString();



        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }finally { //Cada vez que leemos algo, un fichero, un buffer, ... es necesario cerrarlo o finalizarlo
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }


    }

}
