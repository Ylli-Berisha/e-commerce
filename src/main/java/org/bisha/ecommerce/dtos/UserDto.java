package org.bisha.ecommerce.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bisha.ecommerce.enums.Role;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Role is mandatory")
    private Role role;

    @NotNull(message = "Birth date is mandatory")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Profile picture URL is mandatory")
    private String profilePictureURL;

    @NotNull(message = "Created at date is mandatory")
    private LocalDateTime createdAt;

    @NotNull(message = "Telephone number is mandatory")
    private long telephoneNumber;
}