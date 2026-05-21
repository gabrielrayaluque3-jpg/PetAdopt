package es.iesfranciscodelosrios.petadopt.dao;

import es.iesfranciscodelosrios.petadopt.dataAccess.ConnectionBD;
import es.iesfranciscodelosrios.petadopt.model.Voluntario;
import es.iesfranciscodelosrios.petadopt.model.Especialidad;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VoluntarioDAO {
    private static final String SQL_FIND_ALL = "SELECT * FROM voluntario";
    private static final String SQL_FIND_ID  = "SELECT * FROM voluntario WHERE id=?";
    private static final String SQL_FIND_BY_CONTAIN_NAME = "SELECT * FROM voluntario WHERE nombre LIKE ?";
    private static final String SQL_FIND_BY_ESPECIALIDAD = "SELECT * FROM voluntario WHERE especialidad LIKE ?";
    private static final String SQL_INSERT = "INSERT INTO voluntario (nombre, especialidad, dni) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE voluntario SET nombre=?, especialidad=?, dni=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM voluntario WHERE id=?";

    /**
     * Metodo que busca todos los voluntarios de la base de datos
     * @return una lista con todos los voluntarios o una lista vacia si no hay voluntarios
     */
    public static List<Voluntario> findAllVoluntarios(){
        List<Voluntario> voluntarios = new ArrayList<>();
        try(ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_FIND_ALL)){
            while(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String dni = rs.getString("dni");
                Especialidad especialidad = Especialidad.valueOf(rs.getString("especialidad"));

                Voluntario voluntario = new Voluntario(id, nombre, dni, especialidad);
                voluntarios.add(voluntario);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return voluntarios;
    }

    /**
     * Metodo que busca a un voluntario por su ID
     * @param idIntroducida del voluntario que queremos buscar
     * @return voluntario con la id que hemos introducido
     */
    public static Voluntario findVoluntarioById (int idIntroducida){
        Voluntario voluntario = null;
        try(PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_ID)){
            ps.setInt(1, idIntroducida);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String dni = rs.getString("dni");
                Especialidad especialidad = Especialidad.valueOf(rs.getString("especialidad"));

                voluntario = new Voluntario(id, nombre, dni, especialidad);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return voluntario;
    }



    /**
     * Metodo que busca a un voluntario si contiene la cadena de texto que le estamos pasando
     * @param nombreIntroducido cadena de texto que queremos que contenga el voluntario
     * @return lista de voluntario o una lista vacia si no hay
     */
    public static List<Voluntario> findByContainName(String nombreIntroducido){
        List<Voluntario> voluntarios = new ArrayList<>();
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_CONTAIN_NAME)){
            ps.setString(1, "%" + nombreIntroducido + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String dni = rs.getString("dni");
                Especialidad especialidad = Especialidad.valueOf(rs.getString("especialidad"));

                Voluntario voluntario = new Voluntario(id, nombre, dni, especialidad);
                voluntarios.add(voluntario);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return voluntarios;
    }

    /**
     * Metodo que busca a los voluntario por su especialidad
     * @param especialidadIntroducida que queremos que nos muestre
     * @return lista de voluntarios con la especialidad indicada o una lista vacia si no los hay con esa especialidad o no existe en la base de datos
     */
    public static List<Voluntario> findByEspecialidad(Especialidad especialidadIntroducida){
        List<Voluntario> voluntarios = new ArrayList<>();
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ESPECIALIDAD)){
            ps.setString(1, "%"+especialidadIntroducida.name()+"%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String dni = rs.getString("dni");
                Especialidad especialidad = Especialidad.valueOf(rs.getString("especialidad"));

                Voluntario voluntario = new Voluntario(id, nombre, dni, especialidad);
                voluntarios.add(voluntario);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return voluntarios;
    }

    /**
     * Metodo que añade un voluntario a la base de datos
     * @param v voluntario que queremos insertar
     * @return true si se ha podido insertar con existo o false si no se ha podiod inseertar
     */
    public static boolean addVoluntario(Voluntario v) {
        boolean added = false;
        if ((v != null) && findVoluntarioById(v.getId()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, v.getNombre());
                ps.setString(2, v.getEspecialidad().name());
                ps.setString(3, v.getDni());

                ps.executeUpdate();
                added = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return added;
    }

    /**
     * Metodo que modifica a un voluntario de la base de datos
     * @param v voluntario con los parametros cambiados
     * @return true si se ha podido modificar o false si no se ha podido modificar
     */
    public static boolean updateVoluntario(Voluntario v) {
        boolean updated = false;
        if ((v != null) && findVoluntarioById(v.getId()) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, v.getNombre());
                ps.setString(2, v.getEspecialidad().name());
                ps.setString(3, v.getDni());
                ps.setInt(4, v.getId());

                ps.executeUpdate();
                updated = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return updated;
    }

    /**
     * Metodo que elimina a un voluntario de la base de datos
     * @param id id del voluntario que queremo eliminar
     * @return true si se ha podido eliminar o false si no se ha podido eliminar
     */
    public static boolean deleteVoluntario(int id) {
        boolean deleted = false;
        if (findVoluntarioById(id) != null) {
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