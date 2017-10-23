package com.dheeraj.pille.minimalist;

public class Task {

    // Task-defining variables
    String text;
    boolean checked;

    // Task constructor
    public Task(String text, boolean checked) {
        this.text = text;
        this.checked = checked;
    }

    // Gets text value
    public String getText() {
        return this.text;
    }

    // Gets checked value
    public boolean getChecked() {
        return this.checked;
    }

    // Toggles checked value
    public void toggleChecked() {
        this.checked = !this.checked;
    }

}
