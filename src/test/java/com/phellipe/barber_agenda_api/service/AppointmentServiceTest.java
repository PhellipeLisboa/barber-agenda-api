package com.phellipe.barber_agenda_api.service;

import com.phellipe.barber_agenda_api.dto.appointment.AppointmentResponseDto;
import com.phellipe.barber_agenda_api.exception.*;
import com.phellipe.barber_agenda_api.repository.AppointmentRepository;
import com.phellipe.barber_agenda_api.repository.UserRepository;
import com.phellipe.barber_agenda_api.testutil.AppointmentTestConstants;
import com.phellipe.barber_agenda_api.testutil.BusinessHourTestConstants;
import com.phellipe.barber_agenda_api.testutil.UserTestConstants;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private BusinessHourService businessHourService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthService authService;

    @Nested
    class save {

        @Test
        void save_shouldCreateAppointment_WhenSelfAppointmentAndValidDate() {

            // Mocks
            when(authService.getAuthenticatedUser()).thenReturn(UserTestConstants.CUSTOMER);
            when(userRepository.findById(UserTestConstants.CUSTOMER_ID)).thenReturn(Optional.of(UserTestConstants.CUSTOMER));
            when(userRepository.findById(UserTestConstants.PROFESSIONAL_ID)).thenReturn(Optional.of(UserTestConstants.PROFESSIONAL));
            when(appointmentRepository.findByAppointmentDateTime(any(LocalDateTime.class))).thenReturn(Optional.of(Collections.emptyList()));
            when(businessHourService.getHoursFor(any(LocalDate.class))).thenReturn(BusinessHourTestConstants.DEFAULT_BUSINESS_HOUR);
            when(appointmentRepository.save(any())).thenReturn(AppointmentTestConstants.APPOINTMENT);

            // Act
            AppointmentResponseDto response = appointmentService.save(AppointmentTestConstants.APPOINTMENT_DTO);

            // Assert
            assertNotNull(response);
            assertEquals(UserTestConstants.CUSTOMER_ID, response.customerId());
            assertEquals(UserTestConstants.PROFESSIONAL_ID, response.professionalId());
            assertEquals(AppointmentTestConstants.VALID_DATE, response.appointmentDateTime());
            verify(appointmentRepository).save(any());

        }

        @Test
        void save_shouldCreateAppointment_WhenAdminSchedulesForAnotherUser() {

            // Mocks
            when(userRepository.findById(UserTestConstants.PROFESSIONAL_ID)).thenReturn(Optional.of(UserTestConstants.PROFESSIONAL));
            when(authService.getAuthenticatedUser()).thenReturn(UserTestConstants.ADMIN);
            when(userRepository.findById(UserTestConstants.CUSTOMER_ID)).thenReturn(Optional.of(UserTestConstants.CUSTOMER));
            when(businessHourService.getHoursFor(any(LocalDate.class))).thenReturn(BusinessHourTestConstants.DEFAULT_BUSINESS_HOUR);
            when(appointmentRepository.findByAppointmentDateTime(any(LocalDateTime.class))).thenReturn(Optional.of(Collections.emptyList()));
            when(appointmentRepository.save(any())).thenReturn(AppointmentTestConstants.APPOINTMENT);

            // Act
            AppointmentResponseDto response = appointmentService.save(AppointmentTestConstants.APPOINTMENT_DTO);

            // Assert
            assertNotNull(response);
            assertEquals(UserTestConstants.CUSTOMER_ID, response.customerId());
            assertEquals(UserTestConstants.PROFESSIONAL_ID, response.professionalId());
            assertEquals(AppointmentTestConstants.VALID_DATE, response.appointmentDateTime());
            verify(appointmentRepository).save(any());

        }

        @Test
        void save_shouldCreateAppointment_WhenOwnerSchedulesForAnotherUser() {

            // Mocks
            when(userRepository.findById(UserTestConstants.PROFESSIONAL_ID)).thenReturn(Optional.of(UserTestConstants.PROFESSIONAL));
            when(authService.getAuthenticatedUser()).thenReturn(UserTestConstants.OWNER);
            when(userRepository.findById(UserTestConstants.CUSTOMER_ID)).thenReturn(Optional.of(UserTestConstants.CUSTOMER));
            when(businessHourService.getHoursFor(any(LocalDate.class))).thenReturn(BusinessHourTestConstants.DEFAULT_BUSINESS_HOUR);
            when(appointmentRepository.findByAppointmentDateTime(any(LocalDateTime.class))).thenReturn(Optional.of(Collections.emptyList()));
            when(appointmentRepository.save(any())).thenReturn(AppointmentTestConstants.APPOINTMENT);

            // Act
            AppointmentResponseDto response = appointmentService.save(AppointmentTestConstants.APPOINTMENT_DTO);

            // Assert
            assertNotNull(response);
            assertEquals(UserTestConstants.CUSTOMER_ID, response.customerId());
            assertEquals(UserTestConstants.PROFESSIONAL_ID, response.professionalId());
            assertEquals(AppointmentTestConstants.VALID_DATE, response.appointmentDateTime());
            verify(appointmentRepository).save(any());

        }

        @Test
        void save_shouldThrowUserNotFoundException_WhenProfessionalDoesNotExist() {

            // Mocks
            when(userRepository.findById(UserTestConstants.PROFESSIONAL_ID)).thenReturn(Optional.empty());

            // Act + Assert
            UserNotFoundException exception = assertThrows(
                    UserNotFoundException.class,
                    () -> appointmentService.save(AppointmentTestConstants.APPOINTMENT_DTO)
            );

            assertTrue(exception.getMessage().contains(UserTestConstants.PROFESSIONAL.getId().toString()));
            verify(appointmentRepository, never()).save(any());

        }

        @Test
        void save_shouldThrowRequiredRoleException_WhenProfessionalHasNoProfessionalRole() {

            // Mocks
            when(userRepository.findById(UserTestConstants.PROFESSIONAL_WITHOUT_ROLE_ID)).thenReturn(Optional.of(UserTestConstants.PROFESSIONAL_WITHOUT_ROLE));

            // Act + Assert
            assertThrows(
                    RequiredRoleException.class,
                    () -> appointmentService.save(AppointmentTestConstants.APPOINTMENT_PROFESSIONAL_WITHOUT_ROLE_DTO)
            );

            verify(appointmentRepository, never()).save(any());

        }

        @Test
        void save_shouldThrowUserNotFoundException_WhenCustomerDoesNotExist() {

            // Mocks
            when(userRepository.findById(UserTestConstants.PROFESSIONAL_ID)).thenReturn(Optional.of(UserTestConstants.PROFESSIONAL));
            when(userRepository.findById(UserTestConstants.CUSTOMER_ID)).thenReturn(Optional.empty());

            // Act + Assert
            UserNotFoundException exception = assertThrows(
                    UserNotFoundException.class,
                    () -> appointmentService.save(AppointmentTestConstants.APPOINTMENT_DTO)
            );

            assertTrue(exception.getMessage().contains(UserTestConstants.CUSTOMER.getId().toString()));
            verify(appointmentRepository, never()).save(any());

        }

        @Test
        void save_shouldThrowAccessDeniedException_WhenUserSchedulesForAnotherUser() {

            // Mocks
            when(userRepository.findById(UserTestConstants.PROFESSIONAL_ID)).thenReturn(Optional.of(UserTestConstants.PROFESSIONAL));
            when(userRepository.findById(UserTestConstants.CUSTOMER_ID)).thenReturn(Optional.of(UserTestConstants.CUSTOMER));
            when(authService.getAuthenticatedUser()).thenReturn(UserTestConstants.OTHER_CUSTOMER);

            // Act + Assert
            assertThrows(
                    AccessDeniedException.class,
                    () -> appointmentService.save(AppointmentTestConstants.APPOINTMENT_DTO)
            );

            verify(appointmentRepository, never()).save(any());

        }

        @Test
        void save_shouldThrowInvalidAppointmentDateTimeException_WhenAppointmentDateIsInThePast() {
            // Mocks
            when(authService.getAuthenticatedUser()).thenReturn(UserTestConstants.CUSTOMER);
            when(userRepository.findById(UserTestConstants.CUSTOMER_ID)).thenReturn(Optional.of(UserTestConstants.CUSTOMER));
            when(userRepository.findById(UserTestConstants.PROFESSIONAL_ID)).thenReturn(Optional.of(UserTestConstants.PROFESSIONAL));

            // Act + Assert
            assertThrows(
                    InvalidAppointmentDateTimeException.class,
                    () -> appointmentService.save(AppointmentTestConstants.APPOINTMENT_DATE_IN_THE_PAST_DTO)
            );

            verify(appointmentRepository, never()).save(any());
        }

        @Test
        void save_shouldThrowAppointmentOutOfBusinessHourException_WhenAppointmentDateTimeIsOutOfBusinessHour() {
            // Mocks
            when(authService.getAuthenticatedUser()).thenReturn(UserTestConstants.CUSTOMER);
            when(userRepository.findById(UserTestConstants.CUSTOMER_ID)).thenReturn(Optional.of(UserTestConstants.CUSTOMER));
            when(userRepository.findById(UserTestConstants.PROFESSIONAL_ID)).thenReturn(Optional.of(UserTestConstants.PROFESSIONAL));
            when(businessHourService.getHoursFor(any(LocalDate.class))).thenReturn(BusinessHourTestConstants.DEFAULT_BUSINESS_HOUR);

            // Act + Assert
            assertThrows(
                    AppointmentOutOfBusinessHourException.class,
                    () -> appointmentService.save(AppointmentTestConstants.APPOINTMENT_OUT_OF_BUSINESS_HOUR_DTO)
            );

            verify(appointmentRepository, never()).save(any());
        }

        @Test
        void save_shouldThrowAppointmentDateTimeAlreadyBookedException_WhenProfessionalAlreadyHasAnAppointmentAtTheDateTime() {
            // Mocks
            when(authService.getAuthenticatedUser()).thenReturn(UserTestConstants.CUSTOMER);
            when(userRepository.findById(UserTestConstants.CUSTOMER_ID)).thenReturn(Optional.of(UserTestConstants.CUSTOMER));
            when(userRepository.findById(UserTestConstants.PROFESSIONAL_ID)).thenReturn(Optional.of(UserTestConstants.PROFESSIONAL));
            when(businessHourService.getHoursFor(any(LocalDate.class))).thenReturn(BusinessHourTestConstants.DEFAULT_BUSINESS_HOUR);

            when(appointmentRepository.findByAppointmentDateTime(AppointmentTestConstants.VALID_DATE)).thenReturn(Optional.of(List.of(AppointmentTestConstants.APPOINTMENT)));

            // Act + Assert
            AppointmentDateTimeAlreadyBookedException exception = assertThrows(
                    AppointmentDateTimeAlreadyBookedException.class,
                    () -> appointmentService.save(AppointmentTestConstants.APPOINTMENT_DTO)
            );

            assertTrue(exception.getMessage().contains(UserTestConstants.PROFESSIONAL.getName()));
            verify(appointmentRepository, never()).save(any());
        }

        @Test
        void save_shouldThrowInvalidAppointmentDateTimeException_WhenCustomerAlreadyHasAnAppointmentAtTheDateTime() {
            // Mocks
            when(authService.getAuthenticatedUser()).thenReturn(UserTestConstants.CUSTOMER);
            when(userRepository.findById(UserTestConstants.CUSTOMER_ID)).thenReturn(Optional.of(UserTestConstants.CUSTOMER));
            when(userRepository.findById(UserTestConstants.PROFESSIONAL_ID)).thenReturn(Optional.of(UserTestConstants.PROFESSIONAL));
            when(businessHourService.getHoursFor(any(LocalDate.class))).thenReturn(BusinessHourTestConstants.DEFAULT_BUSINESS_HOUR);

            when(appointmentRepository.findByAppointmentDateTime(AppointmentTestConstants.VALID_DATE)).thenReturn(Optional.of(List.of(AppointmentTestConstants.APPOINTMENT_WITH_OTHER_PROFESSIONAL)));

            // Act + Assert
            InvalidAppointmentDateTimeException exception = assertThrows(
                    InvalidAppointmentDateTimeException.class,
                    () -> appointmentService.save(AppointmentTestConstants.APPOINTMENT_DTO)
            );

            assertTrue(exception.getMessage().contains(UserTestConstants.CUSTOMER.getName()));
            verify(appointmentRepository, never()).save(any());
        }

    }

    @Nested
    class findAll {

        @Test
        void findAll_shouldReturnCustomerAppointments_WhenUserIsOnlyUser() {

            // Mocks
            when(authService.getAuthenticatedUser()).thenReturn(UserTestConstants.CUSTOMER);
            when(appointmentRepository.findByCustomerId(UserTestConstants.CUSTOMER_ID)).thenReturn(List.of(AppointmentTestConstants.APPOINTMENT));

            // Act
            List<AppointmentResponseDto> response = appointmentService.findAll();

            // Assert
            assertEquals(1, response.size());
            assertEquals(UserTestConstants.CUSTOMER_ID, response.getFirst().customerId());
            verify(appointmentRepository).findByCustomerId(UserTestConstants.CUSTOMER_ID);
            verify(appointmentRepository, never()).findByProfessionalId(any());
            verify(appointmentRepository, never()).findAll();

        }

        @Test
        void findAll_shouldReturnProfessionalAppointments_WhenIsProfessional() {

            // Mocks
            when(authService.getAuthenticatedUser()).thenReturn(UserTestConstants.PROFESSIONAL);
            when(appointmentRepository.findByProfessionalId(UserTestConstants.PROFESSIONAL_ID)).thenReturn(List.of(AppointmentTestConstants.APPOINTMENT));

            // Act
            List<AppointmentResponseDto> response = appointmentService.findAll();

            // Assert
            assertEquals(1, response.size());
            assertEquals(UserTestConstants.PROFESSIONAL_ID, response.getFirst().professionalId());
            verify(appointmentRepository).findByProfessionalId(UserTestConstants.PROFESSIONAL_ID);
            verify(appointmentRepository, never()).findByCustomerId(any());
            verify(appointmentRepository, never()).findAll();

        }

        @Test
        void findAll_shouldReturnAllAppointments_WhenUserIsAdminOrOwner() {

            // Mocks
            when(authService.getAuthenticatedUser()).thenReturn(UserTestConstants.OWNER);
            when(appointmentRepository.findAll()).thenReturn(List.of(
                    AppointmentTestConstants.APPOINTMENT,
                    AppointmentTestConstants.APPOINTMENT_WITH_OTHER_PROFESSIONAL
            ));

            // Act
            List<AppointmentResponseDto> response = appointmentService.findAll();

            // Assert
            assertEquals(2, response.size());

            assertTrue(
                    response
                            .stream()
                            .anyMatch(appointment -> appointment.professionalId().equals(UserTestConstants.PROFESSIONAL_ID))
            );

            assertTrue(
                    response
                            .stream()
                            .anyMatch(appointment -> appointment.professionalId().equals(UserTestConstants.OTHER_PROFESSIONAL_ID))
            );

            verify(appointmentRepository, never()).findByCustomerId(any());
            verify(appointmentRepository, never()).findByProfessionalId(any());
            verify(appointmentRepository).findAll();

        }

    }

    @Nested
    class findById {

        @Test
        void findById_ShouldReturnAppointmentResponseDto_WhenIdExists() {

            // Mocks
            when(appointmentRepository.findById(AppointmentTestConstants.APPOINTMENT_ID)).thenReturn(Optional.of(AppointmentTestConstants.APPOINTMENT));

            // Act
            AppointmentResponseDto response = appointmentService.findById(AppointmentTestConstants.APPOINTMENT_ID);

            // Assert
            assertNotNull(response);
            assertEquals(AppointmentTestConstants.APPOINTMENT_ID, response.id());
            assertEquals(AppointmentTestConstants.APPOINTMENT.getCustomerId(), response.customerId());
            assertEquals(AppointmentTestConstants.APPOINTMENT.getProfessionalId(), response.professionalId());
            assertEquals(AppointmentTestConstants.APPOINTMENT.getAppointmentDateTime(), response.appointmentDateTime());
            verify(appointmentRepository).findById(AppointmentTestConstants.APPOINTMENT_ID);
        }

        @Test
        void findById_ShouldThrowResourceNotFoundException_WhenIdNotExists() {

            // Mocks
            when(appointmentRepository.findById(AppointmentTestConstants.APPOINTMENT_ID)).thenReturn(Optional.empty());

            // Act
            ResourceNotFoundException exception = assertThrows(
                    ResourceNotFoundException.class,
                    () -> appointmentService.findById(AppointmentTestConstants.APPOINTMENT_ID)
            );

            // Assert
            assertTrue(exception.getMessage().contains("appointment"));
            assertTrue(exception.getMessage().contains(AppointmentTestConstants.APPOINTMENT_ID.toString()));
            verify(appointmentRepository).findById(AppointmentTestConstants.APPOINTMENT_ID);

        }

    }

}