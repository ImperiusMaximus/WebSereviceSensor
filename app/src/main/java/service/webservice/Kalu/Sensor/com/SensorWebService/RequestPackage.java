package service.webservice.Kalu.Sensor.com.SensorWebService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class RequestPackage {
    private String uri;
    private String method = "GET";
    // private String method = "POST";
    private Map<String, String> params = new HashMap<>();

    public String getUri(){
        return uri;

    }

    public void setUri(String uri){
        this.uri = uri;

    }

    public String getMethod(){
        return method;

    }

    public void setMethod(String method){
        this.method = method;

    }

    public Map<String, String> getParams(){
        return params;

    }

    public void setParams(Map<String, String> params){
        this.params = params;

    }

    // En las URLs se envia el parametro y el valor.
    // y no sabemos cuantos pares se enviaran. Pueden enviarse 2 parametros 3, ...

    public void setParam(String key, String value){
        params.put(key, value);

    }

    // Para codificar la URL
    public String getEncodeParams(){
        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            String value = null;
            try {
                value = URLEncoder.encode(params.get(key), "UTF-8");


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // Ahora leer el sb para empezar a unir los parametros
            if(sb.length() > 0)
                sb.append("&");

            sb.append(key + "=" + value);

        }
        return sb.toString();

    }

}
