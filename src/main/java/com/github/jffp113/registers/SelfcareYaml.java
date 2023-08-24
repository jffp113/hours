package com.github.jffp113.registers;

import java.util.List;

public class SelfcareYaml {
    private Credential credentials;

    private List<Entry> entries;

    public Credential getCredentials() {
        return credentials;
    }

    public void setCredentials(Credential credentials) {
        this.credentials = credentials;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
}
