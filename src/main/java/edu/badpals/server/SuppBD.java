package edu.badpals.server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SuppBD {
    Connection c;

    public SuppBD() {
        this.c = conectar();
    }

    public Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/preguntas_respuestasbd", "root", "root");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SuppBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SuppBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void desconectar() {
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> obtenerRespuestas(String pregunta) {
        List<String> respuestas = new ArrayList<>();
        try {

            PreparedStatement preparedStatement = c.prepareStatement(
                    "SELECT cadena_respuesta" +
                            " FROM RESPUESTAS" +
                            " WHERE id in (" +
                            " SELECT id_respuesta" +
                            " FROM preguntas_respuestas" +
                            " WHERE id_pregunta = (" +
                            "SELECT id" +
                            " FROM PREGUNTAS" +
                            " WHERE cadena_pregunta = ?))");
            preparedStatement.setString(1, pregunta);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                respuestas.add(rs.getString(1));
            }

            preparedStatement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return respuestas;
    }
}
