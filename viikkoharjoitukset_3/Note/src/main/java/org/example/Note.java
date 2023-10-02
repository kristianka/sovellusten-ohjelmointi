package org.example;

enum Color {
    RED,
    GREEN,
    BLUE,
    YELLOW,
    ORANGE,
    PURPLE
}

public class Note {
    private String content;
    private Color backgroundColor;

    public Note() {
        this.content = "";
        this.backgroundColor = Color.RED;
    }

    public Note(String content, Color backgroundColor) {
        this.content = content;
        this.backgroundColor = backgroundColor;
    }

    // Get
    public String getContent() {
        return this.content;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    // Set
    public void setContent(String content) {
        this.content = content;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void printNote() {
        System.out.println("Content: " + this.content + ", " + this.backgroundColor);
    }

    public String toString() {
        return "Content: " + this.content + ", BackgroundColor: " + this.backgroundColor;
    }
}
