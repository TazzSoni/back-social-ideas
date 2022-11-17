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
@Table(name ="tags")
@SequenceGenerator(name = "tags_seq", sequenceName = "seq_tags_oid_version", allocationSize = 1)
public class Tags {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tags_seq")
    @Column(name = "oid_version_tags", nullable = false)
    private Long id;

    @Column(name="des_tag")
    private String desTag;
}
