package ru.sejapoe.dz1.model;

import com.mongodb.client.model.changestream.OperationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document("operations")
public class Operation {
    @Id
    private String id;

    private EntityReference entityReference;
    private LocalDateTime time;
    private OperationType type;

    public interface EntityReference {
        String type();
    }

    public record SingleEntityReference(
            String type,
            Long id
    ) implements EntityReference {
    }

    public record ChunkEntitiesReference(
            String type,
            int count,
            int offset
    ) implements EntityReference {

    }

    public enum OperationType {
        WRITE, READ;
    }
}
