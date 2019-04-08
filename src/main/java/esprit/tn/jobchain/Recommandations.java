package esprit.tn.jobchain;

public class Recommandations {
    private int id ;
    private int idUser ;
    private int idSeek ;
    private String description  ;
    private String nom  ;
    private String numero  ;
    private String latitude  ;
    private String longitude  ;

    public Recommandations(int id, int idUser, int idSeek, String description, String nom, String numero, String latitude, String longitude) {
        this.id = id;
        this.idUser = idUser;
        this.idSeek = idSeek;
        this.description = description;
        this.nom = nom;
        this.numero = numero;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdSeek() {
        return idSeek;
    }

    public void setIdSeek(int idSeek) {
        this.idSeek = idSeek;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
