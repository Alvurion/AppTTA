package tta.ehu.eus.apptta.Modelo;

public class Frase {
    int type;
    String phraseEs;
    String phraseAr;
    String audioFrase;


    public Frase() {

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPhraseEs() {
        return phraseEs;
    }

    public void setPhraseEs(String phraseEs) {
        this.phraseEs = phraseEs;
    }

    public String getPhraseAr() {
        return phraseAr;
    }

    public void setPhraseAr(String phraseAr) {
        this.phraseAr = phraseAr;
    }

    public String getAudioFrase() {
        return audioFrase;
    }

    public void setAudioFrase(String audioFrase) {
        this.audioFrase = audioFrase;
    }
}
