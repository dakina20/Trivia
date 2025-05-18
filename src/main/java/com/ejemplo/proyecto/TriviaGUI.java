/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.ejemplo.proyecto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Trivia: ¿Qué tanto sabes? - Interfaz gráfica completa
 * Clase principal que configura toda la estructura de la interfaz gráfica.
 */
public class TriviaGUI extends JFrame {
    // Componentes principales
    private JLabel lblQuestion;
    private JLabel lblTimer;
    private JButton[] btnOptions;
    private JButton btnRetire;
    private JButton btnHalf, btnPhone, btnAudience;
    private JList<String> lstPrizes;
    private DefaultListModel<String> prizeModel;
    private Timer timer;

    // Temporizador inicial en segundos
    private int timeLeft = 30;

    public TriviaGUI() {
        super("Trivia: ¿Qué tanto sabes?");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        initUI();
    }

    /**
     * Inicializa la interfaz gráfica y sus paneles.
     */
    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        // Panel superior con pregunta y temporizador
        add(createTopPanel(), BorderLayout.NORTH);

        // Panel central con opciones de respuesta
        add(createCenterPanel(), BorderLayout.CENTER);

        // Panel lateral con escalera de premios
        add(createSidePanel(), BorderLayout.EAST);

        // Panel inferior con comodines y botón retirarse
        add(createBottomPanel(), BorderLayout.SOUTH);

        // Iniciar temporizador
        timer.start();
    }

    /**
     * Crea y devuelve el panel superior.
     */
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        lblQuestion = new JLabel("<html><b>Pregunta aparecerá aquí</b></html>");
        lblQuestion.setFont(new Font("SansSerif", Font.BOLD, 20));

        lblTimer = new JLabel(String.valueOf(timeLeft));
        lblTimer.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblTimer.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(lblQuestion, BorderLayout.CENTER);
        panel.add(lblTimer, BorderLayout.EAST);

        // Configurar temporizador Swing de 1 segundo
        timer = new Timer(1000, e -> {
            if (timeLeft > 0) {
                timeLeft--;
                lblTimer.setText(String.valueOf(timeLeft));
            } else {
                timer.stop();
                handleTimeout();
            }
        });

        return panel;
    }

    /**
     * Crea y devuelve el panel central con las opciones.
     */
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        btnOptions = new JButton[4];
        for (int i = 0; i < 4; i++) {
            btnOptions[i] = new JButton("Opción " + (char)('A' + i));
            btnOptions[i].setFont(new Font("SansSerif", Font.PLAIN, 16));
            btnOptions[i].addActionListener(this::handleAnswer);
            panel.add(btnOptions[i]);
        }
        return panel;
    }

    /**
     * Crea y devuelve el panel lateral con la escalera de premios.
     */
    private JScrollPane createSidePanel() {
        prizeModel = new DefaultListModel<>();
        String[] prizes = {
            "$100", "$200", "$300", "$500", "$1,000",
            "$2,000", "$4,000", "$8,000", "$16,000", "$32,000",
            "$64,000", "$125,000", "$250,000", "$500,000", "$1,000,000"
        };
        for (String prize : prizes) {
            prizeModel.addElement(prize);
        }
        lstPrizes = new JList<>(prizeModel);
        lstPrizes.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lstPrizes.setEnabled(false);
        lstPrizes.setSelectedIndex(0);

        JScrollPane scroll = new JScrollPane(lstPrizes);
        scroll.setPreferredSize(new Dimension(180, 0));
        return scroll;
    }

    /**
     * Crea y devuelve el panel inferior con comodines y botón retirarse.
     */
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel();

        btnHalf = new JButton("50:50");
        btnPhone = new JButton("Llamada");
        btnAudience = new JButton("Público");
        btnRetire = new JButton("Retirarse");

        btnHalf.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnPhone.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnAudience.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnRetire.setFont(new Font("SansSerif", Font.BOLD, 16));

        btnHalf.addActionListener(e -> useHalf());
        btnPhone.addActionListener(e -> usePhone());
        btnAudience.addActionListener(e -> useAudience());
        btnRetire.addActionListener(e -> retire());

        panel.add(btnHalf);
        panel.add(btnPhone);
        panel.add(btnAudience);
        panel.add(Box.createHorizontalStrut(50));
        panel.add(btnRetire);

        return panel;
    }

    /**
     * Maneja la selección de una respuesta.
     */
    private void handleAnswer(ActionEvent e) {
        timer.stop();
        JButton src = (JButton) e.getSource();
        // TODO: Validar respuesta real
        boolean correct = validateAnswer(src.getText());
        if (correct) {
            advancePrize();
            loadNextQuestion();
            resetTimer();
        } else {
            src.setBackground(Color.RED);
            endGame(false);
        }
    }

    /**
     * Lógica cuando se agota el tiempo.
     */
    private void handleTimeout() {
        JOptionPane.showMessageDialog(this, "¡Tiempo agotado!", "Timeout", JOptionPane.WARNING_MESSAGE);
        endGame(false);
    }

    /**
     * Valida si la respuesta es correcta (placeholder).
     */
    private boolean validateAnswer(String answer) {
        // TODO: conectar con la lógica de preguntas
        return false;
    }

    /**
     * Avanza en la escalera de premios.
     */
    private void advancePrize() {
        int idx = lstPrizes.getSelectedIndex();
        if (idx < prizeModel.getSize() - 1) {
            lstPrizes.setSelectedIndex(idx + 1);
        }
    }

    /**
     * Carga la siguiente pregunta en la interfaz.
     */
    private void loadNextQuestion() {
        // TODO: implementar carga dinámica de preguntas
        lblQuestion.setText("<html><b>Siguiente pregunta aquí</b></html>");
        for (JButton b : btnOptions) {
            b.setText("Opción nueva");
            b.setBackground(null);
            b.setEnabled(true);
        }
    }

    /**
     * Reinicia el temporizador.
     */
    private void resetTimer() {
        timeLeft = 30;
        lblTimer.setText(String.valueOf(timeLeft));
        timer.start();
    }

    /**
     * Comodín 50:50.
     */
    private void useHalf() {
        // TODO: eliminar dos opciones erróneas
        btnHalf.setEnabled(false);
    }

    /**
     * Comodín llamada.
     */
    private void usePhone() {
        // TODO: simular ayuda telefónica
        btnPhone.setEnabled(false);
    }

    /**
     * Comodín público.
     */
    private void useAudience() {
        // TODO: mostrar gráfico de votación pública
        btnAudience.setEnabled(false);
    }

    /**
     * Acción de retirarse.
     */
    private void retire() {
        timer.stop();
        String prize = prizeModel.getElementAt(lstPrizes.getSelectedIndex());
        JOptionPane.showMessageDialog(this, "Te retiras con: " + prize);
        System.exit(0);
    }

    /**
     * Finaliza el juego, indicando si el jugador ganó o perdió.
     */
    private void endGame(boolean victory) {
        String message = victory ? "¡Felicidades! Has ganado." : "Juego terminado. Mejor suerte la próxima.";
        JOptionPane.showMessageDialog(this, message);
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TriviaGUI().setVisible(true));
    }
}
