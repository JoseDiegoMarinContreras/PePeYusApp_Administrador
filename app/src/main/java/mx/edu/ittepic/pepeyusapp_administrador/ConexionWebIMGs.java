package mx.edu.ittepic.pepeyusapp_administrador;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConexionWebIMGs extends AsyncTask<URL,String, Bitmap> {

    IConexion puntero;

    public ConexionWebIMGs(IConexion punteroActivity){
        puntero = punteroActivity;
    }

    @Override
    protected Bitmap doInBackground(URL... urls){

        HttpURLConnection conn = null;
        Bitmap respuesta;

        try {
            publishProgress("Estableciendo conexion con el servidor");
            conn = (HttpURLConnection) urls[0].openConnection();
            conn.connect();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2; // el factor de escala a minimizar la imagen, siempre es potencia de 2
            publishProgress("Recibiendo datos del servidor");
            respuesta = BitmapFactory.decodeStream(conn.getInputStream(), new Rect(0, 0, 0, 0), options);
        } catch (IOException e) {

            return null;

        }
        publishProgress("FIN DE LA CONEXION");
        return respuesta;
    }

    @Override
    protected  void onProgressUpdate(String... r){
        puntero.cambiarMensaje(r[0]);
    }

    @Override
    protected void onPostExecute(Bitmap respuesta){
        puntero.procesarRespuesta(respuesta);
    }

}
