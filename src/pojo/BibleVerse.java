package pojo;

public class BibleVerse {
    private String verseTitle;
    private String verseScripture;
    private String book;
    private String chapter;
    private String verse;
    private String scripture;

    public BibleVerse() {
    }

    public String getVerseTitle() {
        return this.verseTitle;
    }

    public String getVerseScripture() {
        return this.verseScripture;
    }

    public void addVerseTitle(String verseTitle) {
        this.verseTitle = verseTitle;
    }

    public void addVerseScripture(String verseScripture) {
        this.verseScripture = verseScripture;
    }

    public String getBook() {
        return this.book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getChapter() {
        return this.chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getVerse() {
        return this.verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public String getScripture() {
        return this.scripture;
    }

    public void setScripture(String scripture) {
        this.scripture = scripture;
    }
}
