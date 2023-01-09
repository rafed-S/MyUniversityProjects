package twisk.vues;

import twisk.mondeIG.ArcIG;
import twisk.mondeIG.MondeIG;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import twisk.Ecouteur.EcouteurSelectArc;

public class VueArcIG extends Pane implements Observateur {
    private Line line = new Line();
    private Polyline p = new Polyline();
    private ArcIG arc;
    private MondeIG monde;

    public VueArcIG(ArcIG arc1, MondeIG monde) {
        this.arc = arc1;
        this.monde = monde;
        double startX = arc.getP1().getX();
        double startY = arc.getP1().getY();
        double endX = arc.getP2().getX();
        double endY = arc.getP2().getY();
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
        line.setStrokeWidth(4);
        this.setPickOnBounds(false);
        if(monde.EstArcSelect(this.arc))
        {
            line.setStroke(Color.rgb(211, 88, 89, 1.0));
            p.setStyle("-fx-fill:#D35859");
        }
        else
        {
            p.setStyle("-fx-fill:#0f0f0f");
        }
        this.setOnMouseClicked(new EcouteurSelectArc(monde,this.arc,this.arc.getP1().getIdentif(),this.arc.getP2().getIdentif()));
        double size = 17.0;
        double angle = Math.atan2((endY - startY), (endX - startX)) - Math.PI / 2.0;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * size + endX;
        double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * size + endY;
        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * size + endX;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * size + endY;
        p.strokeProperty().bind(p.fillProperty());
        p.getPoints().addAll(x1, y1, x2, y2, endX, endY, x1, y1);
        getChildren().addAll(p,line);
    }

    @Override
    public void reagir() {}
}
