package com.backsocialideas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="users")
@SequenceGenerator(name = "user_seq", sequenceName = "seq_oid_version", allocationSize = 1)
public class UserEntity implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(name = "oid_version", nullable = false)
    private Long id;
    @Column(name = "des_name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "level")
    private int level;
    @Column(name = "teacher")
    private boolean teacher;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "oid_version")
    private List<CommentEntity> comments;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "oid_version")
    private List<PostEntity> posts;
}
