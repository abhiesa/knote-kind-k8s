package com.abhiesa.knote;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author abhiesa
 * */
@Document("notes")
class Note implements Serializable {

    private static final long serialVersionUID = -1398012862052004210L;

    @Id
    private String id;
    private String desc;

    public Note() {
        this(null, null);
    }

    public Note(final String desc) {
       this(null, desc);
    }

    public Note(final String id, final String desc) {
        this.id = id;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }


    public String getDesc() {
        return desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(getId(), note.getId()) &&
                Objects.equals(getDesc(), note.getDesc());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDesc());
    }

    @Override
    public String toString() {
        return this.getDesc();
    }
}

