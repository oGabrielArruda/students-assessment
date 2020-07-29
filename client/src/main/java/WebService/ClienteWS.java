package WebService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;

public class ClienteWS
{
    public static Object getObjeto(Class tipoClasse, String urlWebService, String... parametros) throws Exception{
        Object objRetorno = null;
        try {
            for(String param:parametros)
                urlWebService += "/" + param;
            URL url = new URL(urlWebService);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.connect();

            String respJson = inputStreamToString(connection.getInputStream());
            connection.disconnect();

            objRetorno = fromJson(respJson, tipoClasse);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return objRetorno;
    }

    public static Object postObjeto(Object objEnvio, Class tipoClasse, String urlWebService)
    {
        Object objetoRetorno = null;
        try {
            String requestJson = toJson(objEnvio);

            URL url = new URL(urlWebService);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(15000);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Length", Integer.toString(requestJson.length()));

            DataOutputStream stream = new DataOutputStream(connection.getOutputStream());
            stream.write(requestJson.getBytes("UTF-8"));
            stream.flush();
            stream.close();
            connection.connect();

            String responseJson = inputStreamToString(connection.getInputStream());
            connection.disconnect();
            objetoRetorno = fromJson(responseJson, tipoClasse);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return objetoRetorno;
    }

    public static String inputStreamToString(InputStream is) throws  Exception
    {
        if(is != null)
        {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try{
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while((n = reader.read(buffer))!= -1){
                    writer.write(buffer, 0, n);
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            finally {
                is.close();
            }
            return  writer.toString();
        }
        else
            return "";
    }

    public static String toJson(Object obj) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter jsonValue = new StringWriter();
        mapper.writeValue(new PrintWriter(jsonValue), obj);
        return jsonValue.toString();
    }

    public static Object fromJson(String json, Class tipoRetorno) throws Exception
    {
        JsonFactory f = new MappingJsonFactory();
        JsonParser jp = f.createJsonParser(json);
        Object obj = jp.readValueAs(tipoRetorno);
        return obj;
    }
}