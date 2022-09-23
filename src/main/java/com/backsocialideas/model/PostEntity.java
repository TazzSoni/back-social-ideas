package com.backsocialideas.model;

import com.backsocialideas.dto.enums.Stage;
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
@Table(name ="post")
@SequenceGenerator(name = "post_seq", sequenceName = "seq_post_oid_version", allocationSize = 1)
public class PostEntity implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq")
    @Column(name = "oid_version_post", nullable = false)
    private Long id;

    @Column(name = "des_post")
    private String post;

    @Column(name = "des_stage")
    private Stage stage;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_version", referencedColumnName = "oid_version")
    private UserEntity user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_version_post")
    private List<CommentEntity> comment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_version_post")
    private RateEntity rating;
}
