import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import juegos.flappy_bird.FlappyBird;
import juegos.ping_pong.PingPong;
import juegos.snake.MainSnake;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends Application implements ActionListener {

    @Override
    public void start(Stage primaryStage) {
        //Fondo
        Image imagenFondo = new Image("Img/Fondo.jpg");
        ImageView Fondo = new ImageView(imagenFondo);
        //Ajustar el tamaño del fondo a la ventana
        Fondo.setFitHeight(500);
        Fondo.setFitWidth(800);

        //Agregamos la imagen que contiene el titulo del programa
        Image imagenTitulo = new Image("Img/Titulo.png");
        ImageView Titulo = new ImageView(imagenTitulo);
        //Ajustar imagen del titulo
        Titulo.setX(200);
        Titulo.setY(100);
        //Acomodar el titulo con el fondo
        StackPane panelImagen = new StackPane();
        panelImagen.getChildren().addAll(Fondo,Titulo);
        //Cambiar tamaño del titulo
        Titulo.setFitHeight(200);
        Titulo.setFitWidth(400);

        //Botones que dirigiran a cada juego
        VBox contenedorVertical = new VBox();
        HBox contenedorHorizontal = new HBox();
        Button BFP = new Button("Flappy Bird");
        BFP.setId("flapiberd");
        BFP.setTranslateY(-150);
        //BFP.addActionListener(this);
        Button BPP = new Button("Ping-Pong");
        BPP.setId("pinpon");
        BPP.setTranslateY(-150);
        //BPP.setOnAction(new BPPButtonActionListener());
        Button BS = new Button("Snake");
        BS.setId("esneik");
        BS.setTranslateY(-150);
        //BS.setOnAction(new BSButtonActionListener());
        //Orden de los botones (como una tabla)
        HBox cajaBotones = new HBox(50, BFP, BPP, BS);
        cajaBotones.setAlignment(Pos.CENTER);

        //Musica de fondo para el menu
        /*Media media = new Media(new File("Img/Musica.mp3").toURI().toString());
        // Crea un objeto MediaPlayer a partir del objeto Media
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        // Crea un botón para reproducir el sonido
        Button bmusica = new Button("Play");
        bmusica.setOnAction(e -> {
            mediaPlayer.play();
        });
        // Crea un VBox para contener el botón
        VBox root = new VBox();
        root.getChildren().add(bmusica);*/

        //El setup de acomodo de la ventana
        BorderPane panelPrincipal = new BorderPane();
        panelPrincipal.setCenter(Fondo);
        panelPrincipal.setCenter(panelImagen);
        panelPrincipal.getChildren().addAll(Fondo,Titulo);
        panelPrincipal.setBottom(cajaBotones);

        // Acomodo de la ventana
        Scene ventana = new Scene(panelPrincipal, 800, 500);
        //Agregar los css para poder cambiar el estilo a los botones
        ventana.getStylesheets().add("CSS/FB.css");
        ventana.getStylesheets().add("CSS/PP.css");
        ventana.getStylesheets().add("CSS/S.css");
        //Nombre de la ventana
        primaryStage.setTitle("The Java Arcade");
        //Agregar icono
        Image icono = new Image("Img/Icon.png");
        primaryStage.getIcons().add(icono);
        primaryStage.setScene(ventana);
        //Mostrar la ventana
        primaryStage.show();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*if (e.getSource() == BFP) {
            EventQueue.invokeLater(MainSnake::new);
        } else if (e.getSource() == BPP) {
            new FlappyBird();
        } else if (e.getSource() == BS) {
            launch(PingPong.class);
        }*/
    }

    //Poder correr el 'popup' de la ventana
    public static void main(String[] args) {
        launch(args);
    }
}
