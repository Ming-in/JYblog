package com.ming.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 邹明
 */
@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true,nullable = false)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<>();

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs.stream().filter(Blog::isPublished).collect(Collectors.toList());
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs.stream().filter(Blog::isPublished).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
