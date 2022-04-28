package com.education.localization;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public record Translate(MessageSource messageSource) {


    public String getTranslation(String code, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return getTranslation(code, locale, args);
    }

    public String getTranslation(String code, Locale locale, Object... args) {
        return messageSource.getMessage(code, args, locale);
    }
}
