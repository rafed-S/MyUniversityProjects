package twisk.mondeIG;
import javafx.concurrent.Task;
import twisk.TailleComposants;
import twisk.exceptions.MondeException;
import twisk.exceptions.ParamException;
import twisk.exceptions.TwiskException;
import twisk.monde.*;
import twisk.outils.ClassLoaderPerso;
import twisk.outils.FabriqueIdentifiant;
import twisk.outils.FabriqueNumero;
import twisk.outils.GestionnaireThreads;
import twisk.simulation.Client;
import twisk.simulation.GestionnaireClients;
import twisk.simulation.Simulation;
import twisk.vues.Observateur;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;


public class MondeIG extends SujetObservé implements Iterable<EtapeIG>,Iterator <ArcIG>, Observateur{
    private HashMap<String, EtapeIG> etapes ;
    private ArrayList <ArcIG> arcs = new ArrayList<ArcIG>();
    private PointDeControleIG [] pntDeCntrl = new PointDeControleIG[2];
    private ArrayList<EtapeIG> actSelection = new ArrayList<EtapeIG>();
    private ArrayList<ArcIG> arcSelection = new ArrayList<ArcIG>();
    private ArrayList<String> keysEtp = new ArrayList<String>();
    private ArrayList<EtapeIG> entree = new ArrayList<EtapeIG>();
    private ArrayList<EtapeIG> sortie = new ArrayList<EtapeIG>();
    private ArrayList<Client> clients = new ArrayList<Client>();
    private CorrespondanceEtapes corres ;
    private int nbClients = 4;
    private  int i ;
    private int index ;
    private boolean full,s;
    private int x,y;
    private Simulation simulation;
    private boolean SimualtionActive = false;
    private int nb=45;
    private Object simulation1;
    private Class<?> load;

    public MondeIG() {
        super();
        etapes = new HashMap <String, EtapeIG> ();
        this.simulation = new Simulation();
        FabriqueIdentifiant fab = FabriqueIdentifiant.getInstance();
        String idf = fab.getIdentifiantEtape();
        String idf1 = fab.getIdentifiantEtape();
        TailleComposants tt = new TailleComposants();
        EtapeIG gui1 = new GuichetIG("Guichet", idf1, tt.getSizeActiviteHeight(),tt.getSizeActiviteWidth());
        EtapeIG act1 = new ActiviteIG("Swimming", idf, tt.getSizeActiviteHeight(),tt.getSizeActiviteWidth());
        this.etapes.put(idf1,gui1);
        this.etapes.put(idf,act1);
        this.keysEtp.add(idf1);
        this.keysEtp.add(idf);
        i=1;
        index = 0;
        full = false;
        x=0;y=0;
        keysEtp.removeAll(keysEtp);
        keysEtp.addAll(this.etapes.keySet());
    }

    //**----------------- Getters -----------------**//

    public EtapeIG getEtape(Client cl)
    {
        return corres.get(cl.getEtape());
    }
    public int getNbrEtapes()
    {
        return etapes.size();
    }
    public int getNbrArcs()
    {
        return arcs.size();
    }
    public EtapeIG getEtapes(String idf)
    {
        return this.etapes.get(idf);
    }
    public PointDeControleIG getPntDeCntrl(int i) {
        return pntDeCntrl[i];
    }
    public ArcIG getArcs(int i) {
        return arcs.get(i);
    }
    public String getKey(int i)
    {
        return keysEtp.get(i);
    }
    public int getNbrEtpSelect(){return this.actSelection.size();}
    public EtapeIG getActSelect(int i)
    {
        return actSelection.get(0);
    }
    public double getXClients(Client client)
    {
        EtapeIG etpIG = corres.get(client.getEtape());
        if(etpIG !=null) {
            int nb1 = nb;
            nb = nb + 15;
            return etpIG.getPosX() + 30 + nb1;
        }
        else
            return 0;
    }
    public double getYClients(Client client)
    {
        EtapeIG etpIG = corres.get(client.getEtape());
        if(etpIG != null)
            return etpIG.getPosY()+50;
        return 0;
    }

    //**----------------- Setters -----------------**//

