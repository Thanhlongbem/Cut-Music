package banh.along.cutmusic;

public interface PlaybackListener {
    void onProgress(int progress);
    void onCompletion();
}