package iznimke;

import entiteti.Liga;
import javafx.scene.control.Alert;

public class NijeOdabranEntitetIznimka extends RuntimeException {
    public void izbaciGresku(String objekt) {
        if(objekt.equals("liga")){
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Nije odabrana liga");
            alert1.setHeaderText("Potrebno je odabrati ligu");
            alert1.setContentText("Molim odaberite ligu kojoj se zelite prikljuciti");
            alert1.showAndWait();
        }
        else if(objekt.equals("igrac")){
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Nije odabran student");
            alert1.setHeaderText("Potrebno je odabrati studenta");
            alert1.setContentText("Molim odaberite studenta kako bi izvrsili akciju");
            alert1.showAndWait();
        }
        else if(objekt.equals("korisnik")){
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Nije odabran korisnik");
            alert1.setHeaderText("Potrebno je odabrati korisnika");
            alert1.setContentText("Molim odaberite korisnika kako bi izvrsili akciju");
            alert1.showAndWait();
        }
        else if(objekt.equals("dodavanje")){
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Nije odabran student");
            alert1.setHeaderText("Potrebno je odabrati studenta");
            alert1.setContentText("Molim odaberite studenta kojeg zelite dodati u petorku");
            alert1.showAndWait();
        }
        else if(objekt.equals("micanje")){
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Nije odabran student");
            alert1.setHeaderText("Potrebno je odabrati studenta");
            alert1.setContentText("Molim odaberite studenta kojeg zelite maknuti iz petorke");
            alert1.showAndWait();
        }
        else if(objekt.equals("utakmica")){
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Nije odabrana utakmica");
            alert1.setHeaderText("Potrebno je odabrati utakmicu");
            alert1.setContentText("Molim odaberite utakmicu kako bi izvrsili akciju");
            alert1.showAndWait();
        }
    }
}