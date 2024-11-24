package services;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.ParticipantRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class ParticipantServiceTest {

  @Mock
  private ParticipantRepository repository;

  @InjectMocks
  private ParticipantService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetById() {
    // Given
    Participant participant = new Participant();
    participant.setParticipantId(1L);
    given(repository.getById(1L)).willReturn(participant);

    // When
    Participant result = service.getById(1L);

    // Then
    assertNotNull(result);
    assertEquals(1L, result.getParticipantId());
    then(repository).should(times(1)).getById(1L);
  }

  @Test
  void testGetAll() {
    // Given
    List<Participant> participants = List.of(new Participant(), new Participant());
    given(repository.findAll()).willReturn(participants);

    // When
    List<Participant> result = service.getAll();

    // Then
    assertNotNull(result);
    assertEquals(2, result.size());
    then(repository).should(times(1)).findAll();
  }

  @Test
  void testCreate() {
    // Given
    Participant participant = new Participant();
    given(repository.save(participant)).willReturn(participant);

    // When
    Participant result = service.create(participant);

    // Then
    assertNotNull(result);
    then(repository).should(times(1)).save(participant);
  }

  @Test
  void testUpdateExistingParticipant() {
    // Given
    Participant participant = new Participant();
    given(repository.findById(1L)).willReturn(Optional.of(new Participant()));
    given(repository.save(participant)).willReturn(participant);

    // When
    Participant result = service.update(1L, participant);

    // Then
    assertNotNull(result);
    assertEquals(1L, result.getParticipantId());
    then(repository).should(times(1)).findById(1L);
    then(repository).should(times(1)).save(participant);
  }

  @Test
  void testUpdateNonExistingParticipant() {
    // Given
    Participant participant = new Participant();
    given(repository.findById(1L)).willReturn(Optional.empty());

    // When
    Participant result = service.update(1L, participant);

    // Then
    assertNull(result);
    then(repository).should(times(1)).findById(1L);
    then(repository).shouldHaveNoMoreInteractions();
  }

  @Test
  void testDeleteExistingParticipant() {
    // Given
    given(repository.findById(1L)).willReturn(Optional.of(new Participant()));

    // When
    int result = service.delete(1L);

    // Then
    assertEquals(1, result);
    then(repository).should(times(1)).findById(1L);
    then(repository).should(times(1)).deleteById(1L);
  }

  @Test
  void testDeleteNonExistingParticipant() {
    // Given
    given(repository.findById(1L)).willReturn(Optional.empty());

    // When
    int result = service.delete(1L);

    // Then
    assertEquals(0, result);
    then(repository).should(times(1)).findById(1L);
    then(repository).shouldHaveNoMoreInteractions();
  }
}