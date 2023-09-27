import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CrudCurso extends JFrame {
    private static final String controlador = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3308/escuela";
    private static final String usuario = "root";
    private static final String contraseña = "";

    private JLabel labelId, labelNombre, labelDescripcion, labelIdProfesor;
    private JTextField textFieldId, textFieldNombre, textFieldDescripcion, textFieldIdProfesor;
    private JButton botonCrear, botonLeer, botonActualizar, botonBorrar, vaciarCeldas, regresarButton;

    public CrudCurso() {

        // Configuración de la ventana
        setTitle("CRUD de Cursos");
        setSize(350, 350); // Tamaño ajustado
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Componentes de la interfaz
        labelId = new JLabel("ID:");
        labelNombre = new JLabel("Nombre:");
        labelDescripcion = new JLabel("Descripción:");
        labelIdProfesor = new JLabel("ID del Profesor:");

        textFieldId = new JTextField(10);
        textFieldNombre = new JTextField(10);
        textFieldDescripcion = new JTextField(10);
        textFieldIdProfesor = new JTextField(10);

        botonCrear = new JButton("Agregar");
        botonLeer = new JButton("Consultar");
        botonActualizar = new JButton("Actualizar");
        botonBorrar = new JButton("Borrar");
        vaciarCeldas = new JButton("Vaciar Celdas");
        regresarButton = new JButton("Regresar");

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(8, 1)); // Orden vertical
        panelPrincipal.add(labelId);
        panelPrincipal.add(textFieldId);
        panelPrincipal.add(labelNombre);
        panelPrincipal.add(textFieldNombre);
        panelPrincipal.add(labelDescripcion);
        panelPrincipal.add(textFieldDescripcion);
        panelPrincipal.add(labelIdProfesor);
        panelPrincipal.add(textFieldIdProfesor);
        panelPrincipal.add(regresarButton);
        panelPrincipal.setBackground(Color.PINK);
        // Panel principal
        JPanel panelBotones = new JPanel();
        panelBotones.add(botonCrear);
        panelBotones.add(botonLeer);
        panelBotones.add(botonActualizar);
        panelBotones.add(botonBorrar);
        panelBotones.add(vaciarCeldas);
        panelBotones.setBackground(Color.GREEN); // Color de fondo del panel de botones

        // Configurar colores de fondo de los botones
        regresarButton.setBackground(Color.YELLOW);

        setLayout(new BorderLayout());
        add(panelPrincipal, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);

        // Manejo de eventos
        botonCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                crearCurso();
            }
        });

        botonLeer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                consultarCurso();
            }
        });

        vaciarCeldas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vaciarCeldas();
            }
        });

        botonActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarCurso();
            }
        });

        botonBorrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                borrarCurso();
            }
        });

        // Agregamos el evento para el botón "Regresar"
        regresarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ocultar la interfaz de CrudCurso
                setVisible(false);

                // Mostrar el menú principal
                Menu menu = new Menu();
                menu.setVisible(true);
            }
        });
    }

    // Método para crear un nuevo curso
    private void crearCurso() {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection(url, usuario, contraseña);
            String sql = "INSERT INTO cursos (nombre, descripcion, id_profesor) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql);
            int id = Integer.parseInt(textFieldId.getText().trim());
            ps.setString(1, textFieldNombre.getText());
            ps.setString(2, textFieldDescripcion.getText());
            ps.setInt(3, Integer.parseInt(textFieldIdProfesor.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Curso creado con éxito");
            vaciarCeldas();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al crear el curso");
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    // Función para leer cursos


    // Función para vaciar las celdas de entrada
    private void vaciarCeldas() {
        textFieldId.setText("");
        textFieldNombre.setText("");
        textFieldDescripcion.setText("");
        textFieldIdProfesor.setText("");
    }

    // Función para actualizar un curso
    private void actualizarCurso() {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection(url, usuario, contraseña);
            String sql = "UPDATE cursos SET nombre=?, descripcion=?, id_profesor=? WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, textFieldNombre.getText());
            ps.setString(2, textFieldDescripcion.getText());
            ps.setInt(3, Integer.parseInt(textFieldIdProfesor.getText()));
            ps.setInt(4, Integer.parseInt(textFieldId.getText()));
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Curso actualizado con éxito");
                vaciarCeldas();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el curso con el ID proporcionado");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar el curso");
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Función para borrar un curso
    private void borrarCurso() {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection(url, usuario, contraseña);
            String sql = "DELETE FROM cursos WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(textFieldId.getText()));
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Curso borrado con éxito");
                vaciarCeldas();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el curso con el ID proporcionado");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al borrar el curso");
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    // Función para consultar un curso por ID
    private void consultarCurso() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, usuario, contraseña);
            String sql = "SELECT * FROM cursos WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(textFieldId.getText()));
            rs = ps.executeQuery();

            if (rs.next()) {
                // Mostrar los detalles del curso consultado en las celdas de entrada
                textFieldNombre.setText(rs.getString("nombre"));
                textFieldDescripcion.setText(rs.getString("descripcion"));
                textFieldIdProfesor.setText(Integer.toString(rs.getInt("id_profesor")));
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el curso con el ID proporcionado");
                vaciarCeldas();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al consultar el curso");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CrudCurso frame = new CrudCurso();
                frame.setVisible(true);
            }
        });
    }
    public void mostrarVentanaCursos() {
        CrudCurso frame = new CrudCurso();
        frame.setVisible(true);
    }
}
