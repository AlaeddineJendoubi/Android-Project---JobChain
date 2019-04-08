package esprit.tn.Entities;

public class MaPublication {
    private int id,id_user;
    private String img,img2;
    private String nom , prenom, region , description,nbrfois, spec;
    public MaPublication() {
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public MaPublication(int id, String img, String nom, String prenom, String region, String description, String nbrfois, int id_user) {
        this.id = id;
        this.id_user = id_user;
        this.img = img;
        this.nom = nom;
        this.prenom = prenom;
        this.region = region;
        this.description = description;
        this.nbrfois = nbrfois;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
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