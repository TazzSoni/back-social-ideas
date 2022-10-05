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
@Table(name ="comment")
@SequenceGenerator(name = "comment_seq", sequenceName = "seq_comment_oid_version", allocationSize = 1)
public class CommentEntity implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
    @Column(name = "oid_version_comment", nullable = false)
    private Long id;

    @Column(name = "des_name")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "oid_version", referencedColumnName = "oid_version")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "oid_version_post", referencedColumnName = "oid_version_post")
    private PostEntity posts;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_version_comment")
    private List<LikeComment> likes;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_version_comment")
    private List<DislikeComment> dislikes;
}
