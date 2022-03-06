package com.example.yamlHandler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class Playlist {
    @Getter @Setter
    private int id;

    @Getter @Setter
    private Collection<Tune> PlaylistTitle;

    public Playlist() {
        this.id = 0;
        this.PlaylistTitle = Collections.emptyList();
    }

    @Override
    public String toString(){
        return "Playlist " + getId()
                + ": " + getPlaylistTitle()
                + "\n";
    }
}
