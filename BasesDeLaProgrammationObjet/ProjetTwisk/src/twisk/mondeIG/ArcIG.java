package twisk.mondeIG;

public class ArcIG {
    private PointDeControleIG p1 ;
    private PointDeControleIG p2 ;

    public ArcIG(PointDeControleIG p1, PointDeControleIG p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    //**----------------- Getters -----------------**//
    public PointDeControleIG getP1() {
        return p1;
    }
    public PointDeControleIG getP2() {
        return p2;
    }
}
