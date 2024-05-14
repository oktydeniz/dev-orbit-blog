package com.odeniz.dev.orbit.configration;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

import java.util.Locale;

@Component
public class ConfigurationManager {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PropertyLoader properties;

    public String getParam(String key) {
        return getParam(key, null);
    }

    public String getParam(String key, String defaultValue) {
        try {
            String value = properties.getProperty(key);
            if (value == null){
                return defaultValue;
            } else {
                return value;
            }
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public Integer getParamAsInteger(String key) {
        return Integer.valueOf(this.getParam(key));
    }

    public Integer getParamAsInteger(String key, Integer defaultValue) {
        try {
            return Integer.valueOf(this.getParam(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public Boolean getParamAsBoolean(String key, Boolean defaultValue) {
        try {
            String value = this.getParam(key);
            if (value == null){
                return defaultValue;
            }
            return Boolean.valueOf(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }


    public String getI18n(String key) {
        try {
            return messageSource.getMessage(key, null,
                    LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return null;
        }
    }

    public String getI18n(String key, Locale locale) {
        try {
            return messageSource.getMessage(key, null, locale);
        } catch (NoSuchMessageException e) {
            return null;
        }
    }

    public String getI18n(String key, String[] params) {
        try {
            return messageSource.getMessage(key, params,
                    LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return null;
        }
    }

    public String getI18n(String key, Integer[] params) {
        try {
            return messageSource.getMessage(key, params,
                    LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return null;
        }
    }

    public String getI18n(String key, String[] params, Locale locale) {
        try {
            return messageSource.getMessage(key, params, locale);
        } catch (NoSuchMessageException e) {
            return null;
        }
    }

    public String getMessage(ObjectError error){
        try {
            return messageSource.getMessage(error, Locale.getDefault());
        } catch (NoSuchMessageException e) {
            return null;
        }
    }
}