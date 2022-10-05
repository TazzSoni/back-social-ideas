package com.backsocialideas.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name ="like_comment")
@SequenceGenerator(name = "like_comment_seq", sequenceName = "seq_like_comment_oid_version", allocationSize = 1)
public class LikeComment {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_comment_seq")
    @Column(name = "oid_version_like_comment", nullable = false)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_version_comment", referencedColumnName = "oid_version_comment")
    private CommentEntity comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_version", referencedColumnName = "oid_version")
    private UserEntity user;
}
