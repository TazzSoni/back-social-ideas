package com.backsocialideas.model;

import com.backsocialideas.dto.enums.Stage;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@ToString
@Table(name ="post")
@SequenceGenerator(name = "post_seq", sequenceName = "seq_post_oid_version", allocationSize = 1)
public class PostEntity implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq")
    @Column(name = "oid_version_post", nullable = false)
    private Long id;

    @Column(name = "des_title")
    private String titulo;

    @Column(name = "des_post", columnDefinition="TEXT")
    private String post;

    @Column(name = "des_stage")
    private Stage stage;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ProfileImageEntity file;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_version_post")
    private List<Tags> tags;

    @ManyToOne
    @JoinColumn(name = "oid_version", referencedColumnName = "oid_version")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "oid_cooworker", referencedColumnName = "oid_version")
    private UserEntity cooworker;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_version_post")
    private List<CommentEntity> comment;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_version_post")
    private List<LikePost> likes;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_version_post")
    private List<DislikePost> dislikes;
}
