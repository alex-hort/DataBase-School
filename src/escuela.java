import javax.swing.*;
import java.sql.*;
public class escuela {
    private static final String controlador = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3308/escuela";
    private static final String usr = "root";
    private static final String pass = "";

    public String id;
    public String nom;
    public String ap;
    public String edad;
    public String dir;
    public String tel;

    static {
        try {
            // Cargar el controlador JDBC al inicio
            Class.forName(controlador);
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el controlador");
            e.printStackTrace();
        }
    }
    public static Connection conectar() {
        Connection con = null;
        try {
            // Establecer la conexión con la base de datos
            con = DriverManager.getConnection(url, usr, pass);
            System.out.println("Conexion correcta"); // Mensaje de depuración
        } catch (SQLException e) {
            System.out.println("Error de conexion");
            e.printStackTrace();
        }
        return con;
    }
    // Función para consultar un registro
    public void selecreg() {
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            cn = conectar();
            String seleccionar = "SELECT * FROM estudiantes WHERE id=?";
            pstm = cn.prepareStatement(seleccionar);
            pstm.setString(1, id); // Establecer el parámetro
            rs = pstm.executeQuery();

            while (rs.next()) {
                id = rs.getString(1);
                nom = rs.getString(2);
                ap = rs.getString(3);
                edad = rs.getString(4);
                dir = rs.getString(5);
                tel = rs.getString(6);
            }
            System.out.println("Consulta exitosa");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al consultar");
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Función para eliminar un registro
    public void elimgr(String id) {
        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = conectar();
            String query = "DELETE FROM estudiantes WHERE id=?";
            ps = cn.prepareStatement(query);
            ps.setString(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro borrado");
        } catch (Exception e) {
            System.out.println("Error al borrar registro");
            e.printStackTrace();
        } finally {
            // Cerrar recursos si es necesario
        }
    }


    // Función para actualizar un registro por ID
    public void actreg() {
        Connection cn = null;
        PreparedStatement pstm = null;
        try {
            cn = conectar();
            String upd = "UPDATE estudiantes SET nombre=?, apellido=?, edad=?, direccion=?, telefono=? WHERE id=?";
            pstm = cn.prepareStatement(upd);
            pstm.setString(1, nom);
            pstm.setString(2, ap);
            pstm.setString(3, edad);
            pstm.setString(4, dir);
            pstm.setString(5, tel);
            pstm.setString(6, id);

            int filasAfectadas = pstm.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Registro actualizado");
                JOptionPane.showMessageDialog(null, "Registro actualizado");
            } else {
                System.out.println("No se encontró el registro con el ID proporcionado.");
                JOptionPane.showMessageDialog(null, "No se encontró el registro con el ID proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("Error de actualizacion");
            e.printStackTrace();
        } finally {
            // Cerrar recursos si es necesario
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Función para insertar un nuevo registro
    public void insertar() {
        Connection cn = null;
        PreparedStatement pstm = null;

        try {
            cn = conectar();
            String insertar = "INSERT INTO estudiantes VALUES (?,?,?,?,?,?)";
            pstm = cn.prepareStatement(insertar);
            pstm.setString(1, id);
            pstm.setString(2, nom);
            pstm.setString(3, ap);
            pstm.setString(4, edad);
            pstm.setString(5, dir);
            pstm.setString(6, tel);
            pstm.executeUpdate();
            System.out.println("Registro insertado");

            JOptionPane.showMessageDialog(null, "Registro exitoso");
        } catch (Exception e) {
            System.out.println("Error al insertar");
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

