package com.library.management;

import com.library.management.dto.BorrowerDTO;
import com.library.management.exception.ConflictException;
import com.library.management.model.Borrower;
import com.library.management.repository.BorrowerRepository;
import com.library.management.service.impl.BorrowerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BorrowerServiceTest {

    @Mock
    private BorrowerRepository borrowerRepository;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private BorrowerServiceImpl borrowerService;

    private BorrowerDTO borrowerDTO;
    private Borrower borrower;

    @BeforeEach
    void setUp() {
        // Setup test data
        borrowerDTO = new BorrowerDTO();
        borrowerDTO.setName("John Doe");
        borrowerDTO.setEmail("john.doe@example.com");


        borrower = new Borrower();
        borrower.setId(1L);
        borrower.setName("John Doe");
        borrower.setEmail("john.doe@example.com");

    }

    @Test
    @DisplayName("Should add a new borrower successfully")
    void shouldAddBorrowerSuccessfully() throws ConflictException {
        // Given
        when(borrowerRepository.findByNameAndEmail(anyString(), anyString()))
                .thenReturn(Optional.empty());
        when(borrowerRepository.save(any(Borrower.class))).thenReturn(borrower);

        // When
        borrowerService.addBorrower(borrowerDTO);

        // Then
        verify(borrowerRepository, times(1)).findByNameAndEmail(
                borrowerDTO.getName(), borrowerDTO.getEmail());
        verify(borrowerRepository, times(1)).save(any(Borrower.class));
    }

    @Test
    @DisplayName("Should throw ConflictException when adding existing borrower")
    void shouldThrowConflictExceptionWhenAddingExistingBorrower() {
        // Given
        when(borrowerRepository.findByNameAndEmail(anyString(), anyString()))
                .thenReturn(Optional.of(borrower));

        // When & Then
        assertThrows(ConflictException.class, () -> borrowerService.addBorrower(borrowerDTO));
        verify(borrowerRepository, times(1)).findByNameAndEmail(
                borrowerDTO.getName(), borrowerDTO.getEmail());
        verify(borrowerRepository, never()).save(any(Borrower.class));
    }

    @Test
    @DisplayName("Should correctly map BorrowerDTO to Borrower entity")
    void shouldCorrectlyMapBorrowerDTOToBorrowerEntity() throws ConflictException {
        // Given
        when(borrowerRepository.findByNameAndEmail(anyString(), anyString()))
                .thenReturn(Optional.empty());

        // Use a spy to capture the saved entity
        doAnswer(invocation -> {
            Borrower savedBorrower = invocation.getArgument(0);

            // Verify all fields were mapped correctly
            assertEquals(borrowerDTO.getName(), savedBorrower.getName());
            assertEquals(borrowerDTO.getEmail(), savedBorrower.getEmail());


            return savedBorrower;
        }).when(borrowerRepository).save(any(Borrower.class));

        // When
        borrowerService.addBorrower(borrowerDTO);

        // Then
        verify(borrowerRepository, times(1)).save(any(Borrower.class));
    }

    @Test
    @DisplayName("Should handle null values in BorrowerDTO")
    void shouldHandleNullValuesInBorrowerDTO() throws ConflictException {
        // Given
        BorrowerDTO nullFieldsDTO = new BorrowerDTO();
        nullFieldsDTO.setName("Jane Doe");
        nullFieldsDTO.setEmail("jane@example.com");
        // Phone and address are null

        when(borrowerRepository.findByNameAndEmail(anyString(), anyString()))
                .thenReturn(Optional.empty());

        // When
        borrowerService.addBorrower(nullFieldsDTO);

        // Then
        verify(borrowerRepository, times(1)).findByNameAndEmail(
                nullFieldsDTO.getName(), nullFieldsDTO.getEmail());
        verify(borrowerRepository, times(1)).save(any(Borrower.class));
    }

    @Test
    @DisplayName("Should check for existing borrower using exact name and email")
    void shouldCheckForExistingBorrowerUsingExactNameAndEmail() throws ConflictException {
        // Given
        when(borrowerRepository.findByNameAndEmail(borrowerDTO.getName(), borrowerDTO.getEmail()))
                .thenReturn(Optional.empty());

        // Different case or whitespace shouldn't match the mock, so we set up a specific expectation
        lenient().when(borrowerRepository.findByNameAndEmail("john doe", borrowerDTO.getEmail()))
                .thenReturn(Optional.of(borrower));

        // When
        borrowerService.addBorrower(borrowerDTO);

        // Then
        verify(borrowerRepository, times(1)).findByNameAndEmail(
                borrowerDTO.getName(), borrowerDTO.getEmail());
        verify(borrowerRepository, never()).findByNameAndEmail(
                "john doe", borrowerDTO.getEmail());
        verify(borrowerRepository, times(1)).save(any(Borrower.class));
    }
}