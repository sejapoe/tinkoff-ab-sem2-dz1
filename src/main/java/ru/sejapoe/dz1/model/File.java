package ru.sejapoe.dz1.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "files")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {
    @Id
    @GeneratedValue(generator = "files_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "files_id_seq", sequenceName = "files_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "stored_name")
    private String storedName;
}
