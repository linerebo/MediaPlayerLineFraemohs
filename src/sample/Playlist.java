package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.util.ArrayList;

public class Playlist {
    public String playlistName;
    int playlistID;
    public ObservableList<Video> pvideos;
    public int videoNumber = 0;

    public Playlist(String name, int id){
        playlistName = name;
        playlistID = id;
        pvideos = FXCollections.observableArrayList();
    }

    public void addVideo(Video newVideo){
        pvideos.add(newVideo);
    }

    public void removeVideo(Video removedVideo){
        pvideos.remove(removedVideo);
    }


    public Video nextVideo(){
        if (videoNumber == pvideos.size()) {
            videoNumber = 1;
            return pvideos.get(0);
        } else {
            videoNumber = videoNumber +1;
            return pvideos.get(videoNumber-1);
        }
    }

    @Override
    public String toString(){
        return playlistName;
    }

}
