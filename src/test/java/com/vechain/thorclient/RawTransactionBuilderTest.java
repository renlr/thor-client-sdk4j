package com.vechain.thorclient;

import com.vechain.thorclient.core.model.RawClause;
import com.vechain.thorclient.core.model.RawTransaction;
import com.vechain.thorclient.utils.BytesUtils;
import com.vechain.thorclient.utils.CryptoUtils;
import com.vechain.thorclient.utils.RawTransactionBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

/**
 * {"version":3,"id":"FEE947A3-785B-4839-A782-998E8F471DCC","crypto":{"ciphertext":"3b331bd66571d9e1ff4b7bbd10f68b0089301e0aae3c3507e44ce73588f049b4","cipherparams":{"iv":"d3f26875c39594d3c5fcb8c689e4a9a7"},"kdf":"scrypt","kdfparams":{"r":8,"p":1,"n":262144,"dklen":32,"salt":"ac901f3f5160c786ea0f5e760791e80f9fb4ddb7af2af69a697bcb61bc4dda13"},"mac":"c1444e90194d1d1357aa6d8d4f5754339c803c65867ef058e542a7118f219ca8","cipher":"aes-128-ctr"},"address":"c71adc46c5891a8963ea5a5eeaf578e0a2959779"}
 */

@RunWith(JUnit4.class)
public class RawTransactionBuilderTest  extends BaseTest {



    @Test
    public void testUpdateBuild() throws IOException {
        RawTransactionBuilder builder  = new RawTransactionBuilder();

        byte chainTag = blockchainAPI.getChainTag();
        int  n = chainTag & 0xFF;
        logger.info("Current chainTag:" + n);
        builder.update(Byte.valueOf(chainTag), "chainTag");

        byte[] expirationBytes = BytesUtils.integerToBytes(720);
        builder.update(expirationBytes, "expiration");

        byte[] blockRef = blockchainAPI.getBestBlockRef();
        builder.update(blockRef, "blockRef");

        byte[] nonce = CryptoUtils.generateTxNonce();
        builder.update(nonce , "nonce");

        byte[] gas = BytesUtils.integerToBytes(21000);
        builder.update(gas, "gas");


        RawClause clauses[] = new RawClause[1];
        clauses[0] = new RawClause();
        clauses[0].setTo(BytesUtils.toByteArray("0x42191bd624aBffFb1b65e92F1E51EB16f4d2A3Ce"));
        clauses[0].setValue(BytesUtils.defaultDecimalStringToByteArray("42.42"));
        builder.update(clauses);
        RawTransaction rawTxn = builder.build();
        byte tag = rawTxn.getChainTag();
        Assert.assertEquals("ChainTag is not equal.",chainTag, tag);

    }


}
