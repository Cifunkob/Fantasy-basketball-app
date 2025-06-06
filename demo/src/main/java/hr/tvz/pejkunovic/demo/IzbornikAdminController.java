package hr.tvz.pejkunovic.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class IzbornikAdminController {
        public static void pokreniAdmina() throws IOException {
            if (LoginScreenController.rola==1) {
                FXMLLoader fxmlLoader = new FXMLLoader(LoginScreenController.class.getResource("odabirPetorke.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 650, 750);
                LoginScreenController.getMainStage().setTitle("Odabir petorke");
                LoginScreenController.getMainStage().setScene(scene);
                LoginScreenController.getMainStage().show();
            }
            else {
                FXMLLoader fxmlLoader = new FXMLLoader(LoginScreenController.class.getResource("odabirPetorkeKorisnik.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 650, 750);
                LoginScreenController.getMainStage().setTitle("Odabir petorke");
                LoginScreenController.getMainStage().setScene(scene);
                LoginScreenController.getMainStage().show();
            }
        }

    public static void pokreniLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginScreenController.class.getResource("loginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 750);
        LoginScreenController.getMainStage().setTitle("Ekran za prijavu");
        LoginScreenController.getMainStage().setScene(scene);
        LoginScreenController.getMainStage().show();
    }
    public static void pokreniSimulator() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SimulatorUtakmiceController.class.getResource("simulatorUtakmice.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 750);
        LoginScreenController.getMainStage().setTitle("Simulator Utakmice");
        LoginScreenController.getMainStage().setScene(scene);
        LoginScreenController.getMainStage().show();
    }
    public static void pokreniPodatke() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SimulatorUtakmiceController.class.getResource("podaciKorisnika.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 750);
        LoginScreenController.getMainStage().setTitle("Podaci o korisniku");
        LoginScreenController.getMainStage().setScene(scene);
        LoginScreenController.getMainStage().show();
    }

    public static void pokreniInformacijeOStudentu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InformacijeStudentController.class.getResource("informacijeStudent.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 750);
        LoginScreenController.getMainStage().setTitle("Informacije o studentu");
        LoginScreenController.getMainStage().setScene(scene);
        LoginScreenController.getMainStage().show();
    }

    public static void pokreniOdabirLiga() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LeaderboardLigeController.class.getResource("ligaOdabir.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 750);
        LoginScreenController.getMainStage().setTitle("Pridruzi se ligi");
        LoginScreenController.getMainStage().setScene(scene);
        LoginScreenController.getMainStage().show();
    }

    public static void pokreniLeadearboardLige() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LeaderboardLigeController.class.getResource("leaderboardLige.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 750);
        LoginScreenController.getMainStage().setTitle("Tablica lige");
        LoginScreenController.getMainStage().setScene(scene);
        LoginScreenController.getMainStage().show();
    }

    public static void pokreniPopisUtakmica() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PopisUtakmicaController.class.getResource("popisUtakmica.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 750);
        LoginScreenController.getMainStage().setTitle("Popis utakmica");
        LoginScreenController.getMainStage().setScene(scene);
        LoginScreenController.getMainStage().show();
    }

    public static void pokreniDetaljeUtakmice() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DetaljiUtakmiceController.class.getResource("detaljiUtakmice.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 750);
        LoginScreenController.getMainStage().setTitle("Detalji utakmice");
        LoginScreenController.getMainStage().setScene(scene);
        LoginScreenController.getMainStage().show();
    }
    public static void pokreniBoxScoreIgraca() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BoxScoreIgracaController.class.getResource("boxScoreIgraca.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 750);
        LoginScreenController.getMainStage().setTitle("Statistike igraca u utakmici");
        LoginScreenController.getMainStage().setScene(scene);
        LoginScreenController.getMainStage().show();
    }

    public static void pokreniRegistraciju() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RegisterScreenController.class.getResource("registerScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 750);
        LoginScreenController.getMainStage().setTitle("Registriraj se");
        LoginScreenController.getMainStage().setScene(scene);
        LoginScreenController.getMainStage().show();
    }
}
