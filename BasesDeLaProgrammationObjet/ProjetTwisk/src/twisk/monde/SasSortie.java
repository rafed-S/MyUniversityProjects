package twisk.monde;

public class SasSortie extends Activite{

    public SasSortie() {
        super("SASSORTIE");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Sortie : ");
        sb.append(" - ").append(this.nbSuccesseurs()).append(" Successeurs - ").append(this.gSucc.toString());
        return sb.toString();
    }
}
