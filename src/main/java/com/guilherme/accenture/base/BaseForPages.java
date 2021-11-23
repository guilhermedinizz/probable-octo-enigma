package com.guilherme.accenture.base;

import com.guilherme.accenture.util.Data;

public class BaseForPages extends Page {
    protected BaseForPages() {
        Data.getResourceProperties("application.properties");
    }
}
