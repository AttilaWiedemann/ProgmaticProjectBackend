package backend.model;


import javax.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private String url;

    @Lob
    private byte[] bytes;

    private boolean profilPicture;

    public boolean isProfilPicture() {
        return profilPicture;
    }

    public void setProfilPicture(boolean profilPicture) {
        this.profilPicture = profilPicture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public  byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

}
