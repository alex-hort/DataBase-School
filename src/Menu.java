import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private JPanel cards;
    private CardLayout cardLayout;
    private Interfaz interfaz = new Interfaz();
    private CrudCurso crudCurso = new CrudCurso();
    private ProfesorCRUD profesorCRUD = new ProfesorCRUD();
    private JLabel backgroundImageLabel; // JLabel para la imagen de fondo
    private Timer animationTimer;
    private int currentFrame = 0;


    public Menu() {
        setTitle("Menu Escuela");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crea un panel que utilizará CardLayout para administrar las ventanas
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // Crea y agrega el menú principal como una ventana
        JPanel menuPanel = createMenuPanel();
        cards.add(menuPanel, "menu");

        // Establece el panel de "menu" como el panel inicial visible
        cardLayout.show(cards, "menu");

        getContentPane().add(cards);
        pack();
        setLocationRelativeTo(null);


        // Configura la imagen de fondo (imagen GIF)
            ImageIcon backgroundImage = new ImageIcon("/Users/alexishorteales/IdeaProjects/escuela1/8GfW.gif");

        backgroundImageLabel = new JLabel(backgroundImage);

        // Configura un temporizador para animar la imagen de fondo
        Timer timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Avanza un fotograma en la animación del GIF
                ((ImageIcon) backgroundImageLabel.getIcon()).getImage().flush();
                backgroundImageLabel.repaint();
            }
        });
        timer.start();

        // Agrega el JLabel de la imagen de fondo al panel principal
        getContentPane().add(backgroundImageLabel);
        backgroundImageLabel.setBounds(0, 0, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());

        animationTimer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Añade un nuevo fotograma del GIF
                addNextFrameToGif();
                repaint();

                // Si has alcanzado el último fotograma, detén la animación
                if (currentFrame == getMaxFrame()) {
                    animationTimer.stop();
                }
            }
        });

        // Inicia la animación del GIF gradualmente
        animationTimer.start();

    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout());
        menuPanel.setBackground(Color.YELLOW);

        Font timesNewRomanFont = new Font("Times New Roman", Font.PLAIN, 35); // Cambia el tamaño de fuente si es necesario

        JButton botonEstudiantes = new JButton("Estudiantes");
        botonEstudiantes.setFont(timesNewRomanFont); // Aplicar el tipo de letra a este botón

        JButton botonCurso = new JButton("Cursos");
        botonCurso.setFont(timesNewRomanFont); // Aplicar el tipo de letra a este botón

        JButton botonProfesor = new JButton("Profesores");
        botonProfesor.setFont(timesNewRomanFont); // Aplicar el tipo de letra a este botón

        JButton botonSalir = new JButton("Salir");
        botonSalir.setFont(timesNewRomanFont); // Aplicar el tipo de letra a este botón

        botonEstudiantes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (interfaz.isVisible()) {
                    interfaz.setVisible(false); // Oculta la ventana si está abierta
                } else {
                    cardLayout.show(cards, "estudiantes");
                    interfaz.mostrarVentanaEstudiantes();
                }
            }
        });

        botonCurso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (crudCurso.isVisible()) {
                    crudCurso.setVisible(false); // Oculta la ventana si está abierta
                } else {
                    cardLayout.show(cards, "cursos");
                    crudCurso.mostrarVentanaCursos();
                }
            }
        });

        botonProfesor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (profesorCRUD.isVisible()) {
                    profesorCRUD.setVisible(false); // Oculta la ventana si está abierta
                } else {
                    cardLayout.show(cards, "profesores");
                    profesorCRUD.mostrarVentanaProfesores();
                }
            }
        });

        botonSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuPanel.add(botonEstudiantes);
        menuPanel.add(botonCurso);
        menuPanel.add(botonProfesor);
        menuPanel.add(botonSalir);

        return menuPanel;
    }

    private void addNextFrameToGif() {
        if (currentFrame <= getMaxFrame()) {
            ImageIcon backgroundImage = new ImageIcon("/Users/alexishorteales/IdeaProjects/escuela1/8GfW.gif");
            Image image = backgroundImage.getImage();
            backgroundImageLabel.setIcon(new ImageIcon(image.getScaledInstance(backgroundImage.getIconWidth(), backgroundImage.getIconHeight(), Image.SCALE_DEFAULT)));
            currentFrame++;
        }
    }

    // Método para obtener el número máximo de fotogramas en el GIF
    private int getMaxFrame() {
        // Reemplaza con la cantidad total de fotogramas en tu GIF
        return 2; // Por ejemplo, si tienes 10 fotogramas en tu GIF
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Menu menu = new Menu();
                menu.setVisible(true);
            }
        });
    }
}