    public void setNbClients(String nbClients) throws ParamException {
        int number = 0;
        try {
            number = Integer.parseInt(nbClients);
        } catch (NumberFormatException ex) {
            throw new ParamException("Erreur au niveau des parametres / param invalide");
        }
        if (number > 0 && number < 50) this.nbClients = number;
        else{
            throw new ParamException("on veut un numero qui est entre 0 et 50");
        }
    }
    public void setFull(boolean full) {
        this.full = full;
    }
    public void setNb(int nb) {
        this.nb = nb;
    }
    public void setSimualtionActive(boolean simualtionActive) {
        SimualtionActive = simualtionActive;
    }
    public void stopSimulation()
    {
        GestionnaireThreads.getInstance().detruireTout();
        notifierObservateurs();
    }

    //**----------------------------------**//

    public void ajouter(String type)
    {
        FabriqueIdentifiant fab = FabriqueIdentifiant.getInstance();
        String idf = fab.getIdentifiantEtape();
        EtapeIG etp;
        if(type.equals("Activité")) {
            TailleComposants tt = new TailleComposants();
            etp = new ActiviteIG("Act"+i, idf, tt.getSizeActiviteHeight(), tt.getSizeActiviteWidth());
            Random rand = new Random();
            x = (50+rand.nextInt(800));
            y = (50+rand.nextInt(600));
            etp.setPosX(x);
            etp.setPosY(y);
            etp.ModifPntControl();
            i++;
            x = x+tt.getSizeActiviteWidth()+20;
            y = y+tt.getSizeActiviteWidth()+20;
            this.etapes.put(idf,etp);
            keysEtp.removeAll(keysEtp);
            keysEtp.addAll(this.etapes.keySet());
        }
        else if(type.equals("Guichet")) {
            TailleComposants tt1 = new TailleComposants();
            etp = new GuichetIG("Guichet "+i, idf, tt1.getSizeActiviteHeight(), tt1.getSizeActiviteWidth());
            Random rand = new Random();
            x = (50+rand.nextInt(800));
            y = (50+rand.nextInt(600));
            etp.setPosX(x);
            etp.setPosY(y);
            etp.ModifPntControl();
            i++;
            x = x+tt1.getSizeActiviteWidth()+20;
            y = y+tt1.getSizeActiviteWidth()+20;
            this.etapes.put(idf,etp);
            keysEtp.removeAll(keysEtp);
            keysEtp.addAll(this.etapes.keySet());
        }
        notifierObservateurs();
    }
    @Override
    public Iterator<EtapeIG> iterator() {
        return this.etapes.values().iterator();
    }
    public void ajouter(PointDeControleIG p1, PointDeControleIG p2) throws TwiskException {
        String s1 = p1.getEtp().getIdentifiant();
        String s2 = p2.getEtp().getIdentifiant();
        String s3 = p1.getIdentif();
        String s4 = p2.getIdentif();
        boolean c = false;
        boolean d = false;
        boolean e = false;
        if(p1.getEtp().estAccessibleDepuis(p2.getEtp())) {
            e = true;
            pntDeCntrl[0] = null;
            pntDeCntrl[1] = null;
            throw new TwiskException("ces deux etapes sont deja accessible");
        }
            if(!s1.equals(s2) && !e) {
            for (ArcIG arcIG : arcs) {
                if (s1.equals(arcIG.getP1().getEtp().getIdentifiant()) && s2.equals(arcIG.getP2().getEtp().getIdentifiant())) {
                    c = true;
                }
                else if (s2.equals(arcIG.getP1().getEtp().getIdentifiant()) && s1.equals(arcIG.getP2().getEtp().getIdentifiant())) {
                    c = true;
                }
                else if (s3.equals(arcIG.getP1().getIdentif()) || s4.equals(arcIG.getP2().getIdentif())) {
                    d = true;
                }
                else if (s4.equals(arcIG.getP1().getIdentif()) || s3.equals(arcIG.getP2().getIdentif())) {
                    d = true;
                }
            }
            if(!c && !d && !e) {
                ArcIG arc = new ArcIG(pntDeCntrl[0], pntDeCntrl[1]);
                this.arcs.add(arc);
                this.etapes.get(s1).addSucc(this.etapes.get(s2));
                pntDeCntrl[0] = null;
                pntDeCntrl[1] = null;
                notifierObservateurs();
            }
        }else{
            throw new TwiskException("impossible de faire un arc entre de point de controle de meme etape");
        }
        if(c) {
            s=true;
            c = false;
            throw new TwiskException("Il ya deja un arc entre ses deux etapes");
        }
        if(d) {
            s=true;
            d = false;
            throw new TwiskException("Ce point est deja utilisé pour un arc");
        }
        full = false;
    }
    public void ajouterPntDeCntrl(PointDeControleIG p){
        if(pntDeCntrl[0] !=null) {
            if (pntDeCntrl[1] != null) {
                pntDeCntrl[1] = null;
                pntDeCntrl[0] = p;
            }else{
                pntDeCntrl[1] = p;
                full = true;
            }
        }else{
            pntDeCntrl[0] = p;
        }
    }
    @Override
    public boolean hasNext() {
        return index < this.getNbrArcs();
    }
    @Override
    public ArcIG next() {
        int g = index;
        index++;
        return arcs.get(g);
    }
    public boolean isFull() {
        return full;
    }
    public void resetpnt1(){this.pntDeCntrl[0]= null;}
    public void resetpnt2(){this.pntDeCntrl[1] = null;}
    public void AjoutSelect(EtapeIG act){
        int cpt=0;
        for(EtapeIG act1 : actSelection)
        {
            if(act1.getIdentifiant().equals(act.getIdentifiant()))
                cpt++;
        }
        if (cpt == 0) {
            this.actSelection.add(act);
            this.notifierObservateurs();
        }
        else this.SuppSelect(act);
    }
    public void SuppSelect(EtapeIG act){
        actSelection.removeIf(act1 -> act1.getIdentifiant().equals(act.getIdentifiant()));
        this.notifierObservateurs();
    }
    public boolean EstActSelect(EtapeIG act)
    {
        boolean cpt=false;
        for(EtapeIG act1 : actSelection)
        {
            if (act1.getIdentifiant().equals(act.getIdentifiant())) {
                cpt = true;
                break;
            }
        }
        return cpt;
    }
    public void SuppSelect(){
        ArrayList<String> idfEtp = new ArrayList<String>();
        for(EtapeIG act1 : actSelection) idfEtp.add(act1.getIdentifiant());
        actSelection.removeAll(actSelection);
        int d = 0;
        for (String s : idfEtp) {

            arcs.removeIf(arc1 -> arc1.getP1().getEtp().getIdentifiant().equals(s));

        }
        for (String s : idfEtp) {
            arcs.removeIf(arc1 -> arc1.getP2().getEtp().getIdentifiant().equals(s));
        }
        for (String s : idfEtp) {
            etapes.remove(s);
        }
        keysEtp.removeAll(keysEtp);
        keysEtp.addAll(this.etapes.keySet());
        notifierObservateurs();
    }
    public void RenommerEtp(String nom){
        this.actSelection.get(0).setNom(nom);
        String idf = this.actSelection.get(0).getIdentifiant();
        for(int i=0;i<this.getNbrEtapes();i++)
        {
            if(this.etapes.get(this.keysEtp.get(i)).equals(idf))
                this.etapes.get(this.keysEtp.get(i)).setNom(nom);
        }
        notifierObservateurs();
    }
    public void AjoutSelectArc(ArcIG arc,String p1,String p2){
        boolean b = false;
        for(ArcIG arc2 : arcSelection)
        {
            if(p1.equals(arc2.getP1().getIdentif()) && p2.equals(arc2.getP2().getIdentif())  )
            {
                this.arcSelection.remove(arc2);
                b = true;
                break;
            }
        }
        if(!b) {
            for (ArcIG arc1 : arcs) {
                if (p1.equals(arc1.getP1().getIdentif()) && p2.equals(arc1.getP2().getIdentif())) {
                    this.arcSelection.add(arc1);
                    break;
                }
            }

        }
        this.notifierObservateurs();
    }
    public boolean EstArcSelect(ArcIG arc)
    {
        boolean cpt=false;
        for(ArcIG arc1 : arcSelection)
        {
            if (arc1.getP1().getIdentif().equals(arc.getP1().getIdentif()) && arc1.getP2().getIdentif().equals(arc.getP2().getIdentif())) {
                if(!cpt) cpt = true;
            }
        }
        return cpt;
    }
    public void SuppSelectArc(){
        for(ArcIG aa : arcs)
        {
            if(this.EstArcSelect(aa))
            {
                this.etapes.get(aa.getP1().getIdentifEtp()).deleteSucc( this.etapes.get(aa.getP2().getIdentifEtp()));
            }
        }
        arcs.removeIf(this::EstArcSelect);
        arcSelection.removeAll(arcSelection);
        notifierObservateurs();
    }
    public void DeselectElement(){
        this.arcSelection.removeAll(this.arcSelection);
        this.actSelection.removeAll(this.actSelection);
        notifierObservateurs();
    }
    public void ajouterEntree()
    {
        ArrayList<String> idf = new ArrayList<String>();
        int i=0;
        for(EtapeIG etp : entree)
        {
            for(EtapeIG actselect : actSelection)
            {
                if(actselect.getIdentifiant().equals(etp.getIdentifiant())) {
                    if(i==0) {
                        idf.add(etp.getIdentifiant());
                        i++;
                    }
                }
            }
            i=0;
        }
        entree.addAll(actSelection);
        for (String s : idf) {
            entree.removeIf(etp1 -> etp1.getIdentifiant().equals(s));
        }
        notifierObservateurs();
    }
    public void ajouterSortie() throws ParamException {
        ArrayList<String> idf = new ArrayList<String>();
        int i=0;
        for(EtapeIG etp : sortie)
        {
            for(EtapeIG actselect : actSelection)
            {
                if(actselect.getIdentifiant().equals(etp.getIdentifiant())) {
                    if(i==0) {
                        idf.add(etp.getIdentifiant());
                        i++;
                    }
                }
            }
            i=0;
        }
        for(EtapeIG etpselect : actSelection)
        {
            if(!etpselect.estUnGuichet())
            sortie.add(etpselect);
            else
                throw new ParamException("Une guichet ne peut pas etre une sortie du monde");
        }
        for (String s : idf) {
            sortie.removeIf(etp1 -> etp1.getIdentifiant().equals(s));
        }
        notifierObservateurs();
    }
    public boolean estEntree(EtapeIG etp){
        boolean cpt = false;
        for (EtapeIG etpp : entree) {
            if(etpp.getIdentifiant().equals(etp.getIdentifiant()))
            {
                cpt = true;
                break;
            }
        }
        return cpt;
    }
    public boolean estSortie(EtapeIG etp){
        boolean cpt = false;
        for (EtapeIG etpp : sortie) {
            if(etpp.getIdentifiant().equals(etp.getIdentifiant()))
            {
                cpt = true;
                break;
            }
        }
        return cpt;
    }
    public void ChangeDelai(String delai) throws ParamException {
        double number = 0;
        try{
            number = Double.parseDouble(delai);
        }
        catch (NumberFormatException ex){
            throw new ParamException("Erreur au niveau des parametres / param invalide");
        }
        if(number > 0 && number > this.actSelection.get(0).getEcartTemp()) {
            this.actSelection.get(0).setDelai(number);
            String idf = this.actSelection.get(0).getIdentifiant();
            for (int i = 0; i < this.getNbrEtapes(); i++) {
                if (this.etapes.get(this.keysEtp.get(i)).equals(idf))
                    this.etapes.get(this.keysEtp.get(i)).setDelai(number);
            }
            notifierObservateurs();

        }
        else if(number <=0)
        {
            throw new ParamException("Erreur au niveau des parametres / valeur inferieur à 0");
        }
        else
        {
            throw new ParamException("Le delai doit obligatoirement etre supérieur à l'Ecart temps ");
        }
    }
    public void ChangeEcart(String ecart) throws ParamException {
        double number = 0;
        try {
            number = Double.parseDouble(ecart);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        if (number > 0 && number < this.actSelection.get(0).getDelai()) {
            if (!this.actSelection.get(0).estUnGuichet()) {
                this.actSelection.get(0).setEcartTemp(number);
                String idf = this.actSelection.get(0).getIdentifiant();
                for (int i = 0; i < this.getNbrEtapes(); i++) {
                    if (this.etapes.get(this.keysEtp.get(i)).equals(idf))
                        this.etapes.get(this.keysEtp.get(i)).setEcartTemp(number);
                }
                notifierObservateurs();
            } else if (number <= 0) {
                throw new ParamException("Ecart temps doit etre supp à 0");
            }

            else if(number>= this.actSelection.get(0).getDelai()){
                throw new ParamException("Ecart type doit etre inferieur au delais");

            }

        }
    }
    public void ChangenbJetons(String nbjetons) throws ParamException {
        int number = 0;
        try{
            number = Integer.parseInt(nbjetons);
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
        if(number > 0) {
            if(this.actSelection.get(0).estUnGuichet()) {
                this.actSelection.get(0).setNbjetons(number);
                String idf = this.actSelection.get(0).getIdentifiant();
                for (int i = 0; i < this.getNbrEtapes(); i++) {
                    if (this.etapes.get(this.keysEtp.get(i)).equals(idf))
                        this.etapes.get(this.keysEtp.get(i)).setNbjetons(number);
                }
            }
            else
            {
                throw new ParamException("Erreur, veuillez selectionner un guichet ");

            }

            notifierObservateurs();
        }
        else
        {
            throw new ParamException("Erreur au niveau des parametres");
        }
    }
    public void ChangePlace(String key ,double x, double y) {
        etapes.get(key).setPosX(x);
        etapes.get(key).setPosY(y);
        notifierObservateurs();
    }
    public boolean isS() {
        return s;
    }
    public void deselectEtapes()
    {
        actSelection.removeAll(actSelection);
        this.notifierObservateurs();
    }
   public void simuler() throws MondeException
    {
        verifierMondeIG();
        Monde m = creerMonde();

        ClassLoaderPerso c = new ClassLoaderPerso(this.getClass().getClassLoader());
        try {
            load = c.loadClass("twisk.simulation.Simulation");
            simulation1 = load.getDeclaredConstructor().newInstance();
            Method methdSetNbClients = load.getDeclaredMethod("setNbClients", int.class) ;
            methdSetNbClients.invoke(simulation1,this.nbClients);
            Method methdSetAddObserv = load.getMethod("ajouterObservateur", twisk.vues.Observateur.class) ;
            methdSetAddObserv.invoke(simulation1,this);
            Method methodSimuler = load.getDeclaredMethod("simuler", twisk.monde.Monde.class) ;
            Task<Void> task = new Task<Void>(){
                @Override
                protected Void call() throws Exception{
                    try{
                        SimualtionActive = true;
                        methodSimuler.invoke(simulation1,m) ;
                        Thread.sleep(10);
                        SimualtionActive = false;
                        notifierObservateurs();
                    }catch(InterruptedException e){
                        SimualtionActive = false;

                    }
                    return null;
                }
            };
            GestionnaireThreads.getInstance().lancer(task);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        FabriqueNumero s = FabriqueNumero.getInstance();
        s.reset();
    }

    private void verifierMondeIG() throws MondeException
    {
          if(entree.isEmpty()) throw new MondeException("c'est un monde sans Entree");
          if(sortie.isEmpty()) throw new MondeException("c'est un monde sans sortie");

          for(int i=0; i<etapes.size();i++)
          {
               if(etapes.get(keysEtp.get(i)).estUnGuichet())
               {
                   if(etapes.get(keysEtp.get(i)).succ.size() == 1) {
                       if (!etapes.get(keysEtp.get(i)).succ.get(0).estUneActivite()) {
                           throw new MondeException("un guichet est suivi par un guichet, ce qui est pas correct");
                       }
                   }
                   else
                   {
                       throw new MondeException("un guichet est n'est pas suivi par une actReistreinte");
                   }
               }

          }
          boolean cheminSortie= false;
        for(int i=0; i<etapes.size();i++)
        {
                EtapeIG etp = etapes.get(this.keysEtp.get(i));
                cheminSortie= checkCheminSortie(etp);
                if(!cheminSortie)
                {
                    throw new MondeException("L'etape "+etp.getNom()+" n'a pas un chemin vers la sortie");
                }
        }
    }
    private boolean checkCheminSortie(EtapeIG aa)
    {
        boolean trouve =false;

        if(aa.succ.size() == 0)
        {
            for (int j = 0; j < sortie.size(); j++) {
                if (sortie.get(j).getIdentifiant().equals(aa.getIdentifiant())) {
                    trouve = true;
                    break;
                }
            }
            return trouve;
        }
        else {
            for (int j = 0; j < aa.succ.size(); j++) {
                EtapeIG next = aa.succ.get(j);
                trouve = this.checkCheminSortie(next);
                if(trouve) return trouve;
            }
        }
        return trouve;
    }
    private Monde creerMonde()
    {
        FabriqueNumero.getInstance().reset();

        Monde monde1 = new Monde();
        corres = new CorrespondanceEtapes();
        Etape [] etps = new Etape[etapes.size()];
        for(int i=0;i<etapes.size();i++) {
            EtapeIG aa = etapes.get(keysEtp.get(i));
            if (aa.estUnGuichet()) {
                 etps[i] = new Guichet(aa.getNom(),aa.getNbjetons());
                corres.ajouter(aa, etps[i]);
            } else if (aa.estUneActivite() || aa.estUneActiviteRestreinte()) {
                Etape act;
                if (aa.estUneActivite()) {
                    etps[i] = new Activite(aa.getNom(),(int)aa.getDelai(),(int)aa.getEcartTemp());
                    corres.ajouter(aa, etps[i]);

                } else {
                    etps[i] = new ActiviteRestreinte(aa.getNom());
                    corres.ajouter(aa, etps[i]);
                }
            }
        }
         //Gestion des succ
        for(int i=0;i<etapes.size();i++) {
            EtapeIG aa = etapes.get(keysEtp.get(i));
            Etape a = corres.get(aa);
                if(aa.succSize()>0)
                {
                    for(int j=0;j<aa.succSize();j++)
                    {
                        EtapeIG etapeSucc = aa.getSucc().get(j);
                        a.ajouterSuccesseur(corres.get(etapeSucc));
                    }
                }
        }
            for(int j=0;j<etapes.size();j++) {
                monde1.ajouter(corres.get(etapes.get(keysEtp.get(j))));
            }
        //Gestion entree sortie
    for(Etape aa : etps) {
        for (int j = 0; j < entree.size(); j++) {
        if (entree.get(j).getIdentifiant().equals(corres.get(aa).getIdentifiant())) {
            monde1.aCommeEntree(aa);
        }
    }
        for (int j = 0; j < sortie.size(); j++) {
            if (sortie.get(j).getIdentifiant().equals(corres.get(aa).getIdentifiant())) {
              monde1.aCommeSortie(aa);
            }
        }
    }
        return monde1;
    }
    @Override
    public void reagir() {
            notifierObservateurs();
    }
    public boolean isSimualtionActive() {
        return SimualtionActive;
    }
    public Iterator<Client> IteratorClient()
    {
        GestionnaireClients gs=null;
        try {
            Method methdIterator= load.getDeclaredMethod("getGs") ;
            gs = (GestionnaireClients) methdIterator.invoke(simulation1);
        } catch ( NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return gs.iterator();
    }

    //**----------------- sauvegarder et charger -----------------**//

    /*
    public void sauvegarder(String s)
    {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        //Gson gson = new Gson();
        try {
            Writer writer = new FileWriter("TestSauvegarde.ser");
            gson.toJson(this.etapes,writer);
            writer.close();
            System.out.println("[[ sauvegarder ca marche ]]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 */
/*
    public void charger(String nomFichier) {
        ArrayList<EtapeIG> ArrEtap = new ArrayList<EtapeIG>();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(nomFichier));
            ArrEtap = ((ArrayList)(gson.fromJson(reader,ArrEtap.getClass())));
            System.out.println(ArrEtap.size());
            System.out.println("[[ load ca marche ]]");
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}
