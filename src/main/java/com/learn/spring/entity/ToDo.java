package com.learn.spring.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ToDo {
    @Getter
    @Setter
    @Id
    @GeneratedValue
    private long id;
    @Getter
    @Setter
    private String text;

    @Getter
    @Setter
    private boolean completed;

    public ToDo(){

    }
    public ToDo(long id, String text, boolean completed){
        this.id = id;
        this.text = text;
        this.completed = completed;
    }

    public ToDo(String text, boolean b) {
        this.text = text;
        this.completed = completed;
    }
}
