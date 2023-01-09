package twisk.Ecouteur;

import javafx.event.EventHandler;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import twisk.mondeIG.MondeIG;
import twisk.vues.VueEtapeIG;

public class EcouteurDragSetect implements EventHandler<MouseEvent> {
    private MondeIG monde ;
    private VueEtapeIG Act;

    public EcouteurDragSetect(MondeIG monde, VueEtapeIG act) {
        this.monde = monde;
        Act = act;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if(!monde.isSimualtionActive()) {
            final Dragboard dragBroard = Act.startDragAndDrop(TransferMode.MOVE);
            final ClipboardContent content = new ClipboardContent();
            content.putString(this.Act.getEtp().getIdentifiant());
            final WritableImage capture = Act.snapshot(null, null);
            content.putImage(capture);
            dragBroard.setContent(content);
            mouseEvent.consume();
        }
    }
}
