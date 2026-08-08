package com.example.digital_payment.wallet.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.digital_payment.wallet.api.dto.WalletResponse;
import com.example.digital_payment.wallet.api.facade.WalletFacade;

@RestController
@RequestMapping("/wallets")
public class WalletController {
    private final WalletFacade walletFacade;

    public WalletController(WalletFacade walletFacade) {
        this.walletFacade = walletFacade;
    }

    @GetMapping("/me")
    public ResponseEntity<WalletResponse> ReadCurrentUserWallet() {
        return ResponseEntity.status(HttpStatus.OK).body(walletFacade.getCurrentUserWallet());
    }
}
