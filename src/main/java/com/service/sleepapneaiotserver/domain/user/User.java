package com.service.sleepapneaiotserver.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;


@DynamicInsert
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 유저_id
    private Long id;

    //유저아이디
    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username;

    //유저비밀번호
    @Column(nullable = false, length = 100)
    private String password;

    //유저이메일
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    //status default = 0
    @ColumnDefault("0")
    private Integer status;

    //회원이 가입한시간?
    @CreationTimestamp
    private Timestamp createDate;

    @Builder
    public User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }


}
