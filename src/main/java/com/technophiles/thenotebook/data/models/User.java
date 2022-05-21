package com.technophiles.thenotebook.data.models;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Validated
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.diaries = new HashSet<>();
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Email
    @Column(unique = true)
    private String email;

    @Size(max = 40)
    private String username;

    private String password;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<Diary> diaries;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User(String email, String password, RoleType roleType) {
        this.email = email;
        this.password = password;
        if (roles == null){
            roles = new HashSet<>();
        }
        roles.add(new Role(roleType));
    }

    public void addRole(Role role){
        if (this.roles == null){
            this.roles = new HashSet<>();
        }
        roles.add(role);
    }

    @Override
    public String toString() {
        return String.format("id:%d, username:%s, email:%s", id, username, email);
    }

    public void addDiary(Diary diary){
        diaries.add(diary);
    }

    public void deleteDiary(Diary diary){
        diaries.remove(diary);
    }

    public void deleteAllDiaries(List<Diary> diariesList){
        diariesList.forEach(diaries::remove);
    }



//    public void deleteAllDiary(){
//        diaries.removeAll(diaries);
//    }
}
