package com.dheeraj.pille.minimalist;

import com.google.gson.Gson;

public class Task {
    String text;
    boolean checked;

    public Task() {
        this.text = "";
        this.checked = false;
    }

    public Task(String text, boolean checked) {
        this.text = text;
        this.checked = checked;
    }

    public Task(Task t) {
        this.text = t.getText();
        this.checked = t.getChecked();
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void toggleChecked() {
        this.checked = !this.checked;
    }

    static public Task create(String serializedData) {
        Gson gson = new Gson();
        return gson.fromJson(serializedData, Task.class);
    }

    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
