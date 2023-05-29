package stamps.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import stamps.models.Livres;
import stamps.models.Stamps;

public class CouvertureLivre implements Observateur{

    private Stamps stamps;
    private ImageView couvertureBouquin;
    private Livres bouquin;

    public CouvertureLivre(Stamps stamps, Livres bouquin){
        this.stamps = stamps;
        this.bouquin = bouquin;

        /*this.couvertureBouquin = new ImageView(new Image(bouquin.getResminYolu()));

        this.couvertureBouquin.setFitWidth(200);
        this.couvertureBouquin.setFitHeight(200);
        this.couvertureBouquin.setPreserveRatio(true);*/

        couvertureBouquin = createCouvertureBouquin();
        createContextMenu();

        couvertureBouquin.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY){
                stamps.gosterKiabi(bouquin);
            }
        });
    }

    private ImageView createCouvertureBouquin(){
        ImageView imageView = new ImageView(new Image(bouquin.getResminYolu()));
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private void createContextMenu(){
        MenuItem corbeille = new MenuItem("Corbeille");
        corbeille.setOnAction(event -> stamps.deleteBouquin(bouquin));

        ContextMenu onlem = new ContextMenu(corbeille);
        onlem.setOnShown(e -> showContextMenu(onlem));

        couvertureBouquin.setOnMousePressed(event ->{
            if (event.getButton() == MouseButton.SECONDARY){
                onlem.show(couvertureBouquin, event.getSceneX(), event.getSceneY());
            }
        });
    }

    private void showContextMenu(ContextMenu onlem){
        ImageView cokertme = (ImageView) onlem.getOwnerNode();
        double x = cokertme.getScene().getWindow().getX() + cokertme.getBoundsInParent().getMaxX();
        double y = cokertme.getScene().getWindow().getY() + cokertme.getBoundsInParent().getMaxY();
        onlem.show(cokertme, x, y);
    }

    public void isLarg(int larg){
        couvertureBouquin.setFitWidth(larg);
    }

    public void isHeight(int heigt){
        couvertureBouquin.setFitHeight(heigt);
    }

    public ImageView getCouvertureBouquin() {
        return couvertureBouquin;
    }

    public void setCouvertureBouquin(Livres bouquin) {
        couvertureBouquin = new ImageView(new Image(bouquin.getResminYolu()));

        couvertureBouquin.setFitWidth(200);
        couvertureBouquin.setFitHeight(200);
        couvertureBouquin.setPreserveRatio(true);
    }

    @Override
    public void reagir() {

    }
}
