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
@Table(name ="dislike_post")
@SequenceGenerator(name = "dislike_post_seq", sequenceName = "seq_dislike_post_oid_version", allocationSize = 1)
public class DislikePost {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dislike_post_seq")
    @Column(name = "oid_version_dislike_post", nullable = false)
    private Long id;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_version_post", referencedColumnName = "oid_version_post")
    private PostEntity post;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_version", referencedColumnName = "oid_version")
    private UserEntity user;
}
