package org.example.web.dto;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {
    @Test
    public void lombok_test() {
//given
        String name = "boki62";
        String email = "boki62@test";

//when
        HelloResponseDto dto = new HelloResponseDto(name, email);

//then
        assertThat(dto.getName()).isEqualTo(name); //
        assertThat(dto.getEmail()).isEqualTo(email); //
    }
}