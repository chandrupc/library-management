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

/**
 * Unit tests for {@link BorrowerServiceImpl}, which handles borrower registration logic.
 * <p>
 * This class ensures proper validation, conflict detection, and DTO-to-entity mapping for borrower entities.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @since 2025-05-19
 */
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

    /**
     * Setup method executed before each test.
     */
    @BeforeEach
    void setUp() {
        borrowerDTO = new BorrowerDTO();
        borrowerDTO.setName("John Doe");
        borrowerDTO.setEmail("john.doe@example.com");

        borrower = new Borrower();
        borrower.setId(1L);
        borrower.setName("John Doe");
        borrower.setEmail("john.doe@example.com");
    }

    /**
     * Verifies that a new borrower is added successfully when no conflict exists.
     */
    @Test
    @DisplayName("Should add a new borrower successfully")
    void shouldAddBorrowerSuccessfully() throws ConflictException {
        when(borrowerRepository.findByNameAndEmail(anyString(), anyString()))
                .thenReturn(Optional.empty());
        when(borrowerRepository.save(any(Borrower.class))).thenReturn(borrower);

        borrowerService.addBorrower(borrowerDTO);

        verify(borrowerRepository, times(1)).findByNameAndEmail(
                borrowerDTO.getName(), borrowerDTO.getEmail());
        verify(borrowerRepository, times(1)).save(any(Borrower.class));
    }

    /**
     * Verifies that a {@link ConflictException} is thrown when a borrower already exists.
     */
    @Test
    @DisplayName("Should throw ConflictException when adding existing borrower")
    void shouldThrowConflictExceptionWhenAddingExistingBorrower() {
        when(borrowerRepository.findByNameAndEmail(anyString(), anyString()))
                .thenReturn(Optional.of(borrower));

        assertThrows(ConflictException.class, () -> borrowerService.addBorrower(borrowerDTO));

        verify(borrowerRepository, times(1)).findByNameAndEmail(
                borrowerDTO.getName(), borrowerDTO.getEmail());
        verify(borrowerRepository, never()).save(any(Borrower.class));
    }

    /**
     * Tests that the {@link ModelMapper} correctly maps DTO fields to the Borrower entity.
     */
    @Test
    @DisplayName("Should correctly map BorrowerDTO to Borrower entity")
    void shouldCorrectlyMapBorrowerDTOToBorrowerEntity() throws ConflictException {
        when(borrowerRepository.findByNameAndEmail(anyString(), anyString()))
                .thenReturn(Optional.empty());

        doAnswer(invocation -> {
            Borrower savedBorrower = invocation.getArgument(0);
            assertEquals(borrowerDTO.getName(), savedBorrower.getName());
            assertEquals(borrowerDTO.getEmail(), savedBorrower.getEmail());
            return savedBorrower;
        }).when(borrowerRepository).save(any(Borrower.class));

        borrowerService.addBorrower(borrowerDTO);

        verify(borrowerRepository, times(1)).save(any(Borrower.class));
    }

    /**
     * Tests that the system can handle null fields in the DTO gracefully.
     */
    @Test
    @DisplayName("Should handle null values in BorrowerDTO")
    void shouldHandleNullValuesInBorrowerDTO() throws ConflictException {
        BorrowerDTO nullFieldsDTO = new BorrowerDTO();
        nullFieldsDTO.setName("Jane Doe");
        nullFieldsDTO.setEmail("jane@example.com");
        // Phone and address left null intentionally

        when(borrowerRepository.findByNameAndEmail(anyString(), anyString()))
                .thenReturn(Optional.empty());

        borrowerService.addBorrower(nullFieldsDTO);

        verify(borrowerRepository, times(1)).findByNameAndEmail(
                nullFieldsDTO.getName(), nullFieldsDTO.getEmail());
        verify(borrowerRepository, times(1)).save(any(Borrower.class));
    }

    /**
     * Verifies the repository checks for an exact name and email match during existence validation.
     */
    @Test
    @DisplayName("Should check for existing borrower using exact name and email")
    void shouldCheckForExistingBorrowerUsingExactNameAndEmail() throws ConflictException {
        when(borrowerRepository.findByNameAndEmail(borrowerDTO.getName(), borrowerDTO.getEmail()))
                .thenReturn(Optional.empty());

        lenient().when(borrowerRepository.findByNameAndEmail("john doe", borrowerDTO.getEmail()))
                .thenReturn(Optional.of(borrower)); // Case variation for leniency

        borrowerService.addBorrower(borrowerDTO);

        verify(borrowerRepository, times(1)).findByNameAndEmail(
                borrowerDTO.getName(), borrowerDTO.getEmail());
        verify(borrowerRepository, never()).findByNameAndEmail(
                "john doe", borrowerDTO.getEmail());
        verify(borrowerRepository, times(1)).save(any(Borrower.class));
    }
}
