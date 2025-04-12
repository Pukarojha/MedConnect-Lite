package com.first.demo.models;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "channel", strict = false)
public class Channel {

    @ElementList(inline = true, entry = "item", required = false)
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }
}