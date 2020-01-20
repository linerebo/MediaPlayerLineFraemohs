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
    public MediaPlayer currentMediaPlayer;

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

    public void playNext(MediaView mv) {
        Media nextMedia = new Media(new File(nextVideo().filePath).toURI().toString());
        MediaPlayer nextMediaPlayer = new MediaPlayer(nextMedia);
        nextMediaPlayer.setAutoPlay(true);
        nextMediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override public void run() {
                playNext(mv);
            }
        });
        if (currentMediaPlayer!=null) {currentMediaPlayer.stop();};
        mv.setMediaPlayer(nextMediaPlayer);
        currentMediaPlayer = nextMediaPlayer;
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
