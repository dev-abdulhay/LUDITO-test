package org.ludito.app.rest.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ludito.app.config.exceptions.CustomException;
import org.ludito.app.config.utils.GlobalVar;
import org.ludito.app.rest.entity.auth.User;
import org.ludito.app.rest.entity.transaction.Operation;
import org.ludito.app.rest.entity.transaction.OperationConfig;
import org.ludito.app.rest.entity.wallet.Wallet;
import org.ludito.app.rest.enums.transaction.CurrencyEnum;
import org.ludito.app.rest.enums.transaction.OperationStatus;
import org.ludito.app.rest.enums.transaction.OperationType;
import org.ludito.app.rest.payload.req.transfer.ReqTransferExecute;
import org.ludito.app.rest.payload.req.transfer.ReqTransferFee;
import org.ludito.app.rest.payload.req.transfer.ReqTransferInit;
import org.ludito.app.rest.payload.res.transaction.ResTransferExecute;
import org.ludito.app.rest.payload.res.transaction.ResTransferFee;
import org.ludito.app.rest.payload.res.transaction.ResTransferInit;
import org.ludito.app.rest.service.OperationConfigService;
import org.ludito.app.rest.service.OperationService;
import org.ludito.app.rest.service.TransferService;
import org.ludito.app.rest.service.WalletService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final OperationConfigService operationConfigService;
    private final WalletService walletService;
    private final OperationService operationService;

    @Override
    public ResTransferFee feeTransfer(ReqTransferFee req) {

        Wallet debitWallet = walletService.getByUuid(req.getDebitAccountId());

        Wallet creditWallet;
        if (req.getCreditAccountId() != null) {
            creditWallet = walletService.getByUuid(req.getCreditAccountId());
        } else if (req.getCreditAccount() != null) {
            creditWallet = walletService.getByAccountNumber(req.getCreditAccount());
        } else {
            throw new CustomException("Credit account must be provided");
        }
        if (debitWallet.equals(creditWallet)) {
            throw new CustomException("Debit and credit accounts cannot be the same");
        }
        if(debitWallet.getCurrency() != creditWallet.getCurrency()){
            throw new CustomException("Debit and credit accounts must have the same currency");
        }

        Long commission = calculateCommission(req.getOperationType(), req.getAmount(), debitWallet.getCurrency());

        return new ResTransferFee(commission, req.getAmount(), req.getAmount() + commission);

    }

    @Override
    @Transactional
    public ResTransferInit initTransfer(ReqTransferInit req) {

        User user = GlobalVar.getAuthUser();

        Wallet debitWallet = walletService.getByUuid(req.getDebitAccountId());

        Wallet creditWallet;
        if (req.getCreditAccountId() != null) {
            creditWallet = walletService.getByUuid(req.getCreditAccountId());
        } else if (req.getCreditAccount() != null) {
            creditWallet = walletService.getByAccountNumber(req.getCreditAccount());
        } else {
            throw new CustomException("Credit account must be provided");
        }
        if (debitWallet.equals(creditWallet)) {
            throw new CustomException("Debit and credit accounts cannot be the same");
        }
        if(debitWallet.getCurrency() != creditWallet.getCurrency()){
            throw new CustomException("Debit and credit accounts must have the same currency");
        }

        Long commission = calculateCommission(req.getOperationType(), req.getAmount(), debitWallet.getCurrency());


        Operation operation = Operation.builder()
                .commissionAmount(commission)
                .debitAmount(req.getAmount() + commission)
                .creditAmount(req.getAmount())
                .debitWallet(debitWallet)
                .creditWallet(creditWallet)
                .type(req.getOperationType())
                .description(req.getDescription())
                .user(user)
                .status(OperationStatus.INIT)
                .build();

        operationService.save(operation);

        return new ResTransferInit(operation.getUuid());
    }

    @Override
    @Transactional
    public ResTransferExecute executeTransfer(ReqTransferExecute req) {
        Operation operation = operationService.findByUuidAndStatus(req.getTransferId(), OperationStatus.INIT);
        Wallet debitWallet = operation.getDebitWallet();
        Wallet creditWallet = operation.getCreditWallet();
        Long debitAmount = operation.getDebitAmount();
        Long creditAmount = operation.getCreditAmount();

        transferFunds(debitWallet, creditWallet, debitAmount, creditAmount);

        operation.setExecuteDate(LocalDateTime.now());
        operation.setStatus(OperationStatus.SUCCESS);

        return new ResTransferExecute("Transfer executed successfully");
    }


    private Long calculateCommission(OperationType operationType, Long amount, CurrencyEnum currencyFrom) {

        OperationConfig operationConfig = operationConfigService.getOperationConfig(operationType, currencyFrom);

        if (operationConfig.getMinAmount() > amount || operationConfig.getMaxAmount() < amount) {
            throw new CustomException("Amount is out of bounds for the operation type: " + operationType);
        }

        if (OperationType.OTHER2OWN.equals(operationType)) {
            throw new CustomException("Unsupported operation type: " + operationType);
        }

        return amount * operationConfig.getCommissionPercent() / 100;

    }

    private void transferFunds(Wallet debitWallet, Wallet creditWallet, Long debitAmount, Long creditAmount) {
        if (debitWallet.getBalance() < debitAmount) {
            throw new CustomException("Insufficient balance in debit wallet");
        }

        debitWallet.setBalance(debitWallet.getBalance() - debitAmount);
        creditWallet.setBalance(creditWallet.getBalance() + creditAmount);

        walletService.save(debitWallet);
        walletService.save(creditWallet);
    }

}
