package twisk.vues;

import javafx.scene.text.Font;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import twisk.Ecouteur.EcouteurSelect;
import twisk.TailleComposants;


public class VueActiviteIG extends VueEtapeIG implements Observateur {

    public VueActiviteIG(MondeIG monde, EtapeIG etp) {
        super(monde, etp);
        lb.setText(etp.getNom()+" delai : "+etp.getDelai()+" Ecart : "+etp.getEcartTemp());
        lb.setFont(Font.font ("Verdana", 15));
        HBox hb = new HBox();
        Image image = new Image(getClass().getResourceAsStream("/image/testicone.png"));
        ImageView icon = new ImageView(image);
        Image image1 = new Image(getClass().getResourceAsStream("/image/images.png"));
        ImageView icon1 = new ImageView(image1);
        TailleComposants tt = TailleComposants.getInstance();
        this.setPrefSize(tt.getSizeActiviteHeight(),tt.getSizeActiviteWidth());
        hb.setPrefSize(tt.getSizeActiviteHeight()-50,tt.getSizeActiviteWidth()-20);
        x = this.etp.getPosX();
        y = this.etp.getPosY();
        this.setLayoutX(this.etp.getPosX());
        this.setLayoutY(this.etp.getPosY());
        this.etp.ModifPntControl();
        HBox hbtest = new HBox();
        int e = 1;
        if(monde.estEntree(etp) && monde.estSortie(etp) ) {
            hbtest.getChildren().addAll(lb,icon,icon1);
            this.getChildren().addAll(hbtest, hb);
        }
        else if(monde.estEntree(etp)) {
            hbtest.getChildren().addAll(lb,icon);
            this.getChildren().addAll(hbtest, hb);

        }
        else if (monde.estSortie(etp)) {
            hbtest.getChildren().addAll(lb,icon1);
            this.getChildren().addAll(hbtest,hb);
        }
        else this.getChildren().addAll(lb,hb);
        this.setOnMouseClicked(new EcouteurSelect(monde,this.etp));
        if(monde.EstActSelect(this.etp))
        {
            this.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 4;" + "-fx-border-insets: 0;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: blue;"+"-fx-background-color: #E0E0E0");
            hb.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 4;" + "-fx-border-insets: 0;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: blue;"+"-fx-background-color: #E0E0E0");
        }
        else
        {
            this.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 4;" + "-fx-border-insets: 0;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: blue;"+"-fx-background-color: #CCE5FF");
            hb.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 4;" + "-fx-border-insets: 0;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: blue;"+"-fx-background-color: #CCE5FF");
        }
    }

    //**----------------- Getters -----------------**//
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    //**----------------------------------**//
    @Override
    public void reagir() {}
}
