package com.example.community_parc.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PostStorage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long BoardStorageId;

    @ManyToOne
    private Post Board;

    @ManyToOne
    private Member Member;
}
