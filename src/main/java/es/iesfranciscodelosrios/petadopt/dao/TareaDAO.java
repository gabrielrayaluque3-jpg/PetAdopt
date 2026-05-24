package es.iesfranciscodelosrios.petadopt.dao;

import es.iesfranciscodelosrios.petadopt.dataAccess.ConnectionBD;
import es.iesfranciscodelosrios.petadopt.model.Tarea;
import es.iesfranciscodelosrios.petadopt.model.Prioridad; // Asegúrate de importar tu Enum

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TareaDAO {
    private static final String SQL_FIND_ALL = "SELECT * FROM tarea";
    private static final String SQL_FIND_ID  = "SELECT * FROM tarea WHERE id=?";
    private static final String SQL_INSERT = "INSERT INTO tarea (nombre, descripcion, prioridad) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE tarea SET nombre=?, descripcion=?, prioridad=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM tarea WHERE id=?";

    /**
     * Metodo que devuelve una lista con todas las tareas existentes en la base de datos
     * @return una lista con todas las tareas existentes o una lista vacia si no hay tareas
     */
    public static List<Tarea> findAllTareas(){
        List<Tarea> tareas = new ArrayList<>();
        try(ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_FIND_ALL)){
            while(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                Prioridad prioridad = Prioridad.valueOf(rs.getString("prioridad"));

                Tarea tarea = new Tarea(id, nombre, descripcion, prioridad);
                tareas.add(tarea);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return tareas;
    }

    /**
     * Metodo que busca a una tarea por su ID
     * @param idIntroducida de la tarea que queremos buscar
     * @return la tarea con el id indicado
     */
    public static Tarea findTareaById (int idIntroducida){
        Tarea tarea = null;
        try(PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_ID)){
            ps.setInt(1, idIntroducida);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                Prioridad prioridad = Prioridad.valueOf(rs.getString("prioridad"));

                tarea = new Tarea(id, nombre, descripcion, prioridad);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return tarea;
    }

    /**
     * Metodo que añade una tarea a la base de datos
     * @param t Tarea que queremos añadir
     * @return true si se ha podido añadir correctamente o false si no se ha podido añadir
     */
    public static boolean addTarea(Tarea t) {
        boolean added = false;
        if ((t != null) && findTareaById(t.getId()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, t.getNombre());
                ps.setString(2, t.getDescripcion());
                ps.setString(3, t.getPrioridad().name()); //pasa el enum a un string para que la base de datos lo lee bien

                ps.executeUpdate();
                added = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return added;
    }

    /**
     * Meotod que modifica una tarea
     * @param t Tarea con los datos cambiados que queremos mofidicar
     * @return true si se ha podido modificar o false si no ha sido posible
     */
    public static boolean updateTarea(Tarea t) {
        boolean updated = false;
        if ((t != null) && findTareaById(t.getId()) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, t.getNombre());
                ps.setString(2, t.getDescripcion());
                ps.setString(3, t.getPrioridad().name());//pasa el enum a un string para que la base de datos lo lee bien
                ps.setInt(4, t.getId());

                ps.executeUpdate();
                updated = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return updated;
    }

    /**
     * Metodo que elimina una tarea de la base de datos
     * @param id de la tarea que queremos eliminar
     * @return true si se ha podido eliminar o false si no se ha podido
     */
    public static boolean deleteTarea(int id) {
        boolean deleted = false;
        if (findTareaById(id) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, id);
                ps.executeUpdate();
                deleted = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return deleted;
    }

}