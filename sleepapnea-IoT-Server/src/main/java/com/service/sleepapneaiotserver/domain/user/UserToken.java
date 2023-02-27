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
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 유저_id
    private Long id;

    //유저아이디
    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username;


    @Column(nullable = true, length = 200)
    private String token;


    @Builder
    public UserToken(String username, String token){
        this.username = username;
        this.token = token;
    }


}
