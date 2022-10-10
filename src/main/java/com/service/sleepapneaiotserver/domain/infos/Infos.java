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

    //심박수
    private int pulse;

    //산소포화도
    private double o2;

    //infos : N, user : 1
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
    private User user;

    //데이터가 생성될떄 자동으로 그시간 저장
    @CreationTimestamp
    private Timestamp createDate;

    @Builder
    public Infos(int pulse, double o2, User user){
        this.pulse = pulse;
        this.o2 = o2;
        this.user = user;
    }

}
