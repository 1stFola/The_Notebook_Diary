package com.technophiles.thenotebook.data.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "entry")
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Size(max = 15)
    @Column(name = "id", nullable = false)
    private Long id;

    @CreationTimestamp
    private LocalDateTime entryTime;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedAt;

    @Column(nullable = false)
    private String entryNote;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    public Entry(String entryNote) {
        this.entryNote = entryNote;
    }
}
