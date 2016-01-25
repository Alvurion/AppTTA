package tta.ehu.eus.apptta.Modelo;


public class Content {
    int type;
    String fraseEs;
    String fraseAr;
    int usersId;
    String audio;



    public Content(int type, String phraseEs, String phraseAr, String audioFrase) {
        this.type = type;
        this.fraseEs = phraseEs;
        this.fraseAr = phraseAr;
        this.audio = audioFrase;
    }

    public Content() {

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPhraseEs() {
        return fraseEs;
    }

    public void setPhraseEs(String phraseEs) {
        this.fraseEs = phraseEs;
    }

    public String getPhraseAr() {
        return fraseAr;
    }

    public void setPhraseAr(String phraseAr) {
        this.fraseAr = phraseAr;
    }

    public String getAudioFrase() {
        return audio;
    }

    public void setAudioFrase(String audioFrase) {
        this.audio= audioFrase;
    }
}
