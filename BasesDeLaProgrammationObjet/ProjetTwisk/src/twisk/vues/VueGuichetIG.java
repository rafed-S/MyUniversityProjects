package twisk.vues;

import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import twisk.Ecouteur.EcouteurSelect;
import twisk.TailleComposants;


public class VueGuichetIG extends VueEtapeIG implements Observateur{
    static int a = 0;

    public VueGuichetIG(MondeIG monde, EtapeIG etp) {
        super(monde, etp);
        lb.setText(etp.getNom()+" / nb de Jetons : "+etp.getNbjetons() );
        HBox hb = new HBox();
        HBox  hb1 = new HBox();
        HBox  hb2 = new HBox();
        HBox  hb3 = new HBox();
        HBox  hb4 = new HBox();
        HBox  hb5 = new HBox();
        HBox  hb6 = new HBox();
        HBox  hb7 = new HBox();
        HBox  hb8 = new HBox();
        HBox  hb9 = new HBox();
        HBox  hb10 = new HBox();
        HBox  hb11 = new HBox();
        HBox  hb12 = new HBox();
        HBox  hb13 = new HBox();
        hb1.setPrefSize(30 ,10);
        hb2.setPrefSize(30,10);
        hb3.setPrefSize(30 ,10);
        hb4.setPrefSize(30,10);
        hb5.setPrefSize(30 ,10);
        hb6.setPrefSize(30,10);
        hb7.setPrefSize(30 ,10);
        hb8.setPrefSize(30,10);
        hb9.setPrefSize(30 ,10);
        hb10.setPrefSize(30,10);
        hb11.setPrefSize(30,10);
        hb12.setPrefSize(30,10);
        hb13.setPrefSize(30,10);
        Pane pane1 = new Pane();
        TailleComposants tt = TailleComposants.getInstance();
        this.setPrefSize(tt.getSizeGuichetHeight(),tt.getSizeGuichetWidth());
        hb.setPrefSize(tt.getSizeGuichetHeight() - 15,tt.getSizeGuichetWidth()-10);
        x = this.etp.getPosX()+tt.getSizeActiviteWidth()-30;
        y = this.etp.getPosY()+tt.getSizeActiviteHeight()+50;
        this.setLayoutX(this.etp.getPosX());
        this.setLayoutY(this.etp.getPosY());
        this.etp.ModifPntControl();
        if(a==0)
        {
            this.setLayoutX(this.etp.getPosX()+100);
            this.setLayoutY(this.etp.getPosY()+100);
            this.etp.ModifPntControl();
            a++;
        }
        hb.getChildren().addAll(hb1,hb2,hb3,hb4,hb5,hb6,hb7,hb8,hb9,hb10,hb11,hb12,hb13);
        Image image = new Image(getClass().getResourceAsStream("/image/testicone.png"));
        ImageView icon = new ImageView(image);
        Image image1 = new Image(getClass().getResourceAsStream("/image/images.png"));
        ImageView icon1 = new ImageView(image1);
        HBox hbtest = new HBox();
        if(monde.estEntree(etp)) {
            hbtest.getChildren().addAll(lb,icon);
            this.getChildren().addAll(hbtest, hb);
        }
        else this.getChildren().addAll(lb,hb);
        this.setOnMouseClicked(new EcouteurSelect(monde,this.etp));
        if(monde.EstActSelect(this.etp))
        {
            this.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 4;" + "-fx-border-insets: 0;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: #006633;"+"-fx-background-color: #E0E0E0");
            this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            hb1.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb2.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb3.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb4.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb5.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb6.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb7.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb8.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb9.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb10.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb11.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb12.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb13.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
        }else{
            this.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 4;" + "-fx-border-insets: 0;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: #006633;"+"-fx-background-color: #9AFF66");
            hb1.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb2.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb3.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb4.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb5.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb6.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb7.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb8.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb9.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb10.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb11.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb12.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
            hb13.setStyle("-fx-border-color: #006633; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
        }
    }

    @Override
    public void reagir() {}
}
