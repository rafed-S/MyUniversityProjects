package twisk.Ecouteur;

import javafx.event.EventHandler;
import javafx.scene.input.*;
import twisk.mondeIG.MondeIG;

public class EcouteurDragOver implements EventHandler<DragEvent> {
    private MondeIG monde ;

    public EcouteurDragOver(MondeIG monde) {
        this.monde = monde;
    }

    @Override
    public void handle(DragEvent event) {
        if (event.getGestureSource() != this &&
                event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();

    }
}
