package com.eduvault.backend.service;

import com.eduvault.backend.dto.response.FeeDto;
import com.eduvault.backend.model.Fee;
import com.eduvault.backend.model.Student;
import com.eduvault.backend.repository.FeeRepository;
import com.eduvault.backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeeService {

    private final FeeRepository feeRepository;
    private final StudentRepository studentRepository;

    private FeeDto toDto(Fee fee) {
        return new FeeDto(
                fee.getId(),
                fee.getStudent().getId(),
                fee.getStudent().getFirstName() + " " + fee.getStudent().getLastName(),
                fee.getAmount(),
                fee.getPeriodType().name(),
                fee.getPeriodStart(),
                fee.getPeriodEnd(),
                fee.getIsPaid(),
                fee.getPaidDate(),
                fee.getPaymentMethod(),
                fee.getNotes(),
                fee.getCreatedAt()
        );
    }

    @Transactional
    public List<FeeDto> getFeesByStudent(Long studentId) {
        return feeRepository.findByStudentId(studentId)
                .stream().map(this::toDto).toList();
    }

    @Transactional
    public List<FeeDto> getUnpaidFees() {
        return feeRepository.findByIsPaid(false)
                .stream().map(this::toDto).toList();
    }

    // create a fee record for a period (monthly/quarterly/yearly)
    @Transactional
    public FeeDto createFee(Long studentId, Double amount, String periodType,
                            LocalDate periodStart, LocalDate periodEnd, String notes) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Fee fee = new Fee();
        fee.setStudent(student);
        fee.setAmount(amount);
        fee.setPeriodType(Fee.PeriodType.valueOf(periodType));
        fee.setPeriodStart(periodStart);
        fee.setPeriodEnd(periodEnd);
        fee.setNotes(notes);
        fee.setIsPaid(false);

        return toDto(feeRepository.save(fee));
    }

    @Transactional
    public FeeDto markAsPaid(Long feeId, String paymentMethod) {
        Fee fee = feeRepository.findById(feeId)
                .orElseThrow(() -> new RuntimeException("Fee record not found"));
        fee.setIsPaid(true);
        fee.setPaidDate(LocalDate.now());
        fee.setPaymentMethod(paymentMethod);
        return toDto(feeRepository.save(fee));
    }

    // check if student's fee is paid for today
    @Transactional
    public boolean isFeePaidToday(Long studentId) {
        List<Fee> covering = feeRepository.findCoveringDate(studentId, LocalDate.now());
        return covering.stream().anyMatch(Fee::getIsPaid);
    }

    // calculate next due date based on latest period
    @Transactional
    public LocalDate getNextDueDate(Long studentId) {
        Fee latest = feeRepository.findLatestPeriodByStudent(studentId);
        if (latest == null) {
            return LocalDate.now(); // no payment history, due now
        }
        return latest.getPeriodEnd().plusDays(1);
    }

    public void deleteFee(Long feeId) {
        if (!feeRepository.existsById(feeId)) {
            throw new RuntimeException("Fee record not found");
        }
        feeRepository.deleteById(feeId);
    }

    public Double getTotalCollectedBetween(LocalDate start, LocalDate end) {
        Double total = feeRepository.getTotalCollectedBetween(start, end);
        return total != null ? total : 0.0;
    }

    public Double getTotalPending() {
        Double total = feeRepository.getTotalPending();
        return total != null ? total : 0.0;
    }

    public long getUnpaidCount() {
        return feeRepository.countByIsPaid(false);
    }
}