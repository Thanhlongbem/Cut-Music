package banh.along.cutmusic.model;

public class Song {
    private int iconMenu;
    private String author;
    private String nameOfSong;
    private String path;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String tinme) {
        this.time = tinme;
    }

    public int getIconMenu() {
        return iconMenu;
    }

    public void setIconMenu(int iconMenu) {
        this.iconMenu = iconMenu;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
