package com.warehousedata.warehousedata.service.Impl;

import com.warehousedata.warehousedata.dto.DealRequestDto;
import com.warehousedata.warehousedata.dto.DealResponseDto;
import com.warehousedata.warehousedata.entity.Deal;
import com.warehousedata.warehousedata.exception.DuplicateRequestException;
import com.warehousedata.warehousedata.repository.DealRepository;
import com.warehousedata.warehousedata.service.CurrencyValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DealServiceImplTest {

    @Mock
    private DealRepository dealRepository;

    @Mock
    private CurrencyValidator currencyValidator;

    @InjectMocks
    private DealServiceImpl dealService;

    private Deal savedDeal;
    private DealRequestDto dealRequestDto;

    @BeforeEach
    void setUp() {
        dealRequestDto = new DealRequestDto(
                "deal-123",
                "USD",
                "EUR",
                LocalDateTime.now(),
                new BigDecimal("100.00")
        );
        savedDeal = new Deal();
        savedDeal.setId(dealRequestDto.id());
        savedDeal.setFromCurrency(dealRequestDto.fromCurrency());
        savedDeal.setToCurrency(dealRequestDto.toCurrency());
        savedDeal.setDealTimestamp(dealRequestDto.dealTimeStamp());
        savedDeal.setDealAmount(dealRequestDto.dealAmount());
    }


    @Test
    void shouldSaveDealSuccessfully_whenDealDoesNotExistAndCurrencyValid() {
        when(dealRepository.existsById(dealRequestDto.id())).thenReturn(false);
        when(currencyValidator.validateCurrency(dealRequestDto.fromCurrency(), dealRequestDto.toCurrency())).thenReturn(true);
        when(dealRepository.save(any(Deal.class))).thenReturn(savedDeal);

        DealResponseDto responseDto = dealService.save(dealRequestDto);

        assertNotNull(responseDto);
        assertEquals(savedDeal.getId(), responseDto.id());
        assertEquals(savedDeal.getFromCurrency(), responseDto.fromCurrency());
        assertEquals(savedDeal.getToCurrency(), responseDto.toCurrency());
        assertEquals(savedDeal.getDealAmount(), responseDto.dealAmount());
        assertEquals(savedDeal.getDealTimestamp(), responseDto.dealTimeStamp());

        verify(dealRepository).existsById(dealRequestDto.id());
        verify(currencyValidator).validateCurrency(dealRequestDto.fromCurrency(), dealRequestDto.toCurrency());
        verify(dealRepository).save(any(Deal.class));
        verifyNoMoreInteractions(dealRepository, currencyValidator);
    }

    @Test
    void shouldThrowDuplicateRequestException_whenDealAlreadyExists() {
        when(dealRepository.existsById(dealRequestDto.id())).thenReturn(true);

        assertThrows(DuplicateRequestException.class, () -> dealService.save(dealRequestDto));

        verify(dealRepository).existsById(dealRequestDto.id());
        verify(dealRepository, never()).save(any());
        verify(currencyValidator, never()).validateCurrency(anyString(), anyString());
    }

    @Test
    void shouldReturnNull_whenCurrencyInvalid() {
        when(dealRepository.existsById(dealRequestDto.id())).thenReturn(false);
        when(currencyValidator.validateCurrency(dealRequestDto.fromCurrency(), dealRequestDto.toCurrency())).thenReturn(false);

        DealResponseDto result = dealService.save(dealRequestDto);

        assertNull(result);
        verify(dealRepository).existsById(dealRequestDto.id());
        verify(currencyValidator).validateCurrency(dealRequestDto.fromCurrency(), dealRequestDto.toCurrency());
        verify(dealRepository, never()).save(any());
    }


    @Test
    void shouldSaveMultipleValidDeals() {
        DealRequestDto deal1 = new DealRequestDto("deal-1", "USD", "EUR", LocalDateTime.now(), new BigDecimal("100"));
        DealRequestDto deal2 = new DealRequestDto("deal-2", "GBP", "JPY", LocalDateTime.now(), new BigDecimal("200"));

        when(currencyValidator.validateCurrency(anyString(), anyString())).thenReturn(true);
        when(dealRepository.existsById("deal-1")).thenReturn(false);
        when(dealRepository.existsById("deal-2")).thenReturn(false);

        Deal saved1 = new Deal();
        saved1.setId("deal-1");
        saved1.setFromCurrency("USD");
        saved1.setToCurrency("EUR");
        saved1.setDealAmount(new BigDecimal("100"));
        saved1.setDealTimestamp(deal1.dealTimeStamp());
        Deal saved2 = new Deal();
        saved2.setId("deal-2");
        saved2.setFromCurrency("GBP");
        saved2.setToCurrency("JPY");
        saved2.setDealAmount(new BigDecimal("200"));
        saved2.setDealTimestamp(deal2.dealTimeStamp());

        when(dealRepository.save(any())).thenReturn(saved1, saved2);

        List<DealResponseDto> results = dealService.saveDeals(List.of(deal1, deal2));

        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(d -> d.id().equals("deal-1")));
        assertTrue(results.stream().anyMatch(d -> d.id().equals("deal-2")));
    }

    @Test
    void shouldSkipInvalidDeals_whenSaveDeals() {
        DealRequestDto invalidDeal = new DealRequestDto("deal-3", "USD", "USD", LocalDateTime.now(), new BigDecimal("100"));
        DealRequestDto validDeal = new DealRequestDto("deal-4", "USD", "EUR", LocalDateTime.now(), new BigDecimal("100"));

        when(currencyValidator.validateCurrency("USD", "EUR")).thenReturn(true);
        when(currencyValidator.validateCurrency("USD", "USD")).thenReturn(false);
        when(dealRepository.existsById("deal-4")).thenReturn(false);

        Deal saved = new Deal();
        saved.setId("deal-4");
        saved.setFromCurrency("USD");
        saved.setToCurrency("EUR");
        saved.setDealAmount(new BigDecimal("100"));
        saved.setDealTimestamp(validDeal.dealTimeStamp());
        when(dealRepository.save(any())).thenReturn(saved);

        List<DealResponseDto> results = dealService.saveDeals(List.of(invalidDeal, validDeal));

        assertEquals(1, results.size());
        assertEquals("deal-4", results.get(0).id());
    }

    @Test
    void shouldReturnEmptyList_whenDealsNullOrEmpty() {
        assertTrue(dealService.saveDeals(null).isEmpty());
        assertTrue(dealService.saveDeals(List.of()).isEmpty());
    }

    @Test
    void shouldSkipDuplicateDeals_inSaveDeals() {
        DealRequestDto deal1 = new DealRequestDto("deal-1", "USD", "EUR", LocalDateTime.now(), new BigDecimal("100"));
        DealRequestDto deal2 = new DealRequestDto("deal-2", "GBP", "JPY", LocalDateTime.now(), new BigDecimal("200"));

        when(currencyValidator.validateCurrency(anyString(), anyString())).thenReturn(true);
        when(dealRepository.existsById("deal-1")).thenReturn(true);
        when(dealRepository.existsById("deal-2")).thenReturn(false);

        Deal saved = new Deal();
        saved.setId("deal-2");
        saved.setFromCurrency("GBP");
        saved.setToCurrency("JPY");
        saved.setDealAmount(new BigDecimal("200"));
        saved.setDealTimestamp(deal2.dealTimeStamp());
        when(dealRepository.save(any())).thenReturn(saved);

        List<DealResponseDto> results = dealService.saveDeals(List.of(deal1, deal2));

        assertEquals(1, results.size());
        assertEquals("deal-2", results.get(0).id());
    }
}
