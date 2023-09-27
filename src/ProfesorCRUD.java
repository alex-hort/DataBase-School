import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfesorCRUD extends JFrame {
    private static final String controlador = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3308/escuela";
    private static final String usr = "root";
    private static final String pass = "";

    private JLabel labelId, labelNombre, labelApellido, labelEspecialidad, labelCorreo;
    private JTextField textFieldId, textFieldNombre, textFieldApellido, textFieldEspecialidad, textFieldCorreo;
    private JButton botonCrear, botonConsultar, botonActualizar, botonEliminar, botonSalir, limpiarCeldas, regresarButton;

    public ProfesorCRUD() {
        // Configuración de la ventana
        setTitle("CRUD de Profesores");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());
        panelBotones.setBackground(Color.GREEN);

        // Componentes de la interfaz
        labelId = new JLabel("ID:");
        labelNombre = new JLabel("Nombre:");
        labelApellido = new JLabel("Apellido:");
        labelEspecialidad = new JLabel("Especialidad:");
        labelCorreo = new JLabel("Correo:");

        textFieldId = new JTextField(15);
        textFieldNombre = new JTextField(15);
        textFieldApellido = new JTextField(15);
        textFieldEspecialidad = new JTextField(15);
        textFieldCorreo = new JTextField(15);

        botonCrear = new JButton("Crear");
        botonConsultar = new JButton("Consultar");
        botonActualizar = new JButton("Actualizar");
        botonEliminar = new JButton("Eliminar");
        botonSalir = new JButton("Salir");
        limpiarCeldas = new JButton("Limpiar");
        regresarButton = new JButton("Regresar");



        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(8, 4));
        panelPrincipal.add(labelId);
        panelPrincipal.add(textFieldId);
        panelPrincipal.add(labelNombre);
        panelPrincipal.add(textFieldNombre);
        panelPrincipal.add(labelApellido);
        panelPrincipal.add(textFieldApellido);
        panelPrincipal.add(labelEspecialidad);
        panelPrincipal.add(textFieldEspecialidad);
        panelPrincipal.add(labelCorreo);
        panelPrincipal.add(textFieldCorreo);
        panelPrincipal.add(botonCrear);
        panelPrincipal.add(botonConsultar);
        panelPrincipal.add(botonActualizar);
        panelPrincipal.add(botonEliminar);
        panelPrincipal.add(limpiarCeldas);
        // Configurar colores de fondo
        panelPrincipal.setBackground(Color.CYAN); // Color de fondo del panel principal
        regresarButton.setBackground(Color.YELLOW);

        // Agregar componentes al panel en orden vertical
        panelPrincipal.add(regresarButton);

        // Agregar componentes a la ventana
        setLayout(new BorderLayout());
        add(panelPrincipal, BorderLayout.NORTH);
        add(botonSalir, BorderLayout.SOUTH);

        // Manejo de eventos
        botonCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                crearProfesor();
            }
        });

        limpiarCeldas.addActionListener(new ActionListener() {
            private void vaciarCeldas() {
                textFieldNombre.setText("");
                textFieldApellido.setText("");
                textFieldEspecialidad.setText("");
                textFieldCorreo.setText("");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                vaciarCeldas();
            }
        });

        botonConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                consultarProfesor();
            }
        });

        botonActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarProfesor();
            }
        });

        botonEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarProfesor();
            }
        });

        botonSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ocultar la interfaz de ProfesorCRUD
                setVisible(false);

                // Mostrar el menú principal
                Menu menu = new Menu();
                menu.setVisible(true);
            }
        });
    }

    // Función para establecer la conexión con la base de datos
    public Connection conectar() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, usr, pass);
            System.out.println("Conexión exitosa");
        } catch (SQLException e) {
            System.out.println("Error de conexión");
            e.printStackTrace();
        }
        return con;
    }

    // Método para crear un nuevo profesor
    private void crearProfesor() {
        Connection cn = null;
        PreparedStatement pstm = null;

        try {
            cn = conectar();
            String insertar = "INSERT INTO profesores (nombre, apellido, especialidad, correo) VALUES (?, ?, ?, ?)";
            pstm = cn.prepareStatement(insertar);
            pstm.setString(1, textFieldNombre.getText());
            pstm.setString(2, textFieldApellido.getText());
            pstm.setString(3, textFieldEspecialidad.getText());
            pstm.setString(4, textFieldCorreo.getText());
            pstm.executeUpdate();
            System.out.println("Registro insertado");

            JOptionPane.showMessageDialog(null, "Profesor creado con éxito");
            vaciarCeldas();
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

    // Método para consultar un profesor por ID
    private void consultarProfesor() {
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            cn = conectar();
            String seleccionar = "SELECT * FROM profesores WHERE id=?";
            pstm = cn.prepareStatement(seleccionar);
            pstm.setString(1, textFieldId.getText());
            rs = pstm.executeQuery();

            while (rs.next()) {
                textFieldNombre.setText(rs.getString("nombre"));
                textFieldApellido.setText(rs.getString("apellido"));
                textFieldEspecialidad.setText(rs.getString("especialidad"));
                textFieldCorreo.setText(rs.getString("correo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al consultar");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
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
    // Método para actualizar un profesor por ID
    private void actualizarProfesor() {
        Connection cn = null;
        PreparedStatement pstm = null;

        try {
            cn = conectar();
            String actualizar = "UPDATE profesores SET nombre=?, apellido=?, especialidad=?, correo=? WHERE id=?";
            pstm = cn.prepareStatement(actualizar);
            pstm.setString(1, textFieldNombre.getText());
            pstm.setString(2, textFieldApellido.getText());
            pstm.setString(3, textFieldEspecialidad.getText());
            pstm.setString(4, textFieldCorreo.getText());
            pstm.setString(5, textFieldId.getText());
            int filasAfectadas = pstm.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Profesor actualizado con éxito");
                vaciarCeldas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el profesor con el ID proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("Error de actualización");
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

    // Método para eliminar un profesor por ID
    private void eliminarProfesor() {
        Connection cn = null;
        PreparedStatement pstm = null;

        try {
            cn = conectar();
            String eliminar = "DELETE FROM profesores WHERE id=?";
            pstm = cn.prepareStatement(eliminar);
            pstm.setString(1, textFieldId.getText());
            int filasAfectadas = pstm.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Profesor eliminado con éxito");
                vaciarCeldas();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el profesor con el ID proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar");
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

    // Método para vaciar las celdas de entrada de texto
    private void vaciarCeldas() {
        textFieldId.setText("");
        textFieldNombre.setText("");
        textFieldApellido.setText("");
        textFieldEspecialidad.setText("");
        textFieldCorreo.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ProfesorCRUD ventana = new ProfesorCRUD();
                ventana.setVisible(true);
            }
        });
    }
    public void mostrarVentanaProfesores() {
        ProfesorCRUD ventana = new ProfesorCRUD();
        ventana.setVisible(true);
    }
}


