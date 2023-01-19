package com.service.sleepapneaiotserver.domain.infos;


import com.service.sleepapneaiotserver.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Infos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 선택현황
    private int count;

    //infos : N, user : 1
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
    private User user;

    //데이터가 생성될떄 자동으로 그시간 저장
    @CreationTimestamp
    private Timestamp createDate;

    private String statusStartAt;

    private String statusEndAt;

    @Builder
    public Infos(int count, User user, String statusStartAt, String statusEndAt){
        this.count = count;
        this.user = user;
        this.statusStartAt = statusStartAt;
        this.statusEndAt = statusEndAt;

    }

}


