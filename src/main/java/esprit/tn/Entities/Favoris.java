package esprit.tn.Entities;

public class Favoris {

    private int img;
    private String nom , prenom, region , description,nbrfois,idSeek;

    public Favoris(String idSeek,int img, String nom, String prenom, String region, String description, String nbrfois) {
        this.idSeek = idSeek;
        this.img = img;
        this.nom = nom;
        this.prenom = prenom;
        this.region = region;
        this.description = description;
        this.nbrfois = nbrfois;

    }

    public String getIdSeek() {

        return idSeek;
    }

    public void setIdSeek(String idSeek) {
        this.idSeek = idSeek;
    }

    public Favoris(int id, String prenom, String region, String description, String nbrfois) {
        this.img = img;
        this.nom = nom;
        this.prenom = prenom;
        this.region = region;
        this.description = description;
        this.nbrfois = nbrfois;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNbrfois() {
        return nbrfois;
    }

    public void setNbrfois(String nbrfois) {
        this.nbrfois = nbrfois;
    }
}