package com.shreeganesh.loan.app.customerServiceImplementation;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.shreeganesh.loan.app.customerEntities.CustomerDetails;
import com.shreeganesh.loan.app.customerEntities.EmiInstallment;
import com.shreeganesh.loan.app.customerEntities.Ledger;
import com.shreeganesh.loan.app.customerEnum.LedgerStatus;
import com.shreeganesh.loan.app.customerRepository.LedgerRepository;
import com.shreeganesh.loan.app.customerServiceInterfaces.LedgerService;
import com.shreeganesh.loan.app.customerUtil.MessagesMail;

@Service
public class LedgerServiceImpl implements LedgerService {
	@Autowired
	LedgerRepository ledgerRepository;
	
	@Value("${spring.mail.username}") // @Value is used for binding property to variable....
	private String fromEmail;
	
	@Autowired
	JavaMailSender sender;


	@Override
	public Ledger payMonthlyEmi(Integer customerId, String emiStatus) {
		Optional<CustomerDetails> optional = ledgerRepository.findById(customerId);
		if (optional.isPresent()) {
			CustomerDetails customerDetails = optional.get();
			if (emiStatus.equals(String.valueOf(LedgerStatus.emiPaid))) {
				customerDetails.getCustomerledger().setCurrentMonthEmiStatus(emiStatus);
				customerDetails.getCustomerledger().setTenure(customerDetails.getCustomerLoanTenureInMonth());
				customerDetails.getCustomerledger().setDefaulterCount(0);
				customerDetails.getCustomerledger().setLedgerCreatedDate(customerDetails.getCustomerloandisbursement().getAmountPaidDate()+"");;
				 Instant instant = customerDetails.getCustomerloandisbursement().getAmountPaidDate().toInstant();
				customerDetails.getCustomerledger().setLoanEndDate(instant.atZone(ZoneId.systemDefault()).toLocalDate().plusMonths(customerDetails.getCustomerledger().getTenure()));
				customerDetails.getCustomerledger().setMonthlyEMI(customerDetails.getCustomerSanctionLetter().getMonthlyEmiAmount());
				customerDetails.getCustomerledger().setNextEmiDatestart(LocalDate.now().plusMonths(1));
				customerDetails.getCustomerledger().setPayableAmountwithInterest(customerDetails.getCustomerSanctionLetter().getTotalLoanAmountWithInterest());
				customerDetails.getCustomerledger().setAmountPaidtillDate(customerDetails.getCustomerledger().getAmountPaidtillDate()+customerDetails.getCustomerledger().getMonthlyEMI());;
				customerDetails.getCustomerledger().setRemainingAmount(	customerDetails.getCustomerledger().getPayableAmountwithInterest()-customerDetails.getCustomerledger().getAmountPaidtillDate());
				
				if(customerDetails.getCustomerledger().getRemainingAmount()==0) {
					customerDetails.getCustomerledger().setLoanStatus(String.valueOf(LedgerStatus.Close));
				}else {
					customerDetails.getCustomerledger().setLoanStatus(String.valueOf(LedgerStatus.Active));
				}
				EmiInstallment emiInstallment=new EmiInstallment();
				emiInstallment.setEmiAmount(customerDetails.getCustomerSanctionLetter().getMonthlyEmiAmount());
				emiInstallment.setEmiStatus(emiStatus);
				emiInstallment.setInstallmentDate(LocalDate.now());
				
				customerDetails.getCustomerledger().getEmiInstallment().add(emiInstallment);
				
				 ledgerRepository.save(customerDetails);
				return customerDetails.getCustomerledger();
				
				
			}else if(emiStatus.equals(String.valueOf(LedgerStatus.emiNotPaid))){
				customerDetails.getCustomerledger().setCurrentMonthEmiStatus(emiStatus);
				customerDetails.getCustomerledger().setTenure(customerDetails.getCustomerLoanTenureInMonth());
				customerDetails.getCustomerledger().setLedgerCreatedDate(customerDetails.getCustomerloandisbursement().getAmountPaidDate()+"");;
				 Instant instant = customerDetails.getCustomerloandisbursement().getAmountPaidDate().toInstant();
				customerDetails.getCustomerledger().setLoanEndDate(instant.atZone(ZoneId.systemDefault()).toLocalDate().plusMonths(customerDetails.getCustomerledger().getTenure()));
				customerDetails.getCustomerledger().setMonthlyEMI(customerDetails.getCustomerSanctionLetter().getMonthlyEmiAmount());
				customerDetails.getCustomerledger().setNextEmiDatestart(LocalDate.now().plusMonths(1));
				
				customerDetails.getCustomerledger().setPayableAmountwithInterest(customerDetails.getCustomerSanctionLetter().getTotalLoanAmountWithInterest());
				customerDetails.getCustomerledger().setAmountPaidtillDate(customerDetails.getCustomerledger().getAmountPaidtillDate()+customerDetails.getCustomerledger().getMonthlyEMI());;
				customerDetails.getCustomerledger().setRemainingAmount(	customerDetails.getCustomerledger().getPayableAmountwithInterest()-customerDetails.getCustomerledger().getAmountPaidtillDate());
				
				EmiInstallment emiInstallment=new EmiInstallment();
				emiInstallment.setEmiAmount(0.0);
				emiInstallment.setEmiStatus(emiStatus);
				emiInstallment.setInstallmentDate(LocalDate.now());
				
				customerDetails.getCustomerledger().setDefaulterCount(customerDetails.getCustomerledger().getDefaulterCount()+1);
				
				if(customerDetails.getCustomerledger().getDefaulterCount()==1) {
					MimeMessage mimemessage = sender.createMimeMessage();
					try {
						MimeMessageHelper mimemessageHelper = new MimeMessageHelper(mimemessage, true);
						mimemessageHelper.setFrom(fromEmail);
						mimemessageHelper.setTo(customerDetails.getCustomerEmail());
						mimemessageHelper.setSubject("Reminder for your current month's installment");
						mimemessageHelper.setText(MessagesMail.firstInstallment);
						sender.send(mimemessage);
					} catch (Exception e) {

						e.printStackTrace();
					}
				}else if(customerDetails.getCustomerledger().getDefaulterCount()==2) {
					MimeMessage mimemessage = sender.createMimeMessage();
					try {
						MimeMessageHelper mimemessageHelper = new MimeMessageHelper(mimemessage, true);
						mimemessageHelper.setFrom(fromEmail);
						mimemessageHelper.setTo(customerDetails.getCustomerEmail());
						mimemessageHelper.setSubject(" Payment Reminder: Installment Pending");
						mimemessageHelper.setText(MessagesMail.secondInstallment);
						sender.send(mimemessage);
					} catch (Exception e) {

						e.printStackTrace();
					}
				}else if(customerDetails.getCustomerledger().getDefaulterCount()>=3) {
					MimeMessage mimemessage = sender.createMimeMessage();
					try {
						MimeMessageHelper mimemessageHelper = new MimeMessageHelper(mimemessage, true);
						mimemessageHelper.setFrom(fromEmail);
						mimemessageHelper.setTo(customerDetails.getCustomerEmail());
						mimemessageHelper.setSubject(" Action Taken Regarding Unpaid Loan Installment");
						mimemessageHelper.setText(MessagesMail.thirdInstallment);
						sender.send(mimemessage);
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
				
				customerDetails.getCustomerledger().getEmiInstallment().add(emiInstallment);
				
				 ledgerRepository.save(customerDetails);
				 
				 return customerDetails.getCustomerledger();
				 
				
			}
		} 
			return null;
	}

}
