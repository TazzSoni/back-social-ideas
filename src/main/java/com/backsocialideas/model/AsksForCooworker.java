package com.backsocialideas.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="cooworker")
@SequenceGenerator(name = "cooworker_seq", sequenceName = "cooworker_seq_oid", allocationSize = 1)
public class AsksForCooworker {


    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cooworker_seq")
    @Column(name = "oid_cooworker", nullable = false)
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "user_owner_id")
    private Long userOwnerId;

    @Column(name = "user_request_id")
    private Long userRequestId;
}
