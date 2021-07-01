package com.google;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;

  //placeholder for the video currently playing
  public Video currentPlayingVideo = null;

  //collection of playlists
  ArrayList<HashMap<String, Video>> playlistCollection;

  //collect all ids in video library
  ArrayList<String> idArraylist = new ArrayList<>();



  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {

    //collect all of the videos within our library
    ArrayList<Video> videoArrayList = new ArrayList<>();
    for (int i = 0; i < videoLibrary.getVideos().size(); i++) {
        videoArrayList.add(videoLibrary.getVideos().get(i));
    }

    for (int i = 0; i < videoLibrary.getVideos().size(); i++) {
      videoLibrary.getVideos().get(i).getTags().stream().map(n -> String.valueOf(n)).collect(Collectors.joining("-", "[", "]"));
    }

    //Order lexicographically
    videoArrayList.sort( Comparator.comparing(Video::getTitle ));

    //Print ordered videos to the command line. (in the correct format)
    System.out.println("Here's a list of all available videos:");
    for (Video v: videoArrayList){
      System.out.println(v.toString());
    }

  }


  public void playVideo(String videoId) {

    //error checking: non existent id given as argument

    idArraylist = new ArrayList<>();

    for (int i = 0; i < videoLibrary.getVideos().size(); i++) {
      idArraylist.add(videoLibrary.getVideos().get(i).getVideoId());
    }

    //boolean value for if the collection of id's contain the videoId
    //given as an argument
    boolean present = idArraylist.contains(videoId);


    if (present){

      //if the videoId is present in the library
      if (currentPlayingVideo == null){

        //set the current playing video to the videoId given as an argument
        currentPlayingVideo = videoLibrary.getVideo(videoId);

        //set the paused status to false
        currentPlayingVideo.setPaused(false);

        //Send the note to the command line of the current playing video
        System.out.println("Playing video: " + videoLibrary.getVideo(videoId).getTitle());


      }
      else {
        //if currentPlayingVideo is not null
        //stop the current playing video and play this video
        stopVideo();
        //set the currentPlayingVideo to this videoId
        currentPlayingVideo = videoLibrary.getVideo(videoId);

        //set the paused status to false
        currentPlayingVideo.setPaused(false);
        //Send the note to the command line of the current playing video
        System.out.println("Playing video: " + videoLibrary.getVideo(videoId).getTitle());
      }
    }
    else {
      System.out.println("Cannot play video: Video does not exist");
    }
  }

  public void stopVideo() {

    //when there is no video playing - display error message
    if (currentPlayingVideo == null){
      System.out.println("Cannot stop video: No video is currently playing");
    }
    else {
      //send the message to the command line that we are stopping the title
      System.out.println("Stopping video: " + currentPlayingVideo.getTitle());

      //set currentPlayingVideo to null
      currentPlayingVideo = null;
    }
  }


  public void playRandomVideo() {

    //initialise a Video to store the random video
    Video randomVideo;
    //Find a random variable between 0 and 1
    // and times by the size of videos available
    randomVideo = videoLibrary.getVideos().get(
            (int) (Math.random() *
            videoLibrary.getVideos().size()));

    //check if there is a current video playing
    if (currentPlayingVideo != null){
      //if so, stop the current playing video
      stopVideo();
    }

    //check if there are no videos available
    //if the values of the video library are empty - send message saying no videos available
    if (videoLibrary.getVideos().isEmpty()){
      System.out.println("No videos available");
    }

    //call playVideo with the videoId of the randomly selected video
    playVideo(randomVideo.getVideoId());
  }

  public void pauseVideo() {
    //When the current video is playing
    if ( currentPlayingVideo!= null){

      //given the current video is already paused
      if (currentPlayingVideo.getPaused() == true){

        //display message we cannot pause, already paused
        System.out.println("Video already paused: " + currentPlayingVideo.getTitle());
      }
      else {
        //given the video is playing

        //set paused to true
        currentPlayingVideo.setPaused(true);

        //display message we are pausing the current video
        System.out.println("Pausing video: " + currentPlayingVideo.getTitle());
      }

    } else {
      //in this case, no video is playing, we display the appropriate error message
      //informing the user no video is playing
      System.out.println("Cannot pause video: No video is currently playing");
    }


  }



  public void continueVideo() {
    //When the current video is playing
    if (currentPlayingVideo!= null){

      //given a video is currently playing, if the current video is paused
      if (currentPlayingVideo.getPaused().equals(true)){

        //set paused to false
        currentPlayingVideo.setPaused(false);
        //display message we are continuing video
        System.out.println("Continuing video: " + currentPlayingVideo.getTitle());


      } else {
        //given a video is currently playing, if the current video is NOT paused

        //display message we cannot continue, video is not paused
        System.out.println("Cannot continue video: Video is not paused");
      }

      //when there is not video currently playing
    } else {

      //display message, we cannot continue, nothing playing
      System.out.println("Cannot continue video: No video is currently playing");
    }

  }

  public void showPlaying() {
      if (currentPlayingVideo!= null){
        if ( currentPlayingVideo.getPaused().equals(false)){
          //if there is a video playing that is NOT paused

          //display message
          System.out.println("Currently playing: " + currentPlayingVideo);
        }
        else if (currentPlayingVideo.getPaused().equals(true)){
          //if there is a video playing that is paused

          //display message and pause status
          System.out.println("Currently playing: " + currentPlayingVideo + " - PAUSED");
        }

      } else {
        //if there is no video playing

        //display message
        System.out.println("No video is currently playing");
      }
  }

  public void createPlaylist(String playlistName) {

    //store original playlistName and casing
    String originalName = playlistName;

    //store playlistName capitalised
    String capitalisedName = playlistName.toUpperCase();


    if (!playlistCollection.contains(capitalisedName)){

      //if playlistName is unique, create new playlist
      HashMap<String, Video> createdPlaylist = new HashMap<>();
      createdPlaylist.put(capitalisedName, null);

      //add the newly created playlist to the collection of playlists
      playlistCollection.add(createdPlaylist);

      //display approriate error message
      System.out.println("Successfully created new playlist: " + originalName);

    } else {

      //if the name is not unique, display appropriate error message
      System.out.println("Cannot create playlist: A playlist with the same name already exists");

    }

    //todo: how to check for uniqueness
    //todo: how to create an EMPTY hashmap



  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    System.out.println("addVideoToPlaylist needs implementation");
  }

  public void showAllPlaylists() {
    System.out.println("showAllPlaylists needs implementation");
  }

  public void showPlaylist(String playlistName) {
    System.out.println("showPlaylist needs implementation");
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    System.out.println("removeFromPlaylist needs implementation");
  }

  public void clearPlaylist(String playlistName) {
    System.out.println("clearPlaylist needs implementation");
  }

  public void deletePlaylist(String playlistName) {
    System.out.println("deletePlaylist needs implementation");
  }

  public void searchVideos(String searchTerm) {
    System.out.println("searchVideos needs implementation");
  }

  public void searchVideosWithTag(String videoTag) {
    System.out.println("searchVideosWithTag needs implementation");
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}