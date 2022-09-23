package com.backsocialideas.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="rate")
@SequenceGenerator(name = "rate_seq", sequenceName = "seq_rate_oid_version", allocationSize = 1)
public class RateEntity implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rate_seq")
    @Column(name = "oid_version_rate", nullable = false)
    private Long id;

    @Column(name = "teste")
    private String like;

    @Column(name = "tasta")
    private String dislike;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_version_comment", referencedColumnName = "oid_version_comment", updatable = false)
    private CommentEntity comment;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_version_post", referencedColumnName = "oid_version_post", updatable = false)
    private PostEntity post;
}
