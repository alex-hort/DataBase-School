import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Interfaz extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JButton agregarDatosButton;
    private JButton consultarDatosButton;
    private JButton actualizarDatosButton; // Nuevo botón "Actualizar"
    private JButton eliminarDatosButton; // Nuevo botón "Eliminar"
    private JButton salirButton;
    private escuela Escuela;

    public Interfaz() {
        // Configurar la interfaz y los componentes aquí
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Diseño vertical
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());
        panelBotones.setBackground(Color.RED);

        // Crear etiquetas para los campos de entrada
        JLabel label1 = new JLabel("ID:");
        JLabel label2 = new JLabel("Nombre:");
        JLabel label3 = new JLabel("Apellido:");
        JLabel label4 = new JLabel("Edad:");
        JLabel label5 = new JLabel("Dirección:");
        JLabel label6 = new JLabel("Teléfono:");

        // Crear campos de entrada de texto
        textField1 = new JTextField(15);
        textField2 = new JTextField(15);
        textField3 = new JTextField(15);
        textField4 = new JTextField(15);
        textField5 = new JTextField(15);
        textField6 = new JTextField(15);

        // Crear botones
        agregarDatosButton = new JButton("Agregar");
        consultarDatosButton = new JButton("Consultar");
        actualizarDatosButton = new JButton("Actualizar"); // Nuevo botón "Actualizar"
        eliminarDatosButton = new JButton("Eliminar"); // Nuevo botón "Eliminar"
        salirButton = new JButton("Salir");
        JButton vaciarCeldasButton = new JButton("Limpiar Celdas");
        // Crear el botón "Regresar"
        JButton regresarButton = new JButton("Regresar");

        panel.setBackground(Color.CYAN); // Color de fondo del panel principal
        panelBotones.setBackground(Color.PINK);


        // Cambiar el color de los botones
        agregarDatosButton.setBackground(Color.GREEN);
        consultarDatosButton.setBackground(Color.BLUE);
        actualizarDatosButton.setBackground(Color.ORANGE); // Naranja
        eliminarDatosButton.setBackground(Color.RED);
        salirButton.setBackground(Color.RED);

        // Instanciar la clase escuela para interactuar con la base de datos
        Escuela = new escuela();

        // Configurar el evento del botón "Agregar"
        agregarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener datos de los campos de entrada de texto
                String id = textField1.getText();
                String nom = textField2.getText();
                String ap = textField3.getText();
                String edad = textField4.getText();
                String dir = textField5.getText();
                String tel = textField6.getText();

                // Asignar datos a la instancia de la clase escuela
                Escuela.id = id;
                Escuela.nom = nom;
                Escuela.ap = ap;
                Escuela.edad = edad;
                Escuela.dir = dir;
                Escuela.tel = tel;

                // Llamar al método insertar de la clase escuela para agregar datos a la base de datos
                Escuela.insertar();

                // Limpiar campos de entrada de texto después de agregar datos
                limpiarCampos();
            }
        });

        //vaciar celdas cuando el usuario quiera
        vaciarCeldasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos(); // Llama a la función para limpiar los campos de entrada de texto
            }
        });


        // Configurar el evento del botón "Consultar"
        consultarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el ID ingresado por el usuario
                String id = textField1.getText();
                Escuela.id = id;

                // Llamar al método selecreg de la clase escuela para consultar datos
                Escuela.selecreg();

                // Actualizar los campos de entrada de texto con los datos consultados
                textField2.setText(Escuela.nom);
                textField3.setText(Escuela.ap);
                textField4.setText(Escuela.edad);
                textField5.setText(Escuela.dir);
                textField6.setText(Escuela.tel);
            }
        });

        // Configurar el evento del botón "Regresar"
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ocultar la interfaz de Estudiantes
                setVisible(false);

                // Mostrar el menú principal
                Menu menu = new Menu();
                menu.setVisible(true);
            }
        });

        // Configurar el evento del botón "Actualizar"
        actualizarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = textField1.getText();
                String nombre = textField2.getText();
                String apellido = textField3.getText();
                String edad = textField4.getText();
                String direccion = textField5.getText();
                String telefono = textField6.getText();

                // Verificar que se haya ingresado el ID y al menos un campo adicional para actualizar
                if (!id.isEmpty() && (!nombre.isEmpty() || !apellido.isEmpty() || !edad.isEmpty() || !direccion.isEmpty() || !telefono.isEmpty())) {
                    escuela miEstudiante = new escuela();
                    miEstudiante.id = id;
                    miEstudiante.nom = nombre;
                    miEstudiante.ap = apellido;
                    miEstudiante.edad = edad;
                    miEstudiante.dir = direccion;
                    miEstudiante.tel = telefono;

                    miEstudiante.actreg(); // Actualiza el registro
                    limpiarCampos(); // Puedes agregar esta línea para limpiar los campos después de la actualización
                } else {
                    JOptionPane.showMessageDialog(null, "Debes ingresar el ID y al menos un campo adicional para actualizar.");
                }
            }
        });


        // Configurar el evento del botón "Eliminar"
        eliminarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = textField1.getText();

                // Verificar que se haya ingresado el ID
                if (!id.isEmpty()) {
                    Escuela.elimgr(id);
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(null, "Debes ingresar el ID para eliminar el registro.");
                }
            }
        });

        // Configurar el evento del botón "Salir" para cerrar la aplicación
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Agregar componentes al panel en orden vertical
        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);
        panel.add(label3);
        panel.add(textField3);
        panel.add(label4);
        panel.add(textField4);
        panel.add(label5);
        panel.add(textField5);
        panel.add(label6);
        panel.add(textField6);
        panel.add(agregarDatosButton);
        panel.add(consultarDatosButton);
        panel.add(actualizarDatosButton); // Agregar el botón "Actualizar"
        panel.add(eliminarDatosButton); // Agregar el botón "Eliminar"
        panel.add(salirButton);          //boton de salir
        panel.add(vaciarCeldasButton); //boton de vaciar celdas
        // Agregar el botón "Regresar" al panel
        panel.add(regresarButton);


        // Agregar el panel al contenido de la ventana
        getContentPane().add(panel);

        // Configurar la acción al cerrar la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ajustar el tamaño de la ventana automáticamente
        pack();

        // Establecer el título de la ventana
        setTitle("Escuela");
    }

    // Función para limpiar campos de entrada de texto
    private void limpiarCampos() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        textField6.setText("");
    }


    // Función principal para ejecutar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Interfaz interfaz = new Interfaz();
                interfaz.setVisible(true);
            }
        });
    }

    public void mostrarVentanaEstudiantes() {
        Interfaz interfaz= new Interfaz();
        interfaz.setVisible(true);
    }
}

























