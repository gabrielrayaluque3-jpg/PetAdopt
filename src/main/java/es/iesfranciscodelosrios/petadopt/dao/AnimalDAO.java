package es.iesfranciscodelosrios.petadopt.dao;

import es.iesfranciscodelosrios.petadopt.dataAccess.ConnectionBD;
import es.iesfranciscodelosrios.petadopt.model.Animal;
import es.iesfranciscodelosrios.petadopt.model.Especie;
import es.iesfranciscodelosrios.petadopt.model.Voluntario;
import es.iesfranciscodelosrios.petadopt.model.Adoptante;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO {
    private static final String SQL_FIND_ALL = "SELECT * FROM animal";
    private static final String SQL_FIND_ID  = "SELECT * FROM animal WHERE id=?";
    private static final String SQL_FIND_NAME  = "SELECT * FROM animal WHERE nombre=?";
    private static final String SQL_FIND_BY_CONTAIN_NAME = "SELECT * FROM animal WHERE nombre LIKE ?";
    private static final String SQL_FIND_BY_ESPECIE = "SELECT * FROM animal WHERE especie=?";
    private static final String SQL_INSERT = "INSERT INTO animal (nombre, especie, raza, edad, id_voluntario, id_adoptante) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE animal SET nombre=?, especie=?, raza=?, edad=?, id_voluntario=?, id_adoptante=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM animal WHERE id=?";

    /**
     * Metodo que busca a todos los animales de la base da datos, mostrando el id de su voluntario y el id del adoptante si tuviera
     * @return lista con los animales, o una lsita vacia si no los hubiera
     */
    public static List<Animal> findAllAnimales(){
        List<Animal> animales = new ArrayList<>();
        try(ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_FIND_ALL)){
            while(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                Especie especie = Especie.valueOf(rs.getString("especie"));
                String raza = rs.getString("raza");
                int edad = rs.getInt("edad");
                Voluntario voluntario = VoluntarioDAO.findVoluntarioById(rs.getInt("id_voluntario"));
                Adoptante adoptante = AdoptanteDAO.findAdoptanteById(rs.getInt("id_adoptante"));
                Animal animal = new Animal(id, nombre, especie, raza, edad, voluntario, adoptante);
                animales.add(animal);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return animales;
    }

    /**
     * Metodo que busca a un animal por su id
     * @param idIntroducida del animal que queremos buscar
     * @return un animal con el id introducido o un animal vacio si no existiera con ese id o la bbbdd este vacia
     */
    public static Animal findAnimalById (int idIntroducida){
        Animal animal = null;
        try(PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_ID)){
            ps.setInt(1, idIntroducida);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                Especie especie = Especie.valueOf(rs.getString("especie"));
                String raza = rs.getString("raza");
                int edad = rs.getInt("edad");
                Voluntario voluntario = VoluntarioDAO.findVoluntarioById(rs.getInt("id_voluntario"));
                Adoptante adoptante = AdoptanteDAO.findAdoptanteById(rs.getInt("id_adoptante"));
                animal = new Animal(id, nombre, especie, raza, edad, voluntario, adoptante);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return animal;
    }

    /**
     * Metodo que busca animlaes por una cadena exacta
     * @param nombreIntroducido cadena exxacta que queremos buscar
     * @return lista de animales con esa cadena exacta o lista vacia si no hay animales con esa cadena
     */
    public static List<Animal> findAnimalByNombre(String nombreIntroducido) {
        List<Animal> animales = new ArrayList<>();
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_NAME)) {
            ps.setString(1, nombreIntroducido);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                Especie especie = Especie.valueOf(rs.getString("especie"));
                String raza = rs.getString("raza");
                int edad = rs.getInt("edad");
                Voluntario voluntario = VoluntarioDAO.findVoluntarioById(rs.getInt("id_voluntario"));
                Adoptante adoptante = AdoptanteDAO.findAdoptanteById(rs.getInt("id_adoptante"));
                Animal animal = new Animal(id, nombre, especie, raza, edad, voluntario, adoptante);
                animales.add(animal);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return animales;
    }

    /**
     * Metodo que busca los animales que contienen la cadena de texto que le pasamos
     * @param nombreIntroducido cadena de texto que contendran los animales
     * @return lista de animales que contiene la cadena de texto pasada o lista vacia si no hay animales con esa cadena
     */
    public static List<Animal> findByContainName(String nombreIntroducido) {
        List<Animal> animales = new ArrayList<>();
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_CONTAIN_NAME)) {
            ps.setString(1, "%" + nombreIntroducido + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                Especie especie = Especie.valueOf(rs.getString("especie"));
                String raza = rs.getString("raza");
                int edad = rs.getInt("edad");
                Voluntario voluntario = VoluntarioDAO.findVoluntarioById(rs.getInt("id_voluntario"));
                Adoptante adoptante = AdoptanteDAO.findAdoptanteById(rs.getInt("id_adoptante"));
                Animal animal = new Animal(id, nombre, especie, raza, edad, voluntario, adoptante);
                animales.add(animal);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return animales;
    }

    /**
     * Metodo que filtra a los animales por su especie
     * @param especieIntroducida especie que queremos ver
     * @return lista de animales con la especia introducida o una lista vacia si no hay animales con esa especie
     */
    public static List<Animal> findByEspecie(Especie especieIntroducida){
        List<Animal> animales = new ArrayList<>();
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ESPECIE)){
            ps.setString(1, especieIntroducida.name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                Especie especie = Especie.valueOf(rs.getString("especie"));
                String raza = rs.getString("raza");
                int edad = rs.getInt("edad");
                Voluntario voluntario = VoluntarioDAO.findVoluntarioById(rs.getInt("id_voluntario"));
                Adoptante adoptante = AdoptanteDAO.findAdoptanteById(rs.getInt("id_adoptante"));
                Animal animal = new Animal(id, nombre, especie, raza, edad, voluntario, adoptante);
                animales.add(animal);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return animales;
    }

    /**
     * Metodo que añade a un animal a la BBDD
     * @param a animal que queremos añadir
     * @return true si se ha podido añadir o false si no ha sido posible
     */
    public static boolean addAnimal(Animal a) {
        boolean added = false;
        if ((a != null) && findAnimalById(a.getId()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, a.getNombre());
                ps.setString(2, a.getEspecie().name());
                ps.setString(3, a.getRaza());
                ps.setInt(4, a.getEdad());

                // Manejo de Voluntario
                if (a.getVoluntario() != null && a.getVoluntario().getId() > 0) {
                    ps.setInt(5, a.getVoluntario().getId());
                } else {
                    ps.setNull(5, java.sql.Types.INTEGER);
                }

                // Manejo de Adoptante
                if (a.getAdoptante() != null && a.getAdoptante().getId() > 0) {
                    ps.setInt(6, a.getAdoptante().getId());
                } else {
                    ps.setNull(6, java.sql.Types.INTEGER);
                }

                ps.executeUpdate();
                added = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return added;
    }

    /**
     * Metoto que modifica a un animal existe de la BBDD
     * @param a parametros modificados del animal que queremos modificar
     * @return true si se ha podido modificar o false si no se ha podido
     */
    public static boolean updateAnimal(Animal a) {
        boolean updated = false;
        if ((a != null) && findAnimalById(a.getId()) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, a.getNombre());
                ps.setString(2, a.getEspecie().name());
                ps.setString(3, a.getRaza());
                ps.setInt(4, a.getEdad());

                // Control para el Voluntario: Si es null, guardamos NULL en la BBDD
                if (a.getVoluntario() != null) {
                    ps.setInt(5, a.getVoluntario().getId());
                } else {
                    ps.setNull(5, java.sql.Types.INTEGER);
                }

                // Control para el Adoptante: Si es null (Disponible), guardamos NULL en la BBDD
                if (a.getAdoptante() != null && a.getAdoptante().getId() > 0) {
                    ps.setInt(6, a.getAdoptante().getId());
                } else {
                    ps.setNull(6, java.sql.Types.INTEGER);
                }

                ps.setInt(7, a.getId());

                // Es mejor comprobar si realmente se modificó alguna fila
                updated = ps.executeUpdate() > 0;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return updated;
    }

    /**
     * Metodo que elimnia a un animal de la BBDD
     * @param id del animal que queremos eliminar
     * @return true si se ha podido eliminar o false si no se ha eliminado
     */
    public static boolean deleteAnimal(int id) {
        boolean deleted = false;
        if (findAnimalById(id) != null) {
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