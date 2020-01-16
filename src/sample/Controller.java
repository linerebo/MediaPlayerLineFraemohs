package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.media.*;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

//JavaFX application providing administration of mp4 media files and playlists. The application allows the user to play video and create/edit playlists
public class Controller implements Initializable {
    @FXML private MediaView mv;
    @FXML private Button newPlaylist;
    @FXML private Button editPlaylist;
    @FXML private ListView<String> listView;
    @FXML private ComboBox<String> combobox;
    @FXML private Slider volumeSlider;
    @FXML private TextField namePlaylistField;
    private MediaPlayer mp;
    private Media me;
    private ObservableList<String> listCatagories;
    private ObservableList<String> listMedia;
    private ObservableList<Video> videos;
    private ArrayList<Playlist> playlists = new ArrayList<>();
    private String data;
    private Video newVideo;
    private Connection dbcon;
    private PreparedStatement sqlstatement;
    private ResultSet dataResultset;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {dbcon = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Mediaplayer","sa","123456");}
            catch (SQLException e) {System.err.println(e.getMessage());};
        String path = new File("src/sample/Media/Maroon5.mp4").getAbsolutePath();
        System.out.println(path);
        me = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(me);
        mv.setMediaPlayer(mp);
        mp.setAutoPlay(false);
        getData();
        displayCatagories();
        displayMediaList();
        handleVolume();
    }

    public void handlePlay(){
        mp.play();
    }

    public void handlePause(){
        mp.pause();
    }

    public void handleStop(){
        mp.stop();
    }

    public void handleVolume(){
        volumeSlider.setValue(mp.getVolume()*100);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mp.setVolume(volumeSlider.getValue()/100);
            }
        });
    }


    //inserting data for media in tblVideo in Database
    public void insertData(){
        DB.insertSQL("insert into tblVideo values ('1','src/sample/Media/Alice_Merton.mp4','Alice Merton: No Roots', 'music')");
        DB.insertSQL("insert into tblVideo values ('2','src/sample/Media/Biology_Cell_Structure.mp4','Biology Cell Structure', 'molecular biology')");
        DB.insertSQL("insert into tblVideo values ('3','src/sample/Media/Coldplay_.mp4','Coldplay: The Scientist', 'music')");
        DB.insertSQL("insert into tblVideo values ('4','src/sample/Media/ed_sheeran.mp4','Ed Sheeran: I dont care', 'music')");
        DB.insertSQL("insert into tblVideo values ('5','src/sample/Media/Electron_transport_chain.mp4','Electron transport chain', 'molecular biology')");
        DB.insertSQL("insert into tblVideo values ('6','src/sample/Media/Funny_Dogs.mp4','Funny Dogs', 'animals')");
        DB.insertSQL("insert into tblVideo values ('7','src/sample/Media/George_Ezra.mp4','George Ezra: Shotgun', 'music')");
        DB.insertSQL("insert into tblVideo values ('8','src/sample/Media/John_Mayer.mp4','John Mayer: Bigger than my body', 'music')");
        DB.insertSQL("insert into tblVideo values ('9','src/sample/Media/justin_timberlake.mp4','Justin Timberlake: Cant stop the feeling', 'music')");
        DB.insertSQL("insert into tblVideo values ('10','src/sample/Media/Maroon5.mp4','Maroon 5: Memories', 'music')");
        DB.insertSQL("insert into tblVideo values ('11','src/sample/Media/max_giesinger.mp4','Max Giesinger: Wenn sie tanzt', 'music')");
        DB.insertSQL("insert into tblVideo values ('12','src/sample/Media/mighty_oaks.mp4','Mighty Oaks: Brother', 'music')");
        DB.insertSQL("insert into tblVideo values ('13','src/sample/Media/REM.mp4','REM: Leaving New York', 'music')");
        DB.insertSQL("insert into tblVideo values ('14','src/sample/Media/the_balcony.mp4','The Balcony: The rumour said fire', 'music')");
        DB.insertSQL("insert into tblVideo values ('15','src/sample/Media/The_Structure_of_DNA.mp4','The structure of DNA', 'molecular biology')");
        DB.insertSQL("insert into tblVideo values ('16','src/sample/Media/Wild_animals.mp4','Wild Animals', 'animals')");
    }

    public void handleNewPlaylistButton(){
    }

    //creating catagories to search for media
    public void displayCatagories(){
        listCatagories = FXCollections.observableArrayList();
        for (Video element: videos) {
            if (!listCatagories.contains(element.catagory)) {
                listCatagories.add(element.catagory);
            }
        }
        combobox.setItems(listCatagories);
    }

    //displaying media in listView
    public void displayMediaList(){
        listMedia = FXCollections.observableArrayList();
        for (Video element: videos) {
            listMedia.add(element.videoTitle);
        }
        listView.setItems(listMedia);
    }

    public void getData(){
        try{
            videos = FXCollections.observableArrayList();
            sqlstatement = dbcon.prepareStatement("select * from tblVideo");
            dataResultset = sqlstatement.executeQuery();
            while (dataResultset.next()) {
                newVideo = new Video();
                newVideo.videoID = dataResultset.getInt(1);
                newVideo.filePath = dataResultset.getString(2);
                newVideo.videoTitle = dataResultset.getString(3);
                newVideo.catagory = dataResultset.getString(4);
                System.out.println(newVideo.videoID  + " Title: " + newVideo.videoTitle + " Catagory: " + newVideo.catagory + " File: " + newVideo.filePath);
                videos.add(newVideo);
            }
        } catch (SQLException e) {System.err.println(e.getMessage());};
    }

}