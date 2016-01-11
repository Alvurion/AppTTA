package tta.ehu.eus.apptta.Modelo;

public class Frase {
    int type;
    String phraseEs;
    String phraseAr;
    int usersId;
    String audioFrase;



    public Frase(int type, String phraseEs, String phraseAr, int usersId, String audioFrase) {
        this.type = type;
        this.phraseEs = phraseEs;
        this.phraseAr = phraseAr;
        this.usersId = usersId;
        this.audioFrase = audioFrase;
    }

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

    public int getUsersId() {
        return usersId;
    }

    public void setUsersId(int usersId) {
        this.usersId = usersId;
    }

    public String getAudioFrase() {
        return audioFrase;
    }

    public void setAudioFrase(String audioFrase) {
        this.audioFrase = audioFrase;
    }
}
