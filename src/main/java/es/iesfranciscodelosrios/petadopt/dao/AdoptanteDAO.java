package es.iesfranciscodelosrios.petadopt.dao;

import es.iesfranciscodelosrios.petadopt.dataAccess.ConnectionBD;
import es.iesfranciscodelosrios.petadopt.model.AdopcionDetalle;
import es.iesfranciscodelosrios.petadopt.model.Adoptante;
import es.iesfranciscodelosrios.petadopt.model.Animal;
import es.iesfranciscodelosrios.petadopt.model.Voluntario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdoptanteDAO {
    private static final String SQL_FIND_ALL = "SELECT * FROM adoptante";
    private static final String SQL_FIND_ID  = "SELECT * FROM adoptante WHERE id=?";
    private static final String SQL_FIND_NAME  = "SELECT * FROM adoptante WHERE nombre=?";
    private static final String SQL_FIND_TLF  = "SELECT * FROM adoptante WHERE telefono=?";
    private static final String SQL_FIND_EMAIL  = "SELECT * FROM adoptante WHERE email=?";
    private static final String SQL_FIND_BY_CONTAIN_NAME = "SELECT * FROM adoptante where nombre like ?";
    private static final String SQL_FIND_BY_CONTAIN_EMAIL = "SELECT * FROM adoptante where email like ?";
    private static final String SQL_INSERT = "INSERT INTO adoptante (nombre, dni, telefono, email) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE adoptante SET nombre=?, dni=?, telefono=?, email=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM adoptante WHERE id=?";
    private static final String SQL_ADOPCIONES = "SELECT a.id, ad.nombre AS nombre_adoptante, ad.telefono, a.nombre AS nombre_animal, a.raza FROM animal  a INNER JOIN adoptante ad ON a.id_adoptante = ad.id";
    private static final String SQL_UPDATE_ADOPCION = "UPDATE animal SET id_adoptante = ? WHERE id = ?";
    private static final String SQL_ANIMAL_SIN_ADOPTANTE = "SELECT * FROM animal WHERE id_adoptante IS NULL";
    private static final String SQL_COUNT_ANIMALES_ID_ADOPTANTE = "SELECT COUNT(*) FROM animal WHERE id_adoptante = ?";
    private static final String SQL_DELETE_ADOPCION = "UPDATE animal SET id_adoptante = NULL WHERE id = ?";


    /**
     * Metodo que devuelve una lista con todos los adoptantes que hay en la base de datos
     * @return lista de adoptantes o null si no los hay
     */
    public static List<Adoptante> findAllAdoptantes(){
        List<Adoptante> adoptantes = new ArrayList<>();
        Adoptante adoptante = null;

        try(ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_FIND_ALL)){
            while(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String dni = rs.getString("dni");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                adoptante = new Adoptante(id,nombre,dni,telefono,email);
                adoptantes.add(adoptante);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return adoptantes;
    }

    /**
     * Metodo que devuelve un adoptante diciendole su id, y null si no hay adopntante con dicha id
     * @param idIntroducida del adoptante que queremos buscar
     * @return adoptante con la id introducida
     */
    public static Adoptante findAdoptanteById (int idIntroducida){
        Adoptante adoptante = null;

        try(PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_ID)){
            ps.setInt(1,idIntroducida);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String dni = rs.getString("dni");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                adoptante=new Adoptante(id,nombre,dni,telefono,email);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return adoptante;
    }

    /**
     * Metodo que busca a adoptantes por el nombre, devuleve una lista de adoptante por si hubiera mas de uno con el mismo nombre o null si no existe ninguno con ese nombre
     * @param nombreIntroducido del adoptante que estamos buscando
     * @return lista de adoptantes rellena en caso de que haya alguno cone se nombre o vacía si no hay adoptantes con ese nombre
     */
    public static List<Adoptante> findAdoptanteByNombre (String nombreIntroducido){
        Adoptante adoptante = null;
        List<Adoptante> adoptantes = new ArrayList<>();

        try(PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_NAME)){
            ps.setString(1,nombreIntroducido);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String dni = rs.getString("dni");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                adoptante=new Adoptante(id,nombre,dni,telefono,email);
                adoptantes.add(adoptante);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return adoptantes;
    }

    /**
     * Metodo que devulve un adoptante indicando su numero de telefono, devuelve null si no existe un adoptante con ese telefono
     * @param telefonoIntroducido que tiene el adoptante que queremos buscar
     * @return el adoptante con el numero de telefono indicado
     */
    public static Adoptante findAdoptanteByTelefono (String telefonoIntroducido){
        Adoptante adoptante = null;

        try(PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_TLF)){
            ps.setString(1,telefonoIntroducido);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String dni = rs.getString("dni");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                adoptante=new Adoptante(id,nombre,dni,telefono,email);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return adoptante;
    }

    /**
     * Metodo que devuelve un adoptante indicando su email, devolveria null si no existiera un adoptante con ese email
     * @param emailIntroducido del adoptante que queremos buscar
     * @return un adoptante con el email que hemos indicado o null si no existe un adoptante con ese email
     */
    public static Adoptante findAdoptanteByEmail (String emailIntroducido){
        Adoptante adoptante = null;

        try(PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_EMAIL)){
            ps.setString(1,emailIntroducido);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String dni = rs.getString("dni");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                adoptante=new Adoptante(id,nombre,dni,telefono,email);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return adoptante;
    }

    /**
     * Metodo que busca por nombre a un adoptante sin hacer falta que introuca el nombre completo
     * @param nombreIntroducido parte del nombre que queremos buscar
     * @return lista de adoptantes si exiten con el nombre introducido o  una lista vacia
     */
    public static List<Adoptante> findByContainName(String nombreIntroducido){
        List<Adoptante> adoptantes = new ArrayList<>();
        Adoptante adoptante = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_CONTAIN_NAME)){

            ps.setString(1,"%"+nombreIntroducido+"%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String dni = rs.getString("dni");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                adoptante = new Adoptante(id, nombre, dni,telefono, email);
                adoptantes.add(adoptante);
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return adoptantes;
    }

    /**
     * Metodo que busca por email a un adoptante sin hacer falta que introuca el email completo
     * @param emailIntroducido parte del email que queremos buscar
     * @return lista de adoptantes si exiten con el email introducido o  una lista vacia
     */
    public static List<Adoptante> findByContainEmail(String emailIntroducido){
        List<Adoptante> adoptantes = new ArrayList<>();
        Adoptante adoptante = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_CONTAIN_EMAIL)){

            ps.setString(1,"%"+emailIntroducido+"%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String dni = rs.getString("dni");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                adoptante = new Adoptante(id, nombre, dni,telefono, email);
                adoptantes.add(adoptante);
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return adoptantes;
    }

    /**
     * Metodo que añade un adoptante a la base de datos
     * @param a datos del adoptante que queremos insertar
     * @return true si se ha podido inserta o false si no se ha podido
     */
    public static boolean addAdoptante(Adoptante a) {
        boolean added = false;
        if ((a != null) && findAdoptanteById(a.getId()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, a.getNombre());
                ps.setString(2, a.getDni());
                ps.setString(3, a.getTelefono());
                ps.setString(4, a.getEmail());

                ps.executeUpdate();
                added = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return added;
    }

    /**
     * Metodo que modifica a un adoptante existente
     * @param a todos los datos de el adoptante que queremos cambiar, con esos datos ya cambiados
     * @return true si se ha podido modificar o false si no se ha podido modificar
     */
    public static boolean updateAdoptante(Adoptante a) {
        boolean updated = false;
        if ((a != null) && findAdoptanteById(a.getId()) != null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, a.getNombre());
                ps.setString(2, a.getDni());
                ps.setString(3, a.getTelefono());
                ps.setString(4, a.getEmail());
                ps.setInt(5, a.getId());

                ps.executeUpdate();
                updated = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return updated;
    }

    /**
     * Metodo que elimina a un adoptante de la base de datos
     * @param id del adoptante que queremos eliminar
     * @return true si se ha eliminado con existo o false si no se ha eliminado
     */
    public static boolean deleteAdoptante(int id) {
        boolean deleted = false;
        if (findAdoptanteById(id) != null) {
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

    /**
     * Rompe una adopcion existente desvinculando al adoptante del animal.
     * Establece el campo id_adoptante a NULL para el animal seleccionado.
     * @param idAnimal ID del animal que se quiere liberar de la adopcion.
     * @return true si la operacion se realizo con exito, false en caso contrario.
     */
    public static boolean deleteAdopcion(int idAnimal) {

        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_DELETE_ADOPCION)) {
            ps.setInt(1, idAnimal);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo que cuenta cuantos animales tiene asociados un adoptante en la base de datos.
     * @param idAdoptante ID del adoptante que queremos consultar
     * @return El numero total de animales adoptados por esa persona
     */
    public static int countAnimalesAdoptados(int idAdoptante) {
        int count = 0;

        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_COUNT_ANIMALES_ID_ADOPTANTE)) {
            ps.setInt(1, idAdoptante);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Obtenemos el resultado de la primera columna del COUNT(*)
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return count;
    }

    /**
     * Busca animales que no tienen asignado ningún adoptante (id_adoptante es NULL)
     * @return lista con todos los animales disponibles para adopción
     */
    public static List<Animal> findAnimalesDisponibles() {
        List<Animal> lista = new ArrayList<>();

        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_ANIMAL_SIN_ADOPTANTE);
             ResultSet rs = ps.executeQuery()) {
            Voluntario v = new Voluntario();
            while (rs.next()) {
                 v =  VoluntarioDAO.findVoluntarioById(rs.getInt("id_voluntario"));

                lista.add(new Animal(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        es.iesfranciscodelosrios.petadopt.model.Especie.valueOf(rs.getString("especie")),
                        rs.getString("raza"),
                        rs.getInt("edad"),
                        v,
                        null
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    /**
     * Vincula un animal con un adoptante en la base de datos
     * @param idAnimal ID del animal que va a ser adoptado.
     * @param idAdoptante ID del adoptante que se hace cargo del animal.
     * @return true si la vinculacion se realizo con exito, false en caso contrario.
     */
    public static boolean vincularAdopcion(int idAnimal, int idAdoptante) {

        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE_ADOPCION)) {
            ps.setInt(1, idAdoptante);
            ps.setInt(2, idAnimal);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Recupera el listado de todas las adopciones activas
     * con los datos de los animales con los de sus respectivos adoptantes.
     * @return Lista de objetos AdopcionDetalle con la informacion combinada.
     */
    public static List<AdopcionDetalle> findAdopcionesDetalle() {
        List<AdopcionDetalle> lista = new ArrayList<>();

        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_ADOPCIONES);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new AdopcionDetalle(
                        rs.getInt(1),
                        rs.getString("nombre_adoptante"),
                        rs.getString("telefono"),
                        rs.getString("nombre_animal"),
                        rs.getString("raza")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }


}
