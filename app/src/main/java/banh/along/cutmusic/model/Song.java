package banh.along.cutmusic.model;

public class Song {
    private String author;
    private String nameOfSong;
    private int time;
    private String resource;
    private int icon;

    public Song(String author, String nameOfSong, int time, String resource, int icon) {
        this.author = author;
        this.nameOfSong = nameOfSong;
        this.time = time;
        this.resource = resource;
        this.icon = icon;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNameOfSong() {
        return nameOfSong;
    }

    public void setNameOfSong(String nameOfSong) {
        this.nameOfSong = nameOfSong;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
