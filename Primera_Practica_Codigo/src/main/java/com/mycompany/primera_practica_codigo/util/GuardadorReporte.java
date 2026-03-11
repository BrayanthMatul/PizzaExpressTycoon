package com.mycompany.primera_practica_codigo.util;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.mycompany.primera_practica_codigo.modelo.entidades.Jugador;
import com.mycompany.primera_practica_codigo.modelo.entidades.Partida;

public class GuardadorReporte {

    public void guardarReportePartida(List<Partida> partidas) throws Exception {
        File archivo = seleccionarArchivo("Guardar Reporte de Partidas", "reporte_partidas.csv");
        if (archivo == null) {
            return;
        }

        System.out.println("Intentando guardar archivo en: " + archivo.getAbsolutePath());
        escribirReportePartidas(partidas, archivo);
    }

    public void guardarReporteRanking(List<Jugador> jugadores) throws Exception {
        File archivo = seleccionarArchivo("Guardar Reporte de Ranking", "reporte_ranking.csv");
        if (archivo == null) {
            return;
        }

        System.out.println("Intentando guardar archivo en: " + archivo.getAbsolutePath());
        escribirReporteRanking(jugadores, archivo);
    }

    private File seleccionarArchivo(String titulo, String nombrePorDefecto) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(titulo);

        // Establecer el directorio actual como el home del usuario
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setSelectedFile(new File(fileChooser.getCurrentDirectory(), nombrePorDefecto));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        File archivo = fileChooser.getSelectedFile();
        return new File(archivo.getAbsolutePath());
    }

    private void escribirReportePartidas(List<Partida> partidas, File archivo) throws Exception {
        try (PrintWriter writer = new PrintWriter(archivo)) {
            writer.println(
                    "Jugador,Pedidos Entregados,Pedidos Cancelados,Pedidos No Entregados,Total Pedidos Generados,Puntaje Total,Nivel,Fecha y Hora Fin");

            for (Partida partida : partidas) {
                String linea = partida.getNombreJugador() + "," +
                        String.valueOf(partida.getPedidosCompletados()) + "," +
                        String.valueOf(partida.getPedidosCancelados()) + "," +
                        String.valueOf(partida.getPedidosNoEntregados()) + "," +
                        String.valueOf(partida.getContadorPedidos()) + "," +
                        String.valueOf(partida.getPuntajeTotal()) + "," +
                        String.valueOf(partida.getNivel()) + "," +
                        String.valueOf(partida.getFechaYHoraFin());
                writer.println(linea);
            }
        }
    }

    private void escribirReporteRanking(List<Jugador> jugadores, File archivo) throws Exception {
        try (PrintWriter writer = new PrintWriter(archivo)) {
            writer.println("Jugador,Puntaje Máximo,Nivel Máximo Alcanzado,Puntaje Total Acumulado,Partidas Jugadas");

            for (Jugador jugador : jugadores) {
                String linea = jugador.getNombreUsuario() + "," +
                        String.valueOf(jugador.getPuntajeMaximo()) + "," +
                        String.valueOf(jugador.getNivelMaximoAlcanzado()) + "," +
                        String.valueOf(jugador.getPuntajeTotalAcumulado()) + "," +
                        String.valueOf(jugador.getPartidasJugadas());
                writer.println(linea);
            }
        }
    }

}
