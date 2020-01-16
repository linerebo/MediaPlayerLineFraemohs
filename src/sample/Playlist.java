package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Playlist {
    public String playlistName;
    int playlistID;
    public ObservableList<Video> pvideos;

    public Playlist(String name, int id){
        playlistName = name;
        playlistID = id;
        pvideos = FXCollections.observableArrayList();
    }

}
