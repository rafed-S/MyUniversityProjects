package twisk.Ecouteur;

import javafx.event.EventHandler;
import javafx.scene.input.*;
import twisk.mondeIG.MondeIG;

public class EcouteurDragDropped implements EventHandler<DragEvent> {
    private MondeIG monde ;

    public EcouteurDragDropped(MondeIG monde) {
        this.monde = monde;
    }

    @Override
    public void handle(DragEvent dragEvent) {
        boolean success = false;
        try {
            double x = dragEvent.getX();
            double y = dragEvent.getY();
            final Dragboard dragBroard = dragEvent.getDragboard();

            monde.ChangePlace(dragBroard.getString(), x, y );
            success = true;
        } catch (Exception ex) {

            System.out.println("Erreur");
        } finally {
            dragEvent.setDropCompleted(success);
            dragEvent.consume();
            monde.notifierObservateurs();
        }
    }
}
