package mx.edu.ittepic.pepeyusapp_administrador;

import android.graphics.Bitmap;

public interface IConexion {
    void procesarRespuesta(String r);
    void procesarRespuesta(Bitmap img);
    void cambiarMensaje(String mensaje);
}