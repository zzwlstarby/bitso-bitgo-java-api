package com.bitso.bitgo.v2;

import com.bitso.bitgo.v2.entity.SendCoinsResponse;
import com.bitso.bitgo.v2.entity.Wallet;
import com.bitso.bitgo.v2.entity.WalletTransferResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This interface defines the behavior of the BitGo client.
 *
 * @author Enrique Zamudio
 * Date: 5/8/17 4:46 PM
 */
public interface BitGoClient {

    Optional<String> login(String email, String password, String otp, boolean extensible)
            throws IOException;

    /**
     * List all wallets for the user.  see https://www.bitgo.com/api/v2/?shell#list-wallets
     */
    List<Wallet> listWallets(String coin) throws IOException;

    /**
     * Get the wallet with specified ID. see https://www.bitgo.com/api/v2/?shell#get-wallet
     */
    Optional<Wallet> getWallet(String coin, String wid) throws IOException;

    /**
     * Get the wallet by providing an address and currency.
     * See https://www.bitgo.com/api/v2/?shell#operation/v2.wallet.getwalletbyaddress
     */
    Optional<Wallet> getWalletByAddress(String coin, String waddress) throws IOException;

    /**
     * Invokes the sendmany method see https://www.bitgo.com/api/v2/?shell#send-transaction-to-many
     * Required parameters are:
     * coin                        tbtc for test bitcoin, see full list at https://www.bitgo.com/api/v2/#coin-digital-currency-support
     * walletId                    The ID of the source wallet.
     * walletPass                  The wallet passphrase.
     * recipients                  A map with the recipients' addresses as keys and the corresponding
     *                                    amounts as values. Amounts are in satoshis
     * sequenceId                  A unique identifier for this transaction (optional).
     *
     * Optional parameters are:
     * message                     Notes about the transaction (optional).
     * fee                         Fee (in satoshis), leave null for autodetect. Do not specify unless you are sure it is sufficient.
     * feeTxConfirmTarget          Calculate fees per kilobyte, targeting transaction confirmation in this number of blocks. Default: 2, Minimum: 2, Maximum: 20
     * minConfirms                 only choose unspent inputs with a certain number of confirmations. We recommend setting this to 1 and using enforceMinConfirmsForChange
     * enforceMinConfirmsForChange Defaults to false. When constructing a transaction, minConfirms will only be enforced for unspents not originating from the wallet
     * @return A SendCoinsResponse, or empty if there was a problem (although more likely in case of a problem it will throw).
     */
    Optional<SendCoinsResponse> sendMany(Map<String, Object> parameters) throws IOException;

    Optional<Map<String, Object>> getCurrentUserProfile() throws IOException;

    /**
     * See https://www.bitgo.com/api/v2/#get-wallet-transfer
     *
     * @param coin
     * @param walletId
     * @param walletTransferId
     * @return
     * @throws IOException
     */
    Optional<Map<String, Object>> getWalletTransferId(String coin, String walletId, String walletTransferId) throws IOException;

    /**
     * See https://www.bitgo.com/api/v2/?shell#get-wallet-transfer-by-sequence-id
     *
     * @param coin
     * @param walletId
     * @param sequenceId
     * @throws IOException
     */
    Optional<Map<String, Object>> getWalletTransferSeqId(String coin, String walletId, String sequenceId) throws IOException;

    /**
     * See https://www.bitgo.com/api/v2/#list-wallet-transactions
     *
     * @param coin
     * @param walletId
     * @param nextBatchPrevId Optional
     * @param limit
     * @return
     * @throws IOException
     */
    WalletTransferResponse listWalletTransfers(String coin, String walletId, String nextBatchPrevId, int limit) throws IOException;

    /**
     * See https://www.bitgo.com/api/v2/?shell#unlock
     * <p>
     * Body Parameters
     *
     * @param otp      An OTP code for the account obtained using the second factor authentication device
     * @param duration Optional - Desired duration of the unlock in seconds (default=600, max=3600)
     * @return Errors WalletTransferResponse Description:
     * 400 Bad Request	The request parameters were missing or incorrect
     * 401 Unauthorized	The authentication parameters did not match, or OTP code was incorrect
     * @throws IOException
     */
    int unlock(String otp, Long duration) throws IOException;



}
