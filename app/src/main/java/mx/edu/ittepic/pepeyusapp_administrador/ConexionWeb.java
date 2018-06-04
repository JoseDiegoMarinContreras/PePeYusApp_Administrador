package mx.edu.ittepic.pepeyusapp_administrador;
import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ConexionWeb extends AsyncTask<URL, String, String> {

    List<String[]> variables;
    IConexion puntero;

    public ConexionWeb(IConexion punteroActivity){
        puntero = punteroActivity;
        variables = new ArrayList<String[]>();
    }

    public void agregarVariable(String nombre, String contenido){
        String []temporal = {nombre, contenido};
        variables.add(temporal);
    }

    @Override
    protected String doInBackground(URL... urls){
        String POST = "";
        String respusta = "";
        for(int i = 0; i < variables.size(); i++){
            String []temporal = variables.get(i);
            try{
                POST += temporal[0]+"="+ URLEncoder.encode(temporal[1], "UTF-8")+" ";
            }catch (Exception err){
                return "ERROR_404_0";
            }
        }
        POST.trim();
        POST = POST.replace(" ", "&");

        HttpURLConnection conexion = null;

        try{
            publishProgress("Intentando conectar");
            conexion = (HttpURLConnection)urls[0].openConnection();

            conexion.setDoOutput(true);
            conexion.setFixedLengthStreamingMode(POST.length());
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

            publishProgress("Enviando datos");
            OutputStream flujoSalida = new BufferedOutputStream(conexion.getOutputStream());
            flujoSalida.write(POST.getBytes());
            flujoSalida.flush();
            flujoSalida.close();

            publishProgress("Esperando respuesta del servidor");
            if(conexion.getResponseCode() == 200){
                InputStreamReader input = new InputStreamReader(conexion.getInputStream());
                BufferedReader flujoEntrada = new BufferedReader(input);
                String linea = "";
                do{
                    linea = flujoEntrada.readLine();
                    if(linea != null){
                        respusta += linea;
                    }
                }while(linea != null);
                publishProgress("Datos recibidos.\nOK");
                flujoEntrada.close();
            }else{
                return "ERROR_404_1";
            }

        }catch(UnknownHostException e){
            return "ERROR_2";
        }
        catch(IOException e){
            return "ERROR_1";
        }
        finally {
            if(conexion != null){
                publishProgress("Fin de la conexion");
                conexion.disconnect();
            }
        }
        return respusta;
    }

    @Override
    protected  void onProgressUpdate(String... r){
        puntero.cambiarMensaje(r[0]);
    }

    @Override
    protected void onPostExecute(String respuesta){
        puntero.procesarRespuesta(respuesta);
    }

}
