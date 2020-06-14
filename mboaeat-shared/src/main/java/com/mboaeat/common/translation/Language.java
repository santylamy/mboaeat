package com.mboaeat.common.translation;

import java.io.Serializable;
import java.util.Locale;

public enum Language implements Serializable {

    FRENCH("fr"),
    ENGLISH("en"),
    UNKNOWN("en");

    private String languageString;

    Language(String languageString) {
        this.languageString = languageString;
    }

    public String getLanguageString() {
        return languageString;
    }

    public static Language toLanguage(String code){
        Language language = null;
        switch (code){
            case "fr":
                language = Language.FRENCH;
                break;
            case "en":
                language = Language.ENGLISH;
                break;
            default:
                language = Language.UNKNOWN;
        }
        return language;
    }

    public Locale getLocale(){
        return new Locale(languageString);
    }

    public String getValue(){
        return getLanguageString();
    }

    public boolean isFrecnh(){
        return FRENCH.equals(this);
    }

    public boolean isEnglish(){
        return ENGLISH.equals(this);
    }

}
