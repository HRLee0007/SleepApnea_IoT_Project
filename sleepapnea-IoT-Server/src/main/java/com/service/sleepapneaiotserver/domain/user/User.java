package com.service.sleepapneaiotserver.domain.user;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@DynamicInsert
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
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

    //유저 실명
    @Column(nullable = false, length = 100)
    private String realname;

    //유저이메일
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    //유저 주소, 폰번호, 보호자 폰번호

    @Column(nullable = false, length = 200)
    private String address;

    @Column(nullable = false, length = 50)
    private String phoneNum;

    @Column(nullable = false, length = 50)
    private String c_phoneNum;

    //status default = 0
    @ColumnDefault("0")
    private Integer status;


    private String statusStartAt;

    private String statusEndAt;

    @ColumnDefault("0")
    private Integer sign;

    @Enumerated(EnumType.STRING)
    private Role role;

    //회원이 가입한시간?
    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

    //회원이 수정한시간?
    @UpdateTimestamp
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder
    public User(String username, String password, String realname, String email, String address,
                String phoneNum, String c_phoneNum, Role role){
        this.username = username;
        this.password = password;
        this.realname = realname;
        this.email = email;
        this.address = address;
        this.phoneNum = phoneNum;
        this.c_phoneNum = c_phoneNum;
        this.role = role;

    }


}
