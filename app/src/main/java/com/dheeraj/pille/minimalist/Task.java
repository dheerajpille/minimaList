package com.dheeraj.pille.minimalist;

public class Task {
    String text;
    boolean checked;

    public Task(String text, boolean checked) {
        this.text = text;
        this.checked = checked;
    }

    public String getText() {
        return this.text;
    }

    public boolean getChecked() {
        return this.checked;
    }

    public void toggleChecked() {
        this.checked = !this.checked;
    }

}
