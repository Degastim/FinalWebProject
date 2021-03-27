package com.epam.Pharmacy.command;

public class CommandResult {
    public static final String DEFAULT_PATH = PagePath.INDEX_PAGE;
    private String path;
    private Type type;

    public enum Type {
        FORWARD,
        RETURN_CURRENT_PAGE_WITH_REDIRECT,
        REDIRECT
    }

    public CommandResult(String path, Type type) {
        this.path = path;
        this.type = type;
    }

    public CommandResult() {
    }

    public CommandResult(Type type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String providePath() {
        String result;
        if (path == null || path.isEmpty()) {
            result = DEFAULT_PATH;
        } else {
            result = path;
        }
        return result;
    }
}
