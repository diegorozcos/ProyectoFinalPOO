package juegos.flappy_bird;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Código referenciado del siguiente link y autor: https://www.geeksforgeeks.org/videos/flappy-bird-game-in-java-3svutw/
 * Autor: GeeksforGeeks
 */

public class FlappyBird implements ActionListener, KeyListener {

    public static void main(String[] args) {
        flappyBird = new FlappyBird();
    }

    public static FlappyBird flappyBird;
    public static BirdRenderer birdRenderer;
    public Rectangle pajaro;
    private int tamañoInicial;
    private int tamaño;
    public int ticks, movEjeY, puntos;
    public ArrayList<Rectangle> listaObstaculos;
    public Random random;
    public boolean juegoTerminado, juegoIniciado;
    public final int ANCHO_PANTALLA = 800, ALTO_PANTALLA = 600;
    private boolean superpoder = false;
    private boolean superpoderActivado = false;

    public FlappyBird() {
        JFrame jFrame = new JFrame();
        Timer timer = new Timer(20, this);
        birdRenderer = new BirdRenderer();
        random = new Random();
        jFrame.add(birdRenderer);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(ANCHO_PANTALLA, ALTO_PANTALLA);
        jFrame.addKeyListener(this);
        jFrame.setResizable(false);
        jFrame.setTitle("Flappy Bird");
        jFrame.setVisible(true);

        tamañoInicial = 20;

        pajaro = new Rectangle(ANCHO_PANTALLA / 2 - 10, ALTO_PANTALLA / 2 - 10, tamañoInicial, tamañoInicial);

        if (superpoder)
            tamaño = tamañoInicial / 2;
        else
            tamaño = tamañoInicial;

        listaObstaculos = new ArrayList<>();
        agregarObstaculo(true);
        agregarObstaculo(true);
        agregarObstaculo(true);
        agregarObstaculo(true);

        timer.start();
    }

    public void saltar() {
        if (juegoTerminado) {
            pajaro = new Rectangle(ANCHO_PANTALLA / 2 - 10, ALTO_PANTALLA / 2 - 10, tamañoInicial, tamañoInicial);
            listaObstaculos.clear();
            movEjeY = 0;
            puntos = 0;
            agregarObstaculo(true);
            agregarObstaculo(true);
            agregarObstaculo(true);
            agregarObstaculo(true);
            juegoTerminado = false;
        }

        if (!juegoIniciado) {
            juegoIniciado = true;
        } else if (!juegoTerminado) {
            juegoIniciado = true;
            if (movEjeY > 0) {
                movEjeY = 0;
            }
            movEjeY -= 10;
        }
    }

    public void agregarObstaculo(boolean juegoIniciado) {
        int espacio = 300;
        int ancho = 100;
        int alto = 50 + random.nextInt(300);

        if (juegoIniciado) {
            listaObstaculos.add(new Rectangle(ANCHO_PANTALLA + ancho + listaObstaculos.size() * 300, ALTO_PANTALLA - alto - 120, ancho, alto));
            listaObstaculos.add(new Rectangle(ANCHO_PANTALLA + ancho + (listaObstaculos.size() - 1) * 300, 0, ancho, ALTO_PANTALLA - alto - espacio));
        } else {
            listaObstaculos.add(new Rectangle(ANCHO_PANTALLA + ancho + listaObstaculos.size() * 300, ALTO_PANTALLA - alto - 120, ancho, alto));
            listaObstaculos.add(new Rectangle(ANCHO_PANTALLA + ancho + (listaObstaculos.size() - 1) * 300, 0, ancho, ALTO_PANTALLA - alto - espacio));
        }
    }

    public void pintar(Graphics g) {
        g.setColor(Color.orange);
        g.fillRect(0, 0, ANCHO_PANTALLA, ALTO_PANTALLA);

        g.setColor(Color.CYAN);
        g.fillRect(0, ALTO_PANTALLA - 120, ANCHO_PANTALLA, 120);

        g.setColor(Color.green);
        g.fillRect(0, ALTO_PANTALLA - 120, ANCHO_PANTALLA, 20);

        g.setColor(Color.red);
        g.fillRect(pajaro.x, pajaro.y, pajaro.width, pajaro.height);

        for (Rectangle obstaculo : listaObstaculos) {
            pintarObstaculo(g, obstaculo);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 50));

        if (!juegoIniciado) {
            g.drawString("¡Pulsa espacio para iniciar!", 75, ALTO_PANTALLA / 2 - 50);
        }

        if (juegoTerminado) {
            g.drawString("¡Juego terminado!", 100, ALTO_PANTALLA / 2 - 50);
        }

        if (!juegoTerminado && juegoIniciado) {
            g.drawString(String.valueOf(puntos), ANCHO_PANTALLA / 2 - 25, 100);
        }
    }

    public void pintarObstaculo(Graphics g, Rectangle obstaculo) {
        g.setColor(Color.green.darker());
        g.fillRect(obstaculo.x, obstaculo.y, obstaculo.width, obstaculo.height);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int velocidad = 10;
        ticks++;
        if (juegoIniciado) {
            for (int i = 0; i < listaObstaculos.size(); i++) {
                Rectangle obstaculo = listaObstaculos.get(i);
                obstaculo.x -= velocidad;
            }

            if (ticks % 2 == 0 && movEjeY < 15) {
                movEjeY += 2;
            }

            for (int i = 0; i < listaObstaculos.size(); i++) {
                Rectangle obstaculo = listaObstaculos.get(i);
                if (obstaculo.x + obstaculo.width < 0) {
                    listaObstaculos.remove(obstaculo);
                    if (obstaculo.y == 0) {
                        agregarObstaculo(false);
                    }
                }
            }

            pajaro.y += movEjeY;

            for (Rectangle obstaculo : listaObstaculos) {
                if (obstaculo.y == 0 && pajaro.x + pajaro.width / 2 > obstaculo.x + obstaculo.width / 2 - 10 && pajaro.x + pajaro.width / 2 < obstaculo.x + obstaculo.width / 2 + 10) {
                    puntos++;
                }
                if (obstaculo.intersects(pajaro)) {
                    juegoTerminado = true;
                    superpoderActivado = false;
                    if (pajaro.x <= obstaculo.x) {
                        pajaro.x = obstaculo.x - pajaro.width;
                    } else {
                        if (obstaculo.y != 0) {
                            pajaro.y = obstaculo.y - pajaro.height;
                        } else if (pajaro.y < obstaculo.height) {
                            pajaro.y = obstaculo.height;
                        }
                    }
                }
            }

            if (pajaro.y > ALTO_PANTALLA - 120 || pajaro.y < 0) {
                juegoTerminado = true;
                superpoderActivado = false;
            }

            if (pajaro.y + movEjeY >= ALTO_PANTALLA - 120) {
                pajaro.y = ALTO_PANTALLA - 120 - pajaro.height;
            }

            birdRenderer.repaint();
        }
    }

    public void activarSuperpoder() {
        if (!superpoderActivado) {
            superpoder = true;
            pajaro.setSize(tamañoInicial / 2, tamañoInicial / 2);
            superpoderActivado = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int saltar = e.getKeyCode();
        if (saltar == KeyEvent.VK_SPACE) {
            saltar();
        }
        int superpoderActivo = e.getKeyCode();
        if (superpoderActivo == KeyEvent.VK_A) {
            activarSuperpoder();
        }
        int superpoderDesactivado = e.getKeyCode();
        if (superpoderDesactivado == KeyEvent.VK_S) {
            superpoder = false;
            pajaro.setSize(tamañoInicial, tamañoInicial);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
