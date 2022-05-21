package com.technophiles.thenotebook.data.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Validated
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties("user")
@Table(name = "diary")
public class Diary {

    public Diary(String title) {
        this.title = title;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Size(max = 15)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(nullable = false)
    @NotBlank
    @NotNull
    private String title;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "diary",
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Entry> entries;

    @CreationTimestamp
    private LocalDateTime creationTime;

    @Override
    public String toString() {
        return String.format("id:%d, title:%s", id, title);
    }
}
