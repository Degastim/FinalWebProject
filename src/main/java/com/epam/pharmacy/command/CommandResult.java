package com.epam.pharmacy.command;

/**
 * Class containing transition type and path transition to jsp
 *
 * @author Yauheni Tsitou
 */
public class CommandResult {

    /**
     * The value is used to store the default path to the jsp.
     */
    public static final String DEFAULT_PATH = PagePath.INDEX_PAGE;

    /**
     * The value is used to store the path to the jsp.
     */
    private String path;

    /**
     * The value is used to store the transition type.
     */
    private Type type;

    /**
     * Enum types of transition to jsp
     */
    public enum Type {
        FORWARD,
        RETURN_CURRENT_PAGE_WITH_REDIRECT,
        REDIRECT
    }

    /**
     * Initializes the newly created CommandResult object
     */
    public CommandResult() {
    }

    /**
     * Constructs a new CommandResult using the jsp path and navigation method
     *
     * @param path String that is the source of path
     * @param type Enum that is the source of transition type
     */
    public CommandResult(String path, Type type) {
        this.path = path;
        this.type = type;
    }

    /**
     * Constructs a new Command Result that initializes the page navigation method
     *
     * @param type Enum that is the source of transition type
     */
    public CommandResult(Type type) {
        this.type = type;
    }

    /**
     * Returns the type of transition to jsp.
     *
     * @return type
     * Enum that is the source of transition type
     */
    public Type getType() {
        return type;
    }

    /**
     * Set the type of transition to jsp.
     *
     * @return type
     * Enum that is the source of transition type
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Returns the path to jsp.
     *
     * @return A string that represents the path to jsp
     */
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
