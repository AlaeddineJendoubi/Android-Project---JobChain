package esprit.tn.jobchain;

public class ListItem {

    private String itemSpec;
    private String itemRegion;
    private String itemUserName;
    private String itemDesc;
    private String nbrRecom;
    private String idSeek;
    private String itemImg;
    private String itemIduser;
    private String itemLastName;
    private int img1;

    public String getItemIduser() {
        return itemIduser;
    }

    public void setItemIduser(String itemIduser) {
        this.itemIduser = itemIduser;
    }

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }

    public String getItemLastName() {
        return itemLastName;
    }

    public void setItemLastName(String itemLastName) {
        this.itemLastName = itemLastName;
    }

    public int getImg1() {
        return img1;
    }

    public void setImg1(int img1) {
        this.img1 = img1;
    }

    public void setIdSeek(String idSeek) { this.idSeek = idSeek; }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public void setItemRegion(String itemRegion) {
        this.itemRegion = itemRegion;
    }

    public void setItemUserName(String itemUserName) {
        this.itemUserName = itemUserName;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public void setNbrRecom(String nbrRecom) { this.nbrRecom = nbrRecom; }

    public String getItemUserName() {
        return itemUserName;
    }

    public String getItemSpec() {
        return itemSpec;
    }

    public String getItemRegion() {
        return itemRegion;
    }

    public String getNbrRecom() { return nbrRecom; }

    public String getItemDesc() {
        return itemDesc;
    }

    public String getIdSeek() { return idSeek; }




    public ListItem(String itemUserName ,String itemSpec, String itemRegion,String itemDesc ,String nbrRecom,String idSeek,String itemIduser) {
        this.itemSpec = itemSpec;
        this.itemRegion = itemRegion;
        this.itemUserName = itemUserName;
        this.itemDesc = itemDesc;
        this.nbrRecom=nbrRecom;
        this.idSeek=idSeek;
        this.itemIduser=itemIduser;




    }
}
