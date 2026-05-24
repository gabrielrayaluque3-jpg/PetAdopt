package es.iesfranciscodelosrios.petadopt.dao;

import es.iesfranciscodelosrios.petadopt.dataAccess.ConnectionBD;
import es.iesfranciscodelosrios.petadopt.model.Realiza;
import es.iesfranciscodelosrios.petadopt.model.Voluntario;
import es.iesfranciscodelosrios.petadopt.model.Tarea;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class RealizaDAO {
    private static final String SQL_FIND_ALL = "SELECT * FROM realiza";
    private static final String SQL_FIND_EXACTO = "SELECT * FROM realiza WHERE id_voluntario=? AND id_tarea=? AND fecha=?";
    private static final String SQL_INSERT = "INSERT INTO realiza (fecha, completada, id_voluntario, id_tarea) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE realiza SET completada=? WHERE id_voluntario=? AND id_tarea=? AND fecha=?";
    private static final String SQL_DELETE = "DELETE FROM realiza WHERE id_voluntario=? AND id_tarea=? AND fecha=?";
    private static final String SQL_FIND_BY_ESTADO = "SELECT * FROM realiza WHERE completada=?";

    public static List<Realiza> findAllRealizaciones() {
        List<Realiza> realizaciones = new ArrayList<>();
        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_FIND_ALL)) {
            while (rs.next()) {
                Date fecha = rs.getDate("fecha");
                boolean completada = rs.getBoolean("completada");

                Voluntario voluntario = VoluntarioDAO.findVoluntarioById(rs.getInt("id_voluntario"));
                Tarea tarea = TareaDAO.findTareaById(rs.getInt("id_tarea"));

                Realiza realiza = new Realiza(fecha, completada, voluntario, tarea);
                realizaciones.add(realiza);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return realizaciones;
    }

    /**
     * Busca todas las tareas según su estado
     * @param completadaIntroducida true para ver las hechas o false para las pendientes
     * @return lista de tareas o lista vacia si no hay tareas
     */
    public static List<Realiza> findByCompletada(boolean completadaIntroducida) {
        List<Realiza> lista = new ArrayList<>();
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ESTADO)) {
            ps.setBoolean(1, completadaIntroducida);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                java.sql.Date fecha = rs.getDate("fecha");
                boolean completada = rs.getBoolean("completada");
                Voluntario voluntario = VoluntarioDAO.findVoluntarioById(rs.getInt("id_voluntario"));
                Tarea tarea = TareaDAO.findTareaById(rs.getInt("id_tarea"));

                lista.add(new Realiza(fecha, completada, voluntario, tarea));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public static Realiza findSpecific(int idVoluntario, int idTarea, java.util.Date fechaJava) {
        Realiza realiza = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_EXACTO)) {
            ps.setInt(1, idVoluntario);
            ps.setInt(2, idTarea);
            ps.setDate(3, new java.sql.Date(fechaJava.getTime()));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Date fecha = rs.getDate("fecha");
                boolean completada = rs.getBoolean("completada");
                Voluntario voluntario = VoluntarioDAO.findVoluntarioById(rs.getInt("id_voluntario"));
                Tarea tarea = TareaDAO.findTareaById(rs.getInt("id_tarea"));

                realiza = new Realiza(fecha, completada, voluntario, tarea);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return realiza;
    }

    public static boolean addRealiza(Realiza r) {
        boolean added = false;
        if (r != null && findSpecific(r.getVoluntario().getId(), r.getTarea().getId(), r.getFecha()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setDate(1, new java.sql.Date(r.getFecha().getTime()));
                ps.setBoolean(2, r.isCompletada());
                ps.setInt(3, (r.getVoluntario() != null) ? r.getVoluntario().getId() : 0);
                ps.setInt(4, (r.getTarea() != null) ? r.getTarea().getId() : 0);

                ps.executeUpdate();
                added = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return added;
    }

    public static boolean updateRealiza(Realiza r) {
        boolean updated = false;
        if (r != null && findSpecific(r.getVoluntario().getId(), r.getTarea().getId(), r.getFecha()) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setBoolean(1, r.isCompletada());
                ps.setInt(2, r.getVoluntario().getId());
                ps.setInt(3, r.getTarea().getId());
                ps.setDate(4, new java.sql.Date(r.getFecha().getTime()));

                ps.executeUpdate();
                updated = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return updated;
    }

    public static boolean deleteRealiza(int idVoluntario, int idTarea, java.util.Date fechaJava) {
        boolean deleted = false;
        if (findSpecific(idVoluntario, idTarea, fechaJava) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, idVoluntario);
                ps.setInt(2, idTarea);
                ps.setDate(3, new java.sql.Date(fechaJava.getTime()));

                ps.executeUpdate();
                deleted = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return deleted;
    }
}