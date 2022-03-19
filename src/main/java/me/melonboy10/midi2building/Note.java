package me.melonboy10.midi2building;

public class Note {

    private long time;
    private String note;

    public Note(long time, String note) {
        this.time = time;
        this.note = note;
    }

    public long getTime() {
        return time;
    }

    public String getNote() {
        return note;
    }

    public String getNoteRaw() {
        return note.replaceAll("[0123456789]", "");
    }
}
